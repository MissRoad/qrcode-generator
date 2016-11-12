package com.sylvanas.qrcode.service.impl;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sylvanas.qrcode.model.LogoConfig;
import com.sylvanas.qrcode.service.QRCodeService;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成服务
 * <p>
 * Created by sylvanasp on 2016/11/11.
 */
public class QRCodeServiceImpl implements QRCodeService {

    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

    /**
     * 生成一个带LOGO的二维码
     *
     * @return 对应二维码的imageBase64字符串
     */
    @Override
    public String getLogoQRCode(String url, HttpServletRequest request, String productName) {
        // 获得Logo图片的路径
        String filePath = request.getSession().getServletContext()
                .getRealPath("/") + "resources/images/logoImages/logo.png";
        // 获得二维码文件保存的路径
        String imagePath = request.getSession().getServletContext()
                .getRealPath("/") + "resources/images/qrcodeImages/";
        try {
            BufferedImage bim = this.getQRCodeBufferedImage(url, BarcodeFormat.QR_CODE, 400, 400,
                    this.getDecodeHintType());
            return this.addLogoQRCode(bim, new File(filePath), new LogoConfig(), productName, imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成一个不带Logo的简单二维码
     *
     * @return 返回二维码文件全名
     */
    @Override
    public String createSimpleQRCode(String url, HttpServletRequest request) {
        //设置二维码文件保存的路径
        String imagePath = request.getSession().getServletContext()
                .getRealPath("/") + "resources/images/qrcodeImages/";

        int width = 344;
        int height = 344;
        String format = "png";

        //设置二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE
                    , width, height, hints);

            //拼接输出路径
            String fileName = "QRCode_" + new Date().getTime() + ".png";
            Path outPath = new File(imagePath + fileName).toPath();
            //执行生成
            MatrixToImageWriter.writeToPath(bitMatrix, format, outPath);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析二维码
     */
    @Override
    public Result readQRCode(String filePath) {
        try {
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            BinaryBitmap binaryBitmap =
                    new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

            Map<DecodeHintType,Object> hints = new HashMap<DecodeHintType,Object>();
            hints.put(DecodeHintType.CHARACTER_SET,"utf-8");

            MultiFormatReader multiFormatReader = new MultiFormatReader();
            return multiFormatReader.decode(binaryBitmap,hints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给二维码添加LOGO
     */
    private String addLogoQRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String productName,
                                 String imagePath) {
        try {
            // 读取二维码图片,并构建绘图对象
            BufferedImage image = bim;
            Graphics2D graphics = image.createGraphics();

            //读取logo图片
            BufferedImage logo = ImageIO.read(logoPic);
            //设置logo大小,以下设置为二维码图片的20%
            int logoWidth = logo.getWidth(null) >
                    image.getWidth() * 3 / 10 ? (image.getWidth() * 3 / 10) : logo.getWidth(null);
            int logoHeight = logo.getHeight(null) >
                    image.getHeight() * 3 / 10 ? (image.getHeight() * 3 / 10) : logo.getHeight(null);

            //将logo放置在中心
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;

            //绘制图形
            graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
            graphics.dispose();

            //添加商品名称,最多支持两行,太长会自动截取
            if (productName != null && !"".equals(productName.trim())) {
                //新图片,添加上文字
                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outG = outImage.createGraphics();
                //绘制图形
                outG.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                outG.setColor(Color.BLACK);
                //设置字体、字型、字号
                outG.setFont(new Font("宋体", Font.BOLD, 30));
                int strWidth = outG.getFontMetrics().stringWidth(productName);
                //长度过长则进行换行处理
                if (strWidth > 399) {
                    String productName1 = productName.substring(0, productName.length() / 2);
                    String productName2 = productName.substring(productName.length() / 2, productName.length());
                    int strWidth1 = outG.getFontMetrics().stringWidth(productName1);
                    int strWidth2 = outG.getFontMetrics().stringWidth(productName2);
                    outG.drawString(productName1, 200 - strWidth1 / 2,
                            image.getHeight() + (outImage.getHeight() - image.getHeight()) / 2 + 12);

                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outG2 = outImage2.createGraphics();
                    outG2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                    outG2.setColor(Color.BLACK);
                    outG2.setFont(new Font("宋体", Font.BOLD, 30));
                    outG2.drawString(productName2, 200 - strWidth2 / 2,
                            outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight() / 2 + 5));
                    outG2.dispose();
                    outImage2.flush();
                    outImage = outImage2;
                } else {
                    outG.drawString(productName, 200 - strWidth / 2,
                            image.getHeight() + (outImage.getHeight() - image.getHeight() / 2 + 12));
                }
                outG.dispose();
                outImage.flush();
                image = outImage;
            }
            logo.flush();
            image.flush();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.flush();
            ImageIO.write(image, "png", baos);
            /**
             * 二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
             * 可以看到这个方法最终返回的是这个二维码的imageBase64字符串
             * 前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>
             * 其中${imageBase64QRCode}对应二维码的imageBase64字符串
             */
//            ImageIO.write(image, "png", new File("D:/test/QRCODE/QRCode-" + new Date().getTime() + ".png"));
            ImageIO.write(image, "png", new File(imagePath + "QRCode_" + new Date().getTime() + ".png"));
            //将ByteArray转换为Base64字符串
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String imageBase64QRCode = base64Encoder.encode(baos.toByteArray());
            baos.close();
            return imageBase64QRCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建初始化二维码
     */
    private BufferedImage fileToBufferedImage(BitMatrix bm) {
        if (bm == null) {
            throw new IllegalArgumentException("参数BitMatrix为null");
        }

        int width = bm.getWidth();
        int height = bm.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, bm.get(i, j) ? 0xFF000000 : 0xFFCCDDEE);
            }
        }

        return image;
    }

    /**
     * 生成二维码BufferedImage图片
     *
     * @param content       内容
     * @param barcodeFormat 编码类型
     * @param width         宽度
     * @param height        高度
     * @param hints         设置参数
     * @return BufferedImage
     */
    private BufferedImage getQRCodeBufferedImage(String content, BarcodeFormat barcodeFormat,
                                                 int width, int height, Map<EncodeHintType, ?> hints)
            throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix bitMatrix = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
        int w = bitMatrix.getWidth();
        int h = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        //利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                image.setRGB(i, j, bitMatrix.get(i, j) ? QRCOLOR : BGWHITE);
            }
        }
        return image;
    }

    /**
     * 设置二维码格式参数
     */
    private Map<EncodeHintType, Object> getDecodeHintType() {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }

}

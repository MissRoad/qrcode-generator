package com.sylvanas.qrcode.model;

import java.awt.*;

/**
 * QRCode Logo图片数据模型
 * <p>
 * Created by sylvanasp on 2016/11/11.
 */
public class LogoConfig {

    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR,DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor,int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public int getBorder() {
        return border;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getLogoPart() {
        return logoPart;
    }
}

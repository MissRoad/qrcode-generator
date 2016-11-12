package com.sylvanas.qrcode.service;

import com.google.zxing.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 二维码生成服务接口
 *
 * Created by sylvanasp on 2016/11/11.
 */
public interface QRCodeService {

    /** 生成带logo图片的二维码 **/
    public String getLogoQRCode(String url, HttpServletRequest request, String productName);

    /** 生成一个不带Logo图片的简单二维码 **/
    public String createSimpleQRCode(String url,HttpServletRequest request);

    /** 解析二维码 **/
    public Result readQRCode(String filePath);
}

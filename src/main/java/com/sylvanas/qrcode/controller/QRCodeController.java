package com.sylvanas.qrcode.controller;

import com.sylvanas.qrcode.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 二维码相关Controller
 *
 * Created by sylvanasp on 2016/11/14.
 */
@Controller
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping(value = "/qrcodeGenerate",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public String qrcodeGenerate(String qrcodeContent, HttpServletRequest request) {
        return this.qrCodeService.createSimpleQRCode(qrcodeContent,request);
    }

}

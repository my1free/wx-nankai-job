package com.nankai.wx.job.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/3
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Controller
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}

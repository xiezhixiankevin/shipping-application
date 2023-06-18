package com.xzx.shippingapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <Description> IndexController
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.controller
 */
@Controller
public class IndexController {



    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }
}

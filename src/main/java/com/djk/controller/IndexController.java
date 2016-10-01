package com.djk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dujinkai on 16/10/1.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

}

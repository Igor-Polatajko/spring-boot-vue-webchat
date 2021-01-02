package com.ihorpolataiko.springbootvuewebchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/username")
public class UsernameController {

    @PostMapping
    public String setUsername(@ModelAttribute("username") String username, HttpServletRequest servletRequest) {
        servletRequest.getSession().setAttribute("username", username);
        return "redirect:/chat";
    }

}

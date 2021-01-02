package com.ihorpolataiko.springbootvuewebchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static com.ihorpolataiko.springbootvuewebchat.util.Constants.USERNAME;
import static java.util.Objects.isNull;

@Controller
public class WebController {

    @GetMapping("/chat")
    public String getChatPage(HttpServletRequest servletRequest) {

        if (isNull(servletRequest.getSession().getAttribute(USERNAME))) {
            return "redirect:/";
        }

        return "chat";
    }

    @PostMapping("/username")
    public String setUsername(@ModelAttribute("username") String username, HttpServletRequest servletRequest) {
        servletRequest.getSession().setAttribute(USERNAME, username);
        return "redirect:/chat";
    }

}

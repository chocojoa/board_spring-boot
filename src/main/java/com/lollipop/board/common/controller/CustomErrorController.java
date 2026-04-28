package com.lollipop.board.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = {"/", "/error", "/{path:[^.]*}"})
    public String path(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/rest")) {
            return "forward:/error";
        }
        return "forward:/index.html";
    }
}

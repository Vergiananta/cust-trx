package com.enigma.testing.controller;

import com.enigma.testing.exceptions.PathNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    @GetMapping("/error")
    public void handleError() {
        throw new PathNotFoundException();
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}

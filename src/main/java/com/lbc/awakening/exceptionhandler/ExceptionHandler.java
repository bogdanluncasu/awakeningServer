package com.lbc.awakening.exceptionhandler;

import com.lbc.awakening.exception.InvalidUserException;
import com.lbc.awakening.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandler implements ErrorController {


    private SecurityService<String> securityResponseService;
    @Autowired
    public ExceptionHandler(SecurityService<String> securityResponseService){
        this.securityResponseService = securityResponseService;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidUserException.class)
    public void handleInvalidUserException(
            InvalidUserException exception, HttpServletResponse response) throws IOException {
        response.setStatus(400);
        String encryptedResponse = this.securityResponseService.encrypt(exception.getMessage());
        response.getWriter().println(encryptedResponse);
    }

}

package com.volyVary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.volyVary.service.TransformationService;

@RequestMapping("/transformation")
@Controller
public class TransformationController {
    @Autowired
    private TransformationService transformationService;

    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }
}

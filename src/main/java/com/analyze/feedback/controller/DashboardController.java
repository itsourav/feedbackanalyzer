package com.analyze.feedback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.feedback.model.DashboardResponse;

@RestController
public class DashboardController {
    
     @GetMapping("/api/dashboard")
     public DashboardResponse getDashboardData() {


        return null;
     }
}

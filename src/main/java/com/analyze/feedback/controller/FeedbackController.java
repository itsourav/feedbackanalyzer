package com.analyze.feedback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.feedback.model.AtomicFeedback;
import com.analyze.feedback.model.FeedbackDTO;
import com.analyze.feedback.model.UserFeedback;
import com.analyze.feedback.services.FeedbackProcessor;

@RestController
public class FeedbackController {
    
    @Autowired
    private FeedbackProcessor feedbackProcessor;

    @PostMapping("/api/feedbackprocessor")
    public List<AtomicFeedback>  submitFeedback(@RequestBody FeedbackDTO feedbackDTO) throws Exception {

        System.out.println("Received feedback: " + feedbackDTO.getFeedback());

         UserFeedback processedFeedback = feedbackProcessor.processFeedback(feedbackDTO);

         return processedFeedback.getAtomicFeedbacks();
    }

}

package com.analyze.feedback.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.analyze.feedback.ai.FeedbackAnalyserAIService;
import com.analyze.feedback.ai.FeedbackSplitterAIService;
import com.analyze.feedback.model.AtomicFeedback;
import com.analyze.feedback.model.FeedbackDTO;
import com.analyze.feedback.model.UserFeedback;
import com.analyze.feedback.repository.UserFeedbackDao;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;


@Service
@Transactional
public class FeedbackProcessor {
    
    ChatLanguageModel chatLanguageModel;

    UserFeedbackDao userFeedbackDao;

    FeedbackEmbeddingStore feedbackEmbeddingStore;


    FeedbackProcessor(ChatLanguageModel chatLanguageModel,UserFeedbackDao userFeedbackDao,FeedbackEmbeddingStore feedbackEmbeddingStore){
        this.chatLanguageModel= chatLanguageModel;
       this.userFeedbackDao = userFeedbackDao;
       this.feedbackEmbeddingStore = feedbackEmbeddingStore;
    }

    public UserFeedback processFeedback(FeedbackDTO dto) throws Exception {
       
        UserFeedback feedback = new UserFeedback(
                calculateBirthYear(dto.getAge()),
                dto.getCountry(),
                dto.getGender(),
                dto.getFeedback()
        );

        System.out.println("*********************************************************************************");
        System.out.println("****************  START PROCESSING  *********************************************");
        System.out.println("*********************************************************************************");
        System.out.println("Feedback received: " + feedback);
        // Call the feedbackAnalyzer to populate atomicFeedbackList

          FeedbackSplitterAIService splitter =
                AiServices.create(FeedbackSplitterAIService.class, chatLanguageModel);        

        List<String> coherentFeedbackParts = splitter.generateAtomicFeedbackComponents(feedback.getFeedback());        

        FeedbackAnalyserAIService analyzer =
                AiServices.create(FeedbackAnalyserAIService.class, chatLanguageModel);

        for (String coherentFeedbackPart : coherentFeedbackParts) {
            // TODO this should no
            if (coherentFeedbackPart.contains("You must put every item on a separate line")) {
                continue;
            }

             try {
                AtomicFeedback atomicFeedback = analyzer.generateAtomicFeedbackComponents("User Feedback: " + coherentFeedbackPart + "\n");
                feedback.addAtomicFeedback(atomicFeedback);
            } catch (Exception e) {
                System.out.println("Error while processing feedback: " + e.getMessage());
                e.printStackTrace();
                // TODO add the feedback part nonetheless even if analysis failed
            }
        }

        System.out.println("Feedback analyzed: " + feedback);

        // Persist feedback to the database
        userFeedbackDao.persistFeedback(feedback);

        // Add feedback + embedding to the embedding store
        feedbackEmbeddingStore.addFeedback(feedback);

        return feedback;
    }


     private static int calculateBirthYear(int age) {
        return LocalDate.now().getYear() - age;
    }
}

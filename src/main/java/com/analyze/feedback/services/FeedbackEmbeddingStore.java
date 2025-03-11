package com.analyze.feedback.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.analyze.feedback.model.AtomicFeedback;
import com.analyze.feedback.model.Category;
import com.analyze.feedback.model.UserFeedback;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;

@Service
public class FeedbackEmbeddingStore {
    
    EmbeddingStore<TextSegment> embeddingStore;
    EmbeddingModel embeddingModel;

    public FeedbackEmbeddingStore(EmbeddingStore<TextSegment> embeddingStore,EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

     public void addFeedback(UserFeedback feedback) {
        if (feedback == null) {
            return;
        }

         for (AtomicFeedback atomicFeedback : feedback.getAtomicFeedbacks()) {
                Map<String, String> metaInfo = new HashMap<>();
                metaInfo.put("percentage_of_people_affected", String.valueOf(atomicFeedback.impact));
                metaInfo.put("severity_in_percent", String.valueOf(atomicFeedback.severity));
                metaInfo.put("urgency_in_percent", String.valueOf(atomicFeedback.urgency));
                metaInfo.put("feedback_type", atomicFeedback.feedbackType.toString());
                // TODO will be List again after bugfix
                metaInfo.put("feedback_categories_commaseparated", Arrays.asList(atomicFeedback.category).stream().map(Category::name).collect(Collectors.joining(", ")));
                metaInfo.put("birthyear", String.valueOf(feedback.birthYear));
                metaInfo.put("gender", feedback.gender);
                metaInfo.put("nationality", feedback.nationality);

                TextSegment segment = TextSegment.from(atomicFeedback.feedback, Metadata.from(metaInfo));
                Embedding embedding = embeddingModel.embed(segment).content();
                embeddingStore.add(embedding, segment);
        }



     }



}



package com.analyze.feedback.ai;

import java.util.List;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FeedbackSplitterAIService {
    @SystemMessage("""
            Split the user feedback into coherent parts of literal user text that treat a similar topic, that can then be addressed separately.
            If the user treats the same topic in multiple parts of the feedback, make sure to group them together. If the feedback is about only one topic, return only one item.
            """)
    public List<String> generateAtomicFeedbackComponents(String feedback);
}

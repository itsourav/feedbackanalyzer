package com.analyze.feedback.model;

import java.util.List;


import dev.langchain4j.model.output.structured.Description;


public class AtomicFeedback {
    
      // TODO create the AI solution generator part
    @Description("to be left empty")
    public List<String> solutions;
    // TODO make it List<Category> again after bugfix, and rename to categories
    @Description("the most relevant category for this part of feedback")
    public Category category;
    @Description("best applicable feedback category")
    public FeedbackType feedbackType;
    @Description("number: % of urgency, ranging from 0 (no urgency whatsoever) to 100 (needs fixing in this hour). should be " +
            "0 for positive feedback")
    public int urgency;
    @Description("number, % of severity, ranging from 0 (no problem whatsoever) to 100 (person(s) will die if this is not " +
            "fixed). should be 0 for positive feedback")
    public int severity;
    @Description("number, % of people in the scope that are estimated to be affected by this same problem or solution.")
    public int impact;
    @Description("the literal part(s) of the original UserFeedback that is relevant for this AtomicFeedback part. Use the literal words of the user")
    public String feedback;

    public String prettyPrint() {
        return "\nAtomicFeedback{" +
                "\n\tsolutions=" + solutions +
                ", \n\tcategories=" + category +
                ", \n\tfeedbackType=" + feedbackType +
                ", \n\turgency=" + urgency +
                ", \n\tseverity=" + severity +
                ", \n\timpact=" + impact +
                ", \n\tfeedback='" + feedback + '\'' +
                "'\n}'";
    }
}

package com.analyze.feedback.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.analyze.feedback.model.AnalysisValue;
import com.analyze.feedback.model.AtomicFeedback;
import com.analyze.feedback.model.DashboardResponse;
import com.analyze.feedback.repository.DashboardDao;

@Service
public class DashboardService {

    DashboardDao dashboardDao;

    

    public DashboardService(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }



    public DashboardResponse getDashboardData(DashboardDao dashboardDao){

         List<AnalysisValue> analysis = createAnalysisValues();    

        List<AtomicFeedback> feedbacks = dashboardDao.fetchFeedbacks();

        DashboardResponse response = new DashboardResponse(analysis, feedbacks);

        return response;
    }



    private List<AnalysisValue> createAnalysisValues() {
    
        Integer sevHigh, sevMid, sevLow, urgHigh, urgMid, urgLow, impHigh, impMid, impLow;
        sevHigh = dashboardDao.loadIntHigh("severity");
        sevMid = dashboardDao.loadIntMid("severity");
        sevLow = dashboardDao.loadIntLow("severity");
        urgHigh = dashboardDao.loadIntHigh("urgency");
        urgMid = dashboardDao.loadIntMid("urgency");
        urgLow = dashboardDao.loadIntLow("urgency");
        impHigh = dashboardDao.loadIntHigh("impact");
        impMid = dashboardDao.loadIntMid("impact");
        impLow = dashboardDao.loadIntLow("impact");
    
         Map<String, Integer> categoryCounts = dashboardDao.getCategoryCounts();

         List<AnalysisValue> analysis = List.of(
            new AnalysisValue("categories", categoryCounts),
            new AnalysisValue("feedbacktypes", dashboardDao.getFeedbackTypeCounts()),
            new AnalysisValue("severity", Map.of("high", sevHigh, "medium", sevMid, "low", sevLow)),
            new AnalysisValue("urgency", Map.of("high", urgHigh, "medium", urgMid, "low", urgLow)),
            new AnalysisValue("impact", Map.of("high", impHigh, "medium", impMid, "low", impLow))
    );
    
      return analysis;
    }


}

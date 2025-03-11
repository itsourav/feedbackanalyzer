package com.analyze.feedback.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.analyze.feedback.model.AtomicFeedback;

@Repository
public class DashboardDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

   
  public  List<AtomicFeedback> fetchFeedbacks(){



    return null;
  }


public Integer loadIntHigh(String attribute) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadIntHigh'");
}


public Integer loadIntMid(String string) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadIntMid'");
}


public Integer loadIntLow(String string) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadIntLow'");
}


public Map<String, Integer> getCategoryCounts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCategoryCounts'");
}


public Map<String, Integer> getFeedbackTypeCounts() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getFeedbackTypeCounts'");
}


}

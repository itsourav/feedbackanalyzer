package com.analyze.feedback.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.analyze.feedback.model.AtomicFeedback;
import com.analyze.feedback.model.Category;
import com.analyze.feedback.model.UserFeedback;

@Repository
public class UserFeedbackDao {

    // SQL statement for inserting UserFeedback
   final String insertUserFeedbackSQL = "INSERT INTO UserFeedback(birthYear, nationality, gender, feedback) VALUES(?,?,?,?)";

    // SQL statement for inserting AtomicFeedback
    final String insertAtomicFeedbackSQL = "INSERT INTO AtomicFeedback(userFeedbackId, feedback_type, urgency, severity, impact, feedback) VALUES(?,?,?,?,?,?)";

    final String countCategoryID = "SELECT count(*) FROM Category WHERE name = ?";
    final String retrieveCategoryID = "SELECT ID FROM Category WHERE NAME = ?";
    final String insertAtomicFeedbackCategorySQL = "INSERT INTO AtomicFeedback_Category (atomicFeedbackId, categoryId) VALUES (?, ?);";
    

  @Autowired
  private JdbcTemplate jdbcTemplate;


  @SuppressWarnings("deprecation")
  public void persistFeedback(UserFeedback feedback) throws SQLException {

    KeyHolder keyHolder = new GeneratedKeyHolder();
    int userFeedbackId;
    
    jdbcTemplate.update(connection -> {
        PreparedStatement pstmt = connection
          .prepareStatement(insertUserFeedbackSQL, Statement.RETURN_GENERATED_KEYS);
          pstmt.setInt(1, feedback.getBirthYear());
          pstmt.setString(2, feedback.getNationality());
          pstmt.setString(3, feedback.getGender());
          pstmt.setString(4, feedback.getFeedback());
          return pstmt;
        }, keyHolder);
        
    userFeedbackId = (int) keyHolder.getKeys().get("Id");
    System.out.println("Auto generate key ::"+ keyHolder.getKeys().get("Id"));
  
    KeyHolder keyHolderAfb = new GeneratedKeyHolder();

    for (AtomicFeedback atomicFeedback : feedback.getAtomicFeedbacks()) {
                
      int atomicFeedbackId;
                
      int affectedRows = jdbcTemplate.update(connection -> {
                    PreparedStatement pstmt = connection
                    .prepareStatement(insertAtomicFeedbackSQL, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setLong(1, userFeedbackId);
                    pstmt.setString(2, atomicFeedback.feedbackType.toString());
                    pstmt.setInt(3, atomicFeedback.urgency);
                    pstmt.setInt(4, atomicFeedback.severity);
                    pstmt.setInt(5, atomicFeedback.impact);
                    pstmt.setString(6, atomicFeedback.feedback);
                    return pstmt;
        }, keyHolderAfb);    
        
         if (affectedRows == 0) {
                        throw new SQLException("Creating atomic feedback failed, no rows affected.");
          }

        atomicFeedbackId = (int) keyHolderAfb.getKeys().get("Id");

        // // Insert the category or get its ID if it already exists
             
             for (Category categoryName : Arrays.asList(atomicFeedback.category)) {
              int categoryId = -1;
              System.out.println("categoryName :"+categoryName.toString());
                    
              int count = jdbcTemplate.queryForObject(countCategoryID,
                                                Integer.class,                                           
                                                              categoryName.toString() 
                                                      );

                  if(count>0){

                    categoryId = (int) jdbcTemplate.queryForObject(
                      retrieveCategoryID, new Object[] { categoryName.toString() }, Integer.class);
                 
                  System.out.println("Category found with name :"+categoryName+ " having categoryId :"+categoryId);
                 
                  }
                  
                  jdbcTemplate.update(insertAtomicFeedbackCategorySQL,atomicFeedbackId,categoryId);

                  System.out.println("Inserted AtomicFeedback link to Catergory: " + affectedRows + " for category " + categoryName.toString() + " with ID " + categoryId);
                  
                  }
 
    }
  }


}

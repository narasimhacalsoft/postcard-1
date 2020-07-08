package com.postcard.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardDao;
import com.postcard.model.Postcard;

@Repository
public class PostcardDaoImpl extends BaseDao implements PostcardDao {

    @Override
    public void createPostcard(Postcard postcard) {
        String sqlQuery = "insert into postcard(orderId,cardId,cardKey,recipientJson,submissionStatus,response,attempts,cardStatus) " +
                "values (?,?,?,?,?,?,?,?)";
        // hardcoded for testing
        update(sqlQuery, 500, 1000, "card-key", "recipient", "submit status", "response", 1, "created");
        // mainJdbcTemplate.update(sqlQuery, postcard.getcardId(),
        // postcard.getcardId(),postcard.getcardKey(),postcard.getrecipientJson(),postcard.getsubmissionStatus(),postcard.getResponse(),postcard.getAttempts(),postcard.getcardStatus());
    }

    @Override
    public void updatePostcard(Postcard postcard) {
        String sqlQuery = "update postcard set recipientJson =? ,submissionStatus=? ,response=?,attempts=?,cardStatus=?  " +
                "where cardKey = ?";
        //hardcoded for testing
        update(sqlQuery, "recipient","submit status","response",  1, "update",  "card-key");
        //mainJdbcTemplate.update(sqlQuery, postcard.getrecipientJson(),postcard.getsubmissionStatus(),postcard.getResponse(),postcard.getAttempts(),postcard.getcardStatus());
        }
    
    @Override
    public List<Postcard> findallPostcard() {
        String sqlQuery = "select * from postcard";
        List<Postcard> objects = query(sqlQuery, (rs,rowNum)->{
            Postcard obj = new Postcard();
            obj.setCardId(rs.getInt("cardId"));
            obj.setOrderId(rs.getInt("orderId"));
            obj.setCardKey(rs.getString("cardKey"));
            obj.setRecipientJson(rs.getString("recipientJson"));
            obj.setSubmissionStatus(rs.getString("submissionStatus"));
            obj.setResponse(rs.getString("response"));
            obj.setAttempts(rs.getInt("attempts"));
            obj.setCardStatus(rs.getString("cardStatus"));
            return obj;
            });
            return objects;
       }
    
    @Override
    public Postcard findOne(Long cardId) {
        
        String sql = "SELECT * FROM Postcard WHERE cardId = ?";
        Postcard postcard =  queryForObject(sql, Postcard.class,1000);
        return postcard;
    
    }
    
    @Override
    public void deletePostcard(Postcard postcard) {
        String sqlQuery = "delete from Postcard where cardId = ?";
        //update(sqlQuery, postcard.getcardId());
        //hardcoded for testing
        update(sqlQuery, 1000);
     }
    
    

}

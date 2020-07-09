package com.postcard.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.PostcardOrderDao;
import com.postcard.model.PostcardOrder;

@Repository
public class PostcardOrderDaoImpl extends BaseDao implements PostcardOrderDao {
    
    @Value("${createPostcardOrderQuery}")
    private String createPostcardOrderQuery;
    
    @Value("${updatePostcardOrderQuery}")
    private String updatePostcardOrderQuery;
    
    @Value("${findOnePostcardOrderQuery}")
    private String findOnePostcardOrderQuery;
    
    @Value("${findallPostcardOrderQuery}")
    private String findallPostcardOrderQuery;
    
    @Value("${deletePostcardOrderQuery}")
    private String deletePostcardOrderQuery;
    
    @Override
    public void createPostcardOrder(PostcardOrder postcardOrder) {
        // hardcoded for testing
        update(createPostcardOrderQuery, "sender text", "sender json", "branding text", "branding json");
    }

    @Override
    public void updatePostcardOrder(PostcardOrder postcardOrder) {
        //hardcoded for testing
        update(updatePostcardOrderQuery, "sender text", "sender json", "branding text", "branding json", 2000);
        //mainJdbcTemplate.update(sqlQuery, postcardOrder.getOrderId(), postcardOrder.getSenderText(), postcardOrder.getSenderJson(), postcardOrder.getBrandingText(), postcardOrder.getBrandingJson());
        }

    @Override
    public PostcardOrder findOne(Long orderId) {
        PostcardOrder postcardOrder =  queryForObject(findOnePostcardOrderQuery, PostcardOrder.class,1000);
        return postcardOrder;
    
    }

    @Override
    public void deletePostcardOrder(PostcardOrder postcardOrder) {
        //hardcoded for testing
        update(deletePostcardOrderQuery, 1000);
     }
    
    @Override
    public List<PostcardOrder> findallPostcardOrder() {
        List<PostcardOrder> objects = query(findallPostcardOrderQuery, (rs,rowNum)->{
            PostcardOrder obj = new PostcardOrder();
            obj.setOrderId(rs.getInt("orderId"));
            obj.setSenderText(rs.getString("senderText"));
            obj.setSenderJson(rs.getString("senderJson"));
            obj.setBrandingText(rs.getString("brandingText"));
            obj.setBrandingJson(rs.getString("brandingJson"));
            return obj;
            });
            return objects;
       }
    



    
    

}

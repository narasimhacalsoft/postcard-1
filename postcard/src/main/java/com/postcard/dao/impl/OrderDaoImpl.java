package com.postcard.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.postcard.dao.BaseDao;
import com.postcard.dao.OrderDao;
import com.postcard.dao.PostcardDao;
import com.postcard.model.Order;
import com.postcard.model.Postcard;

@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao {
    
    @Override
    public void createOrder(Order order) {
        /*String sqlQuery = "insert into order(orderId,senderText,senderJson,brandingText,brandingJson) values (?,?,?,?,?)";
        // hardcoded for testing
        update(sqlQuery, 5000, "sender text", "sender json", "branding text", "branding json");*/
        String sqlQuery = "insert into postcard(orderId,cardId,cardKey,recipientJson,submissionStatus,response,attempts,cardStatus) " +
                "values (?,?,?,?,?,?,?,?)";
        // hardcoded for testing
        update(sqlQuery, 500, 1000, "card-key", "recipient", "submit status", "response", 1, "created");
        //update(sqlQuery, order.getOrderId(), order.getSenderText(), order.getSenderJson(), order.getBrandingText(), order.getBrandingJson());
    }

    @Override
    public void updateOrder(Order order) {
        String sqlQuery = "update postcard set senderText =? ,senderJson=? ,brandingText=?,brandingJson=?  " +
                "where orderId = ?";
        //hardcoded for testing
        update(sqlQuery, "sender text", "sender json", "branding text", "branding json", 2000);
        //mainJdbcTemplate.update(sqlQuery, order.getOrderId(), order.getSenderText(), order.getSenderJson(), order.getBrandingText(), order.getBrandingJson());
        }

    @Override
    public Order findOne(Long orderId) {
        
        String sql = "SELECT * FROM Postcard WHERE orderId = ?";
        Order order =  queryForObject(sql, Order.class,1000);
        return order;
    
    }

    @Override
    public void deletePostcard(Order order) {
        String sqlQuery = "delete from Postcard where orderId = ?";
        //update(sqlQuery, order.getOrderId());
        //hardcoded for testing
        update(sqlQuery, 1000);
     }
    
    @Override
    public List<Order> findallOrder() {
        String sqlQuery = "select * from postcard";
        List<Order> objects = query(sqlQuery, (rs,rowNum)->{
            Order obj = new Order();
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

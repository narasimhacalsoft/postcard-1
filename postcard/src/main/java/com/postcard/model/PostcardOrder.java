package com.postcard.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostcardOrder {    
    private Integer orderId;
    private Integer imageId;
    private String senderJson;
    private String brandingText;
    private String brandingJson;
    private String senderText;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp updatedDate;
    private String updatedBy;
    private String orderStatus;
    
    private List<Postcard> postcards;
  
    
}

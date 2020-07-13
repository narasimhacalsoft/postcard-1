package com.postcard.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Postcard implements Serializable{
    
    private static final long serialVersionUID = -5306300141442579255L;
    private Integer orderId;
    private Integer cardId;
    private String cardKey;
    private String recipientJson;
    private String submissionStatus;
    private String response;
    private Integer attempts;
    private String cardStatus;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp updatedDate;
    private String updatedBy;
    
}

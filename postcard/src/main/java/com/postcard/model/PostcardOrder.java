package com.postcard.model;

import lombok.Data;

@Data
public class PostcardOrder {    
    private Integer orderId;
    private String senderJson;
    private String brandingText;
    private String brandingJson;
    private String senderText;
}

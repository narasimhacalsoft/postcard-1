package com.postcard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PostcardCarouselResponse {    
    private Integer orderId;
    private Integer imageId;
    private String orderStatus;
    private String createdDate;
    private String numberOfRecipients;
    
}

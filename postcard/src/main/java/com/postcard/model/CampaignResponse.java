package com.postcard.model;

import lombok.Data;

@Data
public class CampaignResponse {
	
	private String campaignKey;
	private Integer quota;
	private Integer sendPostcards;
	private Integer freeToSendPostcards;

}

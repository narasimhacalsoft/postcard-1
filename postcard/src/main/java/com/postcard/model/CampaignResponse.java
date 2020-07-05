package com.postcard.model;

import java.util.List;

import lombok.Data;

@Data
public class CampaignResponse {
	
	private String campaignKey;
	private int quota;
	private int sendPostcards;
	private int freeToSendPostcards;

}

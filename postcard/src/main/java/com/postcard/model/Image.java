package com.postcard.model;

import java.sql.Blob;

import lombok.Data;

@Data
public class Image {    
    private Integer imageId;
    private Byte[] image;
    private String imageType;
}

package com.example.elsa.imagesearching.base;

public interface IBaseConstant {
    String baseUrl = "https://api.imgur.com";
    //Request timeout time
    int httpTime = 30000;

    /**
     * Get
     */
    //Image Searching
    String GetImageSearching = "/3/gallery/search/1?";
}

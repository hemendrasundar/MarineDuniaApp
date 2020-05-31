
package com.sun.marinedunia.Models;

import com.google.gson.annotations.SerializedName;


public class NewsItem {

    @SerializedName("Imageurl")
    private String mImageurl;
    @SerializedName("Newsurl")
    private String mNewsurl;
    @SerializedName("Title")
    private String mTitle;

    public String getImageurl() {
        return mImageurl;
    }

    public void setImageurl(String imageurl) {
        mImageurl = imageurl;
    }

    public String getNewsurl() {
        return mNewsurl;
    }

    public void setNewsurl(String newsurl) {
        mNewsurl = newsurl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}

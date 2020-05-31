
package com.sun.marinedunia.Models;

import java.util.List;

import com.google.gson.annotations.SerializedName;



public class News {

    @SerializedName("items")
    private List<NewsItem> mItems;

    public List<NewsItem> getItems() {
        return mItems;
    }

    public void setItems(List<NewsItem> items) {
        mItems = items;
    }

}

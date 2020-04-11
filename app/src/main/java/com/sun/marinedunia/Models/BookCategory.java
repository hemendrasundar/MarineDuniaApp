
package com.sun.marinedunia.Models;


import com.google.gson.annotations.SerializedName;


public class BookCategory {

    @SerializedName("CatName")
    private String mCatName;
    @SerializedName("CatUrl")
    private String mCatUrl;

    public String getCatName() {
        return mCatName;
    }

    public void setCatName(String catName) {
        mCatName = catName;
    }

    public String getCatUrl() {
        return mCatUrl;
    }

    public void setCatUrl(String catUrl) {
        mCatUrl = catUrl;
    }

}

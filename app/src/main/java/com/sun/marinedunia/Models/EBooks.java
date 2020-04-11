
package com.sun.marinedunia.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class EBooks {

    @SerializedName("items")
    private List<Ebook> mEbooks;

    public List<Ebook> getItems() {
        return mEbooks;
    }

    public void setItems(List<Ebook> ebooks) {
        mEbooks = ebooks;
    }

}

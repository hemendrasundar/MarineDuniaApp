
package com.sun.marinedunia.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BookCategories {

    @SerializedName("items")
    private List<BookCategory> mBookCategories;

    public List<BookCategory> getItems() {
        return mBookCategories;
    }

    public void setItems(List<BookCategory> bookCategories) {
        mBookCategories = bookCategories;
    }

}

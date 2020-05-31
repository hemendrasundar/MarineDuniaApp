
package com.sun.marinedunia.Models;


import com.google.gson.annotations.SerializedName;

import java.util.Comparator;


public class BookCategory  {

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

    public static Comparator<BookCategory> CategorynameComparator = new Comparator<BookCategory>() {

        public int compare(BookCategory BC1, BookCategory BC2) {
            String Category1 = BC1.getCatName().toUpperCase();
            String Category2 = BC2.getCatName().toUpperCase();

            //ascending order
            return Category1.compareTo(Category2);

            }};

}

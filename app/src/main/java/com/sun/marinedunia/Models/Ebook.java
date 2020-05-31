
package com.sun.marinedunia.Models;


import com.google.gson.annotations.SerializedName;

import java.util.Comparator;


public class Ebook {

    @SerializedName("brand")
    private String mBrand;
    @SerializedName("Category")
    private String mCategory;
    @SerializedName("itemName")
    private String mItemName;



    @SerializedName("Thumbnail")
    private String Thumbnail;

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setmCategory(String id) {
        mCategory = id;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }
    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public static Comparator<Ebook> ebookComparator = new Comparator<Ebook>() {

        public int compare(Ebook Book1, Ebook Book2) {
            String Category1 = Book1.getItemName().toUpperCase();
            String Category2 = Book2.getItemName().toUpperCase();

            //ascending order
            return Category1.compareTo(Category2);

        }};

}

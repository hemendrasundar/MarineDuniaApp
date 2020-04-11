package com.sun.marinedunia.Models;

public class QuizCategoryModel {
    private String Name,Url;
    private int Sets;

    public QuizCategoryModel()
    {
        //for firbase
    }
    public QuizCategoryModel(String name, String url, int Sets) {
        Name = name;
        Url = url;
        this.Sets = Sets;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getSets() {
        return Sets;
    }

    public void setSets(int sets) {
        this.Sets = sets;
    }
}

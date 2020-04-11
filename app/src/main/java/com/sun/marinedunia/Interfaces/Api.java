package com.sun.marinedunia.Interfaces;


import com.sun.marinedunia.Models.BookCategories;
import com.sun.marinedunia.Models.EBooks;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {


    String BASE_URL = "https://script.google.com/";

   @GET("macros/s/AKfycbwSoMUeGG82szbnzqWoIiRL1gkYFOn6sMDi7yNbUuTXqXfdFtrW/exec?action=getItems")
    Call<EBooks> getItems();


    @GET("macros/s/AKfycbwSoMUeGG82szbnzqWoIiRL1gkYFOn6sMDi7yNbUuTXqXfdFtrW/exec?action=getBookCategory")
    Call<BookCategories> getBookCategory();



}

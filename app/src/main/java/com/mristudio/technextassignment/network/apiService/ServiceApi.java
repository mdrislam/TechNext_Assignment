package com.mristudio.technextassignment.network.apiService;

import androidx.lifecycle.LiveData;

import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.model.BlogPostREsponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

   // https://my-json-server.typicode.com/sohel-cse/simple-blog-api/db
    @GET("sohel-cse/simple-blog-api/db")
    Call<BlogPostREsponse > getBlogPosts();
}

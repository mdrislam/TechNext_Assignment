package com.mristudio.technextassignment.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mristudio.technextassignment.dao.BlogPostDAO;
import com.mristudio.technextassignment.model.Author;
import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.model.BlogPostREsponse;
import com.mristudio.technextassignment.network.apiService.ServiceApi;
import com.mristudio.technextassignment.network.retrofitClient.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

@Database(entities = {Blog.class}, version = 1, exportSchema = false)
public abstract class BlogPostDatabase extends RoomDatabase {

    private static BlogPostDatabase instance;

    public abstract BlogPostDAO blogPostDAO();

    public static synchronized BlogPostDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BlogPostDatabase.class, "tbl_blogPost")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsynTask(instance).execute();
        }
    };

    private static class PopulateDBAsynTask extends AsyncTask<Void, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public PopulateDBAsynTask(BlogPostDatabase db) {
            blogPostDAO = db.blogPostDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            List<String> catagoriList = new ArrayList<>();
            catagoriList.add("Catagori1");
            catagoriList.add("Catagori2");
            catagoriList.add("Catagori3");
            Author author = new Author(1, "this is author namedd", "this is avatar", "this is Profession");

//            blogPostDAO.insertBlog(new Blog("this is Tittle1 ", "this is Description", "this is cover Photo", catagoriList, author));
//            blogPostDAO.insertBlog(new Blog("this is Tittle2 ", "this is Description", "this is cover Photo", catagoriList, author));
//            blogPostDAO.insertBlog(new Blog("this is Tittle3 ", "this is Description", "this is cover Photo", catagoriList, author));
//            blogPostDAO.insertBlog(new Blog("this is Tittle4 ", "this is Description", "this is cover Photo", catagoriList, author));
//
           // fetchBlogsData();
            return null;
        }

        public void fetchBlogsData() {
            ServiceApi service = RetrofitClient.getApiClient().create(ServiceApi.class);
            Call<BlogPostREsponse> call = service.getBlogPosts();

            call.enqueue(new retrofit2.Callback<BlogPostREsponse>() {
                @Override
                public void onResponse(Call<BlogPostREsponse> call, Response<BlogPostREsponse> response) {
                    if (response.isSuccessful()) {
                        List<Blog> blogs = response.body().getBlogs();

                        Log.e(TAG, "onResponse: " + blogs.size());
                        for (Blog blog : blogs) {
                            Log.e(TAG, "onResponse: " + blog.toString());
                            blogPostDAO.insertBlog(blog);
                        }

                    } else {
                        Log.e(TAG, "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<BlogPostREsponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        }
    }


}

package com.mristudio.technextassignment.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.database.BlogPostDatabase;
import com.mristudio.technextassignment.dao.BlogPostDAO;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BlogPostRepository {
    private BlogPostDAO blogPostDAO;
    private LiveData<List<Blog>> allBlogs;
    List<Blog> blogs = new ArrayList<>();

    public BlogPostRepository(Application application) {
        BlogPostDatabase blogPostDatabase = BlogPostDatabase.getInstance(application);
        blogPostDAO = blogPostDatabase.blogPostDAO();
        allBlogs = blogPostDAO.getAllBlogsPost();
    }

    public void insertBlog(Blog blogPost) {

        new InsertBlogPostAsynTask(blogPostDAO).execute(blogPost);
    }

    public void insertAllBlog(List<Blog> logList) {

        new InsertAllBlogPostAsynTask(blogPostDAO).execute(logList);
    }

    public void updateBlogPost(Blog blogPost) {
        new UpdateAsynTask(blogPostDAO).execute(blogPost);
    }

    public void deleteBlogPost(Blog blogPost) {
        new DeleteAsynTask(blogPostDAO).execute(blogPost);
    }

    public void deleteAll() {
        new DeleteAllAsynTask(blogPostDAO).execute();
    }

    public LiveData<List<Blog>> getAllBlogs() {
        return allBlogs;
    }

    public LiveData<List<Blog>> getAllBlogsPost() {
        return allBlogs;
    }


    private static class InsertBlogPostAsynTask extends AsyncTask<Blog, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public InsertBlogPostAsynTask(BlogPostDAO blogPostDAO) {
            this.blogPostDAO = blogPostDAO;
        }

        @Override
        protected Void doInBackground(Blog... blogPosts) {
            blogPostDAO.insertBlog(blogPosts[0]);
            return null;
        }
    }

    private static class InsertAllBlogPostAsynTask extends AsyncTask<List<Blog>, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public InsertAllBlogPostAsynTask(BlogPostDAO blogPostDAO) {
            this.blogPostDAO = blogPostDAO;
        }

        @Override
        protected Void doInBackground(List<Blog>... lists) {

            for (Blog blog : lists[0]) {

                blogPostDAO.insertBlog(blog);
            }

            return null;
        }
    }

    private static class UpdateAsynTask extends AsyncTask<Blog, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public UpdateAsynTask(BlogPostDAO blogPostDAO) {
            this.blogPostDAO = blogPostDAO;
        }

        @Override
        protected Void doInBackground(Blog... blogPosts) {
            blogPostDAO.updateBlogPost(blogPosts[0]);
            return null;
        }
    }

    private static class DeleteAsynTask extends AsyncTask<Blog, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public DeleteAsynTask(BlogPostDAO blogPostDAO) {
            this.blogPostDAO = blogPostDAO;
        }

        @Override
        protected Void doInBackground(Blog... blogPosts) {
            blogPostDAO.deleteBlogPost(blogPosts[0]);
            return null;
        }
    }

    private static class DeleteAllAsynTask extends AsyncTask<Void, Void, Void> {

        private BlogPostDAO blogPostDAO;

        public DeleteAllAsynTask(BlogPostDAO blogPostDAO) {
            this.blogPostDAO = blogPostDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            blogPostDAO.deleteAll();
            return null;
        }
    }


//    public List<Blog> fetchBlogsData() {
//
//        ServiceApi service = RetrofitClient.getApiClient().create(ServiceApi.class);
//        Call<BlogPostREsponse> call = service.getBlogPosts();
//
//        call.enqueue(new retrofit2.Callback<BlogPostREsponse>() {
//            @Override
//            public void onResponse(Call<BlogPostREsponse> call, Response<BlogPostREsponse> response) {
//                if (response.isSuccessful()) {
//                     blogs = response.body().getBlogs();
//
//
//                } else {
//                    Log.e(TAG, "onResponse: ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BlogPostREsponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//        return blogs;
//    }
}

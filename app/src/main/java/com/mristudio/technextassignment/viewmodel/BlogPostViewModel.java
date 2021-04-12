package com.mristudio.technextassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.repository.BlogPostRepository;

import java.util.List;

public class BlogPostViewModel extends AndroidViewModel {

    private BlogPostRepository blogPostRepository;

    private LiveData<List<Blog>> allBlogs;


    public BlogPostViewModel(@NonNull Application application) {
        super(application);
        blogPostRepository = new BlogPostRepository(application);
        allBlogs = blogPostRepository.getAllBlogsPost();
    }

    public void insertBlog(Blog blogPost) { blogPostRepository.insertBlog(blogPost); }

    public void insertAllBlog(List<Blog> blogList) { blogPostRepository.insertAllBlog(blogList); }

    public void updateBlogPost(Blog blogPost) {
        blogPostRepository.updateBlogPost(blogPost);
    }

    public void deleteBlogPost(Blog blogPost) {
        blogPostRepository.deleteBlogPost(blogPost);
    }

    public void deleteAll() {
        blogPostRepository.deleteAll();
    }

    public LiveData<List<Blog>> getAllBlogPostes() {
        return allBlogs;
    }
}

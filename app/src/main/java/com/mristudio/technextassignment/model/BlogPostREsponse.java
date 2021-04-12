
package com.mristudio.technextassignment.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogPostREsponse {

    @SerializedName("blogs")
    @Expose
    private List<Blog> blogs = null;

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

}

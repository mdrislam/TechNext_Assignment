package com.mristudio.technextassignment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mristudio.technextassignment.model.Blog;

import java.util.List;

@Dao
public interface BlogPostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBlog(Blog blog);

    @Update
    void updateBlogPost(Blog blogPost);

    @Delete
    void deleteBlogPost(Blog blogPost);

    @Query("DELETE FROM tbl_blogPost")
    void deleteAll();


    @Query("SELECT * FROM tbl_blogPost ORDER BY  id DESC")
    LiveData<List<Blog>> getAllBlogsPost();

//    @Query("SELECT * FROM Persons ORDER BY first_name (:isAsc ? ASC : DESC)")
//    List<Person> getPersonsAlphabetically(boolean isAsc);

}

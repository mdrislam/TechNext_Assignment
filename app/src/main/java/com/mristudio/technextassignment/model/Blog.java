
package com.mristudio.technextassignment.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

@Entity(tableName = "tbl_blogPost")
public class Blog {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "cover_photo")
    @SerializedName("cover_photo")
    @Expose
    private String coverPhoto;

    @TypeConverters(CatagoryTypeConvertor.class)
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;

    @Embedded(prefix = "author")
    @SerializedName("author")
    @Expose
    private Author author;

    @Inject
    public Blog(String title, String description, String coverPhoto, List<String> categories, Author author) {
        this.title = title;
        this.description = description;
        this.coverPhoto = coverPhoto;
        this.categories = categories;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories.addAll(categories) ;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}


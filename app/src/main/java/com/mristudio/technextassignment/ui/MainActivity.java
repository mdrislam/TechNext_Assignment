package com.mristudio.technextassignment.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mristudio.technextassignment.R;
import com.mristudio.technextassignment.adapter.BlogPostAdapter;
import com.mristudio.technextassignment.databinding.ActivityMainBinding;
import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.model.BlogPostREsponse;
import com.mristudio.technextassignment.network.apiService.ServiceApi;
import com.mristudio.technextassignment.network.retrofitClient.RetrofitClient;
import com.mristudio.technextassignment.utils.Constants;
import com.mristudio.technextassignment.viewmodel.BlogPostViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_BLOG_REQUEST = 1;
    private static final int EDIT_BLOG_REQUEST = 2;
    private static final String TAG = "MainActivity";
    private BlogPostViewModel blogPostViewModel;
   private  ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        blogPostViewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(BlogPostViewModel.class);

        binding.blogPostRechyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.blogPostRechyclerView.setHasFixedSize(true);
        final BlogPostAdapter adapter = new BlogPostAdapter(this);
        binding.blogPostRechyclerView.setAdapter(adapter);

        blogPostViewModel.getAllBlogPostes().observe(this, new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogPosts) {
                //  Toast.makeText(MainActivity.this, "Change Data", Toast.LENGTH_SHORT).show();
                adapter.setBlogPost(blogPosts);
            }
        });
        fetchBlogsData();

        binding.fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditBlogPostActivity.class);
                startActivityForResult(intent, ADD_BLOG_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                blogPostViewModel.deleteBlogPost(adapter.getBlogByPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Blog Deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.blogPostRechyclerView);

        adapter.setOnItemClickListener(new BlogPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Blog blog) {
                Intent intent = new Intent(MainActivity.this, AddEditBlogPostActivity.class);
                Gson gson = new Gson();
                String blogString = gson.toJson(blog);
                intent.putExtra(Constants.VALUE_TAG_ID, blog.getId());
                intent.putExtra(Constants.VALUE_TAG_OBJECT, blogString);
                startActivityForResult(intent, EDIT_BLOG_REQUEST);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BLOG_REQUEST && resultCode == RESULT_OK) {

            Gson gson = new Gson();
            Blog blog = gson.fromJson(data.getStringExtra(Constants.VALUE_TAG_OBJECT), Blog.class);
            blogPostViewModel.insertBlog(blog);
            Toast.makeText(this, "Blog SuccessFully Saved !", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_BLOG_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Constants.VALUE_TAG_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Can not Updated !", Toast.LENGTH_SHORT).show();
                return;
            }
            Gson gson = new Gson();
            Blog blog = gson.fromJson(data.getStringExtra(Constants.VALUE_TAG_OBJECT), Blog.class);
            blog.setId(id);
            blogPostViewModel.updateBlogPost(blog);
            Toast.makeText(this, "Blog Update Saved !", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(this, "Blog Not  Save !", Toast.LENGTH_SHORT).show();
        }
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
                    blogPostViewModel.insertAllBlog(blogs);
                }
            }

            @Override
            public void onFailure(Call<BlogPostREsponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_deleteAll:
                blogPostViewModel.deleteAll();
                Toast.makeText(this, "All Blog Deleted !", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
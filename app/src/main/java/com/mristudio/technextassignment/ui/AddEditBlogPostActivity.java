package com.mristudio.technextassignment.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mristudio.technextassignment.R;
import com.mristudio.technextassignment.databinding.ActivityAddBlogPostBinding;
import com.mristudio.technextassignment.model.Author;
import com.mristudio.technextassignment.model.Blog;
import com.mristudio.technextassignment.utils.Constants;
import com.mristudio.technextassignment.utils.Utils;
import com.mristudio.technextassignment.viewmodel.BlogPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddEditBlogPostActivity extends AppCompatActivity {

    private static final String TAG = "AddEditBlogPostActivity";
    private String catgories[] = {"Business", "Lifestyle", "Entertainment", "Productivity"};
    private List<String> selectedcatagoriesList = new ArrayList<>();
    private boolean selected[] = new boolean[catgories.length];
    private Menu menuItem;

    ActivityAddBlogPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_post);
        binding = ActivityAddBlogPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getApplicationContext().getResources().getDrawable(R.drawable.ic_close));

        /**
         * Load Image To Static base Url From Web
         */
        Glide.with(this).load(Constants.coverPath).into(binding.coverImageView);

        if (getIntent().hasExtra(Constants.VALUE_TAG_ID)) {

            setTitle("Edit Blog");
            Gson gson = new Gson();
            Blog blog = gson.fromJson(getIntent().getStringExtra(Constants.VALUE_TAG_OBJECT), Blog.class);
            binding.tittleET.setText(blog.getTitle());
            binding.descriptionACET.setText(blog.getDescription());
            binding.catagoriListTV.setText(Utils.getArratoString(blog.getCategories()));
            selectedcatagoriesList.clear();
            selectedcatagoriesList.addAll(blog.getCategories());

        } else {
            setTitle("Post Blog");
        }

        binding.catagoriesSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectCatPopUp();
            }
        });

    }


    /**
     * Open Catagories Multiple Select Custom AlertDialog
     */
    private void openSelectCatPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEditBlogPostActivity.this);
        builder.setTitle("Select Catagories !");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(catgories, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos, boolean isChecked) {

                if (isChecked) {
                    Integer index = Utils.getIndexByString(selectedcatagoriesList, catgories[pos]);
                    if (index != -1) {
                        Toast.makeText(AddEditBlogPostActivity.this, "Already Added this !", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedcatagoriesList.add(catgories[pos]);
                    }

                } else {
                    selectedcatagoriesList.remove(catgories[pos]);
                }

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG, "openSelectCatPopUp: " + Utils.getArratoString(selectedcatagoriesList));
                dialog.dismiss();
                binding.catagoriListTV.setText(Utils.getArratoString(selectedcatagoriesList));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedcatagoriesList.clear();
                binding.catagoriListTV.setText("");
            }
        });
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_blogpost_menu, menu);
        MenuItem save = menu.findItem(R.id.saveBlogPostMenu);
        MenuItem edit = menu.findItem(R.id.editBlogPostMenu);

        if (getIntent().hasExtra(Constants.VALUE_TAG_ID)) {
            edit.setVisible(true);
            save.setVisible(false);
            enableandDesableEditText(false);
        } else {
            edit.setVisible(false);
            save.setVisible(true);
            enableandDesableEditText(true);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuItem = menu;
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem save = menuItem.findItem(R.id.saveBlogPostMenu);
        MenuItem edit = menuItem.findItem(R.id.editBlogPostMenu);
        switch (item.getItemId()) {
            case R.id.saveBlogPostMenu:
                if (getIntent().hasExtra(Constants.VALUE_TAG_ID)) {
                    save.setVisible(false);
                    edit.setVisible(true);
                    saveBlogPost();
                } else {
                    saveBlogPost();
                }
                return true;
            case R.id.editBlogPostMenu:

                save.setVisible(true);
                edit.setVisible(false);
                enableandDesableEditText(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Enable and Desable ui view
     */
    private void enableandDesableEditText(Boolean bool) {

        binding.tittleET.setEnabled(bool);
        binding.descriptionACET.setEnabled(bool);
        if (bool) {
            binding.catagoriesSelect.setVisibility(View.VISIBLE);
        } else {
            binding. catagoriesSelect.setVisibility(View.GONE);
        }
    }

    /**
     * InsertNew Blog Post
     */
    private void saveBlogPost() {
        String title = binding.tittleET.getText().toString();
        String description =binding. descriptionACET.getText().toString();

        if (cheakInputFieldValidation(title, description, selectedcatagoriesList)) {

            Author author = new Author(1, "Risad Hossain", Constants.authorAvatar, "Content Writer");
            Intent intent = new Intent();
            Gson gson = new Gson();

            int id = getIntent().getIntExtra(Constants.VALUE_TAG_ID, -1);
            if (id != -1) {
                Blog blog = gson.fromJson(getIntent().getStringExtra(Constants.VALUE_TAG_OBJECT), Blog.class);
                blog.setTitle(title);
                blog.setDescription(description);
                blog.setCategories(selectedcatagoriesList);

                String blogString = gson.toJson(blog);
                intent.putExtra(Constants.VALUE_TAG_OBJECT, blogString);
                intent.putExtra(Constants.VALUE_TAG_ID, id);

            } else {

                String blog = gson.toJson(new Blog(title, description, Constants.coverPath, selectedcatagoriesList, author));
                intent.putExtra(Constants.VALUE_TAG_OBJECT, blog);
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Cheak User Input Text Validation
     */
    boolean cheakInputFieldValidation(String tittle, String description, List<String> catagories) {
        binding.tittleET.setError(null);
        binding.descriptionACET.setError(null);

        if (tittle.isEmpty()) {
            binding.tittleET.setError("Title Field is Empty");
            return false;
        }
        else if (description.isEmpty()) {
            binding.descriptionACET.setError("Description Field is Empty");
            return false;
        }
        else if (catagories.isEmpty()) {
            Toast.makeText(this, "Please Select Catagories !", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}
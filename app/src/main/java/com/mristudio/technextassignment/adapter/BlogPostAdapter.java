package com.mristudio.technextassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mristudio.technextassignment.R;
import com.mristudio.technextassignment.databinding.LayoutBlogPostRowBinding;
import com.mristudio.technextassignment.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class BlogPostAdapter extends RecyclerView.Adapter<BlogPostAdapter.BlogPostHolder> {

    private List<Blog> allBlogPosts = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    public BlogPostAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BlogPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutBlogPostRowBinding layoutBlogPostRowBinding = LayoutBlogPostRowBinding.inflate(layoutInflater, parent, false);
        return new BlogPostHolder(layoutBlogPostRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogPostHolder holder, int position) {
        Blog currentPost = allBlogPosts.get(position);
        holder.layoutBlogPostRowBinding.setBlog(currentPost);
        holder.layoutBlogPostRowBinding.executePendingBindings();

    }


    @Override
    public int getItemCount() {
        return allBlogPosts.size();
    }

    public void setBlogPost(List<Blog> allBlogPosts) {
        this.allBlogPosts = allBlogPosts;
        notifyDataSetChanged();
    }

    public Blog getBlogByPosition(int pos) {
        return allBlogPosts.get(pos);
    }

    class BlogPostHolder extends RecyclerView.ViewHolder {

        LayoutBlogPostRowBinding layoutBlogPostRowBinding;

        public BlogPostHolder(@NonNull LayoutBlogPostRowBinding layoutBlogPostRowBinding) {
            super(layoutBlogPostRowBinding.getRoot());
            this.layoutBlogPostRowBinding = layoutBlogPostRowBinding;

            layoutBlogPostRowBinding.postCoverIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {

                        listener.onItemClick(allBlogPosts.get(pos));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Blog blog);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

package com.iuce.news;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {


    private ArrayList<News> newsData;

    public NewsAdapter(ArrayList<News> newsData) {
        this.newsData = newsData;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.itemTitle.setText(newsData.get(position).getTitle());
        holder.itemSource.setText(newsData.get(position).getSource());
        holder.itemDate.setText(newsData.get(position).getPublishedAt());
        Picasso.get()
                .load(Uri.parse(newsData.get(position).getUrlToImage()))
                .resize(50, 50)
                .centerCrop()
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemSource;
        TextView itemDate;

        public Holder(View itemView)  {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSource = itemView.findViewById(R.id.item_source);
            itemDate = itemView.findViewById(R.id.item_date);

        }

    }



}


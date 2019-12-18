package com.iuce.news;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    public NewsAdapter(Context context, ArrayList<News> newsData) {
        this.context = context;
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


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            News news = News.getInstance();
            news.setUrlToImage(newsData.get(pos).getUrlToImage());
            news.setDescription(newsData.get(pos).getDescription());
            news.setTitle(newsData.get(pos).getTitle());
            news.setContent(newsData.get(pos).getContent());
            news.setPublishedAt(newsData.get(pos).getPublishedAt());
            news.setSource(newsData.get(pos).getSource());
            Intent messageIntent = new Intent(context, NewsActivity.class);
            context.startActivity(messageIntent);
        }
    }



}


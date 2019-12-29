package com.iuce.news.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.Globals;
import com.iuce.news.R;
import com.iuce.news.db.entity.News;
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
                .fit()
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

        public Holder(View itemView) {
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

            News news = new News(
                    newsData.get(pos).getUrlToImage(),
                    newsData.get(pos).getDescription(),
                    newsData.get(pos).getTitle(),
                    newsData.get(pos).getContent(),
                    newsData.get(pos).getPublishedAt(),
                    newsData.get(pos).getSource(),
                    newsData.get(pos).getUrl()
            );

            Globals.getInstance().setSelectedNews(news);

            Intent messageIntent = new Intent(context, NewsActivity.class);
            context.startActivity(messageIntent);
        }
    }


}


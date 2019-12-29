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
import com.iuce.news.db.pojo.NewsWithState;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {


    private ArrayList<NewsWithState> newsWithStates;
    private Context context;

    public NewsAdapter(Context context, ArrayList<NewsWithState> newsData) {
        this.context = context;
        this.newsWithStates = newsData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.itemTitle.setText(newsWithStates.get(position).getNews().getTitle());
        holder.itemSource.setText(newsWithStates.get(position).getNews().getSource());
        holder.itemDate.setText(newsWithStates.get(position).getNews().getPublishedAt());
        Picasso.get()
                .load(Uri.parse(newsWithStates.get(position).getNews().getUrlToImage()))
                .fit()
                .centerCrop()
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return newsWithStates.size();
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

            Globals.getInstance().setSelectedNewsWithState(newsWithStates.get(pos));

            Intent messageIntent = new Intent(context, NewsActivity.class);
            context.startActivity(messageIntent);
        }
    }


}


package com.iuce.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {


    private ArrayList<NewsAPI.NewsData> newsData;
    private Context context;

    public NewsAdapter(Context context, ArrayList<NewsAPI.NewsData> newsData){
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
        holder.itemTitle.setText(newsData.get(position).title);
        holder.itemDescription.setText(newsData.get(position).description);
        //holder.itemImage.setImageURI(Uri.parse(newsData.get(position).urlToImage));
        Picasso.get()
                .load(Uri.parse(newsData.get(position).urlToImage))
                .resize(50, 50)
                .centerCrop()
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDescription;

        public Holder(View itemView){
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDescription = itemView.findViewById(R.id.item_description);
        }
    }

}


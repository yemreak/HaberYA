package com.yemreak.haberya.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yemreak.haberya.Globals;
import com.yemreak.haberya.R;
import com.yemreak.haberya.db.entity.News;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> {
    public static final String TAG = "SearchAdapter";

    private List<News> requestedNews;
    private Context context;

    public SearchAdapter(Context context, List<News> requestedNews) {
        this.context = context;
        this.requestedNews = requestedNews;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.Holder holder, int position) {
        holder.itemTitle.setText(requestedNews.get(position).getTitle());
        holder.itemSource.setText(requestedNews.get(position).getSource());
        holder.itemDate.setText(requestedNews.get(position).getPublishedAt());

        Picasso.get()
                .load(Uri.parse(requestedNews.get(position).getUrlToImage()))
                .fit()
                .centerCrop()
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return requestedNews.size();
    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rlMain;

        ImageView itemImage;
        TextView itemTitle;
        TextView itemSource;
        TextView itemDate;

        public Holder(View itemView) {
            super(itemView);

            rlMain = itemView.findViewById(R.id.search_rl_main);
            itemImage = itemView.findViewById(R.id.search_item_image);
            itemTitle = itemView.findViewById(R.id.search_item_title);
            itemSource = itemView.findViewById(R.id.search_item_source);
            itemDate = itemView.findViewById(R.id.search_item_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            Intent newsIntent = new Intent(context, DetailedSearchNews.class);
            Globals.getInstance().setSelectedNews(requestedNews.get(pos));
            context.startActivity(newsIntent);
        }
    }
}

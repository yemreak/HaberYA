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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yemreak.haberya.R;
import com.yemreak.haberya.db.pojo.NewsWithState;
import com.yemreak.haberya.viewmodel.NewsViewModel;

import java.util.List;

public class ReactedAdapter extends RecyclerView.Adapter<ReactedAdapter.Holder> {

    public static final String TAG = "ReactedAdapter";

    private NewsViewModel newsViewModel;
    private List<NewsWithState> newsWithStates;
    private Context context;

    public ReactedAdapter(Context context, List<NewsWithState> newsWithStateList) {
        this.context = context;
        this.newsWithStates = newsWithStateList;
        newsViewModel = new ViewModelProvider((ReactedActivity) context).get(NewsViewModel.class);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reacted_news_item, parent, false);

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
        RelativeLayout rlMain;

        ImageView itemImage;
        TextView itemTitle;
        TextView itemSource;
        TextView itemDate;

        public Holder(View itemView) {
            super(itemView);

            rlMain = itemView.findViewById(R.id.reacted_rl_main);

            itemImage = itemView.findViewById(R.id.reacted_item_image);
            itemTitle = itemView.findViewById(R.id.reacted_item_title);
            itemSource = itemView.findViewById(R.id.reacted_item_source);
            itemDate = itemView.findViewById(R.id.reacted_item_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            Intent newsIntent = new Intent(context, NewsActivity.class);
            newsIntent.putExtra(
                    NewsActivity.NAME_NEWS_ID,
                    newsWithStates.get(pos).getNews().getId())
            ;
            context.startActivity(newsIntent);
        }
    }
}



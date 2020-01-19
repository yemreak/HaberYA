package com.iuce.news.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.R;
import com.iuce.news.api.NewsAPIOptions;
import com.iuce.news.db.entity.State;
import com.iuce.news.db.pojo.NewsWithState;
import com.iuce.news.viewmodel.NewsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
    public static final String TAG = "Adapter";

    private NewsViewModel newsViewModel;
    private List<NewsWithState> newsWithStates;
    private Context context;

    public NewsAdapter(Context context, List<NewsWithState> newsWithStateList) {
        this.context = context;
        this.newsWithStates = newsWithStateList;
        newsViewModel = new ViewModelProvider((MainActivity) context).get(NewsViewModel.class);
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

        if (State.Type.READ.isExist(newsWithStates.get(position).getStates())) {
            holder.rlMain.setAlpha(0.6f);
        }
        if (State.Type.LATER.isExist(newsWithStates.get(position).getStates())) {
            holder.imgBtn.setBackgroundResource(R.drawable.ic_saved_read_later_black_24dp);
        } else {
            holder.imgBtn.setBackgroundResource(R.drawable.ic_add_read_later_black_24dp);
        }
        Picasso.get()
                .load(Uri.parse(newsWithStates.get(position).getNews().getUrlToImage()))
                .fit()
                .centerCrop()
                .into(holder.itemImage);

        if (MainActivity.currentCountry == NewsAPIOptions.Country.AE) {
            holder.rlMain.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.itemImage.setPadding(25, 0, 0, 0);
        }

    }


    @Override
    public int getItemCount() {
        return newsWithStates.size();
    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout rlMain;

        ImageView itemImage;
        TextView itemTitle;
        TextView itemSource;
        TextView itemDate;
        ImageButton imgBtn;

        public Holder(View itemView) {
            super(itemView);

            rlMain = itemView.findViewById(R.id.item_linear_layout);

            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSource = itemView.findViewById(R.id.item_source);
            itemDate = itemView.findViewById(R.id.item_date);
            imgBtn = itemView.findViewById(R.id.read_later_button);


            imgBtn.setOnClickListener(v -> {
                int pos = getAdapterPosition();

                State state = State.Type.LATER.findState(newsWithStates.get(pos).getStates());
                if (state != null) {
                    newsViewModel.deleteStates(state);
                    v.setBackgroundResource(R.drawable.ic_add_read_later_black_24dp);
                } else {
                    newsViewModel.insertStates(State.Builder(newsWithStates.get(pos), State.Type.LATER));
                    v.setBackgroundResource(R.drawable.ic_saved_read_later_black_24dp);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            Intent newsIntent = new Intent(context, NewsActivity.class);
            newsIntent.putExtra(NewsActivity.NAME_NEWS_ID, newsWithStates.get(pos).getNews().getId());
            context.startActivity(newsIntent);
        }

    }
}


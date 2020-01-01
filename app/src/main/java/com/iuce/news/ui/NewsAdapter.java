package com.iuce.news.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.iuce.news.Globals;
import com.iuce.news.R;
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
        newsViewModel = new ViewModelProvider((MainActivity)context).get(NewsViewModel.class);
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

        /*if (newsWithStates.get(position).getNews().isRead()) {
            holder.rlMain.setAlpha(0.6f);
        }*/
        if(isSaved( newsWithStates.get(position).getStates())){
            holder.imgBtn.setBackgroundResource(R.drawable.ic_saved_read_later_black_24dp);
        }else{
            holder.imgBtn.setBackgroundResource(R.drawable.ic_add_read_later_black_24dp);

        }
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
        ImageButton imgBtn;

        public Holder(View itemView) {
            super(itemView);

            rlMain = itemView.findViewById(R.id.rl_main);

            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSource = itemView.findViewById(R.id.item_source);
            itemDate = itemView.findViewById(R.id.item_date);
            imgBtn = itemView.findViewById(R.id.read_later_button);


            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(NewsAdapter.isSaved( newsWithStates.get(pos).getStates())){
                        v.setBackgroundResource(R.drawable.ic_saved_read_later_black_24dp);
                        //imgBtn.setImageResource(R.drawable.ic_add_read_later_black_24dp);
                        // delete saved state fonksiyonu çağrılacak
                    }else{
                        v.setBackgroundResource(R.drawable.ic_add_read_later_black_24dp);
                        State state = new State(newsWithStates.get(pos).getNews().getId(), State.TYPE_LATER);
                        newsViewModel.insertStates(state);
                        Log.e(TAG, state.toString());
                    }

                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            NewsWithState newsWithState = newsWithStates.get(pos);
            Globals.getInstance().setSelectedNewsWithState(newsWithState);

            Intent messageIntent = new Intent(context, NewsActivity.class);
            context.startActivity(messageIntent);
        }

    }

    public static boolean isSaved(List<State> states){
        for (State state : states) {
            if (state.getType() == State.TYPE_LATER) {
                return true;
            }
        }
        return false;
    }

}


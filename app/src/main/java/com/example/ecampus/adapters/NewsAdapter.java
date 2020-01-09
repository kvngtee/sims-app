package com.example.ecampus.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecampus.R;
import com.example.ecampus.activities.NewsfeedActivity;
import com.example.ecampus.activities.PostDetailActivity;
import com.example.ecampus.models.News;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;
import java.util.List;



public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<News> mList;
    private Context mcontext;

    public NewsAdapter(List<News> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        mcontext = parent.getContext();
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        News news = mList.get(position);
        Log.i("DATA", news.toString());
        holder.newsTitle.setText(news.getTitle());
        holder.newsDesc.setText(news.getDesc());
        holder.newsDate.setReferenceTime(news.getDate().getTime());
        Picasso.get().load(news.getImage()).into(holder.newsImage);

        holder.container.setAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.scroll_transition));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDesc;
        ImageView newsImage;
        RelativeTimeTextView newsDate;

        CardView container;

        public MyViewHolder(View view) {
            super(view);

            container = itemView.findViewById(R.id.container);

            newsTitle = view.findViewById(R.id.news_title);
            newsDesc = view.findViewById(R.id.news_desc);
            newsDate = view.findViewById(R.id.dateposted);
            newsImage = view.findViewById(R.id.news_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postdetails = new Intent(mcontext, PostDetailActivity.class);
                    int postion = getAdapterPosition();

                    postdetails.putExtra("title",mList.get(postion).getTitle());
                    postdetails.putExtra("desc",mList.get(postion).getDesc());
                    postdetails.putExtra("image",mList.get(postion).getImage());
                    postdetails.putExtra("date",mList.get(postion).getDate());
                    mcontext.startActivity(postdetails);

                }
            });


        }
    }

}

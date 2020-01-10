package com.example.ecampus.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.activities.PostDetailActivity;
import com.example.ecampus.models.News;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.news_title)
    TextView Title;

    @BindView(R.id.news_desc)
    TextView Desc;

    @BindView(R.id.dateposted)
    RelativeTimeTextView NewsDate;

    @BindView(R.id.news_image)
    ImageView NewsImage;

    @BindView(R.id.touchView)
    View touchView;


    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

    public void bind(Context ctx, News news) {
        context = ctx;

        touchView.setOnClickListener(v -> {
            Intent postdetails = new Intent(context, PostDetailActivity.class);

            postdetails.putExtra("title", news.getTitle());
            postdetails.putExtra("desc", news.getDesc());
            postdetails.putExtra("image", news.getImage());
            postdetails.putExtra("date", news.getDate().getTime());
            context.startActivity(postdetails);

        });

        Title.setText(news.getTitle());
        Desc.setText(news.getDesc());
        NewsDate.setReferenceTime(news.getDate().getTime());
        Picasso.get().load(news.getImage()).placeholder(R.drawable.logo).networkPolicy(NetworkPolicy.OFFLINE).into(NewsImage, new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                Picasso.get().load(news.getImage()).placeholder(R.drawable.logo).error(R.drawable.logo).into(NewsImage, new Callback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError(Exception e) {
                        Log.i("PICASO_CACHE", "Couldn't fetch the image from the network");
                    }
                });

            }
        });
    }
}
package com.example.ecampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.models.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.news_title)
    TextView Title;

    @BindView(R.id.news_desc)
    TextView Desc;

    @BindView(R.id.dateposted)
    TextView NewsDate;

    @BindView(R.id.news_image)
    ImageView NewsImage;

    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context ctx, News news) {
        context = ctx;
        Title.setText(news.getTitle());
        Desc.setText(news.getDesc());
        NewsDate.setText(news.getDate().toString());
        Picasso.get().load(news.getImage()).placeholder(R.drawable.logo).networkPolicy(NetworkPolicy.OFFLINE).into(NewsImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(news.getImage()).placeholder(R.drawable.logo).error(R.drawable.logo).into(NewsImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i("PICASO_CACHE", "Couldn't fetch the image drom the network");
                        Toasty.error(context, "Couldn't fetch the image drom the network").show();
                    }
                });

            }
        });
    }
}
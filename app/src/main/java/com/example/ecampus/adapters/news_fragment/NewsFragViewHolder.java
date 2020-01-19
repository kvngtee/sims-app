package com.example.ecampus.adapters.news_fragment;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddiehl.timesincetextview.TimeSinceTextView;
import com.example.ecampus.R;
import com.example.ecampus.activities.PostDetailActivity;
import com.example.ecampus.models.News;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.news_title)
    TextView Title;

    @BindView(R.id.news_desc)
    TextView Desc;

    @BindView(R.id.dateposted)
    TimeSinceTextView NewsDate;

    @BindView(R.id.news_image)
    ImageView NewsImage;

    @BindView(R.id.touchView)
    View touchView;


    public NewsFragViewHolder(View itemView) {
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
        NewsDate.setDate(news.getDate());
        Picasso.get().load(news.getImage()).fit()
                .centerCrop().into(NewsImage);
    }

    public void showItemView() {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                dpToPx(100));
        itemView.setVisibility(View.VISIBLE);
        params.setMargins(12, 10, 12, 10);
        itemView.setLayoutParams(params);
    }

    public void hideItemView() {
        itemView.setVisibility(View.GONE);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
    }

     public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        Log.i("DP TO PX", String.format("Calculated DP: %d Converted Pixel: %s", dp, px));
        return px;
    }

    //Not in use because setHeight is in PX not DP
    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        Log.i("DP TO PX", String.format("Calculated Pixel: %d Converted DP: %s", px, dp));
        return dp;
    }
}
package com.example.ecampus.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecampus.R;
import com.example.ecampus.activities.PostDetailActivity;
import com.example.ecampus.models.News;
import com.github.curioustechizen.ago.RelativeTimeTextView;
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


   public CardView cardView;

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
        Picasso.get().load(news.getImage()).fit()
                .centerCrop().into(NewsImage);
    }

    public void showItemView(boolean addMargin ){
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                200);
        itemView.setVisibility(View.VISIBLE);
       if (addMargin) params.setMargins(12, 10, 12, 10);
        itemView.setLayoutParams(params);
    }

    public  void  hideItemView(){
        itemView.setVisibility(View.GONE);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
    }

}
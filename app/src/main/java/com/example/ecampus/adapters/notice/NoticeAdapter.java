package com.example.ecampus.adapters.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecampus.R;
import com.example.ecampus.models.Notice;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.curioustechizen.ago.RelativeTimeTextView;


public class NoticeAdapter extends FirestoreRecyclerAdapter<Notice, NoticeAdapter.NoticeHolder> {

    private Context mcontext;


    public NoticeAdapter(@NonNull FirestoreRecyclerOptions<Notice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoticeHolder holder, int position, @NonNull Notice notice) {
        holder.noticetv.setText(notice.getNoticetext());
        holder.noticecattv.setText(notice.getCategory());
        holder.noticedatetv.setReferenceTime(notice.getDate().getTime());

        holder.container.setAnimation(AnimationUtils.loadAnimation(mcontext, R.anim.scroll_transition));

    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row,
                parent, false);
        // set the Context here
        mcontext = parent.getContext();
        return new NoticeHolder(v);
    }


    class NoticeHolder extends RecyclerView.ViewHolder {
        TextView noticetv;
        TextView noticecattv;
        RelativeTimeTextView noticedatetv;

        CardView container;



        public NoticeHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            noticetv = itemView.findViewById(R.id.notice_text);
            noticecattv = itemView.findViewById(R.id.category);
            noticedatetv = itemView.findViewById(R.id.dateposted);
        }
    }

}

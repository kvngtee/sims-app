package com.example.ecampus.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ecampus.models.News;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BackgroundCacher extends AsyncTask<Void, Void, Void> {
    private Context context;

    public BackgroundCacher(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        SqliteHelper sqliteHelper = new SqliteHelper(context);
        Log.i("BACKGROUND", "AsyncTask started");
        CollectionReference NewsCollection = FirebaseFirestore.getInstance().collection("news");
        NewsCollection.get().addOnSuccessListener(allNewsSnap -> {
            for (DocumentSnapshot allYearsSnap : allNewsSnap) {
                for (int i = 1; i <= 12; i++) {
                    NewsCollection.document(allYearsSnap.getId())
                            .collection(i < 10 ? "0" + i : String.valueOf(i)).get().addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot monthSnap : queryDocumentSnapshots) {
                             News news = monthSnap.toObject(News.class);
                            news.setId(monthSnap.getId());
                            sqliteHelper.createNews(news, true);
                        }
                    });
                }
            }
        });

        return null;
    }
}

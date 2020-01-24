package com.example.ecampus.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.ecampus.models.News;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NewsCacher extends Worker {
    public NewsCacher(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        FirebaseFirestore.getInstance().collection("news").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    SqliteHelper sqliteHelper = new SqliteHelper(getApplicationContext());
                    Log.i("WORKER", "CACHING: " + snapshot.toObject(News.class).getTitle());
                    sqliteHelper.createNews(snapshot.toObject(News.class), true);
                }
            }
        });
        return null;
    }
}


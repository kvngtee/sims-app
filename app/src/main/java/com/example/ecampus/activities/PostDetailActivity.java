package com.example.ecampus.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecampus.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class PostDetailActivity extends AppCompatActivity {

    TextView mtitleTv, mdescTv;
    RelativeTimeTextView mdateTv;
    ImageView mImageIv;
    Bitmap bitmap;
    Picasso picasso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Toolbar mToolBar = findViewById(R.id.mtoolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setSubtitle("");
        mToolBar.setNavigationIcon(R.drawable.back);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        mtitleTv = findViewById(R.id.news_title);
        mdescTv = findViewById(R.id.news_desc);
        mImageIv = findViewById(R.id.news_image);
        mdateTv = findViewById(R.id.dateposted);

        //get data from intent
        String image = getIntent().getStringExtra("image");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        long date = getIntent().getLongExtra("date", 0);

        //set data views
        mtitleTv.setText(title);
        mdescTv.setText(desc);
        mdateTv.setReferenceTime(date);
        Picasso.get().load(image).into(mImageIv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareImage();
                return true;

            case R.id.like:
                Drawable myDrawable = getResources().getDrawable(R.drawable.loveafter);
                item.setIcon(myDrawable);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareImage() {
        try {
            //get image from imageview as bitmap
            bitmap = ((BitmapDrawable) mImageIv.getDrawable()).getBitmap();
            String s = mtitleTv.getText().toString() + "\n" + mdescTv.getText().toString();

            File file = new File(getExternalCacheDir(), "sample.png");
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.flush();
            fout.close();
            file.setReadable(true, false);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            //intent to share image and text
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, s);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share via"));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}



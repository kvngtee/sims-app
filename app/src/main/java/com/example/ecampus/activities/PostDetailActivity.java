package com.example.ecampus.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecampus.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PostDetailActivity extends AppCompatActivity {

    TextView mtitleTv, mdescTv;
    RelativeTimeTextView mdateTv;
    ImageView mImageIv;
    Bitmap bitmap;
    Picasso picasso;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

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
        long date = getIntent().getLongExtra("date",0);

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
            case R.id.save:
                //if os >= marshmallow we need runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    } else {
                        //permission already granted,save image
                        saveImage();

                    }

                } else {
                    //System os is less than marshmallow,save
                    saveImage();

                }

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

    private void saveImage() {
        //get image from imageview as bitmap
        bitmap = ((BitmapDrawable) mImageIv.getDrawable()).getBitmap();
        //time stamp for image name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //path to external storage
        File path = Environment.getExternalStorageDirectory();
        //create folder named eCampus
        File dir = new File(path + "/eCampus/");
        dir.mkdirs();
        //image name
        String imageName = timestamp + ".PNG";
        File file = new File(dir, imageName);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, imageName + " saved to" + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //failed saving image
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {
                //if requestcode is cancelled the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted save image
                    saveImage();
                } else {
                    //permission denied
                    Toast.makeText(this, "enable permission to save image!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}

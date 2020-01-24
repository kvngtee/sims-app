package com.example.ecampus.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ecampus.models.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static String DBName = "simsAppDB";
    private static String NewsTB = "newsTB";
    private static String ID = "id";
    private static String NewsID = "newsID";
    private static String NewsImage = "newsImage";
    private static String NewsTitle = "newsTitle";
    private static String NewsDesc = "newsDesc";
    private static String NewsDate = "newsDate";
    private String CreateNewsTB = "CREATE TABLE IF NOT EXISTS " + NewsTB + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NewsID + " TEXT UNIQUE, " +
            NewsTitle + " TEXT, " +
            NewsDesc + " TEXT, " +
            NewsImage + " TEXT, " +
            NewsDate + " TEXT )";

    public SqliteHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateNewsTB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsTB);
        onCreate(sqLiteDatabase);
    }

    private SQLiteDatabase openDB(Boolean writable) {
        return writable ? this.getWritableDatabase() : this.getReadableDatabase();
    }

    // TODO: Return a single news from the DB
    public News getNews(String newsID) {
        return new News();
    }

    public List<News> allNews() {
        SQLiteDatabase db = openDB(false);
        List<News> allNewsList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + NewsTB, null);
            if (cursor.moveToFirst()) {
                do {
                    News news = new News();
                    news.setId(getColumnValue(cursor, NewsID));
                    news.setTitle(getColumnValue(cursor, NewsTitle));
                    news.setImage(getColumnValue(cursor, NewsImage));
                    news.setDesc(getColumnValue(cursor, NewsDesc));
                    news.setDate(new Date(getColumnValue(cursor, NewsDate)));
                    allNewsList.add(news);
                    Log.i("SUCCESS", "ALL_NEWS: " + news.getTitle() + " was added ");
                } while (cursor.moveToNext());
                cursor.close();
                db.close();

            }
        } catch (Exception e) {

        }

        return allNewsList;
    }

    public List<News> searchNews(String searchText) {
        SQLiteDatabase db = openDB(false);
        List<News> allNewsList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + NewsTB +
                            " WHERE " + NewsTitle + " LIKE ? ",
                    new String[]{"%" + searchText + "%"});
            if (cursor.moveToFirst()) {
                do {
                    News news = new News();
                    news.setId(getColumnValue(cursor, NewsID));
                    news.setTitle(getColumnValue(cursor, NewsTitle));
                    news.setImage(getColumnValue(cursor, NewsImage));
                    news.setDesc(getColumnValue(cursor, NewsDesc));
                    news.setDate(new Date(getColumnValue(cursor, NewsDate)));
                    allNewsList.add(news);
                    //    Log.i("SUCCESS", "ALL_NEWS: " + news.getTitle() + " was added ");
                } while (cursor.moveToNext());
                cursor.close();
                db.close();

            }
        } catch (Exception e) {

        }

        return allNewsList;

    }

    private String getColumnValue(Cursor cursor, String colName) {
        return cursor.getString(cursor.getColumnIndex(colName));
    }


    public void createNews(News news, Boolean shouldUpdateIfExists) {
        SQLiteDatabase db = openDB(true);
        try {
            db.beginTransaction();
            long success = db.insertOrThrow(NewsTB, null, getContentValues(news));
            if (success != -1)
                Log.i("SUCCESS", "INSERT: " + news.getTitle() + " was successful " + success);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.i("FAILED ATTEMPT", "TYPE: INSERT: " + news.getTitle() + " " + e.getMessage());
            if (shouldUpdateIfExists) updateNews(news);
            db.endTransaction();
            db.close();
        }
    }


    public void updateNews(News news) {
        SQLiteDatabase db = openDB(false);
        try {
            int success = db.update(NewsTB, getContentValues(news), NewsID + " = ? ", new String[]{news.getId()});
            if (success == 1) Log.i("SUCCESS", "UPDATE: " + news.getTitle() + " was successful");
        } catch (Exception e) {
            Log.i("FAILED ATTEMPT", "TYPE: UPDATE: " + news.getTitle() + " " + e.getMessage());
            db.close();
        }
    }

    public void deleteNews(News news) {
        SQLiteDatabase db = openDB(false);
        try {
            int success = db.delete(NewsTB, NewsID + " = ? ", new String[]{news.getId()});
            if (success == 1) Log.i("SUCCESS", "DELETE: " + news.getTitle() + " was successful");
        } catch (Exception e) {
            Log.i("FAILED ATTEMPT", "TYPE: DELETE: " + news.getTitle() + " " + e.getMessage());
            db.close();
        }
    }


    private ContentValues getContentValues(News news) {
        ContentValues values = new ContentValues();
        values.put(NewsID, news.getId());
        values.put(NewsTitle, news.getTitle());
        values.put(NewsDesc, news.getDesc());
        values.put(NewsImage, news.getImage());
        values.put(NewsDate, news.getDate().toString());
        return values;
    }
}

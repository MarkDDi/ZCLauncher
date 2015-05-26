package com.word.wordinsidehome.service.dao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class WordinsideProvider extends ContentProvider {

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(new GameDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new RecommendDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new EducationDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new MovieDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new AppDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new HealthDAO(WordinsideProvider.this.getContext()).createTableString());
            db.execSQL(new LauncherDAO(WordinsideProvider.this.getContext()).createTableString());
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists wordinside");
            this.onCreate(db);
        }
    }

    private final String DB_NAME;
    private final int DB_VERSION;
    private SQLiteDatabase db;
    private UriMatcher mUriMatcher;

    public WordinsideProvider() {
        super();
        this.DB_NAME = "wordinside_db";
        this.DB_VERSION = 1;
        this.mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_HEALTH, 1);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_GAME, 2);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_RECOMMEND, 3);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_EDUCATION, 4);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_MOVIE, 5);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_APP, 6);
        this.mUriMatcher.addURI(BaseDAO.AUTHORITIES, BaseDAO.TABLE_LAUNCHER, 7);

    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (this.mUriMatcher.match(uri)) {
            case 1: {
                this.db.delete(BaseDAO.TABLE_HEALTH, selection, selectionArgs);
                break;
            }
            case 2: {
                this.db.delete(BaseDAO.TABLE_GAME, selection, selectionArgs);
                break;
            }
            case 3: {
                this.db.delete(BaseDAO.TABLE_RECOMMEND, selection, selectionArgs);
                break;
            }
            case 4: {
                this.db.delete(BaseDAO.TABLE_EDUCATION, selection, selectionArgs);
                break;
            }
            case 5: {
                this.db.delete(BaseDAO.TABLE_MOVIE, selection, selectionArgs);
                break;
            }
            case 6: {
                this.db.delete(BaseDAO.TABLE_APP, selection, selectionArgs);
                break;
            }
            case 7: {
                this.db.delete(BaseDAO.TABLE_LAUNCHER, selection, selectionArgs);
                break;
            }

        }

        return 0;

    }

    public String getType(Uri uri) {
        return uri.toString();
    }

    public Uri insert(Uri uri, ContentValues values) {
        switch (this.mUriMatcher.match(uri)) {
            case 1: {
                this.db.insert(BaseDAO.TABLE_HEALTH, null, values);
                break;
            }
            case 2: {
                this.db.insert(BaseDAO.TABLE_GAME, null, values);
                break;
            }
            case 3: {
                this.db.insert(BaseDAO.TABLE_RECOMMEND, null, values);
                break;
            }
            case 4: {
                this.db.insert(BaseDAO.TABLE_EDUCATION, null, values);
                break;
            }
            case 5: {
                this.db.insert(BaseDAO.TABLE_MOVIE, null, values);
                break;
            }
            case 6: {
                this.db.insert(BaseDAO.TABLE_APP, null, values);
                break;
            }
            case 7: {
                this.db.insert(BaseDAO.TABLE_LAUNCHER, null, values);
                break;
            }
        }

        return null;
    }

    @Override
    public boolean onCreate() {
        this.db = new DBHelper(this.getContext()).getWritableDatabase();
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (this.mUriMatcher.match(uri)) {
            case 1: {
                return this.db.query(BaseDAO.TABLE_HEALTH, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 2: {
                return this.db.query(BaseDAO.TABLE_GAME, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 3: {
                return this.db.query(BaseDAO.TABLE_RECOMMEND, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 4: {
                return this.db.query(BaseDAO.TABLE_EDUCATION, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 5: {
                return this.db.query(BaseDAO.TABLE_MOVIE, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 6: {
                return this.db.query(BaseDAO.TABLE_APP, null, selection, selectionArgs, null, null, sortOrder);
            }
            case 7: {
                return this.db.query(BaseDAO.TABLE_LAUNCHER, null, selection, selectionArgs, null, null, sortOrder);
            }

        }

        return null;

    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        this.mUriMatcher.match(uri);
        return 0;
    }
}


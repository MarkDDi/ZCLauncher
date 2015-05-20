package com.word.wordinsidehome.service.dao;

import android.net.Uri;
import java.util.ArrayList;
import com.word.wordinsidehome.service.entity.*;

public abstract class BaseDAO {
    public static final String AUTHORITIES = "WordinsideAuthorities";
    protected static final char CHARACTER_SLASH = '/';
    protected final String DROP_TABLE;
    public static final int MATRIX_HEALTH = 0;     
    public static final int MATRIX_GAME = 1;
    public static final int MATRIX_RECOMMEND = 2;
    public static final int MATRIX_EDUCATION = 3;       
    public static final int MATRIX_MOVIE = 4;     
    public static final int MATRIX_APP = 5;
   
    public static final String MATRIX_TYPE = "matrix_type";
    public static final String TABLE_HEALTH = "TABLE_HEALTH";
    public static final String TABLE_GAME = "TABLE_GAME";    
    public static final String TABLE_RECOMMEND = "TABLE_RECOMMEND";
    public static final String TABLE_EDUCATION = "TABLE_EDUCATION";
    public static final String TABLE_MOVIE = "TABLE_MOVIE";      
    public static final String TABLE_APP = "TABLE_APP";
    public static final String TABLE_LAUNCHER = "TABLE_LAUNCHER";	
  

    public static final Uri URI_HEALTH;
    public static final Uri URI_GAME;
    public static final Uri URI_RECOMMEND;
    public static final Uri URI_EDUCATION;
    public static final Uri URI_MOVIE;
    public static final Uri URI_APP;
    public static final Uri URI_LAUNCHER;
	
		static{
		URI_HEALTH = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
							.append('/').append("TABLE_HEALTH").toString());
		URI_GAME = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
							.append('/').append("TABLE_GAME").toString());
		URI_RECOMMEND = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
							.append('/').append("TABLE_RECOMMEND").toString());
		URI_EDUCATION = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
		          .append('/').append("TABLE_EDUCATION").toString());
		URI_MOVIE = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
		          .append('/').append("TABLE_MOVIE").toString());
		URI_APP = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
		          .append('/').append("TABLE_APP").toString());
		URI_LAUNCHER = Uri.parse(new StringBuffer().append("content://").append("WordinsideAuthorities")
		          .append('/').append("TABLE_LAUNCHER").toString());
		}
  

    public BaseDAO() {
        super();
        this.DROP_TABLE = "DROP TABLE IF EXISTS ";
    }

    public abstract String createTableString();

    public abstract void delete(String arg1, String[] arg2);

    public abstract String dropTable();

    public abstract void insert(WordinsideBaseEntity wd);

    public abstract void insert(ArrayList arg1);

    public abstract ArrayList query(String[] arg1, String arg2, String[] arg3, String arg4);

    public abstract void update(String arg2, String[] arg3);
}


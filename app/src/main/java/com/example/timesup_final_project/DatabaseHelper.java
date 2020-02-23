package com.example.timesup_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERDSION = 1;
    private static final String DATABASE_NAME = "TimesUp.db";
    private static final String USER_TABLE = "USER_TABLE";
    private static final String COL_1 = "username";
    private static final String COL_2 = "u_password";
    private static final String COL_3 = "firstname";
    private static final String COL_4 = "lastname";
    private static final String COL_5 = "email";
    private static final String COL_6 = "contact_no";

    private static final String CREATE_USER = "CREATE TABLE " + USER_TABLE + "( " +
            COL_1 + " TEXT PRIMARY KEY NOT NULL, " +
            COL_2 + " TEXT NOT NULL, " +
            COL_3 + " TEXT NOT NULL, " +
            COL_4 + " TEXT NOT NULL, " +
            COL_5 + " TEXT NOT NULL, " +
            COL_6 + " TEXT NOT NULL)";

    private static final String TASK_TABLE = "TASK_TABLE";
    private static final String TASK_COL_1 = "username";
    private static final String TASK_COL_2 = "task_title";
    private static final String TASK_COL_3 = "task_desc";
    private static final String TASK_COL_4 = "task_date";

    private static final String CREATE_TASK = "CREATE TABLE " + TASK_TABLE + "( " +
            TASK_COL_1 + " TEXT NOT NULL, " +
            TASK_COL_2 + " TEXT NOT NULL, " +
            TASK_COL_3 + " TEXT NOT NULL, " +
            TASK_COL_4 + ")";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERDSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

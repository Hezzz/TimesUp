package com.example.timesup_final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
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
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(UserClass user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, user.getUsername());
        values.put(COL_2, user.getPassword());
        values.put(COL_3, user.getFirstname());
        values.put(COL_4, user.getLastname());
        values.put(COL_5, user.getEmail());
        values.put(COL_6, user.getContactNo());

        long result = database.insert(USER_TABLE, null, values);
        if (result == -1) return false;
        else return true;
    }

    public String searchUser(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + COL_2 + " FROM " + USER_TABLE + " WHERE "
                + COL_1 + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(COL_2));
        }
        else return "false";
    }

    public void setUser(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + COL_3 + ", " + COL_4 + ", " +
                COL_5 + ", " + COL_6 + " FROM " + USER_TABLE + " WHERE "
                + COL_1 + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username + ""});
        cursor.moveToFirst();
        CurrentUser.setUser(cursor.getString(cursor.getColumnIndex(COL_3)), cursor.getString(cursor.getColumnIndex(COL_4)),
                cursor.getString(cursor.getColumnIndex(COL_5)), cursor.getString(cursor.getColumnIndex(COL_6)));
    }

    public boolean addNewDeadline(String username, String task_title,
                               String task_desc, String task_date){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COL_1, username);
        values.put(TASK_COL_2, task_title);
        values.put(TASK_COL_3, task_desc);
        values.put(TASK_COL_4, task_date);

        long result = database.insert(TASK_TABLE,null, values);
        if (result == -1) return false;
        else return true;
    }

    public void deleteDeadline(String username, String task_title,
                               String task_desc, String task_date){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TASK_TABLE, TASK_COL_1 + " = ? AND " +
                TASK_COL_2 + " = ? AND " + TASK_COL_3 + " = ? AND " + TASK_COL_4 + " = ?",
                new String[]{username, task_title, task_desc, task_date});
    }

}

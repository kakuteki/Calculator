package com.yagiyagi21.android.calculator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAdapter {

    private static final String DATABASE_NAME = "calculator.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "calculator";
    private static final String COL_ID = "_id";
    private static final String COL_MEMO = "memo";
    private static final String COL_FORMULA = "formula";

    private DatabaseHelper _dbHelper;
    private SQLiteDatabase _db;

    public DatabaseAdapter(Context context) {
        _dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        _db = _dbHelper.getWritableDatabase();
    }

    public void close() {
        _dbHelper.close();
    }

    public List<Map<String, String>> getAllMemos() {
        List<Map<String, String>> memoList = new ArrayList<>();

        Cursor cursor = _db.query(TABLE_NAME, null, null, null, null, null, null);

        Map<String, String> memo;
        while (cursor.moveToNext()) {
            memo = new HashMap<>();
            memo.put("id", cursor.getString(cursor.getColumnIndex(COL_ID)));
            memo.put("memo", cursor.getString(cursor.getColumnIndex(COL_MEMO)));
            memo.put("formula", cursor.getString(cursor.getColumnIndex(COL_FORMULA)));
            memoList.add(memo);
        }

        return memoList;
    }

    public long saveMemo(String memo, String formula) {
        String sqlInsert = "INSERT INTO calculator (memo, formula) VALUES (?, ?)";
        SQLiteStatement stmt = _db.compileStatement(sqlInsert);
        stmt.bindString(1, memo);
        stmt.bindString(2, formula);
        return stmt.executeInsert();
    }

    public boolean deleteMemo(int id) {
        return _db.delete(TABLE_NAME, COL_ID + "=" + id, null) > 0;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE " + TABLE_NAME +  "(");
            sb.append(COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
            sb.append(COL_MEMO + " TEXT,");
            sb.append(COL_FORMULA + " TEXT");
            sb.append(");");
            String sql = sb.toString();

            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

package com.example.callerinfo;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehandler {

	public static final String Data = "data";
	public static final String TableName = "mytable";
	public static final String Databasename = "mydb.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_CREATE = "create table mytable (data TEXT); ";

	DataBaseHelper dbhelper;
	Context context;
	SQLiteDatabase db;

	public Databasehandler(Context context) {
		this.context = context;
		dbhelper = new DataBaseHelper(context);
	}

	private static class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context context) {

			// TODO Auto-generated constructor stub
			super(context, Databasename, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("drop table if exists mytable");
			onCreate(db);
		}

	}

	public Databasehandler open() {
		db = dbhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbhelper.close();

	}

	public long insertdata(String data) {
		ContentValues content = new ContentValues();
		content.put(Databasehandler.Data, data);
		return db.insertOrThrow(TableName, null, content);
	}

	public Cursor returndata() {
		return db.query(TableName, new String[] { Data }, null, null, null,
				null, null);
	}

}

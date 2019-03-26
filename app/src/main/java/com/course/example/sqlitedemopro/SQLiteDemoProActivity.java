package com.course.example.sqlitedemopro;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.database.*;
import android.database.sqlite.*;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.TextView;
import android.content.ContentValues;
import android.text.method.ScrollingMovementMethod;

public class SQLiteDemoProActivity extends Activity {
	
	private TextView text;
	private SQLiteDatabase db;
	private ContentValues values;
	private Cursor cursor;
	private SQLHelper helper;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        text = (TextView) findViewById(R.id.Text);
        
        //let textview widget scroll
        text.setMovementMethod(new ScrollingMovementMethod());

//        db = openOrCreateDatabase(helper.DATABASE_NAME,
//                Context.MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE IF EXISTS " + helper.TABLE_NAME);
//        db.execSQL(helper.CREATE_TABLE);

        helper = new SQLHelper(this);

        //create database
        try {
        	db = helper.getWritableDatabase();
        } catch(SQLException e) { 
        						Log.d("SQLiteDemo", "Create database failed");
        						}
            
        //insert records        
     //   helper.addCourse(new Course("CS 480", "PEPE","3:30"));


        
    	//query database
        ArrayList<Course> courseList = helper.getCourseList();
        
        //write contents of list to screen        
        for (Course item : courseList) {
        	text.append(item.getName() + " " + item.getTeacher() + "\n" );
        }
        
    }
    
    //close database
    @Override
	protected void onPause() {
		super.onPause();
		if(db != null)
			db.close();		
	}
}
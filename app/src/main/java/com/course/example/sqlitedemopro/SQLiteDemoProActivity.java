package com.course.example.sqlitedemopro;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.database.*;
import android.database.sqlite.*;
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
        
        helper = new SQLHelper(this);
        
        //create database
        try {
        	db = helper.getWritableDatabase();
        } catch(SQLException e) { 
        						Log.d("SQLiteDemo", "Create database failed");
        						}
            
        //insert records        
        helper.addAnimal(new Animal("tiger", 4));
        helper.addAnimal(new Animal("zebra", 23));
        helper.addAnimal(new Animal("buffalo", 13));
        helper.addAnimal(new Animal("lion", 37));
        helper.addAnimal(new Animal("yak", 18));
                    
        
        //update buffalo to gorilla
    	helper.updateAnimal(new Animal("buffalo"), new Animal("gorilla"));
    	
    	//delete record
    	helper.deleteAnimal(new Animal("tiger"));
        
    	//query database
        ArrayList<Animal> animalList = helper.getAnimalList();
        
        //write contents of list to screen        
        for (Animal item : animalList) {
        	text.append(item.getName() + " " + item.getQuantity() + "\n" );   
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
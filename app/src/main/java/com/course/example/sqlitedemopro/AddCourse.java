package com.course.example.sqlitedemopro;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


public class AddCourse extends Activity {
    private EditText name_course;
    private EditText teacher;
    private EditText time;
    private Button add_course_button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        setTitle("Add Course");

        // Get a handle to all user interface elements
        name_course = (EditText) findViewById(R.id.class_name);
        teacher = (EditText) findViewById(R.id.teacher);
        time = (EditText) findViewById(R.id.time);
        add_course_button = (Button) findViewById(R.id.add_course_button);

        add_course_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //
            }
        });

    }


}

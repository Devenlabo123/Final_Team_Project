package com.course.example.sqlitedemopro;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
    private SQLHelper helper;


    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder = null;
    private int SIMPLE_NOTFICATION_ID = 25;



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
        helper = new SQLHelper(this);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //As of API 26 Notification Channels must be assigned to a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Channel foobar",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            mNotificationManager.createNotificationChannel(channel);
        }

        Intent notifyIntent = new Intent(this, homepage.class);

        //create pending intent to wrap intent so that it
        //will fire when notification selected.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)     //cancel Notification after clicking on it
                .setVibrate(new long[]{1000, 1000, 2000, 2000})
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        add_course_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                   int a = helper.getCourseList().size();
                Log.i("Added Number: ",""+a );
                String t = time.getText().toString();
                    if(t.matches("\\b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))")){

                        Log.i("Time entered is: ", t);
                    helper.addCourse(new Course(a,name_course.getText().toString(), teacher.getText().toString(),time.getText().toString()));
                    name_course.setText("");
                    teacher.setText("");
                    time.setText("");
                    mBuilder.setContentTitle("Class added to Schedule");
                    mBuilder.setContentText(name_course.getText().toString());
                        mNotificationManager.notify(SIMPLE_NOTFICATION_ID,
                                mBuilder.build());
                    }
                else {
                    name_course.setText("");
                    teacher.setText("");
                    time.setText("Course not added, Please enter time in correct format (eg. 2:00 PM)");
                    time.setSelectAllOnFocus(true);
                }

            }
        });




    }


}

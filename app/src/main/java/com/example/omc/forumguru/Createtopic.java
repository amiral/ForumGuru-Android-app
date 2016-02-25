package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Createtopic extends CustomActivity {

    private EditText topic,query;
    private Spinner spinner1;
    private Button btnSubmit;
    ParseUser obj=ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_createtopic, frameLayout);
        if(obj==null) {
            startActivity(new Intent(Createtopic.this, Index.class));
        }
        mDrawerList.setItemChecked(position, true);

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        topic=(EditText)findViewById(R.id.editText);
        query=(EditText)findViewById(R.id.editText2);

        addListenerOnButton();
    }
    // get the selected dropdown list value
    public void addListenerOnButton() {



        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                String name = user.getUsername();
                ParseObject obj = new ParseObject("topics");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy: MM :dd _ HH :mm : ss");
                String currentDateandTime = sdf.format(new Date());
                obj.put("topic", topic.getText().toString());
                obj.put("date", currentDateandTime);
                obj.put("topicby", name);
                obj.put("userid", user.getObjectId());

                obj.put("topic_cat", String.valueOf(spinner1.getSelectedItem()));

                obj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {



                        } else {
                            AlertDialog.Builder b = new AlertDialog.Builder(Createtopic.this);
                            b.setMessage("");
                            b.setTitle("Sorry");
                            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = b.create();
                            alert.show();
                        }
                    }
                });

                ParseObject reply = new ParseObject("reply");
                reply.put("by", user.getUsername());
                reply.put("topic", topic.getText().toString());
                reply.put("userid", user.getObjectId());

                SimpleDateFormat s = new SimpleDateFormat("yyyy: MM :dd _ HH :mm : ss");
                String currentDate = s.format(new Date());
                reply.put("date", currentDate);
                reply.put("reply_content", query.getText().toString());
                reply.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        Toast.makeText(Createtopic.this, "Successfully posted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Createtopic.this, Forum.class));

                    }
                });


            }

        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Createtopic.this, Index.class));
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUserStatus(boolean online)
    {

        obj.put("online", online);
        obj.saveEventually();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Createtopic.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }


}

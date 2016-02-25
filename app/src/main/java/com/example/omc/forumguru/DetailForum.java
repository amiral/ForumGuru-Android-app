package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailForum extends CustomActivity {
    protected java.util.List<ParseObject> replies;
    protected EditText text;
    protected Button btn;

    ParseUser obj=ParseUser.getCurrentUser();

    String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_detail_forum, frameLayout);
        mDrawerList.setItemChecked(position, true);

        text=(EditText)findViewById(R.id.txt);
        btn=(Button)findViewById(R.id.btnSend);
        if (obj == null) {
            Intent i = new Intent(DetailForum.this, Index.class);
            startActivity(i);
            Toast.makeText(DetailForum.this, "You have to login", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent get = getIntent();
            t = get.getStringExtra("topic");

           show();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (text.getText().toString().isEmpty()) {

                    }
                    else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);

                        ParseUser user = ParseUser.getCurrentUser();
                        String name = user.getUsername();
                        ParseObject obj = new ParseObject("reply");
                        obj.put("by", name);
                        obj.put("reply_content", text.getText().toString());
                        obj.put("userid",user.getObjectId());
                        obj.put("topic", t);


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy: MM :dd _ HH :mm : ss");
                        String currentDateandTime = sdf.format(new Date());
                        obj.put("date",currentDateandTime);
                        obj.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                text.setText("");

                             show();
                            }
                        });
                    }
                }
            });
        }
    }



    public void show(){

        getActionBar().setTitle(t);
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("reply");
        query.whereEqualTo("topic",t);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> status, ParseException e) {
                if (e == null) {
                    replies = status;

                    ListView l=(ListView)findViewById(R.id.lis);
                    DetailForumadapter adapter = new DetailForumadapter(DetailForum.this, replies);
                    l.setAdapter(adapter);
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(DetailForum.this);
                    b.setMessage(e.getMessage());
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
    }


    @Override
    protected void onResume() {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(DetailForum.this, Index.class));
        }
        show();
    }

    private void updateUserStatus(boolean online)
    {

        obj.put("online", online);
        obj.saveEventually();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){

            case R.id.action_logout:
                updateUserStatus(false);
                Intent i=new Intent(DetailForum.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;
            case R.id.action_home:

                Intent k=new Intent(DetailForum.this,UserList.class);
                startActivity(k);

        }

        return super.onOptionsItemSelected(item);
    }

}

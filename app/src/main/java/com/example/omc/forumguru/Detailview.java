package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class Detailview extends CustomActivity {

    protected TextView uname;
    protected TextView sta,sub;
    protected ImageView users,pic;
    String objectid,k;
    ParseUser currentuser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_detailview, frameLayout);
        mDrawerList.setItemChecked(position, true);

        if (currentuser == null) {
            Intent i = new Intent(Detailview.this, Index.class);
            startActivity(i);
            Toast.makeText(Detailview.this, "You have to login", Toast.LENGTH_SHORT).show();
        }
        else{
            uname = (TextView) findViewById(R.id.name);
            users=(ImageView)findViewById(R.id.icon);
            pic=(ImageView)findViewById(R.id.d_pic);

            sub = (TextView) findViewById(R.id.d);
            sta = (TextView) findViewById(R.id.sta);
            Intent get = getIntent();
            objectid = get.getStringExtra("objectid");


            ParseQuery<ParseObject> queryt = new ParseQuery<ParseObject>("status");
            queryt.getInBackground(objectid, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {

                    if (e == null) {
                        uname.setText(object.getString("statusby"));
                        sta.setText(object.getString("status"));
                        sub.setText(object.getString("detail"));
                        Picasso.with(Detailview.this).
                                load(object.getParseFile("image").getUrl()).
                                noFade().into(pic);

                        k=object.getString("userid");
                        ParseQuery<ParseUser> query = ParseUser.getQuery();

                        query.getInBackground(k, new GetCallback<ParseUser>() {
                            public void done(ParseUser o, ParseException e) {

                                Picasso.with(Detailview.this).
                                        load(o.getParseFile("profilepic").getUrl()).
                                        noFade().into(users);

                            }
                        });


                    } else {
                        AlertDialog.Builder b = new AlertDialog.Builder(Detailview.this);
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
    }
    private void updateUserStatus(boolean online)
    {
        ParseUser user=ParseUser.getCurrentUser();
        user.put("online", online);
        user.saveEventually();
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
                Intent i=new Intent(Detailview.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(currentuser==null) {
            startActivity(new Intent(Detailview.this, Index.class));
        }
    }

}

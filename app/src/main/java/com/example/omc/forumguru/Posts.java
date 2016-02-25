package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class Posts extends ListActivity {
    protected java.util.List<ParseObject> mstatus;
    ParseUser currentuser=ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);




        if(currentuser!=null){

            ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("status");
            query.orderByDescending("createdAt");

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> status, ParseException e) {
                    if(e==null){
                        mstatus=status;
                        Statusadapter adapter=new Statusadapter(getListView().getContext(),mstatus);
                        setListAdapter(adapter);
                    }
                    else{
                        AlertDialog.Builder b=new AlertDialog.Builder(Posts.this);
                        b.setMessage(e.getMessage());
                        b.setTitle("Sorry");
                        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert=b.create();
                        alert.show();
                    }


                }
            });

        }
        else{
            Intent takeusertologinpage=new Intent(Posts.this,Index.class);
            startActivity(takeusertologinpage);
            Toast.makeText(Posts.this, "You have to login", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject obj=mstatus.get(position);
        String objectid=obj.getObjectId();
        Intent i=new Intent(Posts.this,Detailview.class);
        i.putExtra("objectid",objectid);
        startActivity(i);
    }

    private void updateUserStatus(boolean online)
    {
        currentuser.put("online", online);
        currentuser.saveEventually();
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
                Intent i=new Intent(Posts.this,Login.class);
                startActivity(i);
                ParseUser.logOut();
            case R.id.action_home:

                Intent k=new Intent(Posts.this,UserList.class);
                startActivity(k);



        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();

        if(obj==null) {
            startActivity(new Intent(Posts.this, Index.class));
        }

    }
}

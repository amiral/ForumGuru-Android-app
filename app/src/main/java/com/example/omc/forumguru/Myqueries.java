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
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Myqueries extends ListActivity {

    protected java.util.List<ParseObject> topic;

    ParseUser currentuser=ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqueries);

        if(currentuser==null) {
            startActivity(new Intent(Myqueries.this, Index.class));
        }

        show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void updateUserStatus(boolean online)
    {

        currentuser.put("online", online);
        currentuser.saveEventually();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Myqueries.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;
            case R.id.action_home:

                Intent k=new Intent(Myqueries.this,UserList.class);
                startActivity(k);


        }

        return super.onOptionsItemSelected(item);
    }

    public void show(){

        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("topics");
        query.orderByDescending("createdAt");
        query.whereEqualTo("userid",currentuser.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> status, ParseException e) {
                if (e == null) {
                    topic = status;

                    Forumadaptera adapter = new Forumadaptera(getListView().getContext(), topic);
                    setListAdapter(adapter);
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(Myqueries.this);
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
            startActivity(new Intent(Myqueries.this, Index.class));
        }
        show();

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject obj=topic.get(position);

        String topic= (String) obj.get("topic");

        Intent i=new Intent(Myqueries.this,DetailForum.class);
        i.putExtra("topic", topic);
        startActivity(i);
    }

}

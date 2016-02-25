package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Forum extends ListActivity {

    protected java.util.List<ParseObject> topic;
    ParseUser obj=ParseUser.getCurrentUser();

    protected EditText text;
    private Spinner spinner1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);



        //spinner1 = (Spinner)findViewById(R.id.f_spin);
    text=(EditText)findViewById(R.id.searchtopic);

        if(obj==null) {
            startActivity(new Intent(Forum.this, Index.class));
        }
      show();
    }

    public void onClick(View v){

        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("topics");
        query.orderByDescending("createdAt");
        query.whereContains("topic",text.getText().toString());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> status, ParseException e) {
                if (e == null) {
                    topic = status;

                    Forumadapter adapter = new Forumadapter(getListView().getContext(), topic);
                    setListAdapter(adapter);
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(Forum.this);
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

    public void show(){

        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("topics");
        query.orderByDescending("createdAt");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> status, ParseException e) {
                if (e == null) {
                    topic = status;

                    Forumadapter adapter = new Forumadapter(getListView().getContext(), topic);
                    setListAdapter(adapter);
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(Forum.this);
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

    private void updateUserStatus(boolean online)
    {
        obj.put("online", online);
        obj.saveEventually();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser obj=ParseUser.getCurrentUser();
            if(obj==null) {
                startActivity(new Intent(Forum.this, Index.class));
            }
            show();

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject obj=topic.get(position);

            String topic= (String) obj.get("topic");

        Intent i=new Intent(Forum.this,DetailForum.class);
        i.putExtra("topic", topic);

        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Forum.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



            case R.id.action_fh:



                Intent f=new Intent(Forum.this,Forumcategory.class);
                f.putExtra("topic_cat", "Forum Hub");
                startActivity(f);

                break;

            case R.id.action_wd:



                Intent w=new Intent(Forum.this,Forumcategory.class);
                w.putExtra("topic_cat", "Web development");
                startActivity(w);

                break;
            case R.id.action_pl:



                Intent p=new Intent(Forum.this,Forumcategory.class);
                p.putExtra("topic_cat", "Programming languages");
                startActivity(p);

                break;
            case R.id.action_os:



                Intent o=new Intent(Forum.this,Forumcategory.class);
                o.putExtra("topic_cat", "Operating systems");
                startActivity(o);

                break;
            case R.id.action_dbms:



                Intent d=new Intent(Forum.this,Forumcategory.class);
                d.putExtra("topic_cat", "Dbms");
                startActivity(d);

                break;
            case R.id.action_rb:



                Intent r=new Intent(Forum.this,Forumcategory.class);
                r.putExtra("topic_cat", "Robotics");
                startActivity(r);

                break;
            case R.id.action_iot:



                Intent q=new Intent(Forum.this,Forumcategory.class);
                q.putExtra("topic_cat", "Internet of things");
                startActivity(q);

                break;
            case R.id.action_ar:



                Intent ar=new Intent(Forum.this,Forumcategory.class);
                ar.putExtra("topic_cat", "Arduino");
                startActivity(ar);

                break;

            case R.id.action_rs:



                Intent rs=new Intent(Forum.this,Forumcategory.class);
                rs.putExtra("topic_cat", "Raspberry pi");
                startActivity(rs);

                break;
            case R.id.action_au:



                Intent au=new Intent(Forum.this,Forumcategory.class);
                au.putExtra("topic_cat", "Automobiles");
                startActivity(au);

                break;
            case R.id.action_g:



                Intent g=new Intent(Forum.this,Forumcategory.class);
                g.putExtra("topic_cat", "Gadgets");
                startActivity(g);

                break;
            case R.id.action_w:



                Intent wi=new Intent(Forum.this,Forumcategory.class);
                wi.putExtra("topic_cat", "Windows");
                startActivity(wi);

                break;
            case R.id.action_sm:



                Intent sm=new Intent(Forum.this,Forumcategory.class);
                sm.putExtra("topic_cat", "Social media");
                startActivity(sm);

                break;
            case R.id.action_e:



                Intent e=new Intent(Forum.this,Forumcategory.class);
                e.putExtra("topic_cat", "Entertainment");
                startActivity(e);

                break;
            case R.id.action_an:



                Intent di=new Intent(Forum.this,Forumcategory.class);
                di.putExtra("topic_cat", "Android");
                startActivity(di);

                break;


        }

        return super.onOptionsItemSelected(item);
    }

}

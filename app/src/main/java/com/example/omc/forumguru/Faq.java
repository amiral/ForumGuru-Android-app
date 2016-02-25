package com.example.omc.forumguru;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class Faq extends CustomActivity {


    ParseUser obj=ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        getLayoutInflater().inflate(R.layout.activity_faq, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ListView l=(ListView)findViewById(R.id.sm);

        Myadapter adapter = new Myadapter(this, generateData());

        l.setAdapter(adapter);


        if(obj==null) {
            startActivity(new Intent(Faq.this, Index.class));
        }

    }

    @Override
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

                Intent i=new Intent(Faq.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume()
    {
        super.onResume();

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Faq.this, Index.class));
        }

    }


    private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("What is Forumguru?","Forumguru is basically a forum to let you know about the new topics in each and every field of ur interest"));
        items.add(new Item("Why Forumguru ??","We are Unique ! We serve better ! \n" +
                "Get ur doubts cleared instantly with our growing moderator Crew."));
        items.add(new Item("How can i create my own Category?","You just have to inform us by dropping us a message through \"Contact\" in the navigation tab.\n" +
                "And we will add that category to our Forum ASAP."));
        items.add(new Item("How can i create my own Category?","You just have to inform us by dropping us a message through \"Contact\" in the navigation tab.\n" +
                "And we will add that category to our Forum ASAP."));
        items.add(new Item("How can i create a new topic in an existing category?","Its so simple.Just login to Forumguru and Click on Create topic.Add your topic name \n" +
                "and a short description about the topic .Now ur topic is ready.Enjoy Foruming :) "));

        items.add(new Item("Why do we have Image Gallery ?","Picturization is more effective than writing lot many paragraphs. and running frames gave wings to entertainment!! Lets have some fun with techi stuffs. "));
        items.add(new Item("What services do you offer ??","We own one of the most active and creative group of Web devs and Android devs ,who can build websites and Android apps for you! or any other work related to programming. "));
        return items;
    }

}

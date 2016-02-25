package com.example.omc.forumguru;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.parse.ParseUser;

public class Loguot extends Activity {
    ParseUser obj=ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguot);

        updateUserStatus(false);

        Intent i=new Intent(Loguot.this,Index.class);
        startActivity(i);
        ParseUser.logOut();
    }
    private void updateUserStatus(boolean online)
    {

        obj.put("online", online);
        obj.saveEventually();
    }

}

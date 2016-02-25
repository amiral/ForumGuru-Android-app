package com.example.omc.forumguru;

import android.app.Application;

import com.parse.Parse;


public class ChatApp extends Application {


    @Override
    public void onCreate()
    {
        super.onCreate();

        Parse.initialize(this, "G5t66FU7ctfu1z3r3fCBCxiSCwryUvUUiEdtSaKl",
                "Wt03QcfwV0Df7j3fIlLulBQObeCmpUwON2AgnKq5");

    }
}

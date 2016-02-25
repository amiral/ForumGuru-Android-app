package com.example.omc.forumguru;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.parse.ParseUser;

public class Index extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ParseUser obj=ParseUser.getCurrentUser();
        if(obj!=null) {
            startActivity(new Intent(Index.this, UserList.class));
        }

        Rateus.app_launched(this);

    }

    public void onClick(View v){
        if(v.getId()==R.id.bl){
            startActivity(new Intent(Index.this,Login.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);

    }
}

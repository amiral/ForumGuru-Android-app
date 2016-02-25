package com.example.omc.forumguru;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class push extends Activity {
    ParseUser imgupload=ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.user);
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("androidbegin.png", image);
        // Upload the image into Parse Cloud
        file.saveInBackground();
        ParseUser imgupload=ParseUser.getCurrentUser();
        UserList.user=imgupload;
        imgupload.put("profilepic", file);

        imgupload.saveInBackground();
        startActivity(new Intent(push.this,UserList.class));
        updateUserStatus(true);


    }

    private void updateUserStatus(boolean online)
    {
        imgupload.put("online", online);
        imgupload.saveEventually();
    }

}

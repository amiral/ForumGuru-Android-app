package com.example.omc.forumguru;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.text.ParseException;

public class Forgotpassword extends Activity {

    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        text=(EditText)findViewById(R.id.mail);
    }

    public void onClick(View v){
        ParseUser.requestPasswordResetInBackground(text.getText().toString(), new RequestPasswordResetCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // An email was successfully sent with reset instructions.
                    Toast.makeText(Forgotpassword.this, "Password reset link is sent to your mail id", Toast.LENGTH_LONG).show();
                } else {
                    Utils.showDialog(
                            Forgotpassword.this,
                            "Error:" + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }


        });

    }
}

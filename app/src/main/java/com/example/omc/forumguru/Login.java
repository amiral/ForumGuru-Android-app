package com.example.omc.forumguru;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseUser;



public class Login extends Activity
{

    private EditText user;

    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj!=null) {


            startActivity(new Intent(Login.this, UserList.class));
            obj.put("online", true);
            obj.saveEventually();

        }

    }


    public void onClick(View v)
    {

        if (v.getId() == R.id.btnReg)
        {
            startActivityForResult(new Intent(this,Register.class), 10);
        }
        else
        {

            String u = user.getText().toString();
            String p = pwd.getText().toString();
            if (u.length() == 0 || p.length() == 0)
            {
                Utils.showDialog(this, R.string.err_fields_empty);
                return;
            }
            final ProgressDialog dia = ProgressDialog.show(this, null,
                    getString(R.string.alert_wait));
            ParseUser.logInInBackground(u, p, new LogInCallback() {

                @Override
                public void done(ParseUser user, com.parse.ParseException e) {
                    dia.dismiss();
                    if (user != null) {


                        ParseUser obj=ParseUser.getCurrentUser();
                        if(obj!=null) {


                            startActivity(new Intent(Login.this, UserList.class));
                            obj.put("online", true);
                            obj.saveEventually();

                        }

                        finish();
                    } else {
                        Utils.showDialog(
                                Login.this,
                                getString(R.string.err_login) + " "
                                        + e.getMessage());
                        e.printStackTrace();
                    }
                }


            });
        }
    }

    public void call(View v){
        startActivity(new Intent(Login.this,Forgotpassword.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK)
            finish();

    }
}

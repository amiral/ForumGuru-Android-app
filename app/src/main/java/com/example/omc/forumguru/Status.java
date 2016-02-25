package com.example.omc.forumguru;
 import android.app.AlertDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.net.Uri;
 import android.os.Bundle;
 import android.os.Environment;

 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.parse.ParseException;
 import com.parse.ParseFile;

 import com.parse.ParseUser;
 import com.parse.SaveCallback;
 import com.squareup.picasso.Picasso;


public class Status extends CustomActivity {
    protected Button update,pic,upload;
    protected EditText status;
    protected TextView tv,tv1,tv2;
    public static final int TAKE_PIC_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;
    private Uri mMediaUri;
    protected ImageView mPreviewImageView;
    ParseUser currentuser=ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_status, frameLayout);
        mDrawerList.setItemChecked(position, true);

        if(currentuser==null){
            Intent i=new Intent(Status.this,Index.class);
            startActivity(i);
            Toast.makeText(Status.this, "You have to login to update the status", Toast.LENGTH_SHORT).show();
        }
        else {

            status = (EditText) findViewById(R.id.c_sub);
            update = (Button) findViewById(R.id.updatestatus);
            pic = (Button) findViewById(R.id.ac_pic);
            upload = (Button) findViewById(R.id.ac_picup);
            mPreviewImageView=(ImageView)findViewById(R.id.user_pic);
            tv=(TextView)findViewById(R.id.ac_status);
            tv1=(TextView)findViewById(R.id.ac_user);
            tv2=(TextView)findViewById(R.id.ac_email);
            tv1.setText( currentuser.getUsername());
            tv2.setText("email : "+ currentuser.getEmail());

            tv.setText("status : " + currentuser.getString("status"));


            ParseUser object= ParseUser.getCurrentUser();

            Picasso.with(Status.this).
                    load(object.getParseFile("profilepic").getUrl()).
                    noFade().into(mPreviewImageView);



            //listen to add button click
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Status.this);
                    builder.setTitle("Upload a photo");
                    builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //upload image
                            Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            choosePictureIntent.setType("image/*");
                            startActivityForResult(choosePictureIntent, CHOOSE_PIC_REQUEST_CODE);

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });




            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create parse object for image to upload

                    final ParseUser imageUpload = ParseUser.getCurrentUser();
                    try {
                        //convert image to bytes for upload.
                        byte[] fileBytes = FileHelper.getByteArrayFromFile(Status.this, mMediaUri);
                        if (fileBytes == null) {
                            //there was an error
                            Toast.makeText(getApplicationContext(), "There was an error. Try again!", Toast.LENGTH_LONG).show();
                        } else {

                            fileBytes = FileHelper.reduceImageForUpload(fileBytes);
                            String fileName = FileHelper.getFileName(Status.this, mMediaUri, "image");
                            final ParseFile file = new ParseFile(fileName, fileBytes);

                            imageUpload.saveEventually(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        imageUpload.put("profilepic", file);
                                        imageUpload.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                Toast.makeText(getApplicationContext(), "Success Uploading iMage!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        //there was an error
                                        //there was an error
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }

                    } catch (Exception e1) {
                        Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });







            update.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String text = status.getText().toString();
                    if (text.isEmpty()) {
                        AlertDialog.Builder b = new AlertDialog.Builder(Status.this);
                        b.setMessage("Status should not be empty dear..");
                        b.setTitle("Oops!!!");
                        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = b.create();
                        alert.show();
                    } else {


                        ParseUser user = ParseUser.getCurrentUser();
                        user.put("status", status.getText().toString());

                        user.saveEventually();

                        Toast.makeText(Status.this, "status updated", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Status.this, UserList.class);
                        startActivity(i);


                    }
                }
            });

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Status.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
    //check if external storage is mounted. helper method
    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CHOOSE_PIC_REQUEST_CODE){
                if(data == null){
                    Toast.makeText(getApplicationContext(), "Image cannot be null!", Toast.LENGTH_LONG).show();
                }
                else{
                    mMediaUri = data.getData();
                    //set previews
                    mPreviewImageView.setImageURI(mMediaUri);
                }
            }else {

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);
                //set previews

                mPreviewImageView.setImageURI(mMediaUri);

            }

        }else if(resultCode != RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Status.this, Index.class));
        }

    }
}

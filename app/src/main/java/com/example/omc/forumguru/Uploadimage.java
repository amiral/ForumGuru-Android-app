package com.example.omc.forumguru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Uploadimage extends CustomActivity {
    public static final int TAKE_PIC_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;

    protected Button mAddImageBtn;
    protected Button mUploadImageBtn;
    protected ImageView mPreviewImageView;

    private Uri mMediaUri;

    ParseUser obj=ParseUser.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_uploadimage, frameLayout);
        mDrawerList.setItemChecked(position, true);
        //initialize
        mAddImageBtn = (Button)findViewById(R.id.addImageButton);
        mUploadImageBtn = (Button)findViewById(R.id.uploadImageButton);
        mPreviewImageView = (ImageView)findViewById(R.id.previewImageView);


        if(obj==null) {
            startActivity(new Intent(Uploadimage.this, Index.class));
        }
        //listen to add button click
        mAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Uploadimage.this);
                builder.setTitle("Upload or Take a photo");
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







        //listen to upload button click
        mUploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create parse object for image to upload
                final ParseObject imageUpload = new ParseObject("img");
                try{
                    //convert image to bytes for upload.
                    byte[] fileBytes = FileHelper.getByteArrayFromFile(Uploadimage.this, mMediaUri);
                    if(fileBytes == null){
                        //there was an error
                        Toast.makeText(getApplicationContext(), "There was an error. Try again!", Toast.LENGTH_LONG).show();
                    }else{

                        fileBytes = FileHelper.reduceImageForUpload(fileBytes);
                        String fileName = FileHelper.getFileName(Uploadimage.this, mMediaUri, "image");
                        final ParseFile file = new ParseFile(fileName, fileBytes);
                        imageUpload.saveEventually(new SaveCallback() {
                            @Override
                            public void done(com.parse.ParseException e) {
                                if (e == null) {

                                    imageUpload.put("imageContent", file);
                                    imageUpload.saveInBackground(new SaveCallback() {

                                        @Override
                                        public void done(com.parse.ParseException e) {
                                            Toast.makeText(getApplicationContext(), "Success Uploading iMage!", Toast.LENGTH_LONG).show();

                                        }


                                    });
                                } else {
                                    //there was an error
                                    //there was an error
                                    Toast.makeText(getApplicationContext(), "Oops! image size is too large", Toast.LENGTH_LONG).show();
                                }
                            }
                        });




                }

                }
                        catch (Exception e1){
                    Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //inner helper method
    private Uri getOutputMediaFileUri(int mediaTypeImage) {

        if(isExternalStorageAvailable()){
            //get the URI
            //get external storage dir
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "UPLOADIMAGES");
            //create subdirectore if it does not exist
            if(!mediaStorageDir.exists()){
                //create dir
                if(! mediaStorageDir.mkdirs()){

                    return null;
                }
            }
            //create a file name
            //create file
            File mediaFile = null;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            if(mediaTypeImage == MEDIA_TYPE_IMAGE){
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            }
            //return file uri
            Log.d("UPLOADIMAGE", "FILE: " + Uri.fromFile(mediaFile));

            return Uri.fromFile(mediaFile);
        }else {

            return null;
        }

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

                Intent i=new Intent(Uploadimage.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Uploadimage.this, Login.class));
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == CHOOSE_PIC_REQUEST_CODE){
                if(data == null){
                    Toast.makeText(getApplicationContext(), "Image cannot be null!", Toast.LENGTH_LONG).show();
                }else{
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


}

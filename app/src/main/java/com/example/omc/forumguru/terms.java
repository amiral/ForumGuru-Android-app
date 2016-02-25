package com.example.omc.forumguru;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class terms extends CustomActivity {

    ParseUser obj=ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_terms, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ListView l=(ListView)findViewById(R.id.smp);

        Myadaptera adapter = new Myadaptera(this, generateData());

        l.setAdapter(adapter);

        if(obj==null) {
            startActivity(new Intent(terms.this, Index.class));
        }
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

                Intent i=new Intent(terms.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ParseUser obj=ParseUser.getCurrentUser();

        if(obj==null) {
            startActivity(new Intent(terms.this, Index.class));
        }

    }

    private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();

        items.add(new Item(""," As a part of forumguru you are allowed to jolt down your thoughts and express your opinion freely but not in a manner that it could offend another user.\n" ));
        items.add(new Item(""," You may not upload any viruses, worms, Trojan horses, or other forms of harmful computer code, nor subject Forumguru network or servers to unreasonable traffic loads, or otherwise engage in conduct deemed disruptive to the ordinary operation of the app." ));
        items.add(new Item("","The information about you, provided to us must be accurate,and give us no reason for suspicion." ));
        items.add(new Item("","This app deals with discussions on technical as well as non-technical fields but if we do find that information posted is inappropriate we are allowed to take necessary action. " ));
        items.add(new Item(""," A fellow user’s personal information is not to be misused, and will not be tolerated if you are found doing so.\n" ));
        items.add(new Item("","You are strictly prohibited from communicating on or through this site any unlawful, harmful, offensive, threatening, abusive, libellous, harassing, defamatory, vulgar, obscene, profane, hateful, fraudulent, racially, ethnically, or otherwise objectionable material of any sort, or any material that encourages conduct that would constitute a criminal offense, give rise to civil liability, or otherwise violate any applicable law. " ));
        items.add(new Item(""," We are permitted to remove any sort of post, without your consent, if we feelit will lead to an issue or pose a threat to the integrity of our app, also debar anyone for misconduct and on grounds of suspicion.\n" +
                "\n" ));


        return items;
    }
}

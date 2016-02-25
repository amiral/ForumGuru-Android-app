package com.example.omc.forumguru;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class Aboutus extends CustomActivity {
    ParseUser obj=ParseUser.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_aboutus, frameLayout);
        mDrawerList.setItemChecked(position, true);

        ListView m=(ListView)findViewById(R.id.ltt);

        ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Aboutus.this, Index.class));
        }

        adapterabout adapter2 = new adapterabout(this, generateData());
        m.setAdapter(adapter2);
    }

    protected void onResume()
    {
        super.onResume();
ParseUser obj=ParseUser.getCurrentUser();
        if(obj==null) {
            startActivity(new Intent(Aboutus.this, Index.class));
        }

    }
    private ArrayList<Itemabout> generateData(){
        ArrayList<Itemabout> items = new ArrayList<Itemabout>();
        items.add(new Itemabout("Chandan A V","founder","An aspiring electronics and communication engineer from SJCE, Mysore. An outstanding programmer with immense passion for coding. The geek with a vision to bring together technological enthusiasts from everywhere!"));
        items.add(new Itemabout("Shashank S Gowda","Co-founder","A person with keen desire to learn and implement better ideas in a better way and also with the aspiration to become a notable programmer."));
        items.add(new Itemabout("Suraj Revankar","Co-founder","One of those young and energetic aspiring engineers,Suraj is sincere volunteer and an efficient leader.His technical knowledge adds to enthusiasm making him a balanced professional."));

        items.add(new Itemabout("Ravindra Nayak","Co-founder","An engineer who is so much into technology,and has a expertise in designing and understanding things.\n"));
        items.add(new Itemabout("Amogh Vijay","Co-founder","An aspiring electronics and communication engineer with his skills and creativity tries to entertain everyone.He thinks himself as anarchist."));



        items.add(new Itemabout("Akshay Krishnan","Moderator",""));
        items.add(new Itemabout("Krithika Govind","Moderator",""));
        items.add(new Itemabout("Namitha D'souza","Moderator",""));
        items.add(new Itemabout("Bipin S Kumar","Moderator",""));
        items.add(new Itemabout("Umesh S","Moderator",""));
        items.add(new Itemabout("Meghana K Urs","Moderator",""));
        items.add(new Itemabout("Raksha","Moderator",""));
        items.add(new Itemabout("Anjana S","Moderator",""));

        return items;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){

            case R.id.action_logout:

                updateUserStatus(false);

                Intent i=new Intent(Aboutus.this,Index.class);
                startActivity(i);
                ParseUser.logOut();
                break;




        }

        return super.onOptionsItemSelected(item);
    }
    private void updateUserStatus(boolean online)
    {

        obj.put("online", online);
        obj.saveEventually();
    }


}

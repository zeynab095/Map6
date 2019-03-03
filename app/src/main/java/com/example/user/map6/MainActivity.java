package com.example.user.map6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView r1 ;
    Button b1;
    //String[] s =new String[]{};
    MyAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sh1 = getSharedPreferences("MyOwnShared", MODE_PRIVATE);
        Map<String, ?> prefsMap = sh1.getAll();
        int i=0;
        String[] s =new String[prefsMap.size()];
        if(prefsMap.size()!=0) {
            for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
                Log.d("map values",entry.getKey() + ": " +
                        entry.getValue().toString());
            s[i] = entry.getKey();
            i++;
         }
        }
        r1 =(RecyclerView) findViewById(R.id.myRecycler);
        ad = new MyAdapter(this,s);
        r1.setAdapter(ad);
        r1.setLayoutManager(new LinearLayoutManager(this));
        b1 =(Button)findViewById(R.id.b1);
    }

    public void goTest(View view) {
        Intent i1= new Intent(this,MapsActivity.class); //Zeynabin activitysi (initial picker)
        startActivity(i1);
    }
   // public void goPage3(View view) {
  //     Intent i1= new Intent(this,test.class);
  //     startActivity(i1);
   // }
}

package com.example.user.map6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class recycler extends AppCompatActivity {
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        b1 =(Button)findViewById(R.id.b1);
    }


    public void goPage3(View view) {
        Intent i1= new Intent(this,test.class);
        SharedPreferences sh1 = getSharedPreferences("MyOwnShared",MODE_APPEND);
        String s =b1.getText().toString();
        String s1=sh1.getString(s,"");
        String [] arr = s1.split(",");

        i1.putExtra("source",arr[0]);
        i1.putExtra("destination",arr[1]);
        i1.putExtra("meter",arr[2]);

        startActivity(i1);
    }
}

package com.example.user.map6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class test extends AppCompatActivity {
    TextView dest,meter;
    TextView res;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        dest=(TextView) findViewById(R.id.dest);
      //  meter=(TextView) findViewById(R.id.wakeup);
        res = (TextView) findViewById(R.id.result);
        spinner = (Spinner) findViewById(R.id.list);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meters, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        Bundle b1= getIntent().getExtras();
        if(b1!=null) {
            String name = b1.getString("name");

            SharedPreferences sh1 = getSharedPreferences("MyOwnShared", MODE_APPEND);
            String s1 = sh1.getString(name, "");
            String[] arr = s1.split(",");
            dest.setText(arr[0]);
            spinner.setSelection(Integer.parseInt(arr[1]));
        }
    }

    public void saveData(View view) {
        String s[]={dest.getText().toString(),String.valueOf(spinner.getSelectedItemPosition())};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]).append(",");
        }
        SharedPreferences sh = getSharedPreferences("MyOwnShared",MODE_PRIVATE);
        SharedPreferences.Editor myEdit =sh.edit();
        myEdit.putString(dest.getText().toString(),sb.toString());
        myEdit.commit();
    }

    public void checkInput (View view){

        String d= dest.getText().toString();
        String text = spinner.getSelectedItem().toString();

    }
}
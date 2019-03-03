package com.example.user.map6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Sevinj on 11/11/2017.
 */

public class Start extends AppCompatActivity {

    EditText destination;
    TextView res;
    Spinner spinner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.start_page);
        //location = (EditText) findViewById(R.id.loc);
        destination = (EditText) findViewById(R.id.dest);
        res = (TextView) findViewById(R.id.result);
        spinner = (Spinner) findViewById(R.id.list);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meters, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

   /* public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id){
        s =(String)parent.getItemAtPosition(pos);
    }

   public void onNothingSelected(AdapterView<?> parent){

} */
//Spinner spinner = (Spinner) findViewById(R.id.list);
//spinner.setOnItemSelectedListener(this);

public void checkInput (View view){

//    String l = location.getText().toString();
    String d= destination.getText().toString();
    String text = spinner.getSelectedItem().toString();
  //  String fin= l + "\n"+d+"\n"+text;
    //res.setText(fin);
}
}

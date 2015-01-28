package com.example.deckbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	TextView decksText;
	EditText deckField;
	ArrayList<String> list;
	TinyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deckField = (EditText)findViewById(R.id.editText1);
        decksText = (TextView)findViewById(R.id.decksTextView);
        db = new TinyDB(this);
        

        decksText.setText(db.getList("deckNames").toString());
    }
    
    public void goSearch(View view){
    	Intent intent = new Intent(this, SearchActivity.class);
    	startActivity(intent);
    }
    
    public void add(View view) {
    	String deckName = deckField.getText().toString();
    	deckField.clearFocus();
    	deckField.setText("");
    	
    	ArrayList<String> newList;
//    	newList = new ArrayList<String>();
//    	try{
    		newList = db.getList("deckNames");
//    	}
//    	catch (Exception e){
//    		newList = new ArrayList<String>();
//    	}
    	
    	newList.add(deckName);
    	db.putList("deckNames", newList);
    	decksText.setText(db.getList("deckNames").toString());
    }
    
    public void remove(View view) {
    	String deckName = deckField.getText().toString();
    	db.remove(deckName);
    	ArrayList<String> newList;
    	newList = db.getList("deckNames");
    	newList.remove(deckName);
    	db.putList("deckNames", newList);
    	
    	deckField.clearFocus();
    	deckField.setText("");
    	decksText.setText(db.getList("deckNames").toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Toast.makeText(this, "No settings, sorry",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

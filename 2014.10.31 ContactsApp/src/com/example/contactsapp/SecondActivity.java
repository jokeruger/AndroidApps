package com.example.contactsapp;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends ActionBarActivity {

	TextView text;
	MySQLiteHelper db;
	EditText editText1, editText2, editText3, editText4, editText5, editText6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		text=(TextView)findViewById(R.id.textView1);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		editText5 = (EditText) findViewById(R.id.editText5);
		editText6 = (EditText) findViewById(R.id.editText6);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		db = new MySQLiteHelper(this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void search(View view){
		String last = editText1.getText().toString().trim();
		String first = editText2.getText().toString().trim();
		String address = editText3.getText().toString().trim();
		String email = editText4.getText().toString().trim();
		String phone = editText5.getText().toString().trim();
		
		if (last=="" && first == "" && address == "" && email == "" && phone == ""){
			showAll(null);
		}
		else {
			String where = " WHERE 1=1";
			if (last.length()>0) {
				where = where + " AND UPPER(last) LIKE \"%" + last.toUpperCase() + "%\"";
			}
			if (first.length()>0) {
				where = where + " AND UPPER(first) LIKE \"%" + first.toUpperCase() + "%\"";
			}
			if (address.length()>0) {
				where = where + " AND UPPER(address) LIKE \"%" + address.toUpperCase() + "%\"";
			}
			if (email.length()>0) {
				where = where + " AND UPPER(email) LIKE \"%" + email.toUpperCase() + "%\"";
			}
			if (phone.length()>0){
				where = where + " AND phone LIKE \"" + phone + "%\"";
			}
			
			List<Contact> list = db.getSome(where);
			
			String contacts = "";
			for (Contact contact: list) {
				contacts = contacts + contact.toString() + "\n\n";
			}
			if (contacts=="") {
				text.setText("~~~ NONE FOUND ~~~~");
			}
			else {
				text.setText(contacts);
			}
			
			editText1.setText("");
			editText2.setText("");
			editText3.setText("");
			editText4.setText("");
			editText5.setText("");
		}
	}
	
	public void showAll(View view){
		String contacts = "";
		List<Contact> list = db.getAllContacts();
		for (Contact contact: list) {
			contacts = contacts + contact.toString() + "\n\n";
		}
		text.setText(contacts);
	}
	
	public void delete(View view){
		String id = editText6.getText().toString().trim();
		db.deleteContactById(id);
		editText6.setText("");
		showAll(view);
	}
	
}

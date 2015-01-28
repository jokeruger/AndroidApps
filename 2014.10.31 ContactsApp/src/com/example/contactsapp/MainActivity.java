package com.example.contactsapp;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

	EditText editText1, editText2, editText3, editText4, editText5;
	MySQLiteHelper db;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		editText4 = (EditText) findViewById(R.id.editText4);
		editText5 = (EditText) findViewById(R.id.editText5);
		
		db = new MySQLiteHelper(this);
		
		// add Books
//		db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
//		db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
//		db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));
		
		// get all books
//		List<Book> list = db.getAllBooks();
		
		// delete one book
//		db.deleteBook(list.get(0));
		
		// get all books
//		db.getAllBooks();
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * @param view
	 */
	public void submitContact(View view){
		String last = editText1.getText().toString().trim();
		String first = editText2.getText().toString().trim();
		String address = editText3.getText().toString().trim();
		String email = editText4.getText().toString().trim();
		String phone = editText5.getText().toString().trim();
		
		if (last.length()>0 || first.length()>0 || address.length()>0 || email.length()>0 || phone.length()>0) {
			db.addContact(new Contact(last, first, address, email, phone));
		}

		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
		editText5.setText("");
	}
	
	public void goSearch(View view){
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
	}
}

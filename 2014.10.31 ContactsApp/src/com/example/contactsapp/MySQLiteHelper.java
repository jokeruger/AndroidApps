package com.example.contactsapp;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "ContactDB";
	
	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";
	
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_LAST = "last";
	private static final String KEY_FIRST = "first";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PHONE = "phone";
	
	private static final String[] COLUMNS = {KEY_ID,KEY_LAST,KEY_FIRST,KEY_ADDRESS,KEY_EMAIL,KEY_PHONE};
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create contact table
		String CREATE_CONTACT_TABLE = "CREATE TABLE contacts ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " 
																	+ "last TEXT, "
																	+ "first TEXT, "
																	+ "address TEXT, "
																	+ "email TEXT, "
																	+ "phone TEXT)";
		// create contacts table
		db.execSQL(CREATE_CONTACT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older contacts table if existed
		db.execSQL("DROP TABLE IF EXISTS contacts");
		
		// create fresh contacts table
		this.onCreate(db);
	}
	
	public void addContact(Contact contact) {
		
		//for logging
		Log.d("addContact", contact.toString());
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_LAST, contact.getLast());
		values.put(KEY_FIRST, contact.getFirst());
		values.put(KEY_ADDRESS, contact.getAddress());
		values.put(KEY_EMAIL, contact.getEmail());
		values.put(KEY_PHONE, contact.getPhone());
		
		// 3. insert
		db.insert(TABLE_CONTACTS, // table
				null, //nullColumnHack
				values); // key/value -> keys = column names/ values = column values
		
		// 4. close
		db.close(); 
	}

	public Contact getContact (int id) {
		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		
		// 2. build query
		Cursor cursor = 
				db.query(TABLE_CONTACTS, // a. table
						COLUMNS, // b. column names
						" id = ?", // c. selections 
						new String[] { String.valueOf(id) }, // d. selections args
						null, // e. group by
						null, // f. having
						null, // g. order by
						null); // h. limit
		
		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();
		
		// 4. build contact object
		Contact contact = new Contact();
		contact.setId(Integer.parseInt(cursor.getString(0)));
		contact.setLast(cursor.getString(1));
		contact.setFirst(cursor.getString(2));
		contact.setAddress(cursor.getString(3));
		contact.setEmail(cursor.getString(4));
		contact.setPhone(cursor.getString(5));
		
		//log 
		Log.d("getContact("+id+")", contact.toString());
		
		// 5. return contact
		return contact;
	}
	
	public List<Contact> getSome(String where) {
		List<Contact> contacts = new LinkedList<Contact>();
		
		String query = "Select * FROM " + TABLE_CONTACTS + where;
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		// 3. go over each row, build contact and add it to list
		Contact contact = null;
		if (cursor.moveToFirst()) {
			do {
				contact = new Contact();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setLast(cursor.getString(1));
				contact.setFirst(cursor.getString(2));
				contact.setAddress(cursor.getString(3));
				contact.setEmail(cursor.getString(4));
				contact.setPhone(cursor.getString(5));
				
				// Add contact to contacts
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		
		Log.d("getSome()", contacts.toString());
		
		// return contacts
		return contacts;
	}

	public List<Contact> getAllContacts() {
		List<Contact> contacts = new LinkedList<Contact>();
		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_CONTACTS;
		
		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		// 3. go over each row, build contact and add it to list
		Contact contact = null;
		if (cursor.moveToFirst()) {
			do {
				contact = new Contact();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setLast(cursor.getString(1));
				contact.setFirst(cursor.getString(2));
				contact.setAddress(cursor.getString(3));
				contact.setEmail(cursor.getString(4));
				contact.setPhone(cursor.getString(5));
				
				// Add contact to contacts
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		
		Log.d("getAllContacts()", contacts.toString());
		
		// return contacts
		return contacts;
	}
	
	public int updateContact(Contact contact) {
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put("last", contact.getLast()); // get last
		values.put("first", contact.getFirst()); // get first
		values.put("address", contact.getAddress()); // get address
		values.put("email", contact.getEmail()); // get email
		values.put("phone", contact.getPhone()); // get phone

		
		// 3. updating row
		int i = db.update(TABLE_CONTACTS, //table
				values, // column/value
				KEY_ID+" = ?", // selections
				new String[] { String.valueOf(contact.getId()) }); //selection args
		
		// 4. close
		db.close();
		
		return i;
	}
	
	public void deleteContact(Contact contact){
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. delete
		db.delete(TABLE_CONTACTS, //table name
				KEY_ID+" = ?",  // selections
				new String[] { String.valueOf(contact.getId()) }); //selections args
		
		// 3. close
		db.close();
		
		//log
		Log.d("deleteContact", contact.toString());
	}
	
	public void deleteContactById(String id){

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. delete
		db.delete(TABLE_CONTACTS, //table name
				KEY_ID+" = ?",  // selections
				new String[] { id }); //selections args
		
		// 3. close
		db.close();
		
		//log
		Log.d("deletedContact with id=", id);
	}
	
}

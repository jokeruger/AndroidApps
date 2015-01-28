package com.example.deckbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

public class SearchActivity extends ActionBarActivity {
	static TextView text, deckTextView, dbText, cardCount;
	EditText editText1;
	static ImageView image;
	static String deckName;
	static String cardID;
	static String cardName;
	static Button buttonAdd, buttonRemove;
	static TinyDB tinydb;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		//Just for testing, allow network access in the main thread
		//NEVER use this is productive code
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 

		text=(TextView)findViewById(R.id.textView1);
		text.setMovementMethod(new ScrollingMovementMethod());
		editText1 = (EditText) findViewById(R.id.editText1);
		image = (ImageView) findViewById(R.id.imageView1);
		deckTextView = (TextView)findViewById(R.id.textView2);
		buttonAdd = (Button)findViewById(R.id.buttonAdd);
		buttonAdd.setVisibility(View.GONE);
		buttonRemove = (Button)findViewById(R.id.buttonRemove);
		buttonRemove.setVisibility(View.INVISIBLE);
		dbText = (TextView)findViewById(R.id.textView3);
		cardCount = (TextView)findViewById(R.id.textView4);
		
		tinydb = new TinyDB(this);
		
		
		editText1.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		    	if (event.getAction() == KeyEvent.ACTION_DOWN) {
        			switch (keyCode) {
						case KeyEvent.KEYCODE_DPAD_CENTER:
						case KeyEvent.KEYCODE_ENTER:
							search(v);
						return true;
						default:
						break;
        			}
		    	}
		    	return false;
			}
		});
	}


	private void makeTagLinks(final String text, final TextView tv) {
		if (text == null || tv == null) {
			return;
		}
		final SpannableString ss = new SpannableString(text);
		final List<String> items = Arrays.asList(text.split("\\n\\n\\n"));
		int start = 0, end;
		for (final String item : items) {
			end = start + item.length();
			if (start < end) {
				ss.setSpan(new MyClickableSpan(item), start, end-10, 0);
			}
			start += item.length() + 3;//comma and space in the original text ;)
		}
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setText(ss, TextView.BufferType.SPANNABLE);
		tv.scrollTo(0, 0);
	}
	
	private void makeTagLinksDeck(final String text, final TextView tv) {
		if (text == null || tv == null) {
			return;
		}
		final SpannableString ss = new SpannableString(text);
		final List<String> items = Arrays.asList(text.split("\\n\\n"));
		int start = 0, end;
		for (final String item : items) {
			end = start + item.length();
			if (start < end) {
				ss.setSpan(new MyClickableSpan(item), start, start+(item.indexOf('\n')), 0);
			}
			start += item.length() + 2;//comma and space in the original text ;)
		}
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setText(ss, TextView.BufferType.SPANNABLE);
		tv.scrollTo(0, 0);
	}
	
	public void add(View view) {
		ArrayList<String> newList = tinydb.getList(deckName);
		newList.add(cardID);
		tinydb.putList(deckName, newList);
		dbText.setText(tinydb.getAll().toString());
		
		int count=0;
		for (String card : tinydb.getList(deckName)){
			if (Integer.parseInt(card)==Integer.parseInt(cardID)){
				count=count+1;
			}
		}
		cardCount.setText(""+count);
		tinydb.putString(cardID, cardName);
	}
	
	public void remove(View view) {
		ArrayList<String> newList = tinydb.getList(deckName);
		newList.remove(cardID);
//		tinydb.remove(deckName);
		tinydb.putList(deckName, newList);
		dbText.setText(tinydb.getAll().toString());
		
		int count=0;
		for (String card : tinydb.getList(deckName)){
			if (Integer.parseInt(card)==Integer.parseInt(cardID)){
				count=count+1;
			}
		}
		cardCount.setText(""+count);
	}
	

	public static void show(String id) {
		int space = id.indexOf(" ");
//		int line = id.length();
//		id = id.split(" - ")[0];
		cardName = id.substring(space+2, id.length());
		id = id.substring(0, space);
		cardID=id;
		String link = "http://api.mtgdb.info/content/hi_res_card_images/"+id+".jpg";
		Bitmap bmp = null;
		URL url;
		try {
			url = new URL(link);
			bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			buttonAdd.setVisibility(View.VISIBLE);
			buttonRemove.setVisibility(View.VISIBLE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		image.setImageBitmap(bmp);
		
		int count=0;
		for (String card : tinydb.getList(deckName)){
			if (Integer.parseInt(card)==Integer.parseInt(cardID)){
				count=count+1;
			}
		}
		cardCount.setText(""+count);
		
//		text.setText(link);
	}
	
	public void search(View view){
		String name = editText1.getText().toString().trim().replace(" ", "%20");
		text.setText(readMTG(name));
		
		makeTagLinks(readMTG(name), text);
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);
	}
	
	public String readMTG(String string) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet;
		if(string.length()>2)
			httpGet = new HttpGet("http://api.mtgdb.info/search/"+string);
		else
			httpGet = new HttpGet("http://api.mtgdb.info/cards/random");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {	
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

				
				String[] cards = reader.readLine().split("\"id\":");
				for (String card : cards){
					if (card.length()>3){
						builder.append(card.split(",\"")[0]);
						for (String element:card.split(",\"")){
							if (element.startsWith("name"))
								builder.append(element.replace("name\":\"", " - ").replace("\"", "")).append("\n");
							if (element.startsWith("cardSetName"))
								builder.append(element.replace("cardSetName\":\"", "").replace("\"", "")).append("\n");
							if (element.startsWith("type"))
								builder.append(element.replace("\"", "")).append("\n");
							if (element.startsWith("released"))
								builder.append(element.replace("},{", "").replace("}]", "").replace("}", "").replace("releasedAt\":\"", "").replace("\"", "")).append("\n");
						}
						builder.append("\n\n");
					}
//					builder.append(card).append("\n");
				}
			} 
			else {
				Log.e("ParseJSON.class.toString()", "Failed to download file");
			}
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		List<String> list = tinydb.getList("deckNames");
		for (String deck : list)
			menu.add(deck);
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
		else {
			deckName = (String) item.getTitle();
			StringBuilder cards = new StringBuilder();
			List<String> strings = new ArrayList<String>();
			strings = tinydb.getList(deckName);
			String lastCard = "";
			Collections.sort(strings);
//			String name = tinydb.getString(card);
			for (String card : strings) {
				if (!card.equals(lastCard))
					cards.append(card).append(" - \n" + tinydb.getString(card)+"\n\n");
				lastCard=card;
			}
			makeTagLinksDeck(cards.toString(), text);
//			text.setText(cards);
		}
			deckTextView.setText("Currently selected: " + deckName);
		return super.onOptionsItemSelected(item);
	}

}

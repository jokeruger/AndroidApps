package com.example.counter1passing;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	TextView name1view, name2view, name3view, name4view, 
		score1view, score2view, score3view, score4view, 
		time1view, time2view, time3view, time4view;
	
	String name1, name2, name3, name4;
	
	int seconds1, minutes1, hours1,
		seconds2, minutes2, hours2,
		seconds3, minutes3, hours3,
		seconds4, minutes4, hours4;
	
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        name1view=(TextView)findViewById(R.id.name1);
        name2view=(TextView)findViewById(R.id.name2);
        name3view=(TextView)findViewById(R.id.name3);
        name4view=(TextView)findViewById(R.id.name4);
        
        score1view=(TextView)findViewById(R.id.score1);
        score2view=(TextView)findViewById(R.id.score2);
        score3view=(TextView)findViewById(R.id.score3);
        score4view=(TextView)findViewById(R.id.score4);

        time1view=(TextView)findViewById(R.id.time1);
        time2view=(TextView)findViewById(R.id.time2);
        time3view=(TextView)findViewById(R.id.time3);
        time4view=(TextView)findViewById(R.id.time4);
        
        settings = getApplicationContext().getSharedPreferences("SCORES", 0);
        editor = settings.edit();
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
    
    public void getNames(View view) {  
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        Bundle bndle = new Bundle();
        bndle.putString("name1", name1);
        bndle.putString("name2", name2);
        bndle.putString("name3", name3);
        bndle.putString("name4", name4);
        intent.putExtras(bndle);
        startActivityForResult(intent, 1);// Activity is started with requestCode 1
    }
    
    // Call Back method  to get the Message form other Activity  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    		
    	//setting names
    	if(requestCode==1) {
    		if (data!=null){
    			name1=data.getStringExtra("NAME1");
        		name1view.setText(name1);
        		
        		name2=data.getStringExtra("NAME2");
        		name2view.setText(name2);
        		
        		name3=data.getStringExtra("NAME3");
        		name3view.setText(name3);
        		
        		name4=data.getStringExtra("NAME4");
        		name4view.setText(name4);
    		}
    		updateScores();
    	}
    	
    	//2 player game
    	if(requestCode==2) {
    		if (data!=null) {
	    		String winner = data.getStringExtra("WINNER");
	    		String loser = data.getStringExtra("LOSER");
	    		
	//    		int previousWins = settings.getInt(winner+"2wins", 0);
	//    		int wins = previousWins+1;
	//    		editor.putInt(winner+"2wins", wins);    		
	//    		int previousGames = settings.getInt(winner+"2games", 0);
	//    		int games = previousGames+1;
	//    		editor.putInt(winner+"2games", games);
	
	//    		previousGames = settings.getInt(loser+"2games", 0);
	//    		games = previousGames+1;
	//    		editor.putInt(loser+"2games", games);
	    		
	    		editor.putInt(winner+"2wins", settings.getInt(winner+"2wins", 0)+1);
	    		editor.putInt(winner+"2games", settings.getInt(winner+"2games", 0)+1);
	    		editor.putInt(loser+"2games", settings.getInt(loser+"2games", 0)+1);
	    		
	    		//timer:
	    		Long time = data.getLongExtra("TIME",0);
	    		editor.putLong(winner+"Time", settings.getLong(winner+"Time", 0)+time);
	    		editor.putLong(loser+"Time", settings.getLong(loser+"Time", 0)+time);
	    		//end timer
	    		
	    		editor.apply();
	    		updateScores();
    		}
    	}
    	
    	if(requestCode==3) {
    		if (data!=null) {
	    		String winner = data.getStringExtra("WINNER");
	    		String loser = data.getStringExtra("LOSER");
	    		String mid = data.getStringExtra("MID");
	    		
	    		editor.putInt(winner+"3wins", settings.getInt(winner+"3wins", 0)+1);
	    		editor.putInt(winner+"3games", settings.getInt(winner+"3games", 0)+1);
	    		editor.putInt(loser+"3games", settings.getInt(loser+"3games", 0)+1);
	    		editor.putInt(mid+"3games", settings.getInt(mid+"3games", 0)+1);
	    		
	    		//timer:
	    		Long time = data.getLongExtra("TIME",0);
	    		editor.putLong(winner+"Time", settings.getLong(winner+"Time", 0)+time);
	    		editor.putLong(loser+"Time", settings.getLong(loser+"Time", 0)+time);
	    		editor.putLong(mid+"Time", settings.getLong(mid+"Time", 0)+time);
	    		//end timer
	    		
	    		editor.apply();
	    		updateScores();
    		}
    	}
    	
    	if(requestCode==4) {
    		String winner = data.getStringExtra("WINNER");
    		String loser = data.getStringExtra("LOSER");
    		String other = data.getStringExtra("OTHER");
    		String other2 = data.getStringExtra("OTHER2");
    		
    		editor.putInt(winner+"4wins", settings.getInt(winner+"4wins", 0)+1);
    		editor.putInt(winner+"4games", settings.getInt(winner+"4games", 0)+1);
    		editor.putInt(loser+"4games", settings.getInt(loser+"4games", 0)+1);
    		editor.putInt(other+"4games", settings.getInt(other+"4games", 0)+1);
    		editor.putInt(other2+"4games", settings.getInt(other2+"4games", 0)+1);
    		
    		//timer:
    		Long time = data.getLongExtra("TIME",0);
    		editor.putLong(winner+"Time", settings.getLong(winner+"Time", 0)+time);
    		editor.putLong(loser+"Time", settings.getLong(loser+"Time", 0)+time);
    		editor.putLong(other+"Time", settings.getLong(other+"Time", 0)+time);
    		editor.putLong(other2+"Time", settings.getLong(other2+"Time", 0)+time);
    		//end timer
    		
    		editor.apply();
    		updateScores();
    	}
    }
    
    public void updateScores() {
    	//timer:
    	long time1 = settings.getLong(name1+"Time", 0);
    	seconds1 = (int)(time1 / 1000);
		minutes1 = seconds1 / 60;
		seconds1 = seconds1 % 60;
    	long time2 = settings.getLong(name2+"Time", 0);
    	seconds2 = (int)(time2 / 1000);
		minutes2 = seconds2 / 60;
		seconds2 = seconds2 % 60;
    	long time3 = settings.getLong(name3+"Time", 0);
    	seconds3 = (int)(time3 / 1000);
		minutes3 = seconds3 / 60;
		seconds3 = seconds3 % 60;
    	long time4 = settings.getLong(name4+"Time", 0);
    	seconds4 = (int)(time4 / 1000);
		minutes4 = seconds4 / 60;
		seconds4 = seconds4 % 60;
    	
    	
    	
    	if (name1.length()>1 && name2.length()>1){
    		score1view.setText(" "+settings.getInt(name1+"2wins", 0)+"/"+settings.getInt(name1+"2games", 0));
        	score2view.setText(" "+settings.getInt(name2+"2wins", 0)+"/"+settings.getInt(name2+"2games", 0));
        	score3view.setText(" ");
        	score4view.setText(" ");
        	
        	//timer:
        	time1view.setText("      " + minutes1 + ":" + String.format("%02d", seconds1));
        	time2view.setText("      " + minutes2 + ":" + String.format("%02d", seconds2));
        	time3view.setText("      " + minutes3 + ":" + String.format("%02d", seconds3));
        	time4view.setText("      " + minutes4 + ":" + String.format("%02d", seconds4));
        	//end timer
    	}
    	if (name1.length()>1 && name2.length()>1 && name3.length()>1){
    		score1view.setText(" "+settings.getInt(name1+"3wins", 0)+"/"+settings.getInt(name1+"3games", 0));
        	score2view.setText(" "+settings.getInt(name2+"3wins", 0)+"/"+settings.getInt(name2+"3games", 0));
        	score3view.setText(" "+settings.getInt(name3+"3wins", 0)+"/"+settings.getInt(name3+"3games", 0));
        	score4view.setText(" ");
    	}
    	if (name1.length()>1 && name2.length()>1 && name3.length()>1 && name4.length()>1){
    		score1view.setText(" "+settings.getInt(name1+"4wins", 0)+"/"+settings.getInt(name1+"4games", 0));
        	score2view.setText(" "+settings.getInt(name2+"4wins", 0)+"/"+settings.getInt(name2+"4games", 0));
        	score3view.setText(" "+settings.getInt(name3+"4wins", 0)+"/"+settings.getInt(name3+"4games", 0));
        	score4view.setText(" "+settings.getInt(name4+"4wins", 0)+"/"+settings.getInt(name4+"4games", 0));
    	}
//    	score1view.setText(" "+settings.getInt(name1+"2wins", 0)+"/"+settings.getInt(name1+"2games", 0));
//    	score2view.setText(" "+settings.getInt(name2+"2wins", 0)+"/"+settings.getInt(name2+"2games", 0));
//    	score3view.setText(" "+settings.getInt(name3+"2wins", 0)+"/"+settings.getInt(name3+"2games", 0));
//    	score4view.setText(" "+settings.getInt(name4+"2wins", 0)+"/"+settings.getInt(name4+"2games", 0));
    }
    
    public void start2(View view) {
        Bundle bndle = new Bundle();
        bndle.putString("name1", name1);
        bndle.putString("name2", name2);
        
        Intent intent = new Intent(MainActivity.this,Game2Activity.class);
        
        intent.putExtras(bndle);
        startActivityForResult(intent, 2);
    }
    
    public void start3(View view) {  
        
        Bundle bndle = new Bundle();
        bndle.putString("name1", name1);
        bndle.putString("name2", name2);
        bndle.putString("name3", name3);
        
        Intent intent = new Intent(MainActivity.this,Game3Activity.class);
        
        intent.putExtras(bndle);
        startActivityForResult(intent, 3);
    }
    
    public void start4(View view) {  
        
        Bundle bndle = new Bundle();
        bndle.putString("name1", name1);
        bndle.putString("name2", name2);
        bndle.putString("name3", name3);
        bndle.putString("name4", name4);
        
        Intent intent = new Intent(MainActivity.this,Game4Activity.class);
        
        intent.putExtras(bndle);
        startActivity(intent);
    }
    
}

package com.example.counter1passing;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game2Activity extends ActionBarActivity {
	
	TextView player1view;
	TextView player2view;
	
	TextView player1lifeView;
	TextView player2lifeView;
	
	String name1;
	String name2;
	

	int playerLife1 = 20;
	int playerLife2 = 20;

	//timer:
	private Button startButton;
	private Button pauseButton;
	private TextView textTimer;
	private long startTime = 0L;
	private Handler myHandler = new Handler();
	long timeInMillies = 0L;
	long timeSwap = 0L;
	long finalTime = 0L;
	//end timer

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game2);
		
		player1view=(TextView)findViewById(R.id.playerView1);
		player2view=(TextView)findViewById(R.id.playerView2);
		
		player1lifeView = (TextView)findViewById(R.id.player1lifeView);
		player2lifeView = (TextView)findViewById(R.id.player2lifeView);
		
		player1lifeView.setText(""+playerLife1);
		player2lifeView.setText(""+playerLife2);
		
		//timer:
		textTimer = (TextView) findViewById(R.id.textTimer);
		startTime = SystemClock.uptimeMillis();
		startButton = (Button) findViewById(R.id.btnStart);
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				pauseButton.setEnabled(true);
				startTime = SystemClock.uptimeMillis();
				myHandler.postDelayed(updateTimerMethod, 0);
			}
		});
		pauseButton = (Button) findViewById(R.id.btnPause);
		pauseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				pauseButton.setEnabled(false);
				timeSwap += timeInMillies;
				myHandler.removeCallbacks(updateTimerMethod);
			}
		});
		//end timer
		
		Bundle extras = this.getIntent().getExtras();
		
		if (extras!=null){
			name1 = extras.getString("name1");
			player1view.setText(name1);
			name2 = extras.getString("name2");
			player2view.setText(name2);
		}
	}
	
	//timer:
	private Runnable updateTimerMethod = new Runnable() {
		public void run() {
			timeInMillies = SystemClock.uptimeMillis() - startTime;
			finalTime = timeSwap + timeInMillies;
			
			int seconds = (int) (finalTime / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			int milliseconds = (int) (finalTime % 1000);
			textTimer.setText("" + minutes + ":" + String.format("%02d", seconds));
			myHandler.postDelayed(this, 0);
		}
	};
	//end timer

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game2, menu);
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
	
	public void resetLife(View view) {
		playerLife1 = 20;
		playerLife2 = 20;
		
		player1lifeView.setText(""+playerLife1);
		player2lifeView.setText(""+playerLife2);
	}
	
	public void countScore(View view) {
		String winner, loser;
		winner="none";
		loser="none";
		if (playerLife1>playerLife2){
			winner=name1;
			loser=name2;
		}
		if (playerLife2>playerLife1) {
			winner=name2;
			loser=name1;
		}
        Intent intent=new Intent();  
        intent.putExtra("WINNER",winner);
        intent.putExtra("LOSER",loser);
        //timer:
        intent.putExtra("TIME", finalTime);
        //end timer
        Toast.makeText(getApplicationContext(), winner + " wins!", Toast.LENGTH_LONG).show();
        setResult(2,intent);  
          
        finish();//finishing activity 
	}
	
	public void add1player1(View view) {
		playerLife1 += 1;
		player1lifeView.setText(""+playerLife1);
	}
	
	public void add5player1(View view) {
		playerLife1 += 5;
		player1lifeView.setText(""+playerLife1);
	}
	
	public void sub1player1(View view) {
		playerLife1 -= 1;
		player1lifeView.setText(""+playerLife1);
	}
	
	public void sub5player1(View view) {
		playerLife1 -= 5;
		player1lifeView.setText(""+playerLife1);
	}
	
	public void player1extort(View view) {
		add1player1(view);
		sub1player2(view);
	}
	
	
	
	
	public void add1player2(View view) {
		playerLife2 += 1;
		player2lifeView.setText(""+playerLife2);
	}
	
	public void add5player2(View view) {
		playerLife2 += 5;
		player2lifeView.setText(""+playerLife2);
	}
	
	public void sub1player2(View view) {
		playerLife2 -= 1;
		player2lifeView.setText(""+playerLife2);
	}
	
	public void sub5player2(View view) {
		playerLife2 -= 5;
		player2lifeView.setText(""+playerLife2);
	}
	
	public void player2extort(View view) {
		add1player2(view);
		sub1player1(view);
	}
	
}

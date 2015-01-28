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

public class Game3Activity extends ActionBarActivity {
	
	TextView player1view, player2view, player3view;
	
	TextView player1lifeView, player2lifeView, player3lifeView;
	
	String name1, name2, name3;

	int playerLife1 = 20;
	int playerLife2 = 20;
	int playerLife3 = 20;
	
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
		setContentView(R.layout.activity_game3);
		
		player1view=(TextView)findViewById(R.id.playerView1);
		player2view=(TextView)findViewById(R.id.playerView2);
		player3view=(TextView)findViewById(R.id.playerView3);
		
		player1lifeView = (TextView)findViewById(R.id.player1lifeView);
		player2lifeView = (TextView)findViewById(R.id.player2lifeView);
		player3lifeView = (TextView)findViewById(R.id.player3lifeView);
		
		player1lifeView.setText(""+playerLife1);
		player2lifeView.setText(""+playerLife2);
		player3lifeView.setText(""+playerLife3);
		
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
			name3 = extras.getString("name3");
			player3view.setText(name3);
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
	
	public void countScore(View view) {
		String winner, loser, mid;
		winner="none";
		loser="none";
		mid="none";
		if (playerLife1>playerLife2 && playerLife2>playerLife3){
			winner=name1;
			mid=name2;
			loser=name3;
		}
		if (playerLife1>playerLife3 && playerLife3>playerLife2){
			winner=name1;
			mid=name3;
			loser=name2;
		}
		if (playerLife2>playerLife1 && playerLife1>playerLife3){
			winner=name2;
			mid=name1;
			loser=name3;
		}
		if (playerLife2>playerLife3 && playerLife3>playerLife1){
			winner=name2;
			mid=name3;
			loser=name1;
		}
		if (playerLife3>playerLife1 && playerLife1>playerLife2){
			winner=name3;
			mid=name1;
			loser=name2;
		}
		if (playerLife3>playerLife2 && playerLife2>playerLife1){
			winner=name3;
			mid=name2;
			loser=name1;
		}

        Intent intent=new Intent();  
        intent.putExtra("WINNER",winner);
        intent.putExtra("LOSER",loser);
        intent.putExtra("MID",mid);
        //timer:
        intent.putExtra("TIME", finalTime);
        //end timer
        Toast.makeText(getApplicationContext(), winner + " wins!", Toast.LENGTH_LONG).show();
        setResult(2,intent);  
          
        finish();//finishing activity 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game3, menu);
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
		playerLife3 = 20;
		
		player1lifeView.setText(""+playerLife1);
		player2lifeView.setText(""+playerLife2);
		player3lifeView.setText(""+playerLife3);
	}
	
	
	//Player 1 functions
	
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
		if (playerLife2 > 0) {
			add1player1(view);
			sub1player2(view);
		}
		if (playerLife3 > 0) {
			add1player1(view);
			sub1player3(view);
		}
	}
	
	
	//Player 2
	
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
		if (playerLife1 > 0) {
			add1player2(view);
			sub1player1(view);
		}
		
		if (playerLife3 > 0) {
			add1player2(view);
			sub1player3(view);
		}
	}
	
	
	
	//Player 3
	
	public void add1player3(View view) {
        playerLife3 += 1;
        player3lifeView.setText(""+playerLife3);
    }
    
    public void add5player3(View view) {
        playerLife3 += 5;
        player3lifeView.setText(""+playerLife3);
    }
    
    public void sub1player3(View view) {
        playerLife3 -= 1;
        player3lifeView.setText(""+playerLife3);
    }
    
    public void sub5player3(View view) {
        playerLife3 -= 5;
        player3lifeView.setText(""+playerLife3);
    }
    
    public void player3extort(View view) {
    	if (playerLife1 > 0) {
			add1player3(view);
			sub1player1(view);
		}
    	if (playerLife2 > 0) {
			add1player3(view);
			sub1player2(view);
		}
    }
}

package com.example.counter1passing;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class SecondActivity extends ActionBarActivity {
	EditText editText1, editText2, editText3, editText4;
	Button setButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		editText1=(EditText)findViewById(R.id.editText1);
		editText2=(EditText)findViewById(R.id.editText2);
		editText3=(EditText)findViewById(R.id.editText3);
		editText4=(EditText)findViewById(R.id.editText4);
		
		Bundle extras = this.getIntent().getExtras();
		if (extras!=null){
			String name1 = extras.getString("name1");
			editText1.setText(name1);
			String name2 = extras.getString("name2");
			editText2.setText(name2);
			String name3 = extras.getString("name3");
			editText3.setText(name3);
			String name4 = extras.getString("name4");
			editText4.setText(name4);
			
			
//			player1view.setText(name1);
//			name2 = extras.getString("name2");
//			player2view.setText(name2);
		}
		
		setButton=(Button)findViewById(R.id.setButton);  
        
		setButton.setOnClickListener(new OnClickListener() {  
              
            @Override  
            public void onClick(View view) {  
                String name1=editText1.getText().toString();
                String name2=editText2.getText().toString();
                String name3=editText3.getText().toString();
                String name4=editText4.getText().toString();
                
                Intent intent=new Intent();  
                intent.putExtra("NAME1",name1);
                intent.putExtra("NAME2",name2);
                intent.putExtra("NAME3",name3);
                intent.putExtra("NAME4",name4);
                  
                setResult(2,intent);  
                  
                finish();//finishing activity  
            }  
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
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
}

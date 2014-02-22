package com.brunton.chesstimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String ENTERED_TIME = "com.brunton.chesstimer";
	
	private EditText timeEditText;
	private Button enterButton;
	
	int time = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//make android title bar disappear
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		timeEditText = (EditText) findViewById(R.id.enteredTimeEditText);
		timeEditText.setText("");
		
		enterButton = (Button) findViewById(R.id.goButton);
		
		enterButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				if(timeEditText.getText().toString() != "")
				{
					time = Integer.parseInt(timeEditText.getText().toString()) * 60000;
					
					Intent intent = new Intent(MainActivity.this,TimerActivity.class);
					intent.putExtra("ENTERED_TIME", time);
					
					startActivity(intent);
				}
			}
			
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
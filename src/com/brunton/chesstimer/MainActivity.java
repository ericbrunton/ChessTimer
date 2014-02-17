package com.brunton.chesstimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView player1Timer;
	TextView player2Timer;
	
	Button player1Button;
	Button player2Button;
	Button startButton;
	
	CountDownTimer count1;
	CountDownTimer count2;
	
	Boolean count1Bool = false;
	Boolean count2Bool = false;
	
	int oldTimer1 = 0;
	int oldTimer2 = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		player1Timer = (TextView) findViewById(R.id.player1TextView);
		player2Timer = (TextView) findViewById(R.id.player2TextView);
		
		player1Timer.setText("Start!");
		player2Timer.setText("Start!");
		
		player1Button = (Button) findViewById(R.id.player1Button);
		player2Button = (Button) findViewById(R.id.player2Button);
		
		player1Button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(player1Timer.getText().toString() == "Start!")
				{
					startTimer1();
					count1Bool = true;
				}
				else if(count1Bool == true)
				{
					pauseTimer1();
				}
			}
		});
		
		player2Button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(player2Timer.getText().toString() == "Start!")
				{
					startTimer2();
					count2Bool = true;
				}
				else if(count2Bool == true)
				{
					pauseTimer2();
				}
			}
		});
	}
	
	public void startTimer1()
	{		
		count1 = new CountDownTimer(30000, 1000) {
			
			public void onTick(long millisUntilFinished) {
				player1Timer.setText("" + millisUntilFinished / 1000);
			}
			
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Loses!");
				player2Timer.setText("White Wins!");
			}
		}.start();
		
		player2Timer.setText("30");
	}
	
	public void startTimer2()
	{	  
		count2 = new CountDownTimer(30000, 1000) {
				
			public void onTick(long millisUntilFinished) {
				player2Timer.setText("" + millisUntilFinished / 1000);
			}
		
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Wins!");
				player2Timer.setText("White Loses!");
			}
		}.start();
			
		player1Timer.setText("30");
	}

	public void pauseTimer1()
	{
		count1.cancel();
		count1Bool = false;
		
		oldTimer2 = Integer.parseInt(player2Timer.getText().toString()) * 1000;
		newCountDown2(oldTimer2);
		count2Bool = true;
	}
	
	public void pauseTimer2()
	{
		count2.cancel();
		count2Bool = false;
		
		oldTimer1 = Integer.parseInt(player1Timer.getText().toString()) * 1000;
		newCountDown1(oldTimer1);
		count1Bool = true;
	}
	
	public void newCountDown1(int newTime)
	{
		count1 = new CountDownTimer(newTime, 1000) {
			
			public void onTick(long millisUntilFinished) {
				player1Timer.setText("" + millisUntilFinished / 1000);
			}
			
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Loses!");
				player2Timer.setText("White Wins!");
			}
		}.start();
	}
	
	public void newCountDown2(int newTime)
	{
		count2 = new CountDownTimer(newTime, 1000) {
			
			public void onTick(long millisUntilFinished) {
				player2Timer.setText("" + millisUntilFinished / 1000);
			}
			
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Wins!");
				player2Timer.setText("White Loses!");
			}
		}.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
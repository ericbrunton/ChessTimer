package com.brunton.chesstimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {
		
	//declare variables -- 1 = Black, 2 = White
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
	int time = 0;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		//make android title bar disappear
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_timer);
		
		Bundle extras = getIntent().getExtras();
		time = extras.getInt("ENTERED_TIME");
		
		//initialize variables
		player1Timer = (TextView) findViewById(R.id.player1TextView);
		player2Timer = (TextView) findViewById(R.id.player2TextView);
		
		player1Timer.setText("White Starts!");
		player2Timer.setText("Start!");
		
		player1Button = (Button) findViewById(R.id.player1Button);
		player2Button = (Button) findViewById(R.id.player2Button);
		
		//set what happens when Black button is clicked
		player1Button.setOnClickListener(new View.OnClickListener() {
			
			//White must start timer initially, so do nothing if black not counting
			public void onClick(View v) {
			
				//if the black timer is currently counting, pause and start white timer
				if(count1Bool == true)
				{
					pauseTimer1();
				}
			}
		});
			
		//set what happens when White button is clicked
		player2Button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				//if it is the first time being clicked, start the Black timer
				if(player2Timer.getText().toString() == "Start!")
				{
					startTimer1();
					count1Bool = true;
				}
				//if the white timer is currently counting, pause and start black timer
				else if(count2Bool == true)
				{
					pauseTimer2();
				}
			}
		});
	}
		
	//starts the Black timer for the first time
	public void startTimer1()
	{		
		count1 = new CountDownTimer(time, 1000) {
			
			//count down second by second, update textview
			public void onTick(long millisUntilFinished) {
				player1Timer.setText("" + formatTime(millisUntilFinished));
			}
			
			//show winners and losers if timer finishes
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Loses!");
				player2Timer.setText("White Wins!");
			}
		}.start();
			
		player2Timer.setText("" + formatTime(time));
	}

	//stop Black timer, start White timer
	public void pauseTimer1()
	{
		count1.cancel();
		count1Bool = false;
		
		oldTimer2 = parseStringForPause(player2Timer.getText().toString());
		//oldTimer2 = Integer.parseInt(player2Timer.getText().toString()) * 1000;
		newCountDown2(oldTimer2);
		count2Bool = true;
	}
		
	//stop White timer, start Black timer
	public void pauseTimer2()
	{
		count2.cancel();
		count2Bool = false;
		
		oldTimer1 = parseStringForPause(player1Timer.getText().toString());
		//oldTimer1 = Integer.parseInt(player1Timer.getText().toString()) * 1000;
		newCountDown1(oldTimer1);
		count1Bool = true;
	}
		
	//starting a paused Black timer
	public void newCountDown1(int newTime)
	{
		count1 = new CountDownTimer(newTime, 1000) {
			
			//count down second by second
			public void onTick(long millisUntilFinished) {
				player1Timer.setText("" + formatTime(millisUntilFinished));
			}
			
			//if timer finishes, show winners and losers
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Loses!");
				player2Timer.setText("White Wins!");
			}
		}.start();
	}
		
	//starting a paused White timer
	public void newCountDown2(int newTime)
	{
		count2 = new CountDownTimer(newTime, 1000) {
				
			//count down second by second
			public void onTick(long millisUntilFinished) {
				player2Timer.setText("" + formatTime(millisUntilFinished));
			}
				
			//if timer finishes, show winners and losers
			public void onFinish() {
				player1Timer.setTextSize(50);
				player2Timer.setTextSize(50);
				player1Timer.setText("Black Wins!");
				player2Timer.setText("White Loses!");
			}
		}.start();
	}
		
	//Format the string into a minute:second format rather than just seconds
	public String formatTime(long millis)
	{  
		String output = "00:00";  
	    long seconds = millis / 1000;  
	    long minutes = seconds / 60;  

	    seconds = seconds % 60;  
	    //minutes = minutes % 60;  

	    String sec = String.valueOf(seconds);  
	    String min = String.valueOf(minutes);  

	    if (seconds < 10)  
	        sec = "0" + seconds;  
	    if (minutes < 10)  
	        min= "0" + minutes;  

	    output = min + ":" + sec;  
	    return output;
	}
	
	//Take minute:second format and return milliseconds
	public Integer parseStringForPause(String stringToParse)
	{
		int i = stringToParse.length() - 4, number = 0, j = 1;
		String numString = "";

		while(i >= 0)
		{
			numString = Character.toString(stringToParse.charAt(i)) + numString;
			i--;
		}
		
		number = Integer.parseInt(numString);
		
		number = number * 60000;
		
		numString = Character.toString(stringToParse.charAt(stringToParse.length()-2));
		number += Integer.parseInt(numString) * 10000;
		numString = Character.toString(stringToParse.charAt(stringToParse.length()-1));
		number += Integer.parseInt(numString) * 1000;
		
		return number;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
package com.heiya.game2048;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	private AnimationLayer AnimLayer;
	private TextView ShowScore;
	private TextView getMostScore;
	private static int mostScore=0;
	private static final String mostScoreKey="mostScoreKey";
	
	public MainActivity(){		
		mainActivity=this;
		clearScore();
	}
	
	private int score;
	public void clearScore(){
		score=0;
	}
	
	public void showScore(){
		ShowScore.setText(""+score);
	}
	
	public void addScore(int s){
		score+=s;
		
		if(score>mostScore){
			mostScore=score;
			saveMostScore(mostScore);
			getMostScore.setText(mostScore+"");
		}
		
		showScore();
		
	}
	
	
	public void saveMostScore(int s){
		Editor e=getPreferences(MODE_PRIVATE).edit();
		e.putInt(mostScoreKey, s);
		e.commit();
	}
	public int getMostScore(){
		return getPreferences(MODE_PRIVATE).getInt(mostScoreKey, 0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ShowScore = (TextView) findViewById(R.id.ShowScore);
		getMostScore =(TextView) findViewById(R.id.getMostScore);
		ShowScore.setText(0+"");
		mostScore=getMostScore();
		getMostScore.setText(mostScore+"");
		
		
		//Animation
		AnimLayer=(AnimationLayer) findViewById(R.id.AnimLayer);
		
		
		findViewById(R.id.reStart).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				GameView.gameView.startGame();
			}
		});
	}
	
	public static MainActivity mainActivity=null;
	
	public static MainActivity getMainActivity(){
		return mainActivity;
	}
	
	public AnimationLayer getAnimLayer(){
		return AnimLayer;
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
}

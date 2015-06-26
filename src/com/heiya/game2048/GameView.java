package com.heiya.game2048;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {


	public static int n=5;
	public static int CardWidth;
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGame();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGame();
	}

	public GameView(Context context) {
		super(context);
		initGame();
	}
	
	
	public static GameView gameView=null;
	
	public static GameView getGameView(){
		return gameView;
	}

	
	@SuppressLint("ClickableViewAccessibility")
	public void initGame(){
		
		gameView=this;
		
		setColumnCount(n);
		setBackgroundColor(0xffbbada0);
		
		setOnTouchListener(new View.OnTouchListener() {
			
			float initx,inity,offsetx,offsety;
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initx=arg1.getX();
					inity=arg1.getY();
					break;

				case MotionEvent.ACTION_UP:
					offsetx=arg1.getX()-initx;
					offsety=arg1.getY()-inity;
					
					if(Math.abs(offsetx)>Math.abs(offsety)){//horizontal
						if(offsetx<-5){//left
							toLeft();
						}
						else if(offsetx>5){//right
							toRight();
						}
					}
					else{//vertical
						if(offsety<-5){
							toUp();
						}
						else if(offsety>5){
							toDown();
						}
					}
					
					break;
				}
				return true;
			}
		});
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		System.out.println("change"+w+" "+h+" "+oldw+" "+oldh);
		super.onSizeChanged(w, h, oldw, oldh);
		
		int cardWidth=(Math.min(w, h)-10)/n;
		CardWidth=cardWidth;
		
		addCards(cardWidth, cardWidth);
		startGame();
	}
	
	public void addCards(int cardheight,int cardwidth){
		Card c;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				c=new Card(getContext());
				c.setNum(0);
				cardMap[i][j]=c;
				addView(c, cardwidth, cardheight);
			}
		}
	}
	
	public void startGame(){
		NotWin=true;
		MainActivity.getMainActivity().clearScore();
		MainActivity.getMainActivity().showScore();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				cardMap[i][j].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}
	
	private void addRandomNum(){
		emptyPoints.clear();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(cardMap[i][j].getNum()<=0){
					emptyPoints.add(new Point(i,j));
				}
			}
		}
		
		if(emptyPoints.size()==0)return ;
		Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
		double rate=Math.random();
		cardMap[p.x][p.y].setNum(rate>0.1?2:4);
		MainActivity.getMainActivity().getAnimLayer().ScaleAnimationCreate(cardMap[p.x][p.y]);
	}
	
	
	public void toLeft(){
		
		int flag=0;
		/*
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				for(int k=j+1;k<n;k++){
					if(cardMap[i][k].getNum()>0){
						
						if(cardMap[i][j].getNum()<=0){
							//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[i][k], cardMap[i][j], k, j, i, i);
							cardMap[i][j].setNum(cardMap[i][k].getNum());
							cardMap[i][k].setNum(0);
							j--;
							flag=1;
						}
						else if(cardMap[i][j].equals(cardMap[i][k])){
							MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[i][k], cardMap[i][j], k, j, i, i);
							cardMap[i][j].setNum(cardMap[i][k].getNum()*2);
							if(NotWin&&cardMap[i][j].getNum()==2048)WinTheGame();
							cardMap[i][k].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[i][j].getNum());
							flag=1;
						}
						
						break;
					}
				}
			}
		}*/
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {

				for (int x1 = x+1; x1 <n; x1++) {
					if (cardMap[x1][y].getNum()>0) {

						if (cardMap[x][y].getNum()<=0) {

							MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[x1][y],cardMap[x][y], x1, x, y, y);

							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);

							x--;
							flag = 1;

						}else if (cardMap[x][y].equals(cardMap[x1][y])) {
							MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[x1][y], cardMap[x][y],x1, x, y, y);
							cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
							cardMap[x1][y].setNum(0);

							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							flag=1;
						}

						break;
					}
				}
			}
		}
		if(flag==1){
			addRandomNum();
			addRandomNum();
			checkComplete();
		}
	}
	public void toRight(){
		
		int flag=0;
		for(int i=0;i<n;i++){
			for(int j=n-1;j>=0;j--){
				for(int k=j-1;k>=0;k--){
					if(cardMap[i][k].getNum()>0){
						if(cardMap[i][j].getNum()<=0){
							//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[i][k], cardMap[i][j], k, j, i, i);
							cardMap[i][j].setNum(cardMap[i][k].getNum());
							cardMap[i][k].setNum(0);
							j++;
							flag=1;
						}
						else {
							if(cardMap[i][j].equals(cardMap[i][k])){
								//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[i][k], cardMap[i][j], k, j, i, i);
								cardMap[i][j].setNum(cardMap[i][k].getNum()*2);
								if(NotWin&&cardMap[i][j].getNum()==2048)WinTheGame();
								cardMap[i][k].setNum(0);
								MainActivity.getMainActivity().addScore(cardMap[i][j].getNum());
								flag=1;
							}
						}
						break;
					}
				}
			}
		}
		if(flag==1){
			addRandomNum();
			addRandomNum();
			checkComplete();
		}
		
	}
	public void toUp(){
		
		int flag=0;
		for(int j=0;j<n;j++){
			for(int i=0;i<n;i++){
				for(int k=i+1;k<n;k++){
					if(cardMap[k][j].getNum()>0){
						if(cardMap[i][j].getNum()<=0){
							//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[k][j], cardMap[i][j], j, j, k, i);
							cardMap[i][j].setNum(cardMap[k][j].getNum());
							cardMap[k][j].setNum(0);
							i--;
							flag=1;
						}
						else {
							if(cardMap[i][j].equals(cardMap[k][j])){
								//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[k][j], cardMap[i][j], j, j, k, i);
								cardMap[i][j].setNum(cardMap[k][j].getNum()*2);
								if(NotWin&&cardMap[i][j].getNum()==2048)WinTheGame();
								cardMap[k][j].setNum(0);
								MainActivity.getMainActivity().addScore(cardMap[i][j].getNum());
								flag=1;
							}
						}
						break;
					}
				}
			}
		}
		if(flag==1){
			addRandomNum();
			addRandomNum();
			checkComplete();
		}
	}
	public void toDown(){
		int flag=0;
		for(int j=0;j<n;j++){
			for(int i=n-1;i>=0;i--){
				for(int k=i-1;k>=0;k--){
					if(cardMap[k][j].getNum()>0){
						if(cardMap[i][j].getNum()<=0){
							//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[k][j], cardMap[i][j], j, j, k, i);
							cardMap[i][j].setNum(cardMap[k][j].getNum());
							cardMap[k][j].setNum(0);
							MainActivity.getMainActivity().addScore(cardMap[i][j].getNum());
							i++;
							flag=1;
						}
						else {
							if(cardMap[i][j].equals(cardMap[k][j])){
								//MainActivity.getMainActivity().getAnimLayer().TransAnimationCreate(cardMap[k][j], cardMap[i][j], j, j, k, i);
								cardMap[i][j].setNum(cardMap[k][j].getNum()*2);
								if(NotWin&&cardMap[i][j].getNum()==2048)WinTheGame();
								cardMap[k][j].setNum(0);
								flag=1;
							}
						}
						break;
					}
				}
			}
		}
		if(flag==1){
			addRandomNum();
			addRandomNum();
			checkComplete();
		}
	}
	
	private void checkComplete(){
		int flag=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(cardMap[i][j].getNum()==0
						||(i>0&&cardMap[i][j].equals(cardMap[i-1][j]))
						||(j>0&&cardMap[i][j].equals(cardMap[i][j-1]))){
					flag=1;
					break;
				}
			}
			if(flag==1)break;
		}
		if(flag==0){///completed
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来一次", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					startGame();
				}
			}).show();
		}
	}
	
	public boolean NotWin=true;
	private void WinTheGame(){
		NotWin=false;
		new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("挑战成功").setPositiveButton("继续游戏", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		}).show();
	}
	
	
	private Card[][] cardMap=new Card[n][n];
	private List<Point> emptyPoints=new ArrayList<Point>();

}

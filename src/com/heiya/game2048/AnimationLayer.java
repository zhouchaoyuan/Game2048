package com.heiya.game2048;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class AnimationLayer extends FrameLayout {

	public AnimationLayer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initLayer();
	}

	public AnimationLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayer();
	}

	public AnimationLayer(Context context) {
		super(context);
		initLayer();
	}
	public void initLayer(){
		;
	}
	
	public void TransAnimationCreate(final Card from,final Card to,int fromX,int toX,int fromY,int toY){
		
		final Card c= getCard(from.getNum());
		
		LayoutParams lp=new LayoutParams(GameView.CardWidth, GameView.CardWidth);
		lp.leftMargin=fromX*GameView.CardWidth;
		lp.rightMargin=fromY*GameView.CardWidth;
		c.setLayoutParams(lp);
		
		/*
		 * new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta)
		 * float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；
		 * float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；
		 * float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；
		 * float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值； 
		 * */
		if (to.getNum()<=0) {
			//to.getLabel().setVisibility(View.INVISIBLE);
		}
		
		TranslateAnimation ta = new TranslateAnimation(0, GameView.CardWidth*(toX-fromX), 0, GameView.CardWidth*(toY-fromY));
		ta.setDuration(100);
		ta.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				//c.getLabel().setVisibility(View.INVISIBLE);
				RecycleCard(c);
				
			}
		});
		
		c.setAnimation(ta);
	}
	
	public Card getCard(int num){
		Card c;
		if(cards.size()>0){
			System.out.println("now "+cards.size());
			c=cards.remove(0);
		}
		else{
			c=new Card(getContext());
			//cards.add(c);
			addView(c);
		}
		c.setNum(num);
		c.setVisibility(View.VISIBLE);
		return c;
	}
	
	public void RecycleCard(Card c){
		c.setVisibility(View.INVISIBLE);
		c.setAnimation(null);
		cards.add(c);
	}
	
	public void ScaleAnimationCreate(Card c){
		ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(100);
		
		c.setAnimation(null);
		c.startAnimation(sa);
	}
	
	List<Card> cards = new ArrayList<Card>(); 

}

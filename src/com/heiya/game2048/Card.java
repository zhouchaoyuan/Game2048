package com.heiya.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);
		label=new TextView(getContext());
		label.setTextSize(32);
		label.setBackgroundColor(0x33eee4da);
		label.setGravity(Gravity.CENTER);
		
		LayoutParams lp=new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(label,lp);
		
		setNum(0);
	}
	private int num=0;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		if(num>0){
			switch (num) {
			case 2:
				label.setBackgroundColor(0xffeee4da);
				break;
			case 4:
				label.setBackgroundColor(0xffede0c8);
				break;
			case 8:
				label.setBackgroundColor(0xfff2b179);
				break;
			case 16:
				label.setBackgroundColor(0xfff59563);
				break;	
			case 32:
				label.setBackgroundColor(0xfff67c5f);
				break;
			case 64:
				label.setBackgroundColor(0xfff65e3b);
				break;
			case 128:
				label.setBackgroundColor(0xffedcf72);
				break;
			case 256:
				label.setBackgroundColor(0xffedcc61);
				break;
			case 512:
				label.setBackgroundColor(0xffedc850);
				break;
			case 1024:
				label.setBackgroundColor(0xffEB9437);
				break;
			case 2048:
				label.setBackgroundColor(0xffEA7821);
				break;
			default:
				label.setBackgroundColor(0xffEA7821);
				break;
			}			
			
			if(num<=512)label.setTextSize(32);
			if(num>512)label.setTextSize(24);
			if(num>8192)label.setTextSize(18);
			label.setText(""+num);
		}
		else{
			label.setBackgroundColor(0x33eee4da);
			label.setText("");
		}
		this.num = num;
	}
	private TextView label;
	
	public boolean equals(Card o) {
		return getNum()==o.getNum();
	}
	
	public TextView getLabel(){
		return label;
	}

}

package com.sogou.weekendplan.activity;


import com.sogou.weekendplan.R;
import com.sogou.weekendplan.R.id;
import com.sogou.weekendplan.R.layout;
import com.sogou.weekendplan.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlanDetailsActivity extends LeftMenuBaseActivity {

	
    int screenWidth;  
    int screenHeight;  
    int lastX;  
    int lastY; 
    
    int handlersTop;
    int handlersBottom;
    
    ImageView ivHandler1;
    ImageView ivHandler2;
    ImageView ivHandler3;
    ImageView ivHandler4;
    
    
    private String getEventTimeByPos(int top){
    	
    	if(handlersBottom == 0){
    		handlersTop = ivHandler1.getTop();
    		handlersBottom = ivHandler4.getTop();
    	}
    	double percent = (double)(top - handlersTop) / (double)(handlersBottom - handlersTop);
    	Log.i("@@@", "p:"+percent);
    	double timeHour = 9 + (18-9) * percent;
    	int hour = (int)timeHour;
    	int min = (int)((timeHour - hour) * 60);
    	int minFifteen = min - (min % 15);
    	
    	String str = String.format("%d:%02d", hour, minFifteen);
    	return str;
    	
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_details);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();  
        screenWidth = dm.widthPixels;  
        screenHeight = dm.heightPixels - 50;  
		
		ivHandler1 = (ImageView)findViewById(R.id.iv_handler1);

		ivHandler1.setTag(findViewById(R.id.tv_event_time1));
		findViewById(R.id.tv_event_time1).setTag(findViewById(R.id.layout_event1));
		
		ivHandler2 = (ImageView)findViewById(R.id.iv_handler2);
		ivHandler2.setTag(findViewById(R.id.tv_event_time2));
		findViewById(R.id.tv_event_time2).setTag(findViewById(R.id.layout_event2));
		
		ivHandler3 = (ImageView)findViewById(R.id.iv_handler3);
		ivHandler3.setTag(findViewById(R.id.tv_event_time3));
		findViewById(R.id.tv_event_time3).setTag(findViewById(R.id.layout_event3));
		
		ivHandler4 = (ImageView)findViewById(R.id.iv_handler4);
		ivHandler4.setTag(findViewById(R.id.tv_event_time4));
		findViewById(R.id.tv_event_time4).setTag(findViewById(R.id.layout_event4));
		
		
		
		
		OnTouchListener listener = 
		new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 int action=event.getAction();  
				 Log.i("@@@@@@", "Touch:"+action);  
			     switch(action){  
			     
			     case MotionEvent.ACTION_DOWN:  
			            lastX = (int) event.getRawX();  
			            lastY = (int) event.getRawY();  
			            Log.i("@@@@@@", "lastX " + lastX +",lastY " + lastY); 
			            break;  
			            /** 
			             * layout(l,t,r,b) 
			             * l  Left position, relative to parent  
			            t  Top position, relative to parent  
			            r  Right position, relative to parent  
			            b  Bottom position, relative to parent   
			             * */  
			        case MotionEvent.ACTION_MOVE:  
			            int dx =(int)event.getRawX() - lastX;  
			            int dy =(int)event.getRawY() - lastY;  
			            
			            Log.i("@@@@@@", "dx " + dx +",dy " + dy);     
			            
			          
			            int left = v.getLeft() + dx;  
			            int top = v.getTop() + dy;  
			            int right = v.getRight() + dx;  
			            int bottom = v.getBottom() + dy;   
			            
			            Log.i("@@@@@@", "position1" + left +", " + top + ", " + right + ", " + bottom);     
				           
			            if(left < 0){  
			                left = 0;  
			                right = left + v.getWidth();  
			            }                     
			            if(right > screenWidth){  
			                right = screenWidth;  
			                left = right - v.getWidth();  
			            }                     
			            if(top < 0){  
			                top = 0;  
			                bottom = top + v.getHeight();  
			            }                     
			            if(bottom > screenHeight){  
			                bottom = screenHeight;  
			                top = bottom - v.getHeight();  
			            }                     
			            v.layout(v.getLeft(), top, v.getRight(), bottom);  
			            Log.i("@@@@@@", "position" + left +", " + top + ", " + right + ", " + bottom);     
			            
			            lastX = (int) event.getRawX();  
			            lastY = (int) event.getRawY();  
			            
			            
			            TextView tvEventTime = (TextView)v.getTag();
			            LinearLayout layoutEvent = (LinearLayout)tvEventTime.getTag();
			            
			            layoutEvent.layout(layoutEvent.getLeft(), top, 
			            		layoutEvent.getRight(), top + layoutEvent.getHeight());
			            
			            tvEventTime.setText(getEventTimeByPos(top));
			            
			            break;  
			        case MotionEvent.ACTION_UP:  
			            break;                
			        }  
			            
			            
				return false;
			}
		};
		
		ivHandler1.setOnTouchListener(listener);
		ivHandler2.setOnTouchListener(listener);
		ivHandler3.setOnTouchListener(listener);
		ivHandler4.setOnTouchListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan_details, menu);
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

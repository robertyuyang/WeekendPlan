package com.sogou.weekendplan.activity;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sogou.weekendplan.MenuLeftFragment;
import com.sogou.weekendplan.R;
import com.sogou.weekendplan.R.id;
import com.sogou.weekendplan.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

public class LeftMenuBaseActivity extends SlidingFragmentActivity {
	
	private SlidingMenu slidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initSlidingMenu();
		
	}

	
	
	
	private void initSlidingMenu(){
		
		
		setBehindContentView(R.layout.menu_frame);
		slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); 
		//slidingMenu.setShadowDrawable(R.drawable.shadow_right);
		slidingMenu.setShadowWidth(30); 
		slidingMenu.setBehindOffset(80); 
		slidingMenu.setMode(SlidingMenu.LEFT); 
		//slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_frame); 
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.menu_frame, new MenuLeftFragment()).commit();
		
	}
}

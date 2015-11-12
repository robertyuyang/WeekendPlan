package com.sogou.weekendplan.activity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sogou.weekendplan.ListViewPageAdapter;
import com.sogou.weekendplan.R;
import com.sogou.weekendplan.R.id;
import com.sogou.weekendplan.R.layout;
import com.sogou.weekendplan.R.menu;
import com.sogou.weekendplan.plan.PlanManager;
import com.sogou.weekendplan.plan.PlanPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyPlansActivity extends LeftMenuBaseActivity {

	private ListView mCategories;
	private ListAdapter mAdapter;
	private ViewPager viewPager;
	
	private TextView tvNotStarted;
	private TextView tvOngoing;
	private TextView tvCompleted;
	
	private List<TextView> tabTextViewList = new ArrayList<TextView>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_plans);
		
		
		initViewPager();
	}

	
	private void onTabSelected(int pos){
		int count = tabTextViewList.size();
		for(int i = 0; i < count; i++){
			if(pos == i){
				this.tabTextViewList.get(i).setBackgroundResource(R.drawable.title_menu_current);
			}
			else{
				this.tabTextViewList.get(i).setBackgroundResource(R.drawable.title_menu_bg);
			}
		}
		
	}
	
	private void initViewPager(){
		viewPager = (ViewPager)findViewById(R.id.vp_myplans);
		List<View> viewList = new ArrayList<View>();
		ListView lvNotStarted = new ListView(this);
		
		
		List<String> mDatas = new ArrayList<String>();
		
		List<PlanPackage> myPlans = PlanManager.getInstance().getPlans();
		int size = myPlans.size();
		for(int i = 0; i < size; i++){
			PlanPackage plan = myPlans.get(i);
			mDatas.add(plan.GetName());
		}
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
		lvNotStarted.setAdapter(mAdapter);
		viewList.add(lvNotStarted);
		lvNotStarted.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(MyPlansActivity.this, PlanDetailsActivity.class);
					startActivity(intent);
			}
		});
		
		
		
		ListView lvOngoing = new ListView(this);
		
		
		List<String> mDatas2 = Arrays
				.asList("Ongoing1", "Ongoing2", "Ongoing3", "Ongoing4", "Ongoing5");
		ListAdapter mAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas2);
		lvOngoing.setAdapter(mAdapter2);
		
		viewList.add(lvOngoing);
		
		
		ListView lvCompleted = new ListView(this);
		
		List<String> mDatas3 = Arrays
				.asList("Completed1", "Completed2", "Completed3", "Completed4" );
		ListAdapter mAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas3);
		lvCompleted.setAdapter(mAdapter3);
		
		viewList.add(lvCompleted);
		
		
		viewPager.setAdapter(new ListViewPageAdapter(viewList));
		
		
		tvNotStarted = (TextView)findViewById(R.id.tv_not_started);
		tvOngoing = (TextView)findViewById(R.id.tv_ongoing);
		tvCompleted = (TextView)findViewById(R.id.tv_completed);
		
		tabTextViewList.add(tvNotStarted);
		tabTextViewList.add(tvOngoing);
		tabTextViewList.add(tvCompleted);
		
		for(int i = 0; i < tabTextViewList.size(); i++){
			tabTextViewList.get(i).setTag(i);
			tabTextViewList.get(i).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int pos = (Integer)v.getTag();
					viewPager.setCurrentItem(pos);
					onTabSelected(pos);
					
				}
			});
		}
		
		
		
		
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				onTabSelected(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
//		mCategories = (ListView)findViewById(R.id.id_listview_myplans);
//		
//		List<String> mDatas = new ArrayList<String>();
//		
//		List<PlanPackage> myPlans = PlanManager.getInstance().getPlans();
//		int size = myPlans.size();
//		for(int i = 0; i < size; i++){
//			PlanPackage plan = myPlans.get(i);
//			mDatas.add(plan.GetName());
//		}
//		
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
//		mCategories.setAdapter(mAdapter);
//		
//		mCategories.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//				
//			}
//		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_plans, menu);
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

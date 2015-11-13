package com.sogou.weekendplan.activity;

import java.util.ArrayList;
import java.util.List;

import com.sogou.weekendplan.MenuLeftFragment;
import com.sogou.weekendplan.PlanOptionFragment;
import com.sogou.weekendplan.R;
import com.sogou.weekendplan.R.id;
import com.sogou.weekendplan.R.layout;
import com.sogou.weekendplan.R.menu;
import com.sogou.weekendplan.plan.PlanFactory;
import com.sogou.weekendplan.plan.PlanManager;
import com.sogou.weekendplan.plan.PlanPackage;
import com.sogou.weekendplan.plan.DemoPlanFactory;
import com.sogou.weekendplan.plan.PlanEvent;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.test.InstrumentationTestSuite;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlanGenerateActivity extends LeftMenuBaseActivity {

	private ViewPager planView;
	private List<PlanOptionFragment> planFragmentList;
	
	private PlanFactory planFactory;
	private PlanPackage newPlanPack;

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		planFactory = new DemoPlanFactory();
		
		
		setContentView(R.layout.activity_plan_generate);
		
		initViewPager();
		initIssues();
		
		
		PlanPackage planPackage = this.planFactory.CreatePlan();
		
		planFragmentList.get(0).SetPlanPackage(planPackage);
		
		Button btnOk = (Button)findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				PlanManager.getInstance().addPlan(planFactory.getCurrentPlan());
				Intent intent = new Intent(PlanGenerateActivity.this, PlanDeterminedActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plan, menu);
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
	
	private void initIssues(){
		
		
//		Button btnIssue0 = (Button)findViewById(R.id.btn_issue0);
//		btnIssue0.setTag(0);
//		Button btnIssue1 = (Button)findViewById(R.id.btn_issue1);
//		btnIssue1.setTag(1);
//		Button btnIssue2 = (Button)findViewById(R.id.btn_issue2);
//		btnIssue2.setTag(2);
//		android.view.View.OnClickListener listenr = new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				int issueCode = (Integer)v.getTag();
//				planFactory.AddIssue(issueCode);
//			}
//		};
//		btnIssue0.setOnClickListener(listenr);
//		btnIssue1.setOnClickListener(listenr);
//		btnIssue2.setOnClickListener(listenr);
		
	}
	public void initViewPager(){
		
		planFragmentList = new ArrayList<PlanOptionFragment>();
		planFragmentList.add(new PlanOptionFragment(this.planFactory));
		planFragmentList.add(new PlanOptionFragment(this.planFactory));
		planFragmentList.add(new PlanOptionFragment(this.planFactory));

		
		planView = (ViewPager)findViewById(R.id.viewpager_plan);
		planView.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				planFragmentList.get(arg0).SetPlanPackage(newPlanPack);
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				if(arg1 > 0.3){
					newPlanPack = planFactory.CreatePlan();
					
//					if(arg0 + 1 < planFragmentList.size()){
//						planFragmentList.get(arg0+1).SetPlanPackage(newPlanPack);
//					}
					
				}
				
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		planView.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return planFragmentList.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return planFragmentList.get(arg0);
			}
		});
	}
}

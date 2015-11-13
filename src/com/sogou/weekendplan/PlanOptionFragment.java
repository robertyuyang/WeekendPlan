package com.sogou.weekendplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.sogou.weekendplan.plan.PlanEvent;
import com.sogou.weekendplan.plan.PlanFactory;
import com.sogou.weekendplan.plan.PlanPackage;
import com.sogou.weekendplan.uicontrol.DefinedScrollView;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PlanOptionFragment extends Fragment
{
	private View mView;
	private View itemView;
	private View itemView2;
	private ListAdapter mAdapter;
	private PlanPackage planPackage;
	
	private PlanFactory planFactory;
	
	public PlanOptionFragment(PlanFactory planFactory){
		this.planFactory = planFactory;
	}
	
	public void SetPlanPackage(PlanPackage planPackage){
		this.planPackage = planPackage;
		if(mView != null){
			initView(itemView);
			initView(itemView2);
		}
		
	}
//	public void SetEventList(List<PlanEvent> eventList){
//		this.eventList = eventList;
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mView == null)
		{
			mView = inflater.inflate(R.layout.fragment_plan_options, container, false);
			
			itemView = inflater.inflate(R.layout.fragment_plan_option, container, false);
			itemView2= inflater.inflate(R.layout.fragment_plan_option, container, false);
			
			DefinedScrollView scrollView = (DefinedScrollView) mView.findViewById(R.id.definedview);
			scrollView.addView(itemView);
			scrollView.addView(itemView2);
			
			scrollView.setPageListener(new DefinedScrollView.PageListener() {
				@Override
				public void page(int page) { 
				
				} 
			}); 
			
			
			if(planPackage != null){
				initView(itemView);
				initView(itemView2);
			}
			
		}
		return mView;
	}

	private void initView(View view)	{
		
		ListView planDetails =  (ListView)view.findViewById(R.id.plan_details);
		TextView tvPlanName = (TextView)view.findViewById(R.id.tv_plan_name); 
		
		mAdapter = new PlanAdapter(getActivity(), this.planPackage.eventList);
		planDetails.setAdapter(mAdapter);
		tvPlanName.setText(this.planPackage.GetName());
		
		
		
		
		initIssues();
		this.planFactory.setCurrentPlan(this.planPackage);
		

	}
	
	private void initIssues(){

		int count = this.planPackage.potentialIssueMap.size();
		Button btns[] = new Button[count];
		
		android.view.View.OnClickListener listenr = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(planFactory == null){
					return;
				}
				
				int issueCode = (Integer)v.getTag();
				if(planPackage.isIssueCodeAdded(issueCode)){
					v.setBackgroundResource(R.drawable.btn_normal);
					planPackage.RemoveIssue(issueCode);
				}
				else{
					v.setBackgroundResource(R.drawable.btn_pressed);
					planPackage.AddIssue(issueCode);
				}
				
			}
		};
		
		ViewGroup group = (ViewGroup)this.itemView.findViewById(R.id.issue_group);
		group.removeAllViews();
		int i = 0;
		for(Entry<Integer, String> entry:this.planPackage.potentialIssueMap.entrySet()){
			btns[i] = new Button(getActivity());
			btns[i].setText(entry.getValue());
			btns[i].setTag(entry.getKey());
			btns[i].setOnClickListener(listenr);
			btns[i].setBackgroundResource(R.drawable.btn_normal);
			LayoutParams param = new LayoutParams((int)getResources().getDimension(R.dimen.btn_issue_width),
					(int)getResources().getDimension(R.dimen.btn_issue_height));
			param.leftMargin = (int)getResources().getDimension(R.dimen.btn_issue_marginleft)+30;
			btns[i].setLayoutParams(param);
			group.addView(btns[i]);
			i++;
		}
		
		
		
	}
}

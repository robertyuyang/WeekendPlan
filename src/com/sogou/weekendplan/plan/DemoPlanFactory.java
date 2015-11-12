package com.sogou.weekendplan.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.R.integer;

public class DemoPlanFactory implements PlanFactory {
	
	
	private PlanPackage currentPlanPackage = null;
	private List<PlanPackage> historyPlanPackageList = new LinkedList<PlanPackage>();
	private int personCount;
	
	
	@Override
	public void SetRelationship(int relationshipID) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void SetPersonCount(int count) {
		
		personCount = count;
		
	}
	

	
	

	@Override
	public PlanPackage CreatePlan() {
		
		PlanPackage newPack = new PlanPackage();
		newPack.eventList = new ArrayList<PlanEvent>();
		newPack.potentialIssueMap = new HashMap<Integer, String>();
		
		if(this.currentPlanPackage == null || this.currentPlanPackage.submittedIssueList.isEmpty()){
			newPack.eventList.add(new PlanEvent("", "吃饭", "拿渡麻辣香锅", 80, 8.1));
			newPack.eventList.add(new PlanEvent("", "看电影", "奥体CGV", 50, 8.6));
			newPack.eventList.add(new PlanEvent("", "开房", "盘古酒店", 300, 9.6));
			
			newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_FAR, "距离太远");
			newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_EXPENSIVE, "花费太高");
			
			newPack.setName("奥体看电影");
			
		}
		else{
			
			
			if(this.currentPlanPackage.submittedIssueList.contains(PlanPackage.ISSUE_TOO_HURRY)){
				newPack.eventList.add(new PlanEvent("", "看电影", "华星UME", 80, 8.1));
				newPack.eventList.add(new PlanEvent("", "散步", "南锣鼓巷", 50, 8.6));
				
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_FAR, "距离太远");
				
				newPack.setName("散个步");
			}
			else if(this.currentPlanPackage.submittedIssueList.contains(PlanPackage.ISSUE_TOO_FAR)){
				newPack.eventList.add(new PlanEvent("", "吃饭", "五道口购物中心", 80, 8.1));
				newPack.eventList.add(new PlanEvent("", "看电影", "五道口电影院", 50, 8.6));
				newPack.eventList.add(new PlanEvent("", "画油画", "华清嘉园梦学堂", 300, 9.6));
				newPack.eventList.add(new PlanEvent("", "酒吧", "五角星酒吧", 300, 9.6));
				
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_EXPENSIVE, "花费太高");
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_HURRY, "计划太紧");
				
				newPack.setName("五道口约会");
			}
			else if(this.currentPlanPackage.submittedIssueList.contains(PlanPackage.ISSUE_TOO_EXPENSIVE)){
				newPack.eventList.add(new PlanEvent("", "吃饭", "五道口没名生煎", 80, 8.1));
				newPack.eventList.add(new PlanEvent("", "看电影", "五道口电影院", 50, 8.6));
				newPack.eventList.add(new PlanEvent("", "开房", "如家酒店", 300, 9.6));
				
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_FAR, "距离太远");
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_EXPENSIVE, "花费太高");
				newPack.potentialIssueMap.put(PlanPackage.ISSUE_TOO_HURRY, "计划太紧");
				
				
				newPack.setName("五道口开房");
			}
			
			
		}
		
		this.historyPlanPackageList.add(newPack);
		
		return newPack;
	}

	@Override
	public void setCurrentPlan(PlanPackage planPackage) {
		this.currentPlanPackage = planPackage;
		
	}

	@Override
	public PlanPackage getCurrentPlan() {
		// TODO Auto-generated method stub
		return this.currentPlanPackage;
	}

	
	
}

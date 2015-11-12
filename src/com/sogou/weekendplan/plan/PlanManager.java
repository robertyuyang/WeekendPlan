package com.sogou.weekendplan.plan;

import java.util.LinkedList;
import java.util.List;

public class PlanManager {
	private static PlanManager instance = new PlanManager();
	
	private List<PlanPackage> myPlans = new LinkedList<PlanPackage>();
	
	public static PlanManager getInstance(){
		return instance;
	}
	
	public void addPlan(PlanPackage planPackage){
		myPlans.add(planPackage);
		PlanPackage p = myPlans.get(0);
	}
	
	public List<PlanPackage> getPlans(){
		return myPlans;
	}
}

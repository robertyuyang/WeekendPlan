package com.sogou.weekendplan.plan;

public interface PlanFactory {
	
	
	
	
	public void SetRelationship(int relationshipCode);
	public void SetPersonCount(int count);
	public PlanPackage CreatePlan();
	public void setCurrentPlan(PlanPackage planPackage);
	public PlanPackage getCurrentPlan();
}

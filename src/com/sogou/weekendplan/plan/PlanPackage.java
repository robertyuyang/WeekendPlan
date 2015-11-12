package com.sogou.weekendplan.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanPackage {
	
	static int index = 0;
	
	public static final int ISSUE_TOO_FAR = 0;
	public static final int ISSUE_TOO_EXPENSIVE = 1;
	public static final int ISSUE_TOO_HURRY = 2;

	public List<Integer> submittedIssueList = new ArrayList<Integer>();
	
	public List<PlanEvent> eventList;
	public Map<Integer, String> potentialIssueMap;
	
	private String name;
	
	public PlanPackage(){
		
		name = "Plan " + Integer.toString(index++);
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public String GetName(){
		return name;
	}
	
	public void AddIssue(int issueCode) {
		if(submittedIssueList == null){
			submittedIssueList = new ArrayList<Integer>();
		}
		if(submittedIssueList != null){
			submittedIssueList.add(issueCode);
		}
		
	}
	
	public boolean isIssueCodeAdded(int issueCode) {
		return this.submittedIssueList.contains(issueCode);
		
	}

	public void RemoveIssue(int issueCode) {
		this.submittedIssueList.remove((Integer)issueCode);
		
	}
}

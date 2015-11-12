package com.sogou.weekendplan.plan;

public class PlanEvent {
	public String img;
	public String name;
	public String location;
	public int averageExpense;
	public double mark;
	
	public PlanEvent(String eventImg, String eventName,  String eventLocation,
			int averageExpense, double mark){
		this.img = eventImg;
		this.name = eventName;
		this.location = eventLocation;
		this.averageExpense = averageExpense;
		this.mark = mark;
	}
}

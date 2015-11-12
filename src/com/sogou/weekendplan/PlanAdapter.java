package com.sogou.weekendplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import com.sogou.weekendplan.plan.PlanEvent;

public class PlanAdapter extends BaseAdapter{	
	private List<PlanEvent> eventList;
	private LayoutInflater inflater;
	
	public PlanAdapter(Context context, List<PlanEvent> list) {
		this.inflater = LayoutInflater.from(context);
		eventList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eventList != null ? eventList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return eventList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = this.inflater.inflate(R.layout.item_plan_event,  parent, false);
			holder = new ViewHolder();
			holder.ivImg = (ImageView)convertView.findViewById(R.id.event_img);
			holder.tvName = (TextView)convertView.findViewById(R.id.event_name);
			holder.tvLocation = (TextView)convertView.findViewById(R.id.event_location);
			holder.tvMark = (TextView)convertView.findViewById(R.id.event_mark);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		PlanEvent event = this.eventList.get(position);
		//holder.ivImg = ;
		holder.tvName.setText(event.name);
		holder.tvLocation.setText(event.location);
		
		DecimalFormat df = new DecimalFormat("######0.00");   
		holder.tvMark.setText("人均: " + Integer.toString(event.averageExpense)
		+ " 评分： "+ df.format(event.mark));
		
		return convertView;
	}

	
	private class ViewHolder{
		public ImageView ivImg;
		public TextView tvName;
		public TextView tvLocation;
		public TextView tvMark;
	}
}

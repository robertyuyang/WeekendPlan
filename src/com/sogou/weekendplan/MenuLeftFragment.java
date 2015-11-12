package com.sogou.weekendplan;

import java.util.Arrays;
import java.util.List;

import com.sogou.weekendplan.activity.MainActivity;
import com.sogou.weekendplan.activity.MyPlansActivity;
import com.sogou.weekendplan.activity.PlanDeterminedActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MenuLeftFragment extends Fragment
{
	private View mView;
	private ListView mCategories;
	private List<String> mDatas = Arrays
			.asList("计划生成", "我的计划", "设置", "分享", "关于");
	private ListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mView == null)
		{
			initView(inflater, container);
		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container)
	{
		mView = inflater.inflate(R.layout.left_menu, container, false);
		mCategories = (ListView) mView
				.findViewById(R.id.id_listview_categories);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mDatas);
		mCategories.setAdapter(mAdapter);
		
		mCategories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position == 0){
					Intent intent = new Intent(getActivity(), MainActivity.class);
					startActivity(intent);
				}
				else if(position == 1){
					Intent intent = new Intent(getActivity(), MyPlansActivity.class);
					startActivity(intent);
				}
				
			}
		});
	}
}

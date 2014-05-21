package com.image.example;


import com.image.example.advedit.FrameEffectActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdvancedEditEntryActivity extends ListActivity {

	private String[] itemText = { "Frame", };

	private Class[] itemActivity = { FrameEffectActivity.class,// 图片编辑-相框
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemText));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, itemActivity[(int) id]);
		startActivity(intent);
	}
}

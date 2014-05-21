package com.image.example;

import com.image.example.utils.Directories;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class MainActivity extends ListActivity {

	private String[] itemText = {
			"AdvancedEdit",
			};
	
	private Class[] itemActivity = {
			AdvancedEditEntryActivity.class,//图片高级编辑
			};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemText));
		Directories.init();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, itemActivity[(int)id]);
		startActivity(intent);	
	}

}

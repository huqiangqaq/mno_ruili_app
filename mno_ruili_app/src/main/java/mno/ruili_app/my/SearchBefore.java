package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mno.down.util.SharePreTolls;
import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchBefore extends Fragment{
	TextView tv_deleteAll;
	ListView lv_history;
	View view;
	ArrayAdapter<String> adapter;
	List<String> list = new ArrayList<String>();
	webhandler handler,handler2;
	private int currentPage1 = 1;
	private int currentPage2 = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_before, null);
		init();
		return view;
	}

	private void init() {		
		//list=SharePreTolls.readHistory(view.getContext(), 5);
		list=SharePreTolls.readHistory(SearchBefore.this.getActivity(), 5);
		adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list);
		lv_history.setAdapter(adapter);
		tv_deleteAll=(TextView) view.findViewById(R.id.tv_deleteAll);
		lv_history=(ListView) view.findViewById(R.id.lv_history);
		tv_deleteAll.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				list.clear();	
				adapter.notifyDataSetChanged();
			}
		});
		lv_history.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//String curr=list.get(position);
				String curr=(String) lv_history.getAdapter().getItem(position);
				handler=new webhandler(){
					
				};
				Map<String, String> params = new HashMap<String, String>();
				params.put("page", currentPage1+"");
				params.put("key_word", curr);
				handler.SetRequest(new RequestType("ZP",Type.getPostList),params);
			}
		});
	}
}

package mno.ruili_app.customView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno_ruili_app.adapter.tv;

import android.content.Context;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;


public class ViewMiddle extends LinearLayout implements ViewBaseAction {
	
	private ListView proListView;
	private ListView cityListView;
	private ArrayList<String> groups = new ArrayList<String>();	
	//private ArrayList<tv> groups = new ArrayList<tv>();
	List<String> listId = new ArrayList<String>();//子项id集合
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
	private TextAdapter cityAdapter;//子种类的适配器
	private TextAdapter provinceAdapter;//父种类的适配器
	private OnSelectListener mOnSelectListener;
	private int provincePosition = 0;//初始化父种类的位置
	private int cityPosition = 0;//初始化子种类的位置
	private String showString = "工作地点";
	InputStreamReader inputStreamReader;
	private String[] mFrom,mId;
	
	public String getShowText() {
		return showString;
	}

	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	/*
	 * 多余的
	 */
	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				provinceAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				provincePosition  = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
				cityAdapter.setSelectedPosition(j);
				cityPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}
	String result;
	String name,id;
	JSONObject  jsprovince,jscity;
	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		proListView = (ListView) findViewById(R.id.listView);
		cityListView = (ListView) findViewById(R.id.listView2);
		//setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_mid));
		//1.父项数据初始化
		try {
			inputStreamReader = new InputStreamReader(context.getAssets().open("place.json"), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    String line;
		    StringBuilder stringBuilder = new StringBuilder();
		    while((line = bufferedReader.readLine()) != null) {
		    	stringBuilder.append(line);
		     }
		     result=stringBuilder.toString();
		     bufferedReader.close();
		     inputStreamReader.close();		
		      
		     } catch (UnsupportedEncodingException e) {
		    	  e.printStackTrace();
	    	  } catch (IOException e){
	    		  e.printStackTrace();
	    	  }			
		try {  				
			JSONArray  data =  new JSONArray(result );
			int length=data.length();
		    for(int i=0;i<length; i++){
		    	jsprovince = data.getJSONObject(i);			   
			    name =  jsprovince.getString("name");
			    //id=  jsprovince.getString("id");
				//listId.add(id);		
				groups.add(name);
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//省份
		provinceAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		provinceAdapter.setTextSize(17);
		provinceAdapter.setSelectedPositionNoNotify(provincePosition );
		proListView.setAdapter(provinceAdapter);
		provinceAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				//if (position < children.size()) {
				//2.子项的数据加载
				listId.clear();
				childrenItem.clear();
				String a=groups.get(position);
				getjson(a);
					//childrenItem.addAll(children.get(position));
					//plateListViewAdapter.notifyDataSetChanged();
				//}
			}
		});
//		if (tEaraPosition < children.size())
//			childrenItem.addAll(children.get(tEaraPosition));
		//市份
		cityAdapter = new TextAdapter(context, childrenItem,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		cityAdapter.setTextSize(15);
		cityAdapter.setSelectedPositionNoNotify(cityPosition);
		cityListView.setAdapter(cityAdapter);
		cityAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view,  int position) {
				
				//showString = childrenItem.get(position);
				if (mOnSelectListener != null) {
					showString = childrenItem.get(position);
					mOnSelectListener.getValue(listId.get(position),showString);
				}

			}
		});
		if (cityPosition < childrenItem.size())
			showString = childrenItem.get(cityPosition);
//		if (showString.contains("不限")) {
//			showString = showString.replace("不限", "");
//		}
		
		//setDefaultSelect();//

	}

	public void setDefaultSelect() {
		proListView.setSelection(provincePosition );
		cityListView.setSelection(cityPosition);
	}
	private void getjson(String city) {
		// TODO Auto-generated method stub
			try {  				
				JSONArray  data =  new JSONArray(result );
				int length=data.length();
			    for(int i=0;i<length; i++){		    	
			    	jsprovince =data.getJSONObject(i);			   
					name =jsprovince.getString("name");
					if(name.equals(city)){			    
					    find(city);
					}
			    }
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		private void find(String city) {
			mFrom=new String[]{"全部","北京","天津","上海","重庆","香港","澳门"};
			mId=new String[]{"0","300","2800","2600","400","3100","200"};
			for (int i = 0; i <mFrom.length; i++) { 
				 if(city.equals(mFrom[i])){
					 listId.add(mId[i]);
					 childrenItem.add(mFrom[i]);
					 cityAdapter.notifyDataSetChanged();
			    	 return;
				 }        
		    } 	
			JSONArray code;
				try {				
					code = jsprovince.getJSONArray("child");
					int length=code.length();
					for(int i=0;i<length; i++){				    	
				    	JSONObject jscity =   code.getJSONObject(i);					   
					    String name2 =  jscity.getString("name");
					    String id2=jscity.getString("id");
					    listId.add(id2);
					    childrenItem.add(name2);					    
					    cityAdapter.notifyDataSetChanged();			    							  
					}				    				   				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				    
	}
	

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String id,String showText);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}

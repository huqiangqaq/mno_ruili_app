package mno.ruili_app.customView;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class ViewLeft2 extends RelativeLayout implements ViewBaseAction{

	private ListView mListView;
//	private final String[] items = new String[] { "item1", "item2", "item3", "item4", "item5", "item6" };//显示字段
//	private final String[] itemsVaule = new String[] { "1", "2", "3", "4", "5", "6" };//隐藏id
	List<String> list1 = new ArrayList<String>();//id
	List<String> list2 = new ArrayList<String>();//value
	private OnSelectListener mOnSelectListener;	
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "经验";
	private Context mContext;

	public String getShowText() {
		return showText;
	}

	public ViewLeft2(Context context) {
		super(context);
		init(context);
	}

	public ViewLeft2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewLeft2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		list1.add("0");
		list2.add("全部");
		webhandler handler=new webhandler(){
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONArray data=response.getJSONArray("data");
					for(int i=0;i<data.length();i++){
						JSONObject salary=data.getJSONObject(i);
						String salaryId=salary.getString("id");
						String salaryResult=salary.getString("name");
						list1.add(salaryId);
						list2.add(salaryResult);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		handler.SetRequest(new RequestType("ZP",Type.getJobYear),params);	
		
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		//setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_left));
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new TextAdapter(context, list2, R.drawable.choose_item_right, R.drawable.choose_eara_item_selector);
		adapter.setTextSize(17);
		if (mDistance != null) {
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText= list2.get(i);
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText= list2.get(position);
					mOnSelectListener.getValue(list1.get(position), list2.get(position));
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}


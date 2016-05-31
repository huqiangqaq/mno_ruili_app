package mno.ruili_app.my;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EduDescribeActivity extends Activity{
	TextView tv_submit,tv_warning;
	EditText et_workDescribe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edu_describe_activity);		
		tv_submit=(TextView) findViewById(R.id.tv_submit);
		tv_warning=(TextView) findViewById(R.id.tv_warning);		
		et_workDescribe=(EditText) findViewById(R.id.et_workDescribe);		
		Intent intent=getIntent();
		String workDescribe=intent.getStringExtra("eduDescribe");
		et_workDescribe.setText(workDescribe);
		if(workDescribe!=null){
			tv_warning.setText("还可输入"+(2000-workDescribe.length())+"字");
		}		
		TextWatcher tw=new TextWatcher() {
			private CharSequence temp;
	        private int editStart ;
	        private int editEnd ;
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				tv_warning.setText(s);				
			}			
			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2,int arg3) {
				temp = s;
				//tv_warning.setText("还可输入"+(2000-et_workDescribe.getText().toString().length())+"字");
			}			
			@Override
			public void afterTextChanged(Editable s) {
				editStart = et_workDescribe.getSelectionStart();
	            editEnd = et_workDescribe.getSelectionEnd();
	    		tv_warning.setText("还可输入"+(2000-temp.length())+"字");
	            if (temp.length() > 2000) {
	                Toast.makeText(EduDescribeActivity.this,
	                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
	                s.delete(editStart-1, editEnd);
	                int tempSelection = editStart;
	                et_workDescribe.setText(s);
	                et_workDescribe.setSelection(tempSelection);
	            }
				
			}
		};
		et_workDescribe.addTextChangedListener(tw);
		
		tv_submit.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();			
				intent.putExtra("eduDescribe", et_workDescribe.getText().toString());
				EduDescribeActivity.this.setResult(RESULT_OK, intent);
				EduDescribeActivity.this.finish();				
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("EduDescribeActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("EduDescribeActivity");
		MobclickAgent.onPause(this);
	}
}

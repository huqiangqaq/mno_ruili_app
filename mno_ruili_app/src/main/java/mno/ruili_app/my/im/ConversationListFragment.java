package mno.ruili_app.my.im;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.NetUtils;
//自己的
public class ConversationListFragment extends EaseConversationListFragment{

    //private TextView errorText;

    @Override
    protected void initView() {
        super.initView();        
    }
    String userId,nickname;
    @Override
    protected void setUpView() {
        super.setUpView();
        // 注册上下文菜单
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();//环信用户名
//                userId=username.substring(7);
//        		//根据userId获取自己服务器的头像和昵称
//        		webhandler handler=new webhandler(){
//        			@Override
//        			public void OnResponse(JSONObject response) {
//        				// TODO Auto-generated method stub
//        				super.OnResponse(response);
//        				try {
//        					JSONArray data =response.getJSONArray("data");
//        					for (int j = 0; j < data.length(); j++) {
//        						JSONObject user=data.getJSONObject(j);
//        						nickname=user.getString("username");
//        						if(nickname==null){
//        							nickname="dsxuser"+userId;//如果对方的昵称为空则把昵称设置为环信uid
//        						}
//        					}
//        					
//        				} catch (JSONException e) {
//        					// TODO Auto-generated catch block
//        					e.printStackTrace();
//        				}
//        			}
//        		};
//        		Map<String, String> params = new HashMap<String, String>();
//        		params.put("uids", userId);
//        		handler.SetRequest(new RequestType("", Type.getUserNameAndPhoto), params);
        		
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, 0).show();
                else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);                   
                    // it's single chat
                    intent.putExtra(MConstant.EXTRA_USER_ID, username);
//                    intent.putExtra("nickname", nickname);
//					intent.putExtra("from", "conversation");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
         //errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
          //errorText.setText(R.string.the_current_network);
        }
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu); 
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
    	if (tobeDeleteCons == null) {
    	    return true;
    	}
        try {
            // 删除此会话
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // 更新消息未读数
        //((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}

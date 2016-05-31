package mno.ruili_app.my.im;


import mno.ruili_app.appuser;
import mno_ruili_app.net.RequestType;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
//自己的
public class UserProfileManager {
	private EaseUser currentUser;

	public synchronized EaseUser getCurrentUserInfo() {
		//if (currentUser == null) {
			String username = EMClient.getInstance().getCurrentUser();
			currentUser = new EaseUser(username);
			String nick = appuser.getInstance().getUserinfo().nickname.toString();
			currentUser.setNick((nick != null) ? nick : username);
			String avatar=appuser.getInstance().getUserinfo().bigImg.toString();
			String imageUrl =RequestType.getWebUrl_(avatar);
			currentUser.setAvatar(imageUrl);
		//}
		return currentUser;
	}

	public void asyncGetCurrentUserInfo() {
		setCurrentUserNick(appuser.getInstance().getUserinfo().nickname.toString());		
		setCurrentUserAvatar(appuser.getInstance().getUserinfo().bigImg.toString());
	}
	private void setCurrentUserNick(String nickname) {
		getCurrentUserInfo().setNick(nickname);
		//SpuserInfoManager.getInstance().setCurrentUserNick(nickname);
	}

	private void setCurrentUserAvatar(String avatar) {
		getCurrentUserInfo().setAvatar(avatar);
		//SpuserInfoManager.getInstance().setCurrentUserAvatar(avatar);
	}

//	private String getCurrentUserNick() {
//		return SpuserInfoManager.getInstance().getCurrentUserNick();
//	}

//	private String getCurrentUserAvatar() {
//		return SpuserInfoManager.getInstance().getCurrentUserAvatar();
//	}

}

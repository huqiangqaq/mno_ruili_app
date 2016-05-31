/**
 * 
 */

package mno.ruili_app.ct;

import java.io.File;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.GooglePlusShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.TwitterShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


/**
 * 
 */
public class CustomShareBoard extends PopupWindow implements OnClickListener {

    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
    private Activity mActivity;

    public CustomShareBoard(Activity activity) {
        super(activity);
        this.mActivity = activity;
        initView(activity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.showdialog, null);
        rootView.findViewById(R.id.login_qq).setOnClickListener(this);
        rootView.findViewById(R.id.login_wx).setOnClickListener(this);
        rootView.findViewById(R.id.login_wb).setOnClickListener(this);
        
        setContentView(rootView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_qq:
                performShare(SHARE_MEDIA.QQ);
                break;
            case R.id.login_wx:
                performShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_wb:
                performShare(SHARE_MEDIA.SINA);
                break;
         /*   case R.id.qzone:
                performShare(SHARE_MEDIA.QZONE);
                break;*/
            default:
                break;
        }
    }

    private void performShare(SHARE_MEDIA platform) {
        mController.postShare(mActivity, platform, new SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = platform.toString();
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                    showText += "平台分享成功";
                } else {
                    showText += "平台分享失败";
                }
                Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
   

}

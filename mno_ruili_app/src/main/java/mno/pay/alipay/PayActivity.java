package mno.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.Main;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.home.home_zb_rl;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
public class PayActivity extends FragmentActivity {

	//商户PID
	/*PID:2088901309409654
AppID(应用ID)
wxf80b1e2d2832a6cb
AppSecret(应用密钥)
c33038d1dabdc30d3dd75b13ebe59295*/
	public static String PARTNER = "2088211523214461";
	//商户收款账号
	public static String SELLER = "rlzxjy@wzect.com";
    //商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICWwIBAAKBgQCT6jmV6gXdLpW9bef6+8eF36blysT4/m8+TVl/lTaorhwuGy38XticYuniUiqg9V+R5vFFpfnUV4/2P06C+IJ9LcePG207/0mWdhHbPTlKilT5i6+a1zckYHPep6N6pd1CNxnJwpuDmerkS2X2SHOEr/FxuDE7HWfTerzHQGZOVQIDAQABAoGAZ+CBSogMUDXsERmDZh62E41qv0x0okzFQSK/LLtUpIeJ4NNTqz3Mr59Dk56Ss3E/Cc7/OCOE3RwVTSFOeOofcMqFjD+l+kEuYeT9lqmYerbv2UTNcnsVmfXSlec6eibnG3mk40A0Nm/I2O4lETu9tVOchhKHaKhQwB8D3sRNwc0CQQDEijoFA95lBRrnhdGL8/stVuvbg4OkFVmq425F7pkSidtmgkvuIA2y51oqfemk7p1QddwaQUIxiJR1uY+eJGq7AkEAwKoNY8fLqS6Ip1oKjbdmZ7UmDXI9TD1B+LD5SmHV4bODpSHfusyNxRDwwx9Ow1/JclQLaEEEnsWvejJp66/CLwJAFQEgykBrmCg/g9bNWkOs6F+NvVwEyZhuiNDdjCO/wDdzdzUdoElUrXYEMN5cBgBqV5sA9t1akaijFSHLs3742QJAYGU06dWPKoijysecPCekzwKtlVCxJrIHjfxN3k9xCcoddcOrAvVRgWXmcFn7uHj2e4Ndab4OJ7pU/9i1cjyv9QJAThIOX3kfW6WohZRLvSarDE/UbVtfkMLEyMOt+t9WY8hblkXzshecPQm/XseNWRJvf3bUe+Zi1DIaEue1akYQ/A==";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT6jmV6gXdLpW9bef6+8eF36blysT4/m8+TVl/lTaorhwuGy38XticYuniUiqg9V+R5vFFpfnUV4/2P06C+IJ9LcePG207/0mWdhHbPTlKilT5i6+a1zckYHPep6N6pd1CNxnJwpuDmerkS2X2SHOEr/FxuDE7HWfTerzHQGZOVQIDAQAB";


	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	 webhandler handler_;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Map<String,String> map = new HashMap<String,String>();
					map=transStringToMap(resultInfo);
					String a =map.get("out_trade_no");
					a=a.replace("\"", "").trim();
					if(!appuser.getInstance().IsLogin())
		   	    	 {
		     			 appuser.getInstance().Login(PayActivity.this);
		     			 return;
		   	    	 }
					 Map<String, String> params = new HashMap<String, String>();
	    			 params.put("productId",Constant.productId);
	    		     params.put("paymentId",Constant.paymentId);
	    		    // params.put("grandTotal",Constant.itemid);
	    		     params.put("grandTotal",Constant.grandTotal);
	    		     params.put("totalDue",Constant.totalDue);
	    		     params.put("transNo",a);
	    		     //params.put("grandTotal","");
	    			 handler_.SetRequest(new RequestType("4",Type.setOrder),params);
					//Toast.makeText(PayActivity.this, "支付成功"+a,Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	TextView product_1,product_2,product_3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		PARTNER=Constant.apiMemId;
		SELLER=Constant.apiNo;
		product_1=(TextView)this.findViewById(R.id.product_1);
		product_2=(TextView)this.findViewById(R.id.product_2);
		product_3=(TextView)this.findViewById(R.id.product_3);
		product_1.setText("睿立会员");
		product_2.setText( "睿立会员"+Constant.itemzt+"个月");
		product_3.setText(Constant.totalDue+"元");
		 handler_ = new webhandler(){

 			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(PayActivity.this,mess);
				finish();
			}

	

			@Override
 			public void OnResponse(JSONObject response) {
 				// TODO Auto-generated method stub
 				// TODO Auto-generated method stub
				try {
					Constant.memBeginTime=response.getJSONObject("data").getString("memBeginTime");
					//Constant.memEndTime=response.getJSONObject("data").getString("memEndTime");
					Constant.leftDay=response.getJSONObject("data").getString("leftDay");
					
					Constant.indentify="会员";
					String mess = response.getString("message");
			
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(PayActivity.this, mess, Toast.LENGTH_SHORT).show();
						
					}
					Intent intent = new Intent(PayActivity.this,Main.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					PayActivity.this.startActivity(intent);
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 			}
 			
 			};
		
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	/*public void pay(View v) {
		
		// 订单
		String orderInfo = getOrderInfo("睿立会员", "睿立会员"+Constant.itemzt+"个月",Constant.grandTotal);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			String a=sign;
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
*/
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}
		// 订单Constant.totalDue
		String orderInfo = getOrderInfo("睿立会员", "睿立会员"+Constant.itemzt+"个月",Constant.totalDue);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/** 
	 * 方法名称:transStringToMap 
	 * 传入参数:mapString 形如 username'chenziwen^password'1234 
	 * 返回值:Map 
	*/  
	public static Map transStringToMap(String mapString){  
	  Map map = new HashMap();  
	  java.util.StringTokenizer items;  
	  for(StringTokenizer entrys = new StringTokenizer(mapString, "&");entrys.hasMoreTokens();   
	    map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))  
	      items = new StringTokenizer(entrys.nextToken(), "=");  
	  return map;  
	}  
}

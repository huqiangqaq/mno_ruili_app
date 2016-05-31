package mno.down.extra;

import java.util.ArrayList;
import java.util.List;

import mno.down.modal.DownloadDetail;

public class DownloadUtil {

	private static List<DownloadDetail> list;
	
	public static  List<DownloadDetail> getVideo(){
		 if(list == null){
			 list = new ArrayList<DownloadDetail>();
			 list.add(new DownloadDetail(110, "http://gdown.baidu.com/data/wisegame/fb446df5fdbe3680/aiqiyishipin_80612.apk",
				 "http://g.hiphotos.bdimg.com/wisegame/pic/item/f3ef76c6a7efce1b1bbb5ca9ab51f3deb58f65c5.jpg",
					"aiqiyishipin","爱奇艺","一款流行的视频播放软件","apk"));
			 list.add(new DownloadDetail(111, "http://gdown.baidu.com/data/wisegame/34a6293862d4b5e5/qunaerlvxing_84.apk",
				 "http://h.hiphotos.bdimg.com/wisegame/pic/item/6fd7912397dda14482308eceb6b7d0a20cf48695.jpg",
				 "qunaerlvxing", "去哪儿网", "一款旅游必备软件","apk"));
			 list.add(new DownloadDetail(112, "http://gdown.baidu.com/data/wisegame/a381fe31ef817fa5/kugouyinle_7161.apk",
				 "http://f.hiphotos.bdimg.com/wisegame/pic/item/e982b9014a90f603b6ae73203d12b31bb051edb8.jpg",
				 "kugouyinle", "酷狗音乐", "一款大多数人选择的手机必备软件","apk"));
			 list.add(new DownloadDetail(113, "http://gdown.baidu.com/data/wisegame/c98912e6d2015aa1/baiduditu_620.apk", 
				 "http://h.hiphotos.bdimg.com/wisegame/pic/item/dd096b63f6246b604b651c52eff81a4c510fa2a9.jpg",
				 "baiduditu", "百度地图", "3亿用户选择的导航软件","apk"));
			 list.add(new DownloadDetail(114, "http://gdown.baidu.com/data/wisegame/0ed7b07a790647cc/weibo_1790.apk", 
					 "http://b.hiphotos.bdimg.com/wisegame/pic/item/1fd98d1001e939015892255d7fec54e737d196c5.jpg",
					 "weibo", "新浪微博", "最多中国人在用的社交分享工具","apk"));
			 list.add(new DownloadDetail(115, "http://gdown.baidu.com/data/wisegame/4f9b25fb0e093ac6/QQ_220.apk", 
					 "http://f.hiphotos.bdimg.com/wisegame/pic/item/32da81cb39dbb6fd1fa1f8420d24ab18962b37df.jpg",
					 "QQ", "QQ", "最多中国人在用的聊天工具","apk"));
			 list.add(new DownloadDetail(116, "http://gdown.baidu.com/data/wisegame/344aaed52b9a278d/shoujitianmao_79.apk", 
					 "http://b.hiphotos.bdimg.com/wisegame/pic/item/a0cec3fdfc039245075642708394a4c27c1e25ea.jpg",
					 "tianmao", "天猫", "中国最大的网上购物商城","apk"));
			 list.add(new DownloadDetail(117, "http://gdown.baidu.com/data/wisegame/cfe934697fa9aa6b/zhihu_191.apk", 
					 "http://c.hiphotos.bdimg.com/wisegame/pic/item/0863f6246b600c335de4e2df1e4c510fd9f9a10c.jpg",
					 "zhihu", "知乎", "最高逼格的知识分享社区","apk"));
		 }
		 return list;
	}
	
}

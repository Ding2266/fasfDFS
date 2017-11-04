package cn.mldn.mldnfdfs;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FileTokenDemo {
	public static void main(String[] args) throws Exception{
		//通过ClassPath路径获取要使用的配置文件，实现了资源的定位
		ClassPathResource classPathResource = new ClassPathResource("fastdfs.properties") ; 
		//进行客户端访问的整体配置，需要知道配置文件的完整路径
		ClientGlobal.init(classPathResource.getClassLoader().getResource("fastDfs.properties").getPath());
		//FastDFS的核心操作在于tracker处理上，所以此时需要定义Tracker客户端
		TrackerClient trackerClient = new TrackerClient() ; 
		//定义Tracker的配置信息
		TrackerServer trackerServer = trackerClient.getConnection() ; 
		//设置的是文件路径，但是这个路径前是没有组的
		String fileUrl = "M00/00/00/wKgUiVn8kRCAdS3lAAB6Q5Q45qc516.jpg" ; 
		//要生成一个token，这个token是时间效应的，所以你应该利用时间处理
		int ts = (int)(System.currentTimeMillis()/1000) ;
		StringBuffer accessUrl = new StringBuffer() ; 
		accessUrl.append("http://") ; 
		accessUrl.append(trackerServer.getInetSocketAddress().getHostString()) ; 
		accessUrl.append("/group1/").append(fileUrl) ; //文件的访问路径
		accessUrl.append("?token=").append(ProtoCommon.getToken(fileUrl, ts, ClientGlobal.g_secret_key)) ; 
		accessUrl.append("&ts=").append(ts) ; 
		System.out.println(accessUrl);
		trackerServer.close();
	}
}

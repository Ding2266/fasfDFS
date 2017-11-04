package cn.mldn.mldnfdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FileDeleteDemo {
	public static void main(String[] args) throws Exception{
		//通过ClassPath路径获取要使用的配置文件，实现了资源的定位
		ClassPathResource classPathResource = new ClassPathResource("fastdfs.properties") ; 
		//进行客户端访问的整体配置，需要知道配置文件的完成路径
		ClientGlobal.init(classPathResource.getClassLoader().getResource("fastdfs.properties").getPath());
		//FastDFS的核心操作在于tracker处理上，所以此时需要定义tracker客户端
		TrackerClient trackerClient = new TrackerClient() ; 
		//定义TrackerServer配置信息
		TrackerServer trackerServer = trackerClient.getConnection() ; 
		//在整个FastDFS之中真正负责干活的就是storage
		StorageServer storageServer = null ; 
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer) ; 
		//len=0则表示删除成功，不成功返回2
		int len = storageClient.delete_file1("group1/M00/00/00/wKgUiVn8g26AbQWZAACfBHb9Wcw840.jpg") ; 
		System.err.println(len);
		trackerServer.close();
	}
}

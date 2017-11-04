package cn.mldn.mldnfdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FileUploadDemo1 {
	public static void main(String[] args) throws Exception{
		//如果现在没有文件，也可以传递InputStream，像SpringMVC里面进行文件上传的时候可以得到的内容就是此类型
		File img = new File("g:" + File.separator + "haha.jpg") ; 
		byte[] data = new byte[(int)img.length()] ;
		InputStream input = new FileInputStream(img) ; 
		input.read(data) ;  //将所有的数据保存到字节数组里面
		input.close(); //如果现在使用的SpringMVC上传，自动可以将上传文件变为字节数组
		//需要获得文件的后缀的名称信息，所有的上传文件都有mime类型
		String ext = "jpg" ; 
		//通过ClassPath路径获取要使用的配置文件，实现了资源的定位
		ClassPathResource classPathResource = new ClassPathResource("fastdfs.properties") ; 
		//进行客户端访问的整体配置，需要知道配置文件的完整路径
		ClientGlobal.init(classPathResource.getClassLoader().getResource("fastDfs.properties").getPath());
		//FastDFS的核心操作在于tracker处理上，所以此时需要定义Tracker客户端
		TrackerClient trackerClient = new TrackerClient() ; 
		//定义Tracker的配置信息
		TrackerServer trackerServer = trackerClient.getConnection() ; 
		//在整个FastDFS之中真正负责干活的就是storage
		StorageServer storageServer = null ; 
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer) ; 
		//定义上传文件的元数据
		NameValuePair[] metaList = new NameValuePair[3] ; 
		metaList[0] = new NameValuePair("fileName",img.getName()) ; 
		metaList[1] = new NameValuePair("fileExtName",ext) ; 
		metaList[2] = new NameValuePair("fileLenth",String.valueOf(img.length())) ; 
		//如果要上传则使用trackerClient对象完成
		String upload_file = storageClient1.upload_file1(data, ext, metaList) ; 
		System.out.println(upload_file);
		trackerServer.close();
	}
}

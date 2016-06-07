package com.mome.main.core.datacache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.jessieray.api.request.base.ResponseResult;
import com.mome.main.core.utils.Tools;

/**
 * 数据存储类
 *
 */
public class DataSaveManager {
	
	/**
	 * 本类单例
	 */
	private static DataSaveManager instance= null;
	
	/**
	 * 应用上下文实例
	 */
	private Context context;
	
	/**
	 * sdcard存储目录名
	 */
	public static final String SDFile="/mome";
	public static final String sfkeyString = "key";
	/**
	 * 缓存默认值
	 */
	private static final int DEFAULT_DISK_USAGE_BYTES = 5 * 1024 * 1024;
	/**
	 * 最大缓存容量
	 */
	private final int mMaxCacheSizeInBytes = DEFAULT_DISK_USAGE_BYTES;
	/**
	 * 当前缓存容量
	 */
	private long mTotalSize = 0;
	/**
	 * 获取数据存储管理单例
	 * @return
	 */
	public static DataSaveManager getInstance(){
		if(instance == null){
			instance = new DataSaveManager();
		}
		return instance;
	}
	
	/**
	 * 获取上下文
	 * @param context
	 */
	public void setContext(Context context){
		this.context = context;
		initDiskCurSize();
	}
	
	/**
	 * 初始化磁盘当前缓存容量
	 */
	private void initDiskCurSize() {
//		if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
//			return;
//		}
//		String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			File diskPath = new File(Tools.SAVE_PIC_PATH+SDFile);
		 	if (!diskPath.exists()) {
		 		diskPath.mkdirs();
		    	}
				File[] files = diskPath.listFiles();
				if(files!=null){
				for(File file : files) {
					setmTotalSize(getmTotalSize() + file.length());
				}
				}
	}
	
	/**
	 * SharedPreference存储数据
	 * @param name 数据标识
	 * @param data 要存储的数据(复合数据,数据之间用","分割,也可以直接存储json字符串)
	 */
	public void save(String name, String data) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
    	sharedData.putString(sfkeyString, data);
    	sharedData.commit();
	}
	
	public void save(Context context,String name, String data) {
    	SharedPreferences.Editor sharedData = context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
    	sharedData.putString(sfkeyString, data);
    	sharedData.commit();
	}
	
	/**
	 * SharedPreference读取数据
	 * @param name 数据标识
	 * @return 存储的数据(复合数据,数据之间用","分割,也可以直接存储json字符串)
	 */
	public String read(String name) {
		SharedPreferences sharedData = this.context.getSharedPreferences(name, 
				Context.MODE_PRIVATE);
		return sharedData.getString(sfkeyString, null);
	}
	
	public String read(Context context,String name) {
		SharedPreferences sharedData = context.getSharedPreferences(name, 
				Context.MODE_PRIVATE);
		return sharedData.getString(sfkeyString, null);
	}
	
	/**
	 * 清除存储数据
	 * @param name
	 */
	public void clear(String name){
		SharedPreferences sharedData = this.context.getSharedPreferences(name, 
				Context.MODE_PRIVATE);
		sharedData.edit().clear().commit();
	}
	
	/**
	 * 文件方式存储数据
	 * @param fileName 文件名
	 * @param data 存储的数据(复合数据,数据之间用","分割,可以存储多个值)
	 * @return
	 * @throws IOException
	 */
	public boolean saveFile(String fileName,String data) throws IOException{
		if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			return false;
		}
//		String sdCardDir = Environment.getExternalStorageDirectory()
//				.getAbsolutePath();
		String path = Tools.SAVE_PIC_PATH+SDFile;
		if(mTotalSize >= mMaxCacheSizeInBytes) {
			deleteFile(path);
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File savefile = new File(file,fileName);
		Tools.Log(savefile.getAbsolutePath());
		FileOutputStream fout = new FileOutputStream(savefile);
		fout.write(data.getBytes());
		fout.flush();
		fout.close();
		mTotalSize += savefile.length();
		savefile = null;
		fout = null;
		file = null;
		return true;
	}
	
	/**
	 * 读取sd卡上的文件数据
	 * @param fileName 文件名
	 * @return
	 * @throws IOException
	 */
	public String getSDCardFile(String fileName) throws IOException {
	//	String sdCardDir = Environment.getExternalStorageDirectory()
		//		.getAbsolutePath();
		String path = Tools.SAVE_PIC_PATH+SDFile;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
			return null;
		}
		File readfile = new File(file,fileName);
		FileInputStream fin = new FileInputStream(readfile);
		byte buff[] = new byte[1024];
		ByteArrayOutputStream byOutt = new ByteArrayOutputStream();
		int len = 0;
		while((len = fin.read(buff)) > 0){
			byOutt.write(buff, 0, len);
		}
		byOutt.flush();
		byte[] data = byOutt.toByteArray();
		byOutt.close();
		fin.close();
		byOutt = null;
		fin = null;
		return new String(data,"UTF-8");
	}
	
	/**
	 * 存储图片文件到sd卡
	 * @param imageName 存储名
	 * @param bitmap 图片实例
	 * @return
	 * @throws IOException
	 */
	public boolean saveImage(String imageName,Bitmap bitmap) throws IOException{
		if(bitmap == null) {
			return false;
		}

	//	String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = Tools.SAVE_PIC_PATH+SDFile;
		if(mTotalSize >= mMaxCacheSizeInBytes) {
			deleteFile(path);
		}
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}

		File image = new File(file,imageName);
		if(!image.exists()) {
			image.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(image);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
		fout.flush();
		fout.close();
		fout = null;
		file = null;
		mTotalSize += image.length();
		image = null;
		return true;
	}
	
	/**
	 * 删除缓存文件
	 * @param path
	 */
	private void deleteFile(String path) {
		if(!TextUtils.isEmpty(path)) {
			File diskFile = new File(path);
			if(diskFile.exists()) {
				File[] files = diskFile.listFiles();
				if(files != null) {
                    Arrays.sort(files, new Comparator<File>() {
                        public int compare(File f1, File f2) {
                            return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                        }
                    });
                    for(File file : files) {
                    	if(mTotalSize > (mMaxCacheSizeInBytes>>1)) {
	                    	if(file.exists()) {
	                    		mTotalSize -= file.length();
	                    		file.delete();
	                    	}
                    	} else {
                    		break;
                    	}
                    }
				}
			}
		}
	}

    
	/**
	 * 获取sd卡中的图片文件
	 * @param imageName 文件名
	 * @return
	 * @throws FileNotFoundException
	 */
	public Bitmap getImageFromSdcard(String imageName) throws Exception{
	//	String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = Tools.SAVE_PIC_PATH+SDFile;
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
			return null;
		}
		File image = new File(file,imageName);
		if(!image.exists()) {
			return null;
		}
		FileInputStream fIn = new FileInputStream(image);
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;  
        opt.inPurgeable = true;  
        opt.inInputShareable = true;  
		Bitmap bitmap = BitmapFactory.decodeStream(fIn,null,opt);
		fIn.close();
		fIn = null;
		return bitmap;
	}
	
	/**
	 * 删除sdcard上的图片文件
	 * @param imageName
	 * @throws Exception
	 */
	public void deleteSaveImage(String imageName) throws Exception{
		//String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = Tools.SAVE_PIC_PATH+SDFile;
		File file = new File(path);
		if(!file.exists()){
			return;
		}
		File image = new File(file,imageName);
		if(image.exists()) {
			mTotalSize -= image.length();
			image.delete();
		}
	}
	
	/**
	 * 存储model对象
	 * @param name 名字
	 * @param result model
	 */
	public void saveModel(String name, ResponseResult<?> result) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(result);
			// 将字节流编码成base64的字符窜
//			String obj_Base64 = new String(Base64.encodeBytes(baos.toByteArray()));
			String obj_Base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			sharedData.putString(sfkeyString, obj_Base64);
			sharedData.commit();
		} catch (Exception e) {
			e.printStackTrace();
			Tools.Log("存储对象失败!"+e.toString());
		}
	}
	
	/**
	 * 获取model对象
	 * @param name 名字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResponseResult<?> readModel(String name) {
		ResponseResult<?> obj = null;
		SharedPreferences sharedData = this.context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String productBase64 = sharedData.getString(sfkeyString, "");
				
		//读取字节
		byte[] base64 = null;
		try {
//			base64 = Base64.decode(productBase64.getBytes());
			base64 = Base64.decode(productBase64.getBytes(),Base64.DEFAULT);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(base64 == null || base64.length <= 0) {
			return obj;
		}
		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			//读取对象
			obj = (ResponseResult<?>) bis.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 存储对象
	 * @param name 名字
	 * @param obj 实现了Serializable接口的对象
	 */
	public void saveObject(String name, Object obj) {
    	SharedPreferences.Editor sharedData = this.context.getSharedPreferences(
    			name, Context.MODE_PRIVATE).edit(); 
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(obj);
			// 将字节流编码成base64的字符窜
			String obj_Base64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
			sharedData.putString(sfkeyString, obj_Base64);
			sharedData.commit();
		} catch (Exception e) {
			e.printStackTrace();
			Tools.Log("存储对象失败!"+e.toString());
		}
	}
	
	/**
	 * 获取model对象
	 * @param name 名字
	 * @return 序列化后的对象
	 */
	@SuppressWarnings("unchecked")
	public Object readObject(String name) {
		Object obj = null;
		SharedPreferences sharedData = this.context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String productBase64 = sharedData.getString(sfkeyString, "");
				
		//读取字节
		byte[] base64 = null;
		try {
			base64 = Base64.decode(productBase64.getBytes(),Base64.DEFAULT);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(base64 == null || base64.length <= 0) {
			return obj;
		}
		//封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			//再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			//读取对象
			obj = bis.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int getmMaxCacheSizeInBytes() {
		return mMaxCacheSizeInBytes;
	}

	public long getmTotalSize() {
		return mTotalSize;
	}

	public void setmTotalSize(long mTotalSize) {
		this.mTotalSize = mTotalSize;
	}
	
	
	/** 
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * 
     *  
     * @param context 
     */  
    public static void cleanInternalCache(Context context) {  
        deleteFilesByDirectory(context.getCacheDir());  
    }  
  
    /** 
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * 
     *  
     * @param context 
     */  
    public static void cleanDatabases(Context context) {  
        deleteFilesByDirectory(new File("/data/data/"  
                + context.getPackageName() + "/databases"));  
    }  
  
    /** 
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * 
     *  
     * @param context 
     */  
    public static void cleanSharedPreference(Context context) {  
        deleteFilesByDirectory(new File("/data/data/"  
                + context.getPackageName() + "/shared_prefs"));  
    }  
  
    /** 
     * * 按名字清除本应用数据库 * * 
     *  
     * @param context 
     * @param dbName 
     */  
    public static void cleanDatabaseByName(Context context, String dbName) {  
        context.deleteDatabase(dbName);  
    }  
  
    /** 
     * * 清除/data/data/com.xxx.xxx/files下的内容 * * 
     *  
     * @param context 
     */  
    public static void cleanFiles(Context context) {  
        deleteFilesByDirectory(context.getFilesDir());  
    }  
  
    /** 
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) 
     *  
     * @param context 
     */  
    public static void cleanExternalCache(Context context) {  
        if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {  
            deleteFilesByDirectory(context.getExternalCacheDir());  
        }  
    }  
    /** 
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * 
     *  
     * @param filePath 
     * */  
    public static void cleanCustomCache(String filePath) {  
        deleteFilesByDirectory(new File(filePath));  
    }  
  
    /** 
     * * 清除本应用所有的数据 * * 
     *  
     * @param context 
     * @param filepath 
     */  
    public static void cleanApplicationData(Context context, String... filepath) {  
        cleanInternalCache(context);  
        cleanExternalCache(context);  
        cleanDatabases(context);  
        cleanSharedPreference(context);  
        cleanFiles(context);  
        if (filepath == null||filepath.length==0) {  
            cleanCustomCache(Tools.SAVE_PIC_PATH+SDFile);  
            return;  
        }  
        for (String filePath : filepath) {  
            cleanCustomCache(filePath);  
        }  
    }  
  
    /** 
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * 
     *  
     * @param directory 
     */  
    private static void deleteFilesByDirectory(File directory) {  
        if (directory != null && directory.exists() && directory.isDirectory()) {  
            for (File item : directory.listFiles()) {  
                item.delete();  
            }  
        }  
    }  
      
    // 获取文件  
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据  
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据  
    public static long getFolderSize(File file) throws Exception {  
        long size = 0;  
        try {  
            File[] fileList = file.listFiles();  
            for (int i = 0; i < fileList.length; i++) {  
                // 如果下面还有文件  
                if (fileList[i].isDirectory()) {  
                    size = size + getFolderSize(fileList[i]);  
                } else {  
                    size = size + fileList[i].length();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return size;  
    }  
      
    /** 
     * 删除指定目录下文件及目录 
     *  
     * @param deleteThisPath 
     * @param filepath 
     * @return 
     */  
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {  
        if (!TextUtils.isEmpty(filePath)) {  
            try {  
                File file = new File(filePath);  
                if (file.isDirectory()) {// 如果下面还有文件  
                    File files[] = file.listFiles();  
                    for (int i = 0; i < files.length; i++) {  
                        deleteFolderFile(files[i].getAbsolutePath(), true);  
                    }  
                }  
                if (deleteThisPath) {  
                    if (!file.isDirectory()) {// 如果是文件，删除  
                        file.delete();  
                    } else {// 目录  
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除  
                            file.delete();  
                        }  
                    }  
                }  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
      
    /** 
     * 格式化单位 
     *  
     * @param size 
     * @return 
     */  
    public static String getFormatSize(double size) {  
        double kiloByte = size / 1024;  
        if (kiloByte < 1) {  
            return size + "Byte";  
        }  
  
        double megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "KB";  
        }  
  
        double gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "MB";  
        }  
  
        double teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                    .toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
                + "TB";  
    }  
      
      
    public static String getCacheSize(File file) throws Exception {  
        return getFormatSize(getFolderSize(file));  
    }  
    
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
            cacheSize += getFolderSize(context.getExternalCacheDir());
            cacheSize+=getFolderSize(	new File(Tools.SAVE_PIC_PATH+SDFile));
        }  
        return getFormatSize(cacheSize);
    }
}

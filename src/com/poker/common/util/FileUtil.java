package com.poker.common.util;



import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月21日 
 * @Time 上午9:53:32
 * @Declaration :
 *
 */
public class FileUtil {
    private Context mContext;
    private final String SDPATH;
    /**
     * 创建文件的模式，已经存在的文件要覆盖
     */
    public final static int MODE_COVER = 1;

    /**
     * 创建文件的模式，文件已经存在则不做其它事
     */
    public final static int MODE_UNCOVER = 0;
    
    public FileUtil(Context context){
        mContext = context;
        SDPATH = Environment.getExternalStorageDirectory().getPath();
    }
    
    public static boolean createFile(String path, int mode) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                if (mode == FileUtil.MODE_COVER) {
                    file.delete();
                    file.createNewFile();
                }
            } else {
                // 如果路径不存在，先创建路径
                File mFile = file.getParentFile();
                if (!mFile.exists()) {
                    mFile.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
 // 将文件写入应用的data/data的files目录下  
    public void writeDateFile(String fileName, String buffer) throws Exception {  
        byte[] buf = fileName.getBytes("iso8859-1");  
        fileName = new String(buf, "utf-8");  
        // Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND  
        // Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。  
        // Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。  
        // MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。  
        // 如果希望文件被其他应用读和写，可以传入：  
        // openFileOutput("output.txt", Context.MODE_WORLD_READABLE +  
        // Context.MODE_WORLD_WRITEABLE);  
//        File file = new File(SDPATH + "//" + fileName); 
//        if (!file.exists()) { 
//            file.createNewFile(); 
//        }
//        
//        FileWriter fw = new FileWriter(SDPATH + "//" + fileName); 
//        Log.i("Rinfon",SDPATH + "//" + fileName);
//        FileOutputStream fos = mContext.openFileOutput(SDPATH + "//" + fileName,  
//                Context.MODE_APPEND);// 添加在文件后面  
//        fos.write(buffer);  
//        fos.close();  
        
      //通过getExternalStorageDirectory方法获取SDCard的文件路径  
        File file = new File(SDPATH, fileName);  
        if (!file.exists()) { 
          file.createNewFile(); 
        }
        //获取输出流  
//        FileOutputStream outStream = new FileOutputStream(file);
//        outStream.write(buffer.getBytes());  
//        outStream.close();
        
        FileWriter writer = new FileWriter(SDPATH + "//" +fileName, true);  
        writer.write(buffer);  
        writer.close();  
    }  
    
    /**
     * 向文件的末尾添加数据
     * 
     * @param path
     * @param data
     */
    public static boolean appendData(String path, byte[] data) {
        try {
            File file = new File(path);
            if (file.exists()) {
                FileOutputStream fos = new FileOutputStream(file, true);
                fos.write(data);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

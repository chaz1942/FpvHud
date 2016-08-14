package com.scofieldchang.fpvhud.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ScofieldChang on 16/5/29.
 */
public class FileService {
    private static String filename = "log";
    private Context context;

    public FileService(Context context) {
        this.context = context;
    }

    public void saveLog(String content){
        try {
            saveToSDCard(filename,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToSDCard(String filename , String content) throws Exception {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            Log.d("FILE_INFO", file.getPath() + '/' + file.getName());
            FileOutputStream outStream = new FileOutputStream(file, true);
            outStream.write(content.getBytes());
            outStream.close();
        }
    }

    public void save(String filename, String content) throws Exception {
        FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        outStream.write(content.getBytes());
        outStream.close();
    }
}

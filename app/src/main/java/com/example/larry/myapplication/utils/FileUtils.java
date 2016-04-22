package com.example.larry.myapplication.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Application;
import android.os.Environment;
import android.os.StatFs;

public class FileUtils {
    public static String SDPath;
    {

            SDPath =  UILApplication.getInstance().getApplicationContext().getExternalCacheDir().getAbsolutePath()+File.separator;

    }

    public FileUtils() {
        //得到当前外部存储设备的目录
        SDPath = UILApplication.getInstance().getApplicationContext().getExternalCacheDir().getAbsolutePath()+File.separator;

    }


        public static List<String> getStorageList() throws IOException {
            File file = new File("/proc/mounts");
            if (file.canRead()) {
                BufferedReader reader = null;
                List<String> result = new ArrayList<String>();
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String lines;
                while ((lines = reader.readLine()) != null) {
                    String[] parts = lines.split("\\s+");
                    if (parts.length >= 2) {
                        if (parts[0].contains("vold")) {
                            result.add(parts[1]);
                        }
                    }
                }
                return result;
            }
            return null;
        }


    public static boolean isSDCardEnable()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }
    public static String getExternalSdCardPath() throws Exception {

        if (!isSDCardEnable()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sdCardFile.getAbsolutePath();
        }

        String path = null;

        File sdCardFile = null;

        List<String> devMountList = getStorageList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;
    }
    private boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSizeLong();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocksLong();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }
    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     */
    public File createSDFile(String fileName) {
        File file = new File(SDPath + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName) {
        File file = new File(SDPath + dirName);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    /**
     * 判断SD卡上文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 将一个inputStream里面的数据写到SD卡中
     *
     * @param path
     * @param fileName
     * @param inputStream
     * @return
     */
    public File writeToSDfromInput(String path, String fileName, InputStream inputStream) {
        //createSDDir(path);
        File file = createSDFile(path + fileName);
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            while (inputStream.read(buffer) != -1) {
                outStream.write(buffer);
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }

    /**
     * 安全删除文件.
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }
    public static File[] getFiles(String path){
        File file = new File(path);
        return file.listFiles();
    }

    public static File[] getMP3(String path){
        File[] files = new File(path).listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                String filename = pathname.getPath();
                if (pathname.isDirectory())
                    return true;
                if(filename.endsWith(".mp3"))
                    return true;
                else
                    return false;
            }
        });

        return files;
    }

}
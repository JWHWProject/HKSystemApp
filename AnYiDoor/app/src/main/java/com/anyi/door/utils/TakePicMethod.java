package com.anyi.door.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.nj.www.my_module.tools.FileSystemManager;


/**
 * Created by Administrator on 2017/7/17.
 */

public class TakePicMethod {
    //文件存储的路径
    public static String POTOPATH="";
    private Context context;
    private SurfaceView mySurfaceView;
    private SurfaceHolder myHolder;
    private Camera myCamera;
    public String path;
    private Bitmap mPhotoImg;
    private String picName="";

    public TakePicMethod(Context context, SurfaceView mySurfaceView, SurfaceHolder myHolder) {
        this.context = context;
        this.mySurfaceView = mySurfaceView;
        this.myHolder = myHolder;
        POTOPATH = FileSystemManager.getSlientFilePath(context);
    }

    /**
     * 开始拍照
     */
    public void startTakePhoto(String photoName) {
        //这里得开线程进行拍照，因为Activity还未显示完全的时候是无法进行拍照的，SurfacaView必须先显示
        picName=photoName;
        new Thread() {
            @Override
            public void run() {
                super.run();
                //如果存在摄像头
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    //获取摄像头
                    if (openFacingFrontCamera()) {
                        Log.i("jwei", "openCameraSuccess");
                        //进行对焦
                        autoFocus();
                    } else {
                        Log.i("jwei", "openCameraFailed");
                    }

                }

            }
        }.start();
    }

    // 自动对焦回调函数(空实现)
    private Camera.AutoFocusCallback myAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
        }
    };

    // 对焦并拍照
    private void autoFocus() {

        try {
            // 因为开启摄像头需要时间，这里让线程睡两秒
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 自动对焦
        try {
            myCamera.autoFocus(myAutoFocus);
            // 对焦后拍照
            myCamera.takePicture(null, null, myPicCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 拍照成功回调函数
    private Camera.PictureCallback myPicCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            // 创建并保存图片文件
//            File pictureFile = new File(getDir(), "IMG_" + timeStamp + ".jpg");
            File file = new File(POTOPATH);
            file.mkdirs(); // 创建文件夹保存照片
            String filename = file.getPath() + File.separator + picName + ".jpg";

            // 将得到的照片进行270°旋转，使其竖直
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.preRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);

            try {
                FileOutputStream fos = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception error) {
                Toast.makeText(context, "拍照失败", Toast.LENGTH_SHORT)
                        .show();

                Log.i("jwei", "保存照片失败" + error.toString());
                error.printStackTrace();
                myCamera.stopPreview();
                myCamera.release();
                myCamera = null;
            }

            Log.i("jwei", "获取照片成功");
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }
    };

    // 得到后置摄像头
    private boolean openFacingFrontCamera() {
        // 尝试开启前置摄像头
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    Log.i("jwei", "tryToOpenCamera");
                    myCamera = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        // 如果开启前置失败（无前置）则开启后置
        if (myCamera == null) {
            for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        myCamera = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        return false;
                    }
                }
            }
        }

        try {
            // 这里的myCamera为已经初始化的Camera对象
            myCamera.setPreviewDisplay(myHolder);
        } catch (IOException e) {
            e.printStackTrace();
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }

        myCamera.startPreview();

        return true;
    }
    public String getPictureFromFile() {
        //判断是否有sd卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File[] files = new File(POTOPATH).listFiles();
            if (files.length != 0) {
                path = POTOPATH + File.separator + files[0].getName();
                Log.e("FDs", path);
                return path;
            }
        }
        return null;

    }
    /**
     * 压缩图片
     */
    public void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        mPhotoImg = BitmapFactory.decodeFile(path, options);
    }
}

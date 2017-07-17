package com.anyi.door.video.base;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anyi.door.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class TinyWindowPlayActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;

    private Button bnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_window_play);
        //隐藏标题栏,有效
        getSupportActionBar().hide();
        initTitle();
        init();

    }

    private void initTitle() {
        findViewById(R.id.top_view_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    boolean flag=true;
    int time=1;
    int maxtime=1;
    private int picCount=1;
    private void init() {
        bnFinish = (Button) findViewById(R.id.app_finish_bn);
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        mNiceVideoPlayer.setUp("http://www.12365aq.cn/f/file/20/screenContent/ad8230d2-39e8-4926-ad6e-e98a109ff4f1.mp4", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("测试视频");
        //获取总时长
        controller.setLenght(mNiceVideoPlayer.getDuration());
        //现在的时长
//        mNiceVideoPlayer.getCurrentPosition();

        mNiceVideoPlayer.setController(controller);
        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time>maxtime) {
                    picCount=3;
                    TakePicture();
                }else{
                    Toast.makeText(TinyWindowPlayActivity.this,"您现在还无法完成培训,还没有达到培训时间!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        picCount=1;
        TakePicture();
        long halftime=mNiceVideoPlayer.getDuration()/2;
        maxtime=(int)(halftime/1000f);
        Random random = new Random();
        final int randomTime=random.nextInt(maxtime);
        time=1;
        flag=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(time==randomTime){
                        picCount=2;
                        TakePicture();
                    }
                    time++;
                }
            }
        }).start();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=false;
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }
    private boolean isTakeingPhoto=false;
    private SurfaceView mySurfaceView;
    private SurfaceHolder myHolder;
    private Camera myCamera;
    private String path;
    private Bitmap mPhotoImg;
    int pos=1;
    // 初始化surface
    @SuppressWarnings("deprecation")
    private void initSurface() {
        // 初始化surfaceview
        if (mySurfaceView == null && myHolder == null) {
            mySurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
            // 初始化surfaceholder
            myHolder = mySurfaceView.getHolder();
        }

    }
    //文件存储的路径
    public static String POTOPATH = Environment.getExternalStorageDirectory() + "/silent";
    private void TakePicture(){
        if(isTakeingPhoto) {
            isTakeingPhoto = true;
            CountDownTimer countDownTimer = new CountDownTimer(10000, 2000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    startTakePhoto();
                }

                @Override
                public void onFinish() {
                    isTakeingPhoto = false;
                        try {
                            File file = new File(getPictureFromFile());

                            if (path != null && !path.trim().equals("")) {
                                resizePhoto();
                            }

//                            //将图片显示到textView上面
//                            Glide.with(TinyWindowPlayActivity.this).load(file).asBitmap().into(mPhoto);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                }
            }.start();
        }
    }
    /**
     * 开始拍照
     */
    private void startTakePhoto() {
        //初始化surface
        initSurface();
        //这里得开线程进行拍照，因为Activity还未显示完全的时候是无法进行拍照的，SurfacaView必须先显示
        new Thread() {
            @Override
            public void run() {
                super.run();
                //如果存在摄像头
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 自动对焦
        myCamera.autoFocus(myAutoFocus);

        // 对焦后拍照
        myCamera.takePicture(null, null, myPicCallback);
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
            String filename = file.getPath() + File.separator + timeStamp + ".jpg";

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
                Toast.makeText(TinyWindowPlayActivity.this, "拍照失败", Toast.LENGTH_SHORT)
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
    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        mPhotoImg = BitmapFactory.decodeFile(path, options);
    }
}

package cn.nj.www.my_module.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;

import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseApplication;

public class ImageLoaderUtil
{
    private static ImageLoader imageLoader;

    //定义一个私有的静态全局变量来保存该类的唯一实例 
    private static ImageLoaderUtil loaderUtil;

    /// <summary> 
    /// 构造函数必须是私有的 
    /// 这样在外部便无法使用 new 来创建该类的实例 
    /// </summary> 
    private ImageLoaderUtil()
    {
    }

    /// <summary> 
    /// 定义一个全局访问点 
    /// 设置为静态方法 
    /// 则在类的外部便无需实例化就可以调用该方法 
    /// </summary> 
    /// <returns></returns> 
    public static ImageLoaderUtil getInstance()
    {
        //这里可以保证只实例化一次 
        //即在第一次调用时实例化 
        //以后调用便不会再实例化 
        if (loaderUtil == null)
        {
            loadData(BaseApplication.getInstance().getApplicationContext());
            loaderUtil = new ImageLoaderUtil();
        }
        return loaderUtil;
    }

    /**
     * 初始化option
     * picFail--加载失败时显�?
     * picLoading--正在加载图片时显�?
     * picEmpty--uri为空的时候显�?
     * cornerRadiusPixels-图片弧度
     */
    private DisplayImageOptions setAllDisplayImageOptions(Context context, String picFail, String picLoading,
                                                          String picEmpty, int cornerRadiusPixels)
    {
        DisplayImageOptions options;
        //通过图片名调用图片ID
        int fail = context.getResources().getIdentifier(picFail, "drawable", Constants.packageName);
        int loading = context.getResources().getIdentifier(picLoading, "drawable", Constants.packageName);
        int empty = context.getResources().getIdentifier(picEmpty, "drawable", Constants.packageName);

        if (cornerRadiusPixels > 0)
        {
            options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageOnFail(fail)
                    .showImageForEmptyUri(empty)
                    .showImageOnLoading(loading)
                    .cacheOnDisc(true)
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    //设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
                    .build();
        }
        else
        {
            options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageOnFail(fail)
                    .showImageForEmptyUri(empty)
                    .showImageOnLoading(loading)
                    .cacheOnDisc(true)
                    .imageScaleType(ImageScaleType.NONE)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(0))
                    .build();
        }

        return options;
    }

    /**
     * <加载圆形图片>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void initImageRound(Context context, String url, ImageView imageView, String defaultImage)
    {
        imageLoader.displayImage(url,
                imageView,
                setAllDisplayImageOptions(context, defaultImage, defaultImage, defaultImage, 720));
    }

    /**
     * <加载常规图片>defaultImage,默认图片
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void initImage(Context context, String url, ImageView imageView, String defaultImage)
    {
        imageLoader.displayImage(url,
                imageView,
                setAllDisplayImageOptions(context, defaultImage, defaultImage, defaultImage, 0));
    }

    /**
     * <删除指定图片文件>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void removeImage(String url)
    {
//        File imageFile = imageLoader.getDiscCache().get(url);
//        if (imageFile.exists())
//        {
//            imageFile.delete();
//        }
//        MemoryCacheUtil.removeFromCache(url, imageLoader.getMemoryCache());
        MemoryCacheUtils.removeFromCache(url, imageLoader.getMemoryCache());
//        DiscCacheUtil.removeFromCache(url, imageLoader.getDiscCache());
        DiskCacheUtils.removeFromCache(url, imageLoader.getDiscCache());
    }

    /**
     * <初始化imageloader>
     * <功能详细描述>
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    private static void loadData(Context context)
    {
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
                        .threadPoolSize(4)
                        .tasksProcessingOrder(QueueProcessingType.FIFO)
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                        .discCacheSize(50 * 1024 * 1024)
                        .memoryCacheExtraOptions(480, 800)
                        // default = device screen dimensions
                        //设定缓存在内存的图片大小200x200
                        .denyCacheImageMultipleSizesInMemory()
                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .discCache(new UnlimitedDiskCache(new File(FileSystemManager.getCacheImgFilePath(context))))
                        .build();
        imageLoader.init(config);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String uri, ImageView imageView)
    {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

}
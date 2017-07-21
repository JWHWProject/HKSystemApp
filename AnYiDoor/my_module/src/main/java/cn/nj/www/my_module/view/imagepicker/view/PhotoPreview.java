package cn.nj.www.my_module.view.imagepicker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.view.imagepicker.model.PhotoModel;
import cn.nj.www.my_module.view.photoview.PhotoView;


public class PhotoPreview extends LinearLayout implements OnClickListener {
	private ProgressBar pbLoading;
	private PhotoView ivContent;
	private OnClickListener l;
//	private View save_bt;
	private String path;
	private Context cxt;
	private Bitmap loadedBitamap;
	private View inflate;

	public PhotoPreview(Context context) {
		super(context);
		this.cxt = context;
		inflate = LayoutInflater.from(context).inflate(
				R.layout.view_photopreview, this, true);

		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_vpp);
		ivContent = (PhotoView) findViewById(R.id.iv_content_vpp);
//		save_bt = findViewById(R.id.save_bt);
		ivContent.setOnClickListener(this);


	}

	public PhotoPreview(Context context, AttributeSet attrs, int defStyle) {
		this(context);
	}

	public PhotoPreview(Context context, AttributeSet attrs) {
		this(context);
	}

	public void loadImage(PhotoModel photoModel, Boolean is_save) {

		if (is_save) {
			loadImage(photoModel.getOriginalPath());
		} else {
			loadImage("file://" + photoModel.getOriginalPath());
		}

	}

	private void loadImage(String path) {
		this.path = path;
		ImageLoader.getInstance().loadImage(path,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						pbLoading.setVisibility(View.GONE);
						loadedBitamap = loadedImage;
						ivContent.setImageBitmap(loadedImage);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						ivContent.setImageDrawable(getResources().getDrawable(
								R.drawable.ic_picture_loadfailed));
						pbLoading.setVisibility(View.GONE);
					}
				});
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.l = l;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_content_vpp && l != null){
			l.onClick(ivContent);
		}
	}

}

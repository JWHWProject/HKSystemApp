package cn.nj.www.my_module.view.imagepicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.view.imagepicker.control.PhotoSelectorDomain;
import cn.nj.www.my_module.view.imagepicker.model.PhotoModel;
import cn.nj.www.my_module.view.imagepicker.util.ImageUtils;
import cn.nj.www.my_module.view.imagepicker.util.StringUtils;


/**
 * Created by fengyongge on 2016/5/24
 * 图片预览
 */
public class PhotoPreviewActivity extends BasePhotoPreviewActivity implements PhotoSelectorActivity.OnLocalReccentListener {
	private PhotoSelectorDomain photoSelectorDomain;
	private boolean isSave;//是否保存图片

	private View bnSave;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
		init(getIntent().getExtras());
	}

	@SuppressWarnings("unchecked")
	protected void init(Bundle extras) {
		if (extras == null)
			return;
		bnSave =findViewById(R.id.save_bt);
		isSave = extras.getBoolean("isSave",false);

		if (extras.containsKey("photos")) { // 预览图片，选择需要的图片

			this.photos = (List<PhotoModel>) extras.getSerializable("photos");
			this.current = extras.getInt("position", 0);

			if(isSave){ // 是否保存（一般保存网络图片，本地图片只能查看）
				bnSave.setVisibility(View.VISIBLE);
				bindData(true);
				updatePercent();

			}else{
				bnSave.setVisibility(View.GONE);
				bindData(false);
			}


		} else if (extras.containsKey("album")) { // 点击图片查看
			if(isSave){ // 是否保存（一般保存网络图片，本地图片只能查看）
				bnSave.setVisibility(View.VISIBLE);
				bindData(true);
				updatePercent();

			}else{
				bnSave.setVisibility(View.GONE);
				bindData(false);
			}
			String albumName = extras.getString("album"); // 相册
			this.current = extras.getInt("position");
			if (!StringUtils.isNull(albumName) && albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)) {
				photoSelectorDomain.getReccent(this);
			} else {
				photoSelectorDomain.getAlbum(albumName, this);
			}
		}

		bnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImageUtils.loadImage(PhotoPreviewActivity.this, photos.get(current).getOriginalPath());
				Toast.makeText(PhotoPreviewActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
	}


	@Override
	public void onPhotoLoaded(List<PhotoModel> photos) {
		this.photos = photos;
		updatePercent();
		bindData(false); // 更新界面
	}


}

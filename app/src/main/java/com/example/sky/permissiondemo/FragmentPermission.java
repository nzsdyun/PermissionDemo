package com.example.sky.permissiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emporia.common.util.MPermission;
import com.emporia.common.util.PermissionUtil;

/**
 * this fragment test in fragment request runtime permission
 * 
 * @author sky
 * 
 */
public class FragmentPermission extends Fragment {
	static FragmentPermission newIntance(String tag) {
		FragmentPermission f = new FragmentPermission();
		Bundle bundle = new Bundle();
		bundle.putCharSequence("tag", tag);
		f.setArguments(bundle);
		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getActivity().findViewById(R.id.request_camera_permission)
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						safeCallCamera();
					}
				});
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * safe call camera
	 */
	private void safeCallCamera() {
		PermissionUtil.CameraOperation.obtainCamera(
				MPermission.newInstance(this), new Runnable() {
							
							@Override
							public void run() {
								// call camera
								callCamera();
							}
						});
	}

	/**
	 * this method only test,unavailable
	 */
	private void callCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, "");
		startActivityForResult(intent, 200);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		//FIXME:you can handle events, if you don't want to deal with on your own, you can call the following
		MPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}

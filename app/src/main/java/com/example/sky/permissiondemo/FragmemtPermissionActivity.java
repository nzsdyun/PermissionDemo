package com.example.sky.permissiondemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.emporia.common.util.MPermission;
import com.emporia.common.util.PermissionUtil;

/**
 * verification runtime permission on FragmentActivty
 * 
 * @author sky
 * 
 */
public class FragmemtPermissionActivity extends FragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		loadFragmentPermission();
	}

	private void loadFragmentPermission() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fTransaction = fragmentManager.beginTransaction();
		Fragment fragment = FragmentPermission.newIntance("FragmentPermission");
		fTransaction.replace(R.id.fragment_container, fragment);
		fTransaction.commit();
	}

	public void Click(View v) {
		int id = v.getId();
		if (id == R.id.request_phone_permission) {
			safeCallPhone();
		}
	}

	/**
	 * safe call phone
	 */
	private void safeCallPhone() {
		PermissionUtil.PhoneOperation.obtainCallPhone(
				MPermission.newInstance(this),
				new Runnable() {

					@Override
					public void run() {
						//intent call phone
						callPhone();
					}
				});
	}
	
	private void callPhone() {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + 111110));
		startActivity(intent);
	}
	
	public static void startActivity(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, FragmemtPermissionActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		//FIXME:you can handle events, if you don't want to deal with on your own, you can call the following
		MPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}

package com.emporia.common.util;

import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
/**
 * Android runtime permission operation class
 * you can use {@code Activity} and {@code Fragment}
 * you can use
 * <p>
 * first:
 * 		MPermission.newInstance(this)
 * 			.permissions(permissions)
 * 			.request(new Runnable() {
 * 				//do your things
 * 			});
 * or   MPermission.newInstance(this)
 * 			.addRequestCode(requestCode)
 * 			.permissions(permissions)
 * 			.request();
 * 		MPermission.newInstance().setOnAskPermissionListener(this);
 * 			
 * second:
 * 			//you need to register to get Fragment onRequestPermissionsResult callback method Activity
 * 			@Override
 *			public void onRequestPermissionsResult(int requestCode,
 *					String permissions[], int[] grantResults) {
 *					//If you do not want to deal with calls Permission of onRequestPermissionsResult methods
 *					MPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
 *			}
 * </p>
 * @author sky
 * @version 1.1
 */
public final class MPermission {
	private static final String TAG = MPermission.class.getSimpleName();
	
	private Random mRandom = new Random();
	/** TreeMap guarantees order of the keys */ 
	private TreeMap<Integer, Runnable[]> mPermissionRequestCallbacks = new TreeMap<Integer, Runnable[]>();
	/** more permissions */
	private String[] mPermissions;
	/** add request code */
	private int mRequestCode;
	/** activity or fragment object */
	private Object mObject;
	/** MPermission single instance */
	private static MPermission sPermission;
	/**
	 * private and can not be configured 
	 */
	private MPermission() {}
	/**
	 * use fragment initialize MPermission
	 * @param @see Fragment
	 * @return MPermission object
	 * @exception fragment is null throw {@code NullPointerException}
	 */
	public static MPermission newInstance(Fragment fragment) {
		if (fragment == null) throw new NullPointerException("PermissionsFragment is null, obtain permission failed");
		if (sPermission == null)
			sPermission = new MPermission();
		sPermission.mObject = fragment;
		return sPermission;
	}
	/**
	 * use activity initialize MPermission
	 * @param activity @see Activity
	 * @return MPermission object
	 * @exception activity is null throw {@code NullPointException}
	 */
	public static MPermission newInstance(Activity activity) {
		if (activity == null) throw new NullPointerException("PermissionsActivity is null, obtain permission failed"); 
		if (sPermission == null)
			sPermission = new MPermission();
		sPermission.mObject = activity;
		return sPermission;
	}
	/**
	 * use method handle the callback if you want not handler the callback
	 * @exception mObject is null throw {@code NullPointException}
	 */
	public static MPermission getInstance() {
		return sPermission;
	}
	/**
	 * add support more permissions 
	 * @param permissions permissions array
	 * @return @see MPermission
	 */
	public MPermission permissions(String ...permissions) {
		sPermission.mPermissions = permissions;
		return sPermission;
	}
	/**
	 * add request code 
	 * @param requestCode {@code Integer}
	 * @return @see MPermission
	 */
	public MPermission addRequestCode(int requestCode) {
		sPermission.mRequestCode = requestCode;
		return sPermission;
	}
	
	/**
	 * support multiple permissions
	 * @param permission @see PermissionUtil#Permission
	 * @return if check application have permission return true, otherwise false
	 */
	private boolean isPermissionGranted(Context context, String ...permissions){
		return PermissionUtil.checkPermissions(context, permissions);
	}
	/**
	 * @param context @see Context
	 * @param permission @see PermissionUtil#Permission
	 * @return if shouldShowRequestPermissionRationale is true, you maybe need show explain to user
	 */
	private boolean isShowRequestPermissionExplain(Context context, String ...permissions) {
		return PermissionUtil.isShowRequestPermissionExplain(context, permissions);
	}
	/**
	 * support multiple permissions, note that the variable argument must be in the last and must have only one
	 * @return if application have permission return true,otherwise will requestPermissions
	 */
	public boolean request(){
		return request(null, mPermissions);
	}
	/**
	 * support multiple permissions, note that the variable argument must be in the last and must have only one
	 * @param callback @see Runnable
	 * @return if application have permission return true,otherwise will requestPermissions
	 */
	public boolean request(Runnable callback){
		return request(callback, mPermissions);
	}
	/**
	 * support multiple permissions, note that the variable argument must be in the last and must have only one
	 * @param callback @see Runnable
	 * @param permissions @see PermissionUtil#Permission
	 * @return if application have permission return true,otherwise will requestPermissions
	 */
	public boolean request(Runnable callback, String ...permissions) {
		if (isPermissionGranted(PermissionUtil.getActivity(mObject), permissions)){
			if (callback != null) {
				callback.run();
			} else {
				permissionGrantCallBack(mRequestCode, permissions, new int[permissions.length]);
			}
			return true;
		} else {
			boolean isExplain = false;
			if (isShowRequestPermissionExplain(PermissionUtil.getActivity(mObject), permissions)) {
				if (mObject instanceof ShowRequestPermissionExplain) {
					ShowRequestPermissionExplain explain = (ShowRequestPermissionExplain) mObject;
					isExplain = explain.showRequestPermissionExplain(mRequestCode, permissions);
				}
			}
			if (!isExplain) {
				if(callback != null){

					if (mPermissionRequestCallbacks.size() >= 255){
						throw new IllegalArgumentException("Cannot have more permission request callbacks, " +
								"since the requestPermissions() only accepts 8bit ints for their request code");
					}
					int newKey = getNew8BitIntKey(mRandom, mPermissionRequestCallbacks);
					List<String> deniedPermissions = PermissionUtil.findDeniedPermissions(PermissionUtil.getActivity(mObject), permissions);
					if (deniedPermissions.size() > 0) {
						if (mObject instanceof Fragment) {
							((Fragment)mObject).requestPermissions(
									deniedPermissions.toArray(new String[deniedPermissions.size()]), newKey);
						} else if (mObject instanceof Activity) {
							ActivityCompat.requestPermissions((Activity)mObject,
									deniedPermissions.toArray(new String[deniedPermissions.size()]), newKey);
						}
						mPermissionRequestCallbacks.put(newKey, new Runnable[]{ callback });
					} else {
						callback.run();
						return true;
					}
				} else if (callback == null) {
					List<String> deniedPermissions = PermissionUtil.findDeniedPermissions(PermissionUtil.getActivity(mObject), permissions);
					if (deniedPermissions.size() > 0) {
						if (mObject instanceof Fragment) {
							((Fragment)mObject).requestPermissions(
									deniedPermissions.toArray(new String[deniedPermissions.size()]), mRequestCode);
						} else if (mObject instanceof Activity) {
							ActivityCompat.requestPermissions((Activity)mObject,
									deniedPermissions.toArray(new String[deniedPermissions.size()]), mRequestCode);
						}
					} else {
						permissionGrantCallBack(mRequestCode, permissions, new int[permissions.length]);
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * permission grant call back
	 * @param requestCode request code
	 * @param permissions permissions array
	 * @param grantResults grant result
	 */
	private void permissionGrantCallBack(int requestCode, String[] permissions, int[] grantResults) {
		if (mAskPermissionListener != null) {
			mAskPermissionListener.onPermissionGranted(requestCode, permissions, grantResults);
		}
	}
	/**
	 * permission denied call back
	 * @param requestCode request code
	 * @param permissions permissions array
	 * @param grantResults grant result
	 */
	private void permissionDeniedCallBack(int requestCode, String[] permissions, int[] grantResults) {
		if (mAskPermissionListener != null) {
			mAskPermissionListener.onPermissionDenied(requestCode, permissions, grantResults);
		}
	}
	/** ask permissions call back, you can user this listen success grant permission */
	private AskPermissionListener mAskPermissionListener;
	public void setOnAskPermissionListener(AskPermissionListener askPermissionListener) {
		this.mAskPermissionListener = askPermissionListener;
	}
	public interface AskPermissionListener {
		void onPermissionGranted(int requestCode, String[] permissions, int[] grantResults);
		void onPermissionDenied(int requestCode, String[] permissions, int[] grantResults);
	}
	/**
	 * Get a new 8 bit Integer key, different from the existing keys of a SortedMap
	 * @param random
	 * @param sortedMap
	 * @return
	 */
	private static Integer getNew8BitIntKey(Random random, SortedMap<Integer, ?> sortedMap){
		Integer[] keys = new Integer[sortedMap.size()];
		sortedMap.keySet().toArray(keys);
		return getRandomWithExclusion(random, 0, 255, keys);
	}
	
	/**
	 * http://stackoverflow.com/a/6443346/689223
	 * 
	 * It generates a random number (int) between start and end (both inclusive) and does not give you
	 * any number which is contained in the array exclude. All other numbers occur with equal probability.
	 * Note, that the following constrains must hold: exclude is sorted ascendingly and all numbers are
	 * within the range provided and all of them are mutually different.
	 * 
	 * @param rnd
	 * @param start
	 * @param end
	 * @param exclude Need to be ordered
	 * @return
	 */
	private static int getRandomWithExclusion(Random rnd, int start, int end, Integer... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	/**
	 * permission request call back
	 * @param requestCode 
	 * @param permissions
	 * @param grantResults
	 */
	public void onRequestPermissionsResult(int requestCode,
			String permissions[], int[] grantResults) {
		Runnable[] callbacks = mPermissionRequestCallbacks.get(requestCode);
		mPermissionRequestCallbacks.remove(requestCode);
		
		Log.i(TAG, "callbacks:" + callbacks + " requestCode:" + requestCode);
		if (callbacks != null){
			printResults(requestCode, permissions, grantResults);
			if (PermissionUtil.checkGrantResults(grantResults)){
				PermissionUtil.getActivity(mObject).runOnUiThread(callbacks[0]);
			} else {
				//do not anything
			}
		} else if (callbacks == null) {
			if (PermissionUtil.checkGrantResults(grantResults)){
				permissionGrantCallBack(requestCode, permissions, grantResults);
			} else {
				permissionDeniedCallBack(requestCode, permissions, grantResults);
			}
		}
	}
	
	private void printResults(int requestCode,
			String permissions[], int[] grantResults) {
		StringBuffer sb = new StringBuffer();
		sb.append("requestCode:" + requestCode + "\n");
		for (int i = 0; i < permissions.length; i++) {
			sb.append("permission[" + i + "] = " + permissions[i]);
			if (i < permissions.length)
				sb.append(", ");
		}
		sb.append("\n");

		for (int i = 0; i < grantResults.length; i++) {
			sb.append("grantResult[" + i + "] = " + grantResults[i]);
			if (i < grantResults.length)
				sb.append(", ");
		}
		sb.append("\n");
		Log.i(TAG, "request permissions result\n:" + sb.toString());
	}
	

}

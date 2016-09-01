package com.emporia.common.util;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.emporia.common.util.PermissionUtil.Permission.CalendarPermission;
import com.emporia.common.util.PermissionUtil.Permission.CameraPermission;
import com.emporia.common.util.PermissionUtil.Permission.ContactsPermission;
import com.emporia.common.util.PermissionUtil.Permission.LocationPermission;
import com.emporia.common.util.PermissionUtil.Permission.MicrophonePermission;
import com.emporia.common.util.PermissionUtil.Permission.PhonePermission;
import com.emporia.common.util.PermissionUtil.Permission.SensorsPermission;
import com.emporia.common.util.PermissionUtil.Permission.SmsPermission;
import com.emporia.common.util.PermissionUtil.Permission.StoragePermission;

/**
 * Operation of PermissionFragment package
 * you can find all run Android 23 when permissions from here
 * you just need something like that to call it
 * <p>
 * single permission request:
 * 		MPermission p = MPermission.newIntance(this);
 *
 * 		PermissionUtil.ContactsOperation.obtainRead(p, new Runnable() {
 * 				@Override
 * 				public void run() {
 * 					//do your some things
 * 				}
 * 		}
 * more permission request:
 * 		@see PermissionUtil#obtainSafePermissions(MPermission)
 * 		@see PermissionUtil#obtainSafePermissions(MPermission, Runnable)
 * 		@see PermissionUtil#obtainSafePermissions(MPermission, Runnable, String...)
 * </p>
 * @author sky
 * @version 1.0
 *
 */
public final class PermissionUtil {
	/**
	 * dangerous permission list name you can select the appropriate authority
	 * under the authority of the module
	 */
	public interface Permission {
		/**
		 * Calendar runtime permission
		 */
		interface CalendarPermission {
			String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
			String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
		}

		/**
		 * Contacts runtime permission
		 */
		interface ContactsPermission {
			String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
			String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
		}

		/**
		 * camera runtime permission
		 */
		interface CameraPermission {
			String CAMERA = Manifest.permission.CAMERA;
		}

		/**
		 * location runtime permission
		 */
		interface LocationPermission {
			String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
			String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
		}

		/**
		 * microphone runtime permission
		 */
		interface MicrophonePermission {
			String MICROPHONE = Manifest.permission.RECORD_AUDIO;
		}

		/**
		 * phone runtime permission
		 */
		interface PhonePermission {
			String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
			String CALL_PHONE = Manifest.permission.CALL_PHONE;
			String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
			String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
			String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
			String USE_SIP = Manifest.permission.USE_SIP;
			String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
		}

		/**
		 * sensor runtime permission
		 */
		interface SensorsPermission {
			String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
		}

		/**
		 * sms runtime permission
		 */
        interface SmsPermission {
            String SEND_SMS = Manifest.permission.SEND_SMS;
            String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
            String READ_SMS = Manifest.permission.READ_SMS;
            String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
            String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
		}

		/**
		 * storage runtime permission
		 */
		interface StoragePermission {
			String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
			String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
		}

	}
	/**
	 * Android runtime permission for Contacts
	 * in android 6.0 or higher
	 */
	public static final class ContactsOperation {
		private ContactsOperation() {}
		/**
		 * obtain permission in fragment
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read contacts permission success return true ,otherwise false
		 */
		public static boolean obtainRead(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, ContactsPermission.READ_CONTACTS);
		}
		/**
		 * obtain permission in fragment
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain write contacts permission success return true ,otherwise false
		 */
		public static boolean obtainWrite(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, ContactsPermission.WRITE_CONTACTS);
		}
	}
	/**
	 * Android runtime permission for Calendar
	 * in android 6.0 or higher
	 */
	public static final class CalendarOperation {
		private CalendarOperation() {}

		/**
		 * obtain permission in fragment
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read calendar permission success return true ,otherwise false
		 */
		public static boolean obtainRead(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, CalendarPermission.READ_CALENDAR);
		}
		/**
		 * obtain permission in fragment
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain write calendar permission success return true ,otherwise false
		 */
		public static boolean obtainWrite(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, CalendarPermission.WRITE_CALENDAR);
		}

	}

	/**
	 * Android runtime permission for Camera
	 * in android 6.0 or higher
	 */
	public static final class CameraOperation {
		private CameraOperation() {}
		/**
		 * obtain permission in fragment for camera
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain camera permission success return true, otherwise false
		 */
		public static boolean obtainCamera(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, CameraPermission.CAMERA);
		}
	}
	/**
	 * Android runtime permission for location
	 * in android 6.0 or higher
	 */
	public static final class LocationOperation {
		private LocationOperation() {}
		/**
		 * obtain permission in fragment for Location
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain location access fine location permission success return true
		 * , otherwise false
		 */
		public static boolean obtainAccessFineLocation(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, LocationPermission.ACCESS_FINE_LOCATION);
		}
		/**
		 * obtain permission in fragment for Location
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain location access coarse location permission success return true
		 * , otherwise false
		 */
		public static boolean obtainAccessCoarseLocation(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, LocationPermission.ACCESS_COARSE_LOCATION);
		}
	}
	/**
	 * Android runtime permission for microphone
	 * in android 6.0 or higher
	 */
	public static final class MicrophoneOperation {
		private MicrophoneOperation() {}
		/**
		 * obtain permission in fragment for microphone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain microphone permission success return true, otherwise false
		 */
		public static boolean obtainMicrophone(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, MicrophonePermission.MICROPHONE);
		}
	}
	/**
	 * Android runtime permission for Phone
	 * in android 6.0 or higher
	 */
	public static final class PhoneOperation {
		private PhoneOperation() {}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read phone state permission success return true, otherwise false
		 */
		public static boolean obtainReadPhoneState(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.READ_PHONE_STATE);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain call phone permission success return true, otherwise false
		 */
		public static boolean obtainCallPhone(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.CALL_PHONE);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read call log permission success return true, otherwise false
		 */
		public static boolean obtainReadCallLog(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.READ_CALL_LOG);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain write call log permission success return true, otherwise false
		 */
		public static boolean obtainWriteCallLog(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.WRITE_CALL_LOG);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain add voice mail permission success return true, otherwise false
		 */
		public static boolean obtainAddVoiceMail(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.ADD_VOICEMAIL);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain use sip permission success return true, otherwise false
		 */
		public static boolean obtaionUseSip(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.USE_SIP);
		}
		/**
		 * obtain permission in fragment for phone
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain process out going calls success return true, otherwise false
		 */
		public static boolean obtainProcessOutGoingCalls(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, PhonePermission.PROCESS_OUTGOING_CALLS);
		}
	}
	/**
	 * Android runtime permission for Sensors
	 * in android 6.0 or higher
	 */
	public static final class SensorsOperation {
		private SensorsOperation() {}
		/**
		 * obtain permission in fragment for Sensors
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain body sensors permission success return true, otherwise false
		 */
		public static boolean obtainBodySensors(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SensorsPermission.BODY_SENSORS);
		}
	}
	/**
	 * Android runtime permission for Sms
	 * in android 6.0 or higher
	 */
	public static final class SmsOperation {
		private SmsOperation() {}
		/**
		 * obtain permission in fragment for sms
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain send sms permission success return true, otherwise false
		 */
		public static boolean obtainSendSms(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SmsPermission.SEND_SMS);
		}
		/**
		 * obtain permission in fragment for sms
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain receive sms permission success return true, otherwise false
		 */
		public static boolean obtainReceiveSms(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SmsPermission.RECEIVE_SMS);
		}
		/**
		 * obtain permission in fragment for sms
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read sms permission success return true, otherwise false
		 */
		public static boolean obtainReadSms(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SmsPermission.READ_SMS);
		}
		/**
		 * obtain permission in fragment for sms
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain receive wap push permission success return true, otherwise false
		 */
		public static boolean obtainReceiveWapPush(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SmsPermission.RECEIVE_WAP_PUSH);
		}
		/**
		 * obtain permission in fragment for sms
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain receive mms permission success return true, otherwise false
		 */
		public static boolean obtainReceiveMms(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, SmsPermission.RECEIVE_MMS);
		}
	}
	/**
	 * Android runtime permission for external Storage
	 * in android 6.0 or higher
	 */
	public static final class StorageOperation {
		private StorageOperation() {}
		/**
		 * obtain permission in fragment for external storage
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain read external storage permission success return true, otherwise false
		 */
		public static boolean obtainReadExternalStorage(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, StoragePermission.READ_EXTERNAL_STORAGE);
		}
		/**
		 * obtain permission in fragment for external storage
		 * @param permissionsFragment {@code PermissionsFragment}}
		 * @param runnable {@code Runnable}
		 * @return obtain write external storage permission success return true, otherwise false
		 */
		public static boolean obtainWriteExternalStorage(MPermission permission
				, Runnable runnable) {
			return obtainSafePermission(permission, runnable, StoragePermission.WRITE_EXTERNAL_STORAGE);
		}
	}
	/**
	 * request runtime permission
	 * @param permission {@code MPermission}
	 * @param runnable @see Runnable
	 * @param requestPermission request permission
	 * @return request permission success return true, otherwise false
	 */
	private static boolean obtainSafePermission(MPermission permission,
			Runnable runnable, String requestPermission) {
		return obtainSafePermissions(permission, runnable, requestPermission);
	}
	/**
	 * request runtime permission, support multiple permissions
	 * @param permission {@code MPermission}
	 * @return request permission success return true, otherwise false
	 */
	public static boolean obtainSafePermissions(MPermission permission) {
		if (permission == null) return false;
		return permission.request();
	}
	/**
	 * request runtime permission, support multiple permissions
	 * @param permission {@code MPermission}
	 * @param runnable @see Runnable
	 * @return request permission success return true, otherwise false
	 */
	public static boolean obtainSafePermissions(MPermission permission, Runnable runnable) {
		if (permission == null) return false;
		return permission.request(runnable);
	}
	/**
	 * request runtime permission, support multiple permissions
	 * @param permission {@code MPermission}
	 * @param runnable @see Runnable
	 * @param requestPermission request permission @see {@link Permission}
	 * @return request permission success return true, otherwise false
	 */
	public static boolean obtainSafePermissions(MPermission permission,
			Runnable runnable, String ...requestPermissions) {
		if (permission == null) return false;
		return permission.request(runnable, requestPermissions);
	}
	/**
	 * check single permission
	 * @param context {@link Context}
	 * @param permission permission is PemissionUtil.Permission defined permissions
	 * @return if check application have permission return true, otherwise false
	 */
	public static boolean checkPermissions(Context context, String ...permissions) {
		if (permissions == null
				|| permissions.length == 0)
			return false;
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(context, permission)
					!= PackageManager.PERMISSION_GRANTED)
				return false;
		}
		return true;
	}
	/**
	 * check grant results
	 * @param grantResults see {@code requestPermissions} callback method {@code onRequestPermissionsResult}
	 * @return if permissions all by return true,otherwise false
	 */
	public static boolean checkGrantResults(int[] grantResults) {
		if (grantResults == null
				|| grantResults.length == 0)
			return false;
		for (int grantResult : grantResults) {
			if (grantResult != PackageManager.PERMISSION_GRANTED)
				return false;
		}
		return true;
	}
	/**
	 * check shouldShowRequestPermissionRationale
	 * @param context @see Context
	 * @param permission @see PermissionUtil#Permission
	 * @return if shouldShowRequestPermissionRationale is true, you maybe need show explain to user
	 */
	public static boolean isShowRequestPermissionExplain(Context context, String ...permissions) {
		if (context == null || permissions == null)
			throw new NullPointerException("parameter is null");
		for (String permission : permissions) {
			if (ActivityCompat
					.shouldShowRequestPermissionRationale(getActivity(context), permission)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * object convert Activity
	 * @param object @see Object
	 * @return covert success return Activity, otherwise null
	 */
	public static Activity getActivity(Object object) {
		if (object instanceof Fragment) {
			return ((Fragment)object).getActivity();
		} else if (object instanceof Activity) {
			return (Activity) object;
		}
		return null;
	}
	/**
	 * check denied permissions
	 * @param activity @see Activity
	 * @param permissions permissions array
	 * @return return permissions list
	 */
	public static List<String> findDeniedPermissions(Context context, String... permissions) {
		List<String> denyPermissions = new ArrayList<String>();
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(context, permission)
					!= PackageManager.PERMISSION_GRANTED) {
				denyPermissions.add(permission);
			}
		}
		return denyPermissions;
	}

}

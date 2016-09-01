package com.example.sky.permissiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.emporia.common.util.MPermission;
import com.emporia.common.util.PermissionUtil;
import com.emporia.common.util.ShowRequestPermissionExplain;

public class MainActivity extends AppCompatActivity implements ShowRequestPermissionExplain {
    private static final String TAG = MainActivity.class.getSimpleName();

    /** permission request code */
    private int mRequestCode = 100;
    /** here you can list need dangerous permissions  */
    private String[] mRequestPermissions = new String[] {
            PermissionUtil.Permission.LocationPermission.ACCESS_FINE_LOCATION,
            PermissionUtil.Permission.StoragePermission.READ_EXTERNAL_STORAGE,
            PermissionUtil.Permission.ContactsPermission.READ_CONTACTS,
            PermissionUtil.Permission.PhonePermission.CALL_PHONE,
            PermissionUtil.Permission.CameraPermission.CAMERA
    };

    private AskOnPermissionListener mAskOnPermissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MPermission
                .newInstance(this)
                .addRequestCode(mRequestCode)
                .permissions(mRequestPermissions)
                .request();
        MPermission.getInstance().setOnAskPermissionListener(
                mAskOnPermissionListener = new AskOnPermissionListener());
    }

    /**
     * you can listener Permission event callback
     */
    private class AskOnPermissionListener implements MPermission.AskPermissionListener {

        @Override
        public void onPermissionGranted(int requestCode, String[] permissions, int[] grantResults) {
            Log.i(TAG, "permission granted");
        }

        @Override
        public void onPermissionDenied(int requestCode, String[] permissions, int[] grantResults) {
            Log.i(TAG, "permission denied");
        }
    }


    public void Click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.request_contacts_permission:
                selectContacts();
                break;
            case R.id.new_fragment_activity:
                FragmemtPermissionActivity.startActivity(this);
                break;
            default:
                break;
        }
    }
    /**
     * safe request contacts permission for select contacts
     */
    private void selectContacts() {
        PermissionUtil.ContactsOperation.obtainRead(MPermission.newInstance(this), new Runnable() {

            @Override
            public void run() {
                //intent pick for select contacts
                pickContact();
            }
        });
    }
    /**
     * use intent for select contacts
     */
    private void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //FIXME:you can handle events, if you don't want to deal with on your own, you can call the following
        MPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean showRequestPermissionExplain(int requestCode, String... permissions) {
        //you can show a explain dialog
        Log.i(TAG, "request code:" + requestCode + ", permissions" + permissions.toString());
        return false;
    }
}

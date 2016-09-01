package com.emporia.common.util;

/**
 * you need explain why the app needs permissions when
 * user privilege previously rejected, you need to implement
 * the interface and rewrite showRequestPermissionExplain method
 * @author sky
 */
public interface ShowRequestPermissionExplain {
    boolean showRequestPermissionExplain(int requestCode, String ...permissions);
}

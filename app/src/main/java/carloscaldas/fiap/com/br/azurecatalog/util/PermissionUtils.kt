package carloscaldas.fiap.com.br.azurecatalog.util

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

object PermissionUtils {
    @Suppress("UNUSED_PARAMETER")
    // Get permissions
    fun validate(activity: Activity, requestCode: Int, vararg permissions: String): Boolean {
        val list = ArrayList<String>()
        for (permission in permissions) {
            // Validat permission
            val ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
            if (!ok) {
                list.add(permission)
            }
        }
        if (list.isEmpty()) {
            // If ok, return true
            return true
        }
        // List permissions that lack access.
        val newPermissions = arrayOfNulls<String>(list.size)
        list.toArray(newPermissions)
        // Get permissions
        ActivityCompat.requestPermissions(activity, newPermissions, 1)
        return false
    }
}
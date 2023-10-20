package com.za.filemanagerapp.utils.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.za.filemanagerapp.R

class ReadExternalStoragePermission(
    private val fragment: FragmentActivity,
    private val permissionGranted: (Boolean) -> Unit,
) {
    private val requestPermissionLauncher =
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        permissionGranted.invoke(true)
                    } else {
                        permissionGranted.invoke(false)
                        showAndroid10PlusPermissionDialog()
                    }
                } else {
                    permissionGranted.invoke(true)
                }
            } else {
                permissionGranted.invoke(false)
            }
        }


    private val settingsResultLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionGranted.invoke(true)
            } else {
                permissionGranted.invoke(false)
            }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    private val android11PlusSettingResultLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Environment.isExternalStorageManager()) {
                permissionGranted.invoke(true)
            } else {
                permissionGranted.invoke(false)
            }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    fun requestReadExternalStoragePermission() {
        when {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        permissionGranted.invoke(true)
                    } else {
                        permissionGranted.invoke(false)
                        showAndroid10PlusPermissionDialog()
                    }
                } else {
                    permissionGranted.invoke(true)
                }
            }
            fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        permissionGranted.invoke(true)
                        return
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    showAndroid10PlusPermissionDialog()
                } else {
                    showRationalPermissionDialog()
                }
                permissionGranted.invoke(false)
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun showRationalPermissionDialog() {

        MaterialAlertDialogBuilder(fragment)
            .setTitle(fragment.getString(R.string.external_storage_permission))
            .setMessage(fragment.getString(R.string.external_storage_rationale_message))
            .setPositiveButton(fragment.getString(R.string.open_setting)) { dialog, _ ->
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", fragment.packageName, null)
                }
                dialog.dismiss()
                settingsResultLauncher.launch(intent)
            }
            .setNegativeButton(fragment.getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun showAndroid10PlusPermissionDialog() {
        MaterialAlertDialogBuilder(fragment)
            .setTitle(fragment.getString(R.string.allow_access))
            .setMessage(fragment.getString(R.string.external_storage_rationale_message))
            .setPositiveButton(fragment.getString(R.string.open_setting)) { dialog, _ ->
                val intent = Intent().apply {
                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    data = Uri.fromParts("package", fragment.packageName, null)
                }
                dialog.dismiss()
                android11PlusSettingResultLauncher.launch(intent)
            }
            .setNegativeButton(fragment.getString(R.string.not_now)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun checkPermission(permission: String) =
        ContextCompat.checkSelfPermission(
            fragment,
            permission
        ) == PackageManager.PERMISSION_GRANTED

}
package com.za.filemanagerapp.features.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.za.filemanagerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var pageAdapter: FragmentAdapter

    private val readWriteExternalStoragePermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { map ->
            var isAllPermissionsGranted = false
            for (items in map) {
                isAllPermissionsGranted = items.value
            }
            if (isAllPermissionsGranted) {
                setUpViewPager()
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        Log.d("check life cycle", "Activity On create")
    }

    override fun onStart() {
        super.onStart()
        Log.d("check life cycle", "Activity On start")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("check life cycle", "Activity On restart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("check life cycle", "Activity On resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("check life cycle", "Activity On pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("check life cycle", "Activity On stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("check life cycle", "Activity On distroy")
    }

    private fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
        if (hasPermission(permissions[0])) {
            setUpViewPager()
        } else {
            readWriteExternalStoragePermission.launch(permissions)
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setUpViewPager() {
        pageAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.apply {
            tabLayout.addTab(binding.tabLayout.newTab().setText("Audios"))
            tabLayout.addTab(binding.tabLayout.newTab().setText("Videos"))
            tabLayout.addTab(binding.tabLayout.newTab().setText("Other Files"))
            viewPager2.adapter = pageAdapter
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        binding.viewPager2.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            }
            )
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                }
            })

        }

    }
}
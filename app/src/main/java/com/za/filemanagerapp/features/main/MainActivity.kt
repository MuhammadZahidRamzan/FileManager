package com.za.filemanagerapp.features.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.za.filemanagerapp.databinding.ActivityMainBinding
import com.za.filemanagerapp.utils.permissions.ReadExternalStoragePermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var pageAdapter: FragmentAdapter

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun init() {
        ReadExternalStoragePermission(this) {
            if (it) {
                setUpViewPager()
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }

        }.requestReadExternalStoragePermission()
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
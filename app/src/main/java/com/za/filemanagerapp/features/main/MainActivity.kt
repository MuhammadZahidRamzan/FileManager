package com.za.filemanagerapp.features.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.za.filemanagerapp.databinding.ActivityMainBinding
import com.za.filemanagerapp.utils.Extensions.gone
import com.za.filemanagerapp.utils.Extensions.visible
import com.za.filemanagerapp.utils.permissions.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var pageAdapter: FragmentAdapter

    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initClicks()

    }
    private fun init() {
        permissionManager = PermissionManager(this) {
            if (it) {
                setUpViewPager()
                binding.viewPager2.visible()
                binding.tabLayout.visible()
                binding.btnAllowPermission.gone()
            } else {
                binding.viewPager2.gone()
                binding.tabLayout.gone()
                binding.btnAllowPermission.visible()
            }
        }
        permissionManager.checkStoragePermissionsEnabled()
    }

    private fun initClicks(){
        binding.btnAllowPermission.setOnClickListener {
            permissionManager.requestReadExternalStoragePermission()
        }
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
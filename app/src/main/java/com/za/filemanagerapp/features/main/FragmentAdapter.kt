package com.za.filemanagerapp.features.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle
import com.za.filemanagerapp.features.audio.presentation.AudioFragment
import com.za.filemanagerapp.features.document.presentation.DocumentFragment
import com.za.filemanagerapp.features.video.presentation.VideoFragment

class FragmentAdapter(
    fragmentManager:FragmentManager,
    lifecycle: androidx.lifecycle.Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AudioFragment()
            }
            1 -> {
                VideoFragment()
            }
            else -> {
                DocumentFragment()
            }
        }
    }
}
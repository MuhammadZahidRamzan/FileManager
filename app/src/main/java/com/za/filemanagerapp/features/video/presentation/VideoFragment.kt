package com.za.filemanagerapp.features.video.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.FragmentAudioBinding
import com.za.filemanagerapp.databinding.FragmentVideoBinding
import com.za.filemanagerapp.features.audio.adapter.AudioAdapter
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.presentation.AudioViewModel
import com.za.filemanagerapp.features.video.adapter.VideoAdapter
import com.za.filemanagerapp.features.video.domain.model.Video
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : Fragment() {
    private val viewModel: VideoViewModel by viewModels()
    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoAdapter: VideoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.videosList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                populateRecycler(it)
            }
        }
    }

    private fun populateRecycler(videoList: List<Video>) {
        binding.pbVideo.visibility = View.GONE
        binding.tvAllVideos.text = "All Videos : ${videoList.size}"
        binding.videoRecycler.setHasFixedSize(true)
        binding.videoRecycler.layoutManager = LinearLayoutManager(requireContext())
        videoAdapter = VideoAdapter(requireContext(),videoList )
        binding.videoRecycler.adapter = videoAdapter
    }



}
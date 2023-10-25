package com.za.filemanagerapp.features.video.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.FragmentVideoBinding
import com.za.filemanagerapp.features.video.adapter.VideoAdapter
import com.za.filemanagerapp.features.video.domain.model.Video
import com.za.filemanagerapp.features.video.presentation.activities.VideoPlayerActivity
import com.za.filemanagerapp.features.video.presentation.view_model.VideoViewModel
import com.za.filemanagerapp.utils.Constants
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
        binding.tvAllVideos.visibility = View.VISIBLE
        binding.tvAllVideos.text = resources.getString(R.string.all_videos_string,videoList.size)
        binding.videoRecycler.setHasFixedSize(true)
        binding.videoRecycler.layoutManager = LinearLayoutManager(requireContext())
        videoAdapter = VideoAdapter(requireContext(),videoList){
            goToVideoPlayerActivity(it)
        }
        binding.videoRecycler.adapter = videoAdapter
    }

    private fun goToVideoPlayerActivity(video: Video){
        val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
        intent.putExtra(Constants.VIDEO,video)
        startActivity(intent)
    }




}
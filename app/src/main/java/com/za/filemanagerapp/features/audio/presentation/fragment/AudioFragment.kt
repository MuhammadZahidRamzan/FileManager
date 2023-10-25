package com.za.filemanagerapp.features.audio.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.FragmentAudioBinding
import com.za.filemanagerapp.features.audio.adapter.AudioAdapter
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.audio.presentation.activity.AudioPlayerActivity
import com.za.filemanagerapp.features.audio.presentation.view_model.AudioViewModel
import com.za.filemanagerapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : Fragment() {
    private val viewModel: AudioViewModel by viewModels()
    private lateinit var binding: FragmentAudioBinding
    private lateinit var audioAdapter: AudioAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.audiosList.observe(viewLifecycleOwner) {
            populateRecycler(it)
        }
    }
    private fun populateRecycler(audioList: List<Audio>) {
        binding.pbAudio.visibility = View.GONE
        binding.tvAllAudios.visibility = View.VISIBLE
        binding.tvAllAudios.text = resources.getString(R.string.all_audios_string,audioList.size)
        binding.audioRecycler.setHasFixedSize(true)
        binding.audioRecycler.layoutManager = LinearLayoutManager(requireContext())
        audioAdapter = AudioAdapter(requireContext(), audioList){
            goToAudioPlayerActivity(it)
        }
        binding.audioRecycler.adapter = audioAdapter
    }

    private fun goToAudioPlayerActivity(audio: Audio){
        val intent = Intent(requireContext(),AudioPlayerActivity::class.java)
        intent.putExtra(Constants.AUDIO,audio)
        startActivity(intent)
    }
}
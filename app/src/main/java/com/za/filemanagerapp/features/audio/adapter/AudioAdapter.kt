package com.za.filemanagerapp.features.audio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.AudioViewBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.features.document.domain.model.Document
import com.za.filemanagerapp.utils.Utils.formateDuration

class AudioAdapter(private val context: Context, private val audioList: List<Audio>) :
    RecyclerView.Adapter<AudioAdapter.MyHolder>() {
    class MyHolder(binding: AudioViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.audioName
        val album = binding.songAlbum
        val image = binding.image
        val duration = binding.audioDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(AudioViewBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = audioList[position].title
        holder.album.text = audioList[position].album
        holder.duration.text = formateDuration(audioList[position].duration!!)
        Glide.with(context).load(audioList[position].artUri)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }
}
package com.za.filemanagerapp.features.audio.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.za.filemanagerapp.R
import com.za.filemanagerapp.databinding.AudioViewBinding
import com.za.filemanagerapp.databinding.DetailViewBinding
import com.za.filemanagerapp.databinding.MoreBinding
import com.za.filemanagerapp.features.audio.domain.model.Audio
import com.za.filemanagerapp.utils.Utils.formatDuration

class AudioAdapter(private val context: Context,
                   private val audioList: List<Audio>,
                   private val onItemClicked: (Audio) -> Unit)
    :RecyclerView.Adapter<AudioAdapter.MyHolder>() {

    class MyHolder(binding: AudioViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.audioName
        val album = binding.songAlbum
        val image = binding.image
        val duration = binding.audioDuration
        val root = binding.getRoot()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(AudioViewBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = audioList[position].title
        holder.album.text = audioList[position].album
        holder.duration.text = formatDuration(audioList[position].duration!!)
        Glide.with(context).load(audioList[position].artUri)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher).centerCrop())
            .into(holder.image)
        holder.root.setOnClickListener {
            onItemClicked(audioList[position])
        }
        holder.root.setOnLongClickListener {
            val customDialog =
                LayoutInflater.from(context).inflate(R.layout.more, holder.root, false)
            val bindingDialog = MoreBinding.bind(customDialog)
            val dialog = MaterialAlertDialogBuilder(context).setView(customDialog).create()
            dialog.show()
            bindingDialog.btnShare.setOnClickListener {
                dialog.dismiss()
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "audio/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(audioList[position].path))
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(shareIntent, "Shearing Audio File!!"),
                    null
                )
            }
            bindingDialog.btnDetails.setOnClickListener {
                dialog.dismiss()
                val customDialogDetails =
                    LayoutInflater.from(context).inflate(R.layout.detail_view, holder.root, false)
                val bindingIF = DetailViewBinding.bind(customDialogDetails)
                val dialogDetails = MaterialAlertDialogBuilder(context).setView(customDialogDetails)
                    .setCancelable(false)
                    .create()
                dialogDetails.show()
                val detailText = SpannableStringBuilder().bold { append("DETAILS\n\nName:") }
                    .append(audioList[position].title)
                    .bold { append("\n\nDuration:") }
                    .append(DateUtils.formatElapsedTime(audioList[position].duration?.div(1000) ?: 0))
                    .bold { append("\n\nFile Size:") }.append(
                        Formatter.formatShortFileSize(
                            context,
                            audioList[position].size?.toLong() ?: 0
                        )
                    )
                    .bold { append("\n\nLocation:") }.append(audioList[position].path)
                bindingIF.tvDetails.text = detailText
                bindingIF.btnOk.setOnClickListener {
                    dialogDetails.dismiss()
                }
            }
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return audioList.size
    }
}
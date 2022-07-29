package jp.ceed.android.mylapslogger.adatpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.android.mylapslogger.BR
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.databinding.PracticeByTrackListItemBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack

class PracticeByTrackAdapter(
    context: Context,
    private val onClick: (PracticeTrack) -> Unit,
): ListAdapter<PracticeTrack, PracticeByTrackAdapter.ViewHolder>(diffCallback) {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PracticeByTrackListItemBinding = DataBindingUtil
            .inflate(inflater, R.layout.practice_by_track_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PracticeTrack>() {
            override fun areItemsTheSame(
                oldItem: PracticeTrack,
                newItem: PracticeTrack
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PracticeTrack,
                newItem: PracticeTrack
            ): Boolean = oldItem == newItem

        }
    }

}
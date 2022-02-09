package jp.ceed.android.mylapslogger.adatpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.android.mylapslogger.BR
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.databinding.TrackBestListItemBinding
import jp.ceed.android.mylapslogger.entity.PracticeTrack

class TrackBestAdapter(
    context: Context,
    private val onClick: (PracticeTrack) -> Unit,
    private val hideTrackName: Boolean = false): RecyclerView.Adapter<TrackBestAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    var items: List<PracticeTrack> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TrackBestListItemBinding = DataBindingUtil.inflate(inflater, R.layout.track_best_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.viewDataBinding?.setVariable(BR.trackNameVisibility, if(hideTrackName){
            View.GONE
        }else{
            View.VISIBLE
        })
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setListItems(_items: List<PracticeTrack>){
        items = _items
    }

}
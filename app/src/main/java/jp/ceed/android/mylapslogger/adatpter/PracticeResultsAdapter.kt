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
import jp.ceed.android.mylapslogger.databinding.LaptimeListLapBinding
import jp.ceed.android.mylapslogger.databinding.LaptimeListLapWithSectorBinding
import jp.ceed.android.mylapslogger.databinding.LaptimeListSectionBinding
import jp.ceed.android.mylapslogger.databinding.SummaryListLapBinding
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.util.AppSettings

class PracticeResultsAdapter(
    context: Context,
    private val onClickListener: ((PracticeResultsItem.Section) -> Unit)?
) : ListAdapter<PracticeResultsItem, PracticeResultsAdapter.ViewHolder>(diffCallback) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val sessionMenuButtonVisibility = if (onClickListener == null) {
        View.GONE
    } else {
        View.VISIBLE
    }

    private val showSectorTime: Boolean by lazy {
        AppSettings(context).isShowSectorTime()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            VIEW_TYPE_SECTION -> {
                val binding: LaptimeListSectionBinding = DataBindingUtil.inflate(inflater, R.layout.laptime_list_section, parent, false)
                ViewHolder(binding.root)
            }
            VIEW_TYPE_LAP -> {
                val binding = if (showSectorTime) {
                    DataBindingUtil.inflate(inflater, R.layout.laptime_list_lap_with_sector, parent, false) as LaptimeListLapWithSectorBinding
                } else {
                    DataBindingUtil.inflate(inflater, R.layout.laptime_list_lap, parent, false) as LaptimeListLapBinding
                }
                ViewHolder(binding.root)
            }
            else -> {
                val binding: SummaryListLapBinding = DataBindingUtil.inflate(inflater, R.layout.summary_list_lap, parent, false)
                ViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PracticeResultsItem = getItem(position)
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.viewDataBinding?.setVariable(BR.sessionMemoButtonVisibility, sessionMenuButtonVisibility)
        holder.itemView.setOnClickListener {
            when(item){
                is PracticeResultsItem.Section -> { onClickSection(item) }
                else -> {}
            }
        }
    }

    private fun onClickSection(item: PracticeResultsItem.Section) {
        if (item.sessionId != 0L) {
            onClickListener?.let { it(item) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is PracticeResultsItem.Section -> VIEW_TYPE_SECTION
            is PracticeResultsItem.Lap -> VIEW_TYPE_LAP
            else -> VIEW_TYPE_SUMMARY
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    companion object {
        private const val VIEW_TYPE_SECTION = 0
        private const val VIEW_TYPE_LAP = 1
        private const val VIEW_TYPE_SUMMARY = 2

        val diffCallback = object : DiffUtil.ItemCallback<PracticeResultsItem>() {
            override fun areItemsTheSame(
                oldItem: PracticeResultsItem,
                newItem: PracticeResultsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PracticeResultsItem,
                newItem: PracticeResultsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
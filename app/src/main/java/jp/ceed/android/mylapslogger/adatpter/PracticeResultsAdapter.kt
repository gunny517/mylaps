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
import jp.ceed.android.mylapslogger.databinding.LaptimeListLapBinding
import jp.ceed.android.mylapslogger.databinding.LaptimeListSectionBinding
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem

class PracticeResultsAdapter(
    context: Context,
    private val onClickListener: (Long, String) -> Unit
) : RecyclerView.Adapter<PracticeResultsAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var items: List<PracticeResultsItem> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_SECTION) {
            val binding: LaptimeListSectionBinding = DataBindingUtil.inflate(inflater, R.layout.laptime_list_section, parent, false)
            ViewHolder(binding.root)
        } else {
            val binding: LaptimeListLapBinding = DataBindingUtil.inflate(inflater, R.layout.laptime_list_lap, parent, false)
            ViewHolder(binding.root)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PracticeResultsItem = items[position]
        holder.viewDataBinding?.setVariable(BR.lapDto, item)
        holder.itemView.setOnClickListener {
            when(item){
                is PracticeResultsItem.Section -> { onClickSection(item) }
                else -> null
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun onClickSection(item: PracticeResultsItem.Section) {
        if (item.sessionId != 0L) {
            onClickListener(item.sessionId, item.sectionTitle)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is PracticeResultsItem.Section -> VIEW_TYPE_SECTION
            else -> VIEW_TYPE_LAP
        }
    }

    fun setItems(_items: List<PracticeResultsItem>) {
        items = _items
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    companion object {

        private const val VIEW_TYPE_SECTION = 0
        private const val VIEW_TYPE_LAP = 1
    }

}
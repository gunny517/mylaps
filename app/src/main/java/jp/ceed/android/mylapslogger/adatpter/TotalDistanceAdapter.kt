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
import jp.ceed.android.mylapslogger.databinding.TotalDistacneItemBinding
import jp.ceed.android.mylapslogger.entity.TotalDistance

class TotalDistanceAdapter(
    context: Context,
    private val onClickItem: (item: TotalDistance) -> Unit): RecyclerView.Adapter<TotalDistanceAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private var items: List<TotalDistance> = listOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TotalDistacneItemBinding = DataBindingUtil.inflate(inflater, R.layout.total_distacne_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.itemView.setOnClickListener {
            onClickItem(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setListItem(_items: List<TotalDistance>){
        items = _items
    }


}
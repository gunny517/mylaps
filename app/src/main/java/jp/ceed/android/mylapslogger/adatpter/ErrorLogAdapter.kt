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
import jp.ceed.android.mylapslogger.databinding.ErrorLogItemBinding
import jp.ceed.android.mylapslogger.dto.ErrorLogItem

class ErrorLogAdapter(val context: Context): RecyclerView.Adapter<ErrorLogAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private var items: List<ErrorLogItem> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    fun setItems(_items: List<ErrorLogItem>){
        items = _items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ErrorLogItemBinding = DataBindingUtil.inflate(inflater, R.layout.error_log_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.viewDataBinding?.setVariable(BR.item, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
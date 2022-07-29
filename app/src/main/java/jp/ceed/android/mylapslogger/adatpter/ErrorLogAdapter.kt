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
import jp.ceed.android.mylapslogger.databinding.ErrorLogItemBinding
import jp.ceed.android.mylapslogger.dto.ErrorLogItem

class ErrorLogAdapter(val context: Context): ListAdapter<ErrorLogItem, ErrorLogAdapter.ViewHolder>(
    diffCallback) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ErrorLogItemBinding = DataBindingUtil.inflate(inflater, R.layout.error_log_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewDataBinding?.setVariable(BR.item, item)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ErrorLogItem>() {
            override fun areItemsTheSame(oldItem: ErrorLogItem, newItem: ErrorLogItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ErrorLogItem, newItem: ErrorLogItem): Boolean =
                oldItem == newItem

        }
    }

}
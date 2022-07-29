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
import jp.ceed.android.mylapslogger.databinding.SessionListItemBinding
import jp.ceed.android.mylapslogger.model.SessionListItem

class SessionListAdapter(
    context: Context,
    private val onClickCell: (Int) -> Unit
):
    ListAdapter<SessionListItem, SessionListAdapter.ViewHolder>(diffCallback) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<SessionListItemBinding>(inflater, R.layout.session_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.itemView.setOnClickListener(View.OnClickListener {
            onClickCell(Integer.parseInt(item.no))
        })
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<SessionListItem>() {
            override fun areItemsTheSame(
                oldItem: SessionListItem,
                newItem: SessionListItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SessionListItem,
                newItem: SessionListItem
            ): Boolean = oldItem == newItem

        }
    }

}
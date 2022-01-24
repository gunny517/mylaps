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
import jp.ceed.android.mylapslogger.databinding.SessionListItemBinding
import jp.ceed.android.mylapslogger.model.SessionListItem

class SessionListAdapter(context: Context): RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    private var items: List<SessionListItem> = mutableListOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val _binding = DataBindingUtil.inflate<SessionListItemBinding>(inflater, R.layout.session_list_item, parent, false)
        return ViewHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.viewDataBinding?.setVariable(BR.item, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setItems(_items: List<SessionListItem>){
        items = _items
    }

}
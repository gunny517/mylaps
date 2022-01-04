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
import jp.ceed.android.mylapslogger.databinding.ActivitiesListItemBinding
import jp.ceed.android.mylapslogger.model.ActivitiesItem

class ActivitiesAdapter(
    context: Context,
    private var activitiesItem: List<ActivitiesItem>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    fun setItems(_activitiesItem: List<ActivitiesItem>) {
        activitiesItem = _activitiesItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ActivitiesListItemBinding = DataBindingUtil.inflate(inflater, R.layout.activities_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = activitiesItem[position]
        holder.viewDataBinding?.setVariable(BR.item, item)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
    }

    override fun getItemCount(): Int {
        return activitiesItem.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    interface OnClickListener {

        fun onClick(activitiesItem: ActivitiesItem)
    }

}
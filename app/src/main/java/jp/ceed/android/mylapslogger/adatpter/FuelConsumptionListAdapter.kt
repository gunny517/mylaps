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
import jp.ceed.android.mylapslogger.model.FuelConsumptionListItem

class FuelConsumptionListAdapter(
    context: Context,
): ListAdapter<FuelConsumptionListItem, FuelConsumptionListAdapter.ViewHolder>(diffCallback) {

    private val inflator: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflator.inflate(R.layout.fuel_consumption_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding?.setVariable(BR.item, getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<FuelConsumptionListItem>() {
            override fun areItemsTheSame(
                oldItem: FuelConsumptionListItem,
                newItem: FuelConsumptionListItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: FuelConsumptionListItem,
                newItem: FuelConsumptionListItem
            ): Boolean = oldItem == newItem

        }
    }
}
package jp.ceed.android.mylapslogger.adatpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.databinding.FinalRatioItemBinding

class FinalRatioAdapter(val context: Context, private val itemList: List<String>): RecyclerView.Adapter<FinalRatioAdapter.ViewHolder>() {

    class ViewHolder(val binding: FinalRatioItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FinalRatioItemBinding = DataBindingUtil.inflate(inflater, R.layout.final_ratio_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}
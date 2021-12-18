package jp.ceed.android.mylapslogger.adatpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.model.ActivitiesIItem

class ActivitiesAdapter(
	private val context: Context,
	private val itemList: List<ActivitiesIItem>)
	: RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

	private val inflater: LayoutInflater = LayoutInflater.from(context)



	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
		val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
		val placeTextView: TextView = itemView.findViewById(R.id.placeTextView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(inflater.inflate(R.layout.activities_list_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = itemList[position]
		holder.dateTextView.text = item.startTime
		holder.placeTextView.text = item.place
	}

	override fun getItemCount(): Int {
		return itemList.size
	}

}
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
import jp.ceed.android.mylapslogger.dto.LapDto

class PracticeResultsAdapter(
	private val context: Context,
	private var items: List<LapDto>

) : RecyclerView.Adapter<PracticeResultsAdapter.ViewHolder>() {

	private val inflater: LayoutInflater = LayoutInflater.from(context)

	fun setItems(_items: List<LapDto>){
		items = _items
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return if(viewType == VIEW_TYPE_SECTION){
			val binding: LaptimeListSectionBinding = DataBindingUtil.inflate(inflater, R.layout.laptime_list_section, parent, false)
			ViewHolder(binding.root)
		}else{
			val binding: LaptimeListLapBinding = DataBindingUtil.inflate(inflater, R.layout.laptime_list_lap, parent, false)
			ViewHolder(binding.root)
		}
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.viewDataBinding?.setVariable(BR.lapDto, item)
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun getItemViewType(position: Int): Int {
		return if(items[position].sectionTitle == null){
			VIEW_TYPE_LAP
		}else{
			VIEW_TYPE_SECTION
		}
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
		val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
	}

	companion object{
		private val VIEW_TYPE_SECTION = 0
		private val VIEW_TYPE_LAP = 1
	}

}
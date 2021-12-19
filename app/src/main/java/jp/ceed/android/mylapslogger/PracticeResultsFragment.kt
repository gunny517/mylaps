package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.android.mylapslogger.adatpter.PracticeResultsAdapter
import jp.ceed.android.mylapslogger.args.PracticeSummaryFragmentParams
import jp.ceed.android.mylapslogger.databinding.FragmentPracticeResultBinding
import jp.ceed.android.mylapslogger.dto.LapDto
import jp.ceed.android.mylapslogger.viewModel.PracticeResultFragmentViewModel

class PracticeResultsFragment: Fragment() {

	private var _binding: FragmentPracticeResultBinding? = null

	private val binding get() = _binding!!

	private val viewModel: PracticeResultFragmentViewModel by viewModels()

	private val args: PracticeResultsFragmentArgs by navArgs()


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		setHasOptionsMenu(true)
		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_result, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLayout()
		viewModel.getPracticeResult(args.sessionId)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when(item.itemId){
			R.id.action_summary -> {
				navigateToSessionSummary()
				return true
			}
			else -> return false
		}
	}

	private fun initLayout(){
		context?.let {
			val adapter = PracticeResultsAdapter(it)
			binding.recyclerView.adapter = adapter
			binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
			binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
			viewModel.lapList.observe(viewLifecycleOwner, {
				adapter.setItems(it.sessionData)
				adapter.notifyDataSetChanged()
			})
		}
	}

	private fun navigateToSessionSummary(){
		viewModel.lapList.value?.let {
			val params = PracticeSummaryFragmentParams(
				it.sessionSummary as ArrayList<LapDto>,
				args.sessionDate
			)
			val action = PracticeResultsFragmentDirections.actionPracticeResultsFragmentToPracticeSummaryFragment(params)
			findNavController().navigate(action)
		}
	}

}
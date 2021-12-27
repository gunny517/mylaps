package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.*
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
	}


	override fun onPrepareOptionsMenu(menu: Menu) {
		menu.findItem(R.id.action_session_info).setVisible(true)
		menu.findItem(R.id.action_session_summary).setVisible(true)
		menu.findItem(R.id.action_user_info).setVisible(false)
		super.onPrepareOptionsMenu(menu)
	}


	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when(item.itemId){
			R.id.action_session_summary -> {
				navigateToSessionSummary()
				true
			}
			R.id.action_session_info -> {
				navigateToActivityInfo()
				true
			}
			else -> false
		}
	}

	private fun initLayout(){
		context?.let {
			val adapter = PracticeResultsAdapter(it){ i: Int -> navigateToSessionInfo(i) }
			binding.recyclerView.adapter = adapter
			binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
			binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
			viewModel.lapList.observe(viewLifecycleOwner, {
				adapter.setItems(it.sessionData)
				adapter.notifyDataSetChanged()
			})
			viewModel.sessionId.observe(viewLifecycleOwner, {
				viewModel.getPracticeResult()
			})
			viewModel.sessionId.value = args.activityId
		}
	}

	private fun navigateToSessionSummary(){
		viewModel.lapList.value?.let {
			val params = PracticeSummaryFragmentParams(
				it.sessionSummary as ArrayList<LapDto>,
				args.sessionDate
			)
			findNavController().navigate(PracticeResultsFragmentDirections
				.actionPracticeResultsFragmentToPracticeSummaryFragment(params))
		}
	}

	private fun navigateToActivityInfo(){
		findNavController().navigate(PracticeResultsFragmentDirections
			.actionPracticeResultsFragmentToActivityInfoFragment(args.activityId))
	}


	private fun navigateToSessionInfo(sessionId: Int){
		findNavController().navigate(PracticeResultsFragmentDirections
			.actionPracticeResultFragmentToSessionInfoFragment(sessionId))
	}


}
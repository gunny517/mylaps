package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.android.mylapslogger.adatpter.ActivitiesAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentActivitiesBinding
import jp.ceed.android.mylapslogger.model.ActivitiesItem
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ActivitiesFragment : Fragment() {

	private var _binding: FragmentActivitiesBinding? = null

	private val binding get() = _binding!!

	private val viewModel: ActivitiesFragmentViewModel by viewModels()

	lateinit var userAccountRepository: UserAccountRepository


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		userAccountRepository = UserAccountRepository(view.context)
	}


	override fun onResume() {
		super.onResume()
		checkAccount()
	}


	private fun checkAccount(){
		val token: String? = userAccountRepository.getAccessToken();
		val userId: String? = userAccountRepository.getUserId()
		if(token == null || userId == null){
			findNavController().navigate(R.id.action_ActivitiesFragment_to_LoginFragment)
		}else{
			initRecyclerView()
		}
	}

	private fun initRecyclerView(){
		context?.let { it ->
			val adapter = ActivitiesAdapter(it, mutableListOf(), object : ActivitiesAdapter.OnClickListener {
				override fun onClick(activitiesItem: ActivitiesItem) {
					navigateToPracticeResults(activitiesItem.sessionId, activitiesItem.startTime)
				}
			})
			binding.recyclerView.adapter = adapter
			binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
			viewModel.activities.observe(viewLifecycleOwner, Observer {
				adapter.setItems(it)
				adapter.notifyDataSetChanged()
			})
			viewModel.callActivitiesRequest()
		}
	}

	private fun navigateToPracticeResults(sessionId: Int, sessionDatetime: String){
		val action = ActivitiesFragmentDirections.actionActivitiesFragmentToPracticeResultsFragment(sessionId, sessionDatetime)
		findNavController().navigate(action)
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
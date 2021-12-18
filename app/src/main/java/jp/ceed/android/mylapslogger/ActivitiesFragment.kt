package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.android.mylapslogger.adatpter.ActivitiesAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentActivitiesBinding
import jp.ceed.android.mylapslogger.network.request.ActivitiesRequest
import jp.ceed.android.mylapslogger.network.response.ActivitiesResponse
import jp.ceed.android.mylapslogger.repository.UserAccountRepository
import jp.ceed.android.mylapslogger.util.Util
import jp.ceed.android.mylapslogger.viewModel.ActivitiesFragmentViewModel
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ActivitiesFragment : Fragment() {

	private var _binding: FragmentActivitiesBinding? = null

	lateinit var userAccountRepository: UserAccountRepository

	private val binding get() = _binding!!

	private val viewModel: ActivitiesFragmentViewModel by viewModels()


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
			activitiesRequest(userId)
		}
	}

	private fun activitiesRequest(userId: String){
		val request = ActivitiesRequest()
		request.userId = userAccountRepository.getUserId()
		request.executeRequest(context, object : Callback<ActivitiesResponse> {
			override fun success(t: ActivitiesResponse?, response: Response?) {
				onSuccessActivities(t)
			}
			override fun failure(error: RetrofitError?) {
				Toast.makeText(context, R.string.get_activities_failed, Toast.LENGTH_LONG).show()
			}
		})
	}

	private fun onSuccessActivities(response: ActivitiesResponse?){
		response?.let {
			val list = Util.convertToActivitiesItem(it.activities)
			val adapter = context?.let { it1 -> ActivitiesAdapter(it1, list) }
			binding.recyclerView.adapter = adapter
			binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		}
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
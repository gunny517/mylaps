package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import jp.ceed.android.mylapslogger.databinding.FragmentLoginBinding
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.viewModel.LoginFragmentViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment(), TextWatcher {

	private var _binding: FragmentLoginBinding? = null

	private val binding get() = _binding!!

	private val viewModel: LoginFragmentViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeEvent()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}


	private fun observeEvent(){
		binding.userNameEditText.addTextChangedListener(this)
		binding.passwordEditText.addTextChangedListener(this)
		viewModel.loginResult.observe(viewLifecycleOwner){	onFinishLogin(it) }
	}

	override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
		// Nothing to do.
	}

	override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
		val userName = binding.userNameEditText.text?.toString()
		val password = binding.passwordEditText.text?.toString()
		viewModel.loginButtonEnabled.value = !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)
	}

	override fun afterTextChanged(p0: Editable?) {
		// Nothing to do.
	}

	private fun onFinishLogin(loginResult: LoginResult){
		when(loginResult){
			LoginResult.Success -> findNavController().navigate(R.id.action_LoginFragment_to_ActivitiesFragment)
			else -> Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show()
		}
	}
}
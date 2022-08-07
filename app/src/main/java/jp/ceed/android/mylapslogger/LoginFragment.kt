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
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.databinding.FragmentLoginBinding
import jp.ceed.android.mylapslogger.model.LoginResult
import jp.ceed.android.mylapslogger.viewModel.LoginFragmentViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

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


    private fun observeEvent() {
        viewModel.userName.observe(viewLifecycleOwner) { viewModel.updateLoginButtonEnabled() }
        viewModel.password.observe(viewLifecycleOwner) { viewModel.updateLoginButtonEnabled() }
        viewModel.loginResult.observe(viewLifecycleOwner) { onFinishLogin(it) }
    }


    private fun onFinishLogin(loginResult: LoginResult) {
        when (loginResult) {
            LoginResult.Success -> findNavController().navigate(R.id.GoToActivitiesFragment)
            else -> Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show()
        }
    }
}
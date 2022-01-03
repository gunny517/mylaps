package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jp.ceed.android.mylapslogger.databinding.FragmentUserInfoBinding
import jp.ceed.android.mylapslogger.viewModel.UserInfoFragmentViewModel

class UserInfoFragment : Fragment() {

    private var _binding: FragmentUserInfoBinding? = null

    val binding get() = _binding!!

    val viewModel: UserInfoFragmentViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

}
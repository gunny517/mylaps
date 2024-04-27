package jp.ceed.kart.settings.ui.data.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.android.mylapslogger.R
import jp.ceed.android.mylapslogger.adatpter.FinalRatioAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentFinalRatioBinding
import jp.ceed.android.mylapslogger.util.Util
import jp.ceed.android.mylapslogger.viewModel.FinalRatioFragmentViewModel

@AndroidEntryPoint
class FinalRatioFragment: Fragment() {

    private var _binding: FragmentFinalRatioBinding? = null

    val binding get() = _binding!!

    val viewModel: FinalRatioFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_final_ratio, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewModel.headerItems.observe(viewLifecycleOwner){
            val adapter = FinalRatioAdapter(requireContext(), it)
            binding.headerView.adapter = adapter
            binding.headerView.layoutManager = GridLayoutManager(requireContext(), viewModel.colSize.value ?: 0)
            binding.headerView.addItemDecoration(DividerItemDecoration(requireContext(), GridLayoutManager.VERTICAL))
        }
        viewModel.finalRatioList.observe(viewLifecycleOwner){
            val adapter = FinalRatioAdapter(requireContext(), it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), viewModel.colSize.value ?: 0)
            binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), GridLayoutManager.VERTICAL))
        }
        viewModel.event.observe(viewLifecycleOwner){
            Util.hideKeyboard(requireContext(), binding.root)
        }
    }
}
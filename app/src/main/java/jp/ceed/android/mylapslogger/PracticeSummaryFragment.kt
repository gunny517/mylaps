package jp.ceed.android.mylapslogger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.android.mylapslogger.adatpter.PracticeResultsAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentPrecticeSummaryBinding
import jp.ceed.android.mylapslogger.dto.PracticeResultsItem
import jp.ceed.android.mylapslogger.viewModel.PracticeSummaryFragmentViewModel

class PracticeSummaryFragment : Fragment() {

    private var _binding: FragmentPrecticeSummaryBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PracticeSummaryFragmentViewModel by viewModels()

    private val args: PracticeSummaryFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prectice_summary, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PracticeResultsAdapter(requireContext()) {  }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        viewModel.recyclerViewItem.observe(viewLifecycleOwner) {
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.recyclerViewItem.value = args.params.sessionSummary
    }

}
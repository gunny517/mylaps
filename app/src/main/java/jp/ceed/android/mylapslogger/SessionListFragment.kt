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
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.android.mylapslogger.adatpter.SessionListAdapter
import jp.ceed.android.mylapslogger.databinding.FragmentSessionListBinding
import jp.ceed.android.mylapslogger.viewModel.SessionListFragmentViewModel

class SessionListFragment: Fragment() {

    private val args: SessionListFragmentArgs by navArgs()

    private var _binding: FragmentSessionListBinding? = null

    val binding get() = _binding!!

    private val viewModel: SessionListFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_session_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        val adapter = SessionListAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        viewModel.sessionItemList.observe(viewLifecycleOwner){
            adapter.setItems(it)
            adapter.notifyDataSetChanged()
        }
    }


    private fun factoryProducer(): SessionListFragmentViewModel.Factory{
        return SessionListFragmentViewModel.Factory(args.activityId, requireContext())
    }

}
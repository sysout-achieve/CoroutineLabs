package com.gunt.coroutinelabs.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gunt.coroutinelabs.databinding.FragmentMainNavigatorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavigatorFragment : Fragment() {

    private lateinit var binding: FragmentMainNavigatorBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainNavigatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initViews(){
        binding.button1.setOnClickListener {
            findNavController().navigate(MainNavigatorFragmentDirections.actionMainNavigatorFragmentToCoroutineScopeTest())
        }
    }
}
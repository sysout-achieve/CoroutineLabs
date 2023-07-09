package com.gunt.coroutinelabs.presentation.fragment.coroutineScopeTest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gunt.coroutinelabs.databinding.FragmentCoroutineScopeTestBinding

class CoroutineScopeTestFragment : Fragment() {

    private lateinit var binding: FragmentCoroutineScopeTestBinding
    private val viewModel: CoroutineScopeTestViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        subscribeStates()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCoroutineScopeTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.startButton.setOnClickListener {
            viewModel.coroutineScopeTest1()
        }
        binding.resetButton.setOnClickListener {
            viewModel.resetTest()
        }
    }

    private fun subscribeStates() {
        with(viewModel) {
            exceptionText.observe(this@CoroutineScopeTestFragment.viewLifecycleOwner) {
                binding.exceptionText.text = it
            }
            parentLaunchText.observe(this@CoroutineScopeTestFragment.viewLifecycleOwner) {
                binding.parentCoroutineText.text = it
            }
            nestedLaunchText.observe(this@CoroutineScopeTestFragment.viewLifecycleOwner) {
                binding.nestedCoroutineText.text = it
            }
        }
    }
}
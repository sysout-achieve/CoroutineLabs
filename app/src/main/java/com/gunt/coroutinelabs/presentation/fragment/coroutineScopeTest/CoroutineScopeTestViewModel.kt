package com.gunt.coroutinelabs.presentation.fragment.coroutineScopeTest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoroutineScopeTestViewModel @Inject constructor(

) : ViewModel() {
    var exceptionText: MutableLiveData<String> = MutableLiveData("")
    var parentLaunchText: MutableLiveData<String> = MutableLiveData("")
    var nestedLaunchText: MutableLiveData<String> = MutableLiveData("")
    var number = 1

    fun coroutineScopeTest1() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            exceptionText.value = "exceptionHandler\n order of priority : $number \n exception occur Time : ${System.currentTimeMillis()} ${throwable.message}\n"
            number++
        }
        val scope = CoroutineScope(Job() + exceptionHandler + Dispatchers.Main)
        scope.launch {
            coroutineScope {
                launch {
                    nestedLaunchText.value = "nestedLaunch\n order of priority : $number \n nestedLaunch occur Time : ${System.currentTimeMillis()}\n"
                    number++
                }
            }
            parentLaunchText.value = "parentLaunch\n order of priority : $number \n parentLaunch occur Time : ${System.currentTimeMillis()}\n"
            number++
        }
    }

    fun resetTest(){
        exceptionText.value = ""
        parentLaunchText.value = ""
        nestedLaunchText.value = ""
        number = 1
    }

}
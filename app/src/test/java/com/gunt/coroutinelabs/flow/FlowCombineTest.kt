package com.gunt.coroutinelabs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowCombineTest {

    // 데이터가 완전히 짝을 이뤄야하는 경우 zip,  가장 최신의 데이터가 필요한 경우 combine을 사용

    @Test
    fun flowWithZip() = runBlocking {
        // zip은 양쪽의 데이터를 한꺼번에 묶어 새로운 데이터 생성
        // print :
        // 1은(는) 일
        // 2은(는) 이
        // 3은(는) 삼
        val nums = (1..3).asFlow()
        val strs = flowOf("일", "이", "삼")
        nums.zip(strs) { a, b -> "${a}은(는) $b" }
            .collect { println(it) }
    }

    @Test
    fun flowWithCombine() = runBlocking {
        // combine은 양쪽의 데이터를 같은 시점에 묶지 않고 한 쪽이 갱신되면 새로 묶어 데이터 생성
        // print :
        // 1은(는) 일
        // 2은(는) 일
        // 3은(는) 일
        // 3은(는) 이
        // 3은(는) 삼
        val nums = (1..3).asFlow().onEach { delay(100L) }
        val strs = flowOf("일", "이", "삼").onEach { delay(200L) }
        nums.combine(strs) { a, b -> "${a}은(는) $b" }
            .collectLatest {
                println(it)
            }
    }
}
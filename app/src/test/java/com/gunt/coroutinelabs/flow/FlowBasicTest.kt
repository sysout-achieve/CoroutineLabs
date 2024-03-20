package com.gunt.coroutinelabs.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class FlowBasicTest {

    //들어오는 데이터의 유형이 어떤 데이터인지를 확인하고 어떤걸 써야할지 고민해봐야함
    // buffer, conflate, collectLatest
    private fun emitDelayFlow(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    @Test
    fun collectWithDelayTest() = runBlocking {
        // 하나의 flow당 400ms -> 최소 1200ms가 소요됨
        val time = measureTimeMillis {
            emitDelayFlow().collect { value ->
                delay(300)
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    @Test
    fun collectWithBufferDelayTest() = runBlocking {
        // emitDelayFlow를 미리 소요시킨 buffer를 추가해 upstream이 기다리지 않고 먼저 값을 방출할 수 있음
        // 최소 1000ms가 소요됨
        val time = measureTimeMillis {
            emitDelayFlow().buffer().collect { value ->
                delay(300)
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    @Test
    fun collectWithConflateDelayTest() = runBlocking {
        // 처리보다 빨리 발생한 데이터의 중간 값들을 누락
        // 최소 700ms가 소요됨
        val time = measureTimeMillis {
            emitDelayFlow().conflate().collect { value ->
                delay(300)
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    @Test
    fun collectLatestWithDelayTest() = runBlocking {
        // 수집 측이 느릴 경우 새로운 데이터가 있을 때 수집 측을 종료시키고 새로 시작하는 방법
        // 최소 700ms가 소요됨
        val time = measureTimeMillis {
            emitDelayFlow().collectLatest { value ->
                delay(300)
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    @Test
    fun test() {
        val time1 = measureTimeMillis { collectWithConflateDelayTest() }
        val time2 = measureTimeMillis { collectLatestWithDelayTest() }
        println("Collected in time1 : $time1 ms")
        println("Collected in time2 : $time2 ms")
    }
}
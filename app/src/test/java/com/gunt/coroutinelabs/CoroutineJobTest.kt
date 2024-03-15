package com.gunt.coroutinelabs

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutineJobTest {

    @Test
    fun cancelChildJob() = runBlocking {
        val job = launch {
            // 코루틴 Job을 생성하여 만든 경우 부모코루틴과 별개의 코루틴으로 인식
            // job.cancelAndJoin() 을 실행해도 Job을 생성하여 만든 코루틴은 cancel되지 않음
            launch(Job()) {
                println(coroutineContext[Job])
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            }

            launch {
                println(coroutineContext[Job])
                println("launch2: ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            }
        }

        delay(500L)
        job.cancelAndJoin()
        delay(1000L) // <- delay 제거하면 3! 호출되지 않음 (자식 코루틴이 아님) 기존 코루틴이 기다려주지 않고 코루틴을 종료함
    }
}
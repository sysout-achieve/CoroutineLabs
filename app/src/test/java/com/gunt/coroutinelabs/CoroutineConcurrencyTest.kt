package com.gunt.coroutinelabs

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutineConcurrencyTest {

    // 예상 결과값 ->  100,000, 그러나 더 작은 값이 나옴
    // 값을 변경하는 동안 다른 쓰레드에서도 값을 변경하면서 같은 값을 증가시키는 경우가 발생
    // @Volatile 을 사용해도 마찬가지
    private var counter = 0
    // AtomicInteger를 사용할 경우 100,000 가 보장, 하지만 늘 AtomicInteger가 정답 아님
    // private var counter = AtomicInteger(0)

    @Test
    fun concurrencyTest() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
                // counter.incrementAndGet()
            }
        }
        println("Counter = $counter")
    }

    private suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100 // 시작할 코루틴의 갯수
        val k = 1000 // 코루틴 내에서 반복할 횟수
        val elapsed = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("$elapsed ms동안 ${n * k}개의 액션을 수행했습니다.")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val counterContext = newSingleThreadContext("CounterContext")

    @Test
    fun concurrencyTestWithSingleThreadContext() = runBlocking {
        // 하나의 쓰레드에서만 실행되도록 함
        // 결과값은 100,000이 보장됨
        withContext(counterContext) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }

    private val mutex = Mutex()

    @Test
    fun concurrencyTestWithMutex() = runBlocking {
        // 하나의 쓰레드에서만 실행되도록 함
        // 결과값은 100,000이 보장됨
        withContext(Dispatchers.Default) {
            massiveRun {
                mutex.withLock {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }
}
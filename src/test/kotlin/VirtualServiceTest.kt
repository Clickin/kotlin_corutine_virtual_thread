import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlin.time.measureTime
import kotlin.time.toDuration

class VirtualServiceTest {
    val virtualService = VirtualService()
    @Test
    fun `virtual thread async`() = runTest {
        val time = measureTime {
            val task1 = asyncVirtual { virtualService.blockSecond(10) }
            val task2 = asyncVirtual { virtualService.blockSecond(20) }
            val task3 = asyncVirtual { virtualService.blockSecond(5) }
            println("[${Thread.currentThread().name}] current thread is blocking? ${Thread.currentThread().state}")
            val sum = task1.await() + task2.await() + task3.await()
            assertEquals(35, sum)
            println("[${Thread.currentThread().name}] count sum: $sum")
        }
        println("[${Thread.currentThread().name}] virtual thread total time: $time")
        assertTrue(time < (21).toDuration(DurationUnit.SECONDS))
    }
}
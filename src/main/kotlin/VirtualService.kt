import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import java.util.concurrent.Executors

val Dispatchers.LOOM: CoroutineDispatcher get() = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-").factory()).asCoroutineDispatcher()
fun <T> CoroutineScope.asyncVirtual(block: suspend CoroutineScope.() -> T) =
    async(context = Dispatchers.LOOM, block = block)

class VirtualService {
    fun blockSecond(count: Int): Int {
        println("[${Thread.currentThread().name}] start blocking $count seconds")
        Thread.sleep(1000L * count)
        println("[${Thread.currentThread().name}] end blocking $count seconds")
        return count
    }
}

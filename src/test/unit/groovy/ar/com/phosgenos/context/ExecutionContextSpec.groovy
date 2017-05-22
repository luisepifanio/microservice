package ar.com.phosgenos.context

import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutionContextSpec extends Specification {

    static final UUID_KEY = 'uuid_key'

    class FirstLevelWorker implements Runnable {
        private final String name
        private final CountDownLatch latch

        FirstLevelWorker(String _name, CountDownLatch _latch) {
            this.name = _name
            this.latch = _latch
        }

        @Override
        void run() {
            ExecutionContext.put(UUID_KEY, UUID.randomUUID().toString())
            println(name + 'has id' + ExecutionContext.currentContext.get(UUID_KEY))
            SecondLevelWorker subWorker = new SecondLevelWorker("sub" + name, ExecutionContext.get(UUID_KEY) as String)
            Thread subWorkerThread = new Thread(subWorker)
            subWorkerThread.start()
            try {
                subWorkerThread.join()
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt()
            }
            ExecutionContext.currentContext.finalize()
            latch.countDown()
        }
    }

    class SecondLevelWorker implements Runnable {
        private final String name
        private final String parentuuid

        SecondLevelWorker(String name, String parentuuid) {
            this.name = name
            this.parentuuid = parentuuid
        }

        @Override
        void run() {
            assert parentuuid == ExecutionContext.get(UUID_KEY)
        }
    }

    def "multithread"() {
        setup:
        List<String> names = ['worker1', 'worker2']
        CountDownLatch latch = new CountDownLatch(names.size())
        ExecutorService taskExecutor = Executors.newFixedThreadPool(4)

        when:
        names.each { taskExecutor.execute(new FirstLevelWorker(it, latch)) }

        try {
            latch.await()
        } catch (InterruptedException e) {
            // handle
        }

        then:
        noExceptionThrown()

    }
}

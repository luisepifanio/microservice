package ar.com.phosgenos.context

import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutionContextSpec extends Specification {

    static final UUID_KEY = 'uuid_key'

    class Worker implements Runnable {
        private final String name
        private final CountDownLatch latch

        Worker(String _name, CountDownLatch _latch) {
            this.name = _name
            this.latch = _latch
        }

        @Override
        void run() {
            println([Thread.currentThread().name, Thread.currentThread().id, name].join('##'))
            ExecutionContext.put(UUID_KEY, UUID.randomUUID().toString())
            println(name + " after start - " + ExecutionContext.currentContext.get(UUID_KEY))
            SubWorker subWorker = new SubWorker("sub" + name)
            Thread subWorkerThread = new Thread(subWorker)
            subWorkerThread.start()
            try {
                subWorkerThread.join()
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt()
            }
            ExecutionContext.currentContext.finalize()
            println(name + " after end - " + ExecutionContext.currentContext.get(UUID_KEY))
            latch.countDown()
        }
    }

    class SubWorker implements Runnable {
        private final String name

        SubWorker(String name) {
            this.name = name;
        }

        @Override
        void run() {
            println(name + " - " + ExecutionContext.get(UUID_KEY))
        }
    }

    def "multithread"() {
        setup:
        List<String> names = ['worker1', 'worker2']
        CountDownLatch latch = new CountDownLatch(names.size())
        ExecutorService taskExecutor = Executors.newFixedThreadPool(4)

        when:
        names.each { taskExecutor.execute(new Worker(it, latch)) }

        try {
            latch.await();
        } catch (InterruptedException e) {
            // handle
        }


        then:
        true

    }
}

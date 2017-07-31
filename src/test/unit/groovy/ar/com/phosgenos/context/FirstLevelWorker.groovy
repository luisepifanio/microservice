package ar.com.phosgenos.context

import static ar.com.phosgenos.context.ExecutionContextSpec.UUID_KEY

import java.util.concurrent.CountDownLatch

class FirstLevelWorker implements Runnable {
    private final String name
    private final CountDownLatch latch
    private final Set<SecondLevelWorker> children

    FirstLevelWorker(String _name, CountDownLatch _latch) {
        this.name = _name
        this.latch = _latch
        children = []
    }

    @Override
    void run() {
        ExecutionContext.put(UUID_KEY, UUID.randomUUID().toString())
        println(name + ' has id' + ExecutionContext.get(UUID_KEY))
        SecondLevelWorker subWorker = new SecondLevelWorker("sub" + name, ExecutionContext.get(UUID_KEY) as String)
        children << subWorker
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

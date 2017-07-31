package ar.com.phosgenos.context

import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutionContextSpec extends Specification {

    static final UUID_KEY = 'uuid_key'

    def "multithread"() {
        setup:
        List<String> names = (1..8).collect { "worker$it" }
        CountDownLatch latch = new CountDownLatch(names.size())
        ExecutorService taskExecutor = Executors.newFixedThreadPool(2)
        Collection<FirstLevelWorker> workers = []

        when:
        workers += names.collect { new FirstLevelWorker(it, latch) }
                .each { taskExecutor.execute(it) }

        try {
            latch.await()
        } catch (InterruptedException e) {
            // handle
        }

        then:
        noExceptionThrown()

    }

    @Ignore
    def "consistency"() {
        setup:
        UUID uid = UUID.randomUUID()
        String identifier = uid.toString()

        expect:
        ExecutionContext.put(identifier, UUID) == ExecutionContext.get(identifier)
    }


}

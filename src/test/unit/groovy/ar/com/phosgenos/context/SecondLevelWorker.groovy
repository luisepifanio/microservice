package ar.com.phosgenos.context

import static ar.com.phosgenos.context.ExecutionContextSpec.UUID_KEY

class SecondLevelWorker implements Runnable {
    private final String name
    private final String parentuuid

    SecondLevelWorker(String name, String parentuuid) {
        this.name = name
        this.parentuuid = parentuuid
    }

    @Override
    void run() {
        ExecutionContext.printExecutionContext()
        println( ExecutionContext.get(UUID_KEY) )
        assert parentuuid == ExecutionContext.get(UUID_KEY)
    }
}

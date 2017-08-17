package ar.com.phosgenos.context

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.SerializationUtils

@Slf4j
class ExecutionContext {

    protected final Context context

    private static
    final InheritableThreadLocal<ExecutionContext> sharedContext = new InheritableThreadLocal<ExecutionContext>() {
        @Override
        protected ExecutionContext initialValue() {
            return new ExecutionContext()
        }

        @Override
        protected ExecutionContext childValue(ExecutionContext executionContext) {
            // This process probably is not most reliable but tries to isolate
            // thread execution context
            Map<Serializable, ContextItem> repoCopy = executionContext.context.repository.collectEntries {
                final Serializable key, final ContextItem value ->
                    final Serializable newKey = SerializationUtils.clone(key)
                    ContextItem item = new ContextItem.ContextItemBuilder()
                            .id(newKey)
                            .data((value.data instanceof Serializable) ? SerializationUtils.clone(value.data) : value.data)
                            .build()
                    [(newKey): item]
            }

            ExecutionContext childContext = new ExecutionContext(new Context(repoCopy))

            // log.info(['childValue:', childContext.toString()].join('\n'))

            return childContext
        }
    }

    private ExecutionContext(final Context _context) {
        this.context = _context ?: new Context()
    }

    private ExecutionContext() {
        this(new Context())
    }

    private ExecutionContext(ExecutionContext original) {
        if (!original) {
            this.context = new Context()
        } else {
            this.context = new Context(original.context)
        }
    }

    static ExecutionContext getCurrentContext() {
        if (sharedContext.get() == null) {
            log.warn('Execution should not be injected ')
            sharedContext.set(new ExecutionContext())
        }
        return sharedContext.get()
    }

    static void finalize() {
        sharedContext.remove()
    }

    static <T> T put(final String key, final T payload) {
        return currentContext.context.putContextValue(key, payload)
    }

    static <T> T get(final String key, final T payload) {
        currentContext.context.getContextValue(key, payload)
    }

    static <T> T get(final String key) {
        return currentContext.context.getContextValue(key)
    }

    static <T> T remove(final String key) {
        currentContext.context.removeContextValue(key)
    }

    static Set<String> contextKeys() {
        return currentContext.context.repository.keySet()
    }

    static void printExecutionContext() {
        StringBuilder result = new StringBuilder()
        result << '\n' << 'EXECUTION CONTEXT ' << ':\n'
        result << 'For Thread ' << Thread.currentThread().getName() << ':\n'
        result << currentContext.context.toString()
        log.info(result.toString())
    }

    static void cleanup() {
        log.info 'Clearing ExecutionContext!'
        sharedContext.remove()
    }

    @Override
    public String toString() {
        return context.toString()
    }
}

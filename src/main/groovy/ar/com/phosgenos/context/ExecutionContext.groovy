package ar.com.phosgenos.context

import groovy.util.logging.Slf4j
import org.apache.commons.lang3.SerializationUtils

@Slf4j
class ExecutionContext {

    final Context context

    private static
    final InheritableThreadLocal<ExecutionContext> sharedContext = new InheritableThreadLocal<ExecutionContext>() {
        @Override
        protected ExecutionContext initialValue() {
            return new ExecutionContext()
        }

        @Override
        protected ExecutionContext childValue(ExecutionContext executionContext) {

            Map<Serializable, ContextItem> repoCopy = executionContext.context.repository.collectEntries {
                final Serializable key, final ContextItem value ->
                    final Serializable newKey = SerializationUtils.clone(key)
                    ContextItem item = new ContextItem(SerializationUtils.clone(key), (value instanceof Serializable) ? SerializationUtils.clone(value) : value)
                    [(newKey): item]
            }

            ExecutionContext childContext = new ExecutionContext(new Context(repoCopy))
            return super.childValue(childContext)
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

    static <T> T put(final String key, final T payload) {
        getCurrentContext().context.putContextValue(key, payload)
    }

    static <T> T readOnWrite(final String key, final T payload) {
        getCurrentContext().context.getContextValue(key, payload)
    }

    static <T> T get(final String key) {
        getCurrentContext().context.getContextValue(key)
    }

    static <T> T remove(final String key) {
        getCurrentContext().context.removeContextValue(key)
    }

    static Set<String> contextKeys() {
        return getCurrentContext().context.repository.keySet()
    }

    void printContextToDebug() {
        StringBuilder result = new StringBuilder()
        result << 'For Thread ' << Thread.currentThread().getName() << ':\n'
        contextKeys().each { String key ->
            Object val = getCurrentContext().context.getContextValue(key)
            result << "${key}->${val}\n"
        }
        log.info(result.toString())
    }

    static void cleanup() {
        log.info 'Clearing ExecutionContext!'
        getCurrentContext().contextStorage.clear()
        sharedContext.remove()
    }

}

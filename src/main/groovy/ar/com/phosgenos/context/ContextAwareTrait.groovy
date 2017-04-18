package ar.com.phosgenos.context

trait ContextAwareTrait implements ContextAware{

    @Override
    Context retrieveContext() {
        ExecutionContext.currentContext.context
    }

    @Override
    void injectContext(Context _context) {
        ExecutionContext.currentContext.context.extend(_context)
    }
}
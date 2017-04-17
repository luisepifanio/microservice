package ar.com.phosgenos.context;


public interface ContextAware {
    Context retrieveContext();
    void injectContext(Context _context);
}

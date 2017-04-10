package ar.com.phosgenos.util


class Functions {
    static <R> R swallow(Closure<R> closure) {
        try {
            return closure.call()
        } catch (all) {  // Groovy shortcut: we can omit the Exception class
            return null
        }
    }
}

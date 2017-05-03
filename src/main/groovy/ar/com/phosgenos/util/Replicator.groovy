package ar.com.phosgenos.util

import ar.com.phosgenos.functional.ChoiceHandler
import ar.com.phosgenos.functional.Either
import ar.com.phosgenos.functional.Try
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.SerializationException

import java.lang.reflect.Field

import static org.apache.commons.lang3.SerializationUtils.clone

@Slf4j
class Replicator {

    static <T> T replicate(T instance) {
        if (null == instance) {
            return null
        } else if (isCollection(instance)) {
            return (T) collectionClone(instance)
        }
        return singularObjectClone(instance)
    }

    protected static boolean isCollection(Object value) {
        if (value == null) {
            return false
        }
        [Collection, Map].any { it.isAssignableFrom(value.getClass()) }
    }

    protected static Map collectionClone(Map _instance) {
        log.debug('collectionClone map version')
        _instance.collectEntries { key, value ->
            def newKey = replicate(key)
            def newValue = replicate(value)
            [(newKey): newValue]
        }
    }

    protected static Set collectionClone(Set _instance) {
        log.debug('collectionClone set version')
        _instance.collect {
            replicate(it)
        }.toSet()
    }

    protected static Collection collectionClone(Collection _instance) {
        log.debug('collectionClone set version')
        _instance.collect {
            replicate(it)
        }
    }

    protected static Object collectionClone(Object _instance) {
        log.debug('collectionClone default null handling version')
        return _instance
    }

    protected static <T> T singularObjectClone(T instance) {
        if (null == instance) {
            return null
        }

        if (instance.metaClass.respondsTo('asBuilder')) {// For current implementation we use native posibilities
            return (T) instance.asBuilder().build()
        } else if (instance in Serializable /* Could improve options here? */) {
            Either<Serializable, SerializationException> choice = Try.catching(SerializationException) {
                clone(instance as Serializable)
            }
            return (T) ChoiceHandler.on(choice)
                    .onExpected { Serializable serializable ->
                return serializable
            }.onAlternative { SerializationException exc ->
                return recursiveWithInstantiation(instance)
            }.fold()
        } else {
            return recursiveWithInstantiation(instance)
        }
    }

    protected static <T> T recursiveWithInstantiation(T instance) {
        Class<T> clazz = this.getClass()
        T clone = (T) clazz.newInstance()

        instance.metaClass.properties
                .findAll { 'class' != it.name }
                .each { Field it ->
            String propName = it.name
            def value = instance[propName]
            // println "${it.name} -> ".toString() + value.toString() + "-> ${ value.getClass() }"
            if (isCollection(value)) {
                clone[propName] = collectionClone(value)
            } else {
                clone[propName] = singularObjectClone(value)
            }
        }
        clone
    }

}

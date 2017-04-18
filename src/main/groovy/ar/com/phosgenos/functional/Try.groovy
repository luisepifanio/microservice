package ar.com.phosgenos.functional

import groovy.util.logging.Slf4j

/**
 * @author luis
 * Date 13/04/17 17:54
 * Project: microservice
 */
@Slf4j
class Try {
    static <A,B extends Throwable> Either<A,B> catching(final Class<B> exceptionType, final Closure<A> code){
        Objects.requireNonNull(exceptionType ,'exceptionType could not be null')
        Objects.requireNonNull(code ,'code could not be null')

        try{
            return Either.expected( code.call() )
        }catch (B exc){
            if(exceptionType.isAssignableFrom(exc.getClass())){
                return Either.alternative(exc)
            }
            log.warn('Something did not go as expected', exc)
            throw  exc
        }
    }
}

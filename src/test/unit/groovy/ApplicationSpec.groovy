/*
 * This Spock specification was auto generated by running the Gradle 'init' task
 * by 'luis' at '21/01/17 20:51' with Gradle 3.1
 *
 * @author luis, @date 21/01/17 20:51
 */

import spock.lang.Specification
import application.Application

class ApplicationSpec extends Specification{

    //@Ignore
    def "someLibraryMethod returns true"() {
        setup:
        Application lib = new Application()
        when:
        def result = lib.someLibraryMethod()
        then:
        result == false
    }
}

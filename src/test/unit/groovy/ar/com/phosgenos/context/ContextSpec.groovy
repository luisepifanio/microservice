package ar.com.phosgenos.context

import groovy.test.GroovyAssert
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.core.IsNull.notNullValue
import static org.junit.Assert.assertThat

class ContextSpec extends Specification {

    Context context
    Node<String> node

    void setup() {
        node = problematicNode()

        Map<Serializable, Object> model = [:]
        model.node = node
        model.key = 'value'
        model.put(1, 2G)

        and: 'setup unit'
        context = new Context()
        model.each { key, value ->
            context.putContextValue(key, value)
        }
    }

    def "Clear"() {
        setup:

        when:
        context.clear()

        then:
        // For this cases is difficult
        assertThat(context.repository, is(notNullValue()))
        // Simpler with groovy
        null != context.repository
        context.repository.isEmpty()

    }

    def "Get"() {
        setup:

        when:
        ContextItem<Node<String>> entry = context.get('node')

        then:
        'node' == entry.id
        node.is(entry.data)

        and: 'Same instance get delivered every call'
        when:
        ContextItem<Node<String>> entry2 = context.get('node')
        then:
        'node' == entry2.id
        entry2.is(entry)

    }

    @Unroll
    def "Put: #title"() {

        setup: 'Reference to throwable'
        Throwable th = null
        and:
        ContextItem<BigDecimal> result

        when:


        if (exceptionType) {
            th = GroovyAssert.shouldFail exceptionType, {
                result = context.put(input)
            }
        } else {
            result = context.put(input)
        }


        then:
        expected == result
        !result || context.repository.size() == 3

        where:
        title             | exceptionType         | input                                        | expected
        'Null input'      | NullPointerException  | null                                         | null
        'Null input data' | NullPointerException  | new ContextItem<BigDecimal>('k', null)       | null
        'Null input key'  | IllegalStateException | new ContextItem<BigDecimal>(null, 123.456G)  | null
        'normal'          | null                  | new ContextItem<BigDecimal>('key', 123.456G) | new ContextItem<BigDecimal>('key', 123.456G)

    }

    @Unroll
    def "PutContextValue: #title"() {
        setup: 'Reference to throwable'
        Throwable th = null
        and:
        def result

        when:


        if (exceptionType) {
            th = GroovyAssert.shouldFail exceptionType, {
                result = context.putContextValue(key, value)
            }
        } else {
            result = context.putContextValue(key, value)
        }


        then:
        expected == result
        !result || context.repository.size() == 3

        where:
        title          | exceptionType         | key   | value  | expected
        'null'         | NullPointerException  | null  | null   | null
        'null key'     | IllegalStateException | null  | 123    | null
        'null value'   | NullPointerException  | 12.7G | null   | null
        'override key' | null                  | 'key' | 'osom' | new String('osom')

    }

    def "GetContextValue simple"() {

    }

    def "RemoveContextValue"() {

    }

    def "GetContextValue1"() {

    }

    def "Extend"() {

    }

    def "ExtendACopy"() {

    }

    /// Tooling
    private Node<String> problematicNode() {
        Node<String> node = Node.builder()
                .data('Demo')
                .children([
                Node.builder()
                        .data('child 1')
                        .build()
        ])
                .build()
        node.parent = node
        node
    }
}

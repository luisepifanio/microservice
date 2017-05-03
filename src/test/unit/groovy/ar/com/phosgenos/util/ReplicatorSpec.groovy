package ar.com.phosgenos.util

import ar.com.phosgenos.util.support.SerializableNode
import spock.lang.Specification


class ReplicatorSpec extends Specification {
    def "Replicate"() {

        //Given
        SerializableNode node = SerializableNode.builder()
            .id(UUID.randomUUID().toString())
            .children([])
        //When
        //Then

    }

    def "IsCollection"() {

    }

    def "CollectionClone"() {

    }

    def "CollectionClone1"() {

    }

    def "CollectionClone2"() {

    }

    def "CollectionClone3"() {

    }

    def "SingularObjectClone"() {

    }

    def "RecursiveWithInstantiation"() {

    }
}

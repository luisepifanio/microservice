package ar.com.phosgenos.util.support

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder


@Builder
@EqualsAndHashCode(includes = ['id'])
class SerializableNode implements Serializable {
    String id
    SerializableNode parent
    Collection<SerializableNode> children

    void setParent(SerializableNode parent) {
        if(this == parent){
            throw new IllegalStateException('Hey that\'s sick being my own parent')
        }
        this.parent = parent
    }

    void setChildren(Collection<SerializableNode> children) {
        this.children = children
        this.children.each {
            it.parent = this
        }
    }

    def asBuilder() {
        Set<SerializableNode> nodes = [].toSet()
        nodes += children ?: []

        builder()
                .id(id)
                .parent(parent)
                .children(nodes)
    }
}

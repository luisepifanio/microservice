package ar.com.phosgenos.context

import groovy.transform.builder.Builder

@Builder
class Node<T> {
    T data
    Node<T> parent
    Collection<Node<T>> children = []

    Node<T> addChild(Node<T> _child) {
        if (_child) {
            children << _child
            _child.@parent = this
        }
        this

    }

    void setParent(Node<T> _parent) {
        if (_parent) {
            _parent.addChild(this)
        }
    }

    void setChildren(Collection<Node<T>> _children) {
        this.children.clear()
        _children?.each { addChild(it) }
    }


    def asBuilder() {

        Collection<Node<T>> _c = new ArrayList<>()

        children && (_c += children as Set<Node<T>>)
        builder()
                .data(data)
                .parent(parent)
                .children(_c)

    }
}

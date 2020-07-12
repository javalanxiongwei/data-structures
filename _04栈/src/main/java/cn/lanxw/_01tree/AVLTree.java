package cn.lanxw._01tree;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class AVLTree<E> extends BST<E> {
    public AVLTree() {
        this(null);
    }
    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }
    @Override
    protected void afterAdd(Node<E> node) {

    }
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }
    private static class AVLNode<E> extends Node<E> {
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }
}

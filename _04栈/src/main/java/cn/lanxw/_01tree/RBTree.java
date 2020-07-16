package cn.lanxw._01tree;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class RBTree<E> extends BBST<E> {
    public static final boolean RED = Boolean.TRUE;
    public static final boolean BLACK = Boolean.FALSE;
    public RBTree() {
        this(null);
    }
    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        if(parent==null){
            //如果添加的是根节点，染成黑色
            color(node,BLACK);
            return;
        }
        if(isBlack(parent)){
            return;
        }
        Node<E> grand = node.parent.parent;
        Node<E> uncle = node.parent.sibling();
        grand = red(grand);
        if(isRed(uncle)){
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        if(parent.isLeftChild()){//L
            if(node.isLeftChild()){//LL
                black(parent);
            }else{//LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }else{//R
            if(node.isLeftChild()){//RL
                black(node);
                rotateRight(parent);
            }else{//RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>)node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }
    private boolean colorOf(Node<E> node){
        return node == null?BLACK:((RBNode)node).color;
    }
    private boolean isBlack(Node<E> node){
        return colorOf(node)==BLACK;
    }
    private boolean isRed(Node<E> node){
        return colorOf(node)==RED;
    }
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }
    private static class RBNode<E> extends Node<E> {
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }
}

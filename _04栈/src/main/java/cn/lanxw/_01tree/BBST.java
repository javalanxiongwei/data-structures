package cn.lanxw._01tree;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class BBST<E> extends BST<E> {
    public BBST() {
        this(null);
    }
    public BBST(Comparator<E> comparator) {
        super(comparator);
    }
    //节点左旋转
    public void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }
    //节点右旋转
    public void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }
    //旋转之后的逻辑
    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        //维护节点的父节点
        if (child != null) {
            child.parent = grand;
        }

        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }
        parent.parent = grand.parent;
        grand.parent = parent;
        updateHeight(grand);
        updateHeight(parent);
    }
    //更新节点高度
    protected void updateHeight(Node<E> node) { }
}

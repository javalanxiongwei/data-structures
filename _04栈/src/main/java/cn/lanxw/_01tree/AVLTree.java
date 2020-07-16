package cn.lanxw._01tree;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class AVLTree<E> extends BBST<E> {
    public AVLTree() {
        this(null);
    }
    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }
    //添加之后需要判断是否平衡
    @Override
    protected void afterAdd(Node<E> node) {
        while((node = node.parent)!=null){
            if(isBalance(node)){
                updateHeight(node);
            }else{
                reBalance(node);
            }
        }
    }
    //恢复平衡
    private void reBalance(Node<E> grand) {
        Node<E> parent = ((AVLNode)grand).tallerChild();
        Node<E> node = ((AVLNode)parent).tallerChild();
       if(parent.isLeftChild()){ //L
           if(node.isLeftChild()){//L
               //LL情况
               rotateRight(grand);
           }else{
               //LR情况
               rotateLeft(parent);
               rotateRight(grand);
           }
       }else{//R
           if(node.isLeftChild()){//L
               //RL情况
               rotateRight(parent);
               rotateLeft(grand);
           }else{
               //RR情况
               rotateLeft(grand);
           }
       }
    }
    //更新节点高度
    protected void updateHeight(Node<E> node) {
        ((AVLNode)node).updateHeight();
    }
    //判断是否平衡
    private boolean isBalance(Node<E> node) {
        return Math.abs(((AVLNode)node).loadFactor())<=1;
    }
    @Override
    protected void afterRemove(Node<E> node) {
        while((node = node.parent)!=null){
            if(isBalance(node)){
                updateHeight(node);
            }else{
                reBalance(node);
            }
        }
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
        public int loadFactor(){
            int leftHeight = left!=null?((AVLNode)left).height:0;
            int rightHeight = right!=null?((AVLNode)right).height:0;
            return leftHeight - rightHeight;
        }
        public void updateHeight(){
            int leftHeight = left!=null?((AVLNode)left).height:0;
            int rightHeight = right!=null?((AVLNode)right).height:0;
            this.height = 1+ Math.max(leftHeight,rightHeight);
        }
        public Node<E> tallerChild() {
            int leftHeight = left!=null?((AVLNode)left).height:0;
            int rightHeight = right!=null?((AVLNode)right).height:0;
            if(leftHeight>rightHeight){
                return left;
            }else if (leftHeight<rightHeight){
                return right;
            }else{
                return isLeftChild()?left:right;
            }
        }
    }
}

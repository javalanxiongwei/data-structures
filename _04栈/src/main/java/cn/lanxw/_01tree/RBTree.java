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
        //如果是往黑色节点添加，不需要做任何的处理
        if(isBlack(parent)){
            return;
        }
        //找到祖父节点
        Node<E> grand = node.parent.parent;
        //找到叔父阶段
        Node<E> uncle = node.parent.sibling();
        //把祖父节点染红
        grand = red(grand);
        if(isRed(uncle)){
            //这种情况红黑红的情况，添加之后会导致上溢
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        //剩下的情况就是(黑红,红黑)的情况，进行旋转染色
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

    @Override
    protected void afterRemove(Node<E> node) {
        //判断删除/被替代的节点为红色的情况,如果是删除的节点是红色不用管(执行染黑动作也没关系)，如果被替代节点是红色的情况，需要把节点染黑
        if(isRed(node)){
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        //如果删除的是根节点，不做任何处理
        if(parent==null) return;
        //剩下的情况就是删除单个黑色节点，产生下溢的情况，需要分为删除节点是左节点还是右节点.
        //仔细分析是不存在删除黑色节点没有兄弟节点的情况(BTree的特点)
        //  如果父节点是红色有三种情况, 红<--黑-->红(会有四个分叉)   红>--黑  黑-->红(会有三个分叉)  通过这种情况分析，一定会有兄弟节点
        //  如果父节点是黑色(二叉树只有一个元素),那必须有两个分叉,所以肯定是有兄弟节点的
        //node.isLeftChild()是用于处理后面递归调用，下溢并不是真正删除元素的情况.
        boolean isLeft = parent.left==null || node.isLeftChild();
        Node<E> sibling = isLeft?parent.right:parent.left;
        if(isLeft){
            if(isRed(sibling)){
                //处理兄弟节点为红色的情况，需要转换一下，然后处理逻辑就和兄弟节点为黑色的情况一样了.
                /**
                 *          节点5(黑) --> 节点10（红）
                 *         /               /         \
                 *       节点4（黑）     节点8(黑)   节点11(黑)
                 *
                 *        把父节点染红，把兄弟节点染黑，对父节点进行左旋转,修改一下sibling
                 *
                 *         节点5(红) <-- 节点10（黑）
                 *           /       \         \
                 *       节点4（黑）节点8(黑)  节点11(黑)
                 */
                red(parent);
                black(sibling);
                rotateLeft(parent);
                sibling = parent.right;
            }
            //接着就需要判断黑兄弟是否有元素可以借的情况.
            if(isBlack(sibling.right) && isBlack(sibling.left)){
                //黑兄弟没有红色节点的情况
                //判断父节点是否为红色，红色说明父节点在BTree中还可以往下借，不会下溢，染色就可以了
                if(isRed(parent)){
                    //如果父节点是红色节点，此时不会产生下溢.
                    black(parent);
                    red(sibling);
                }else{
                    red(sibling);
                    //如果父节点是黑色节点，此时会产生下溢
                    afterRemove(parent);
                }
            }else{
                //黑兄弟最少有一个红色节点的情况
                if(isRed(sibling.left) && isBlack(sibling.right)){
                    //兄弟节点是黑--->红的情况,进行旋转，统一转换成RR的情况.
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                //兄弟节点继承父节点的颜色
                color(sibling,colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else{
            if(isRed(sibling)){
                //处理兄弟节点为红色的情况，需要转换一下，然后处理逻辑就和兄弟节点为黑色的情况一样了.
                /**
                 *          节点5(黑) --> 节点10（红）
                 *         /               /         \
                 *       节点4（黑）     节点8(黑)   节点11(黑)
                 *
                 *        把父节点染红，把兄弟节点染黑，对父节点进行左旋转,修改一下sibling
                 *
                 *         节点5(红) <-- 节点10（黑）
                 *           /       \         \
                 *       节点4（黑）节点8(黑)  节点11(黑)
                 */
                red(parent);
                black(sibling);
                rotateRight(parent);
                sibling = parent.left;
            }
            //接着就需要判断黑兄弟是否有元素可以借的情况.
           if(isBlack(sibling.left) && isBlack(sibling.right)){
               //黑兄弟没有红色节点的情况
               //判断父节点是否为红色，红色说明父节点在BTree中还可以往下借，不会下溢，染色就可以了
               if(isRed(parent)){
                   //如果父节点是红色节点，此时不会产生下溢.
                   black(parent);
                   red(sibling);
               }else{
                   red(sibling);
                   //如果父节点是黑色节点，此时会产生下溢
                   afterRemove(parent);
               }
           }else{
               //黑兄弟最少有一个红色节点的情况
               if(isRed(sibling.right) && isBlack(sibling.left)){
                   //兄弟节点是黑--->红的情况,进行旋转，统一转换成LL的情况.
                   rotateLeft(sibling);
                   sibling = parent.left;
               }
               //兄弟节点继承父节点的颜色
               color(sibling,colorOf(parent));
               black(sibling.left);
               black(parent);
               rotateRight(parent);
           }
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
    @Override
    public Object string(Object node) {
        return ((RBNode)node).color==RED?((RBNode) node).element+"_R":((RBNode) node).element;
    }
}

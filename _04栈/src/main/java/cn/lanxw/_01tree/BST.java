package cn.lanxw._01tree;

import cn.lanxw.printer.BinaryTreeInfo;

import java.util.*;

/**
 * Created by wolfcode-lanxw
 */
public class BST<E> extends BinaryTree<E> {
    private Comparator<E> comparator;
    public BST(){}
    public BST(Comparator<E> comparator){
        this.comparator = comparator;
    }
    public void add(E element) {
        elementNotNullCheck(element);
        if(root==null){
            root = createNode(element,null);
            afterAdd(root);
        }else{
            Node<E> node = root;
            int comp = 0;
            Node<E> parentNode = null;
            while(node!=null){
                comp = compare(element,node.element);
                parentNode = node;
                if(comp>0){
                    node = node.right;
                }else if (comp<0){
                    node = node.left;
                }else{
                    return;
                }
            }
            Node<E> newNode = createNode(element,parentNode);
            if(comp>0){
                parentNode.right = newNode;
            }else{
                parentNode.left = newNode;
            }
            afterAdd(newNode);
        }
        size++;
    }
    /**
     * 添加node之后的调整
     * @param node 新添加的节点
     */
    protected void afterAdd(Node<E> node) { }
    //比较逻辑
    private int compare(E e1, E e2) {
        if(comparator!=null){
            return comparator.compare(e1,e2);
        }
        return ((Comparable)e1).compareTo(e2);
    }
    //获取节点
    public Node<E> node(E element){
        if(element==null) return null;
        Node<E> node = root;
        int compare = 0;
        while(node!=null){
            compare = comparator.compare(element,node.element);
            if(compare ==0){
                return node;
            }else if(compare >0){
                node = node.right;
            }else{
                node = node.left;
            }
        }
        return node;
    }
    //删除元素
    public void remove(E element) {
        remove(node(element));
    }
    private void remove(Node<E> node) {
        if(node==null) return;
        //如果元素有两个节点，找到后继节点，替换内容.
        if(node.hasTwoChildren()){
            Node<E> successor = successor(node);
            node.element = successor.element;
            node = successor;
        }
        Node<E> replaceNode = node.left!=null?node.left:node.right;
        if(replaceNode!=null){
            replaceNode.parent = node.parent;
        }
        if(node.parent==null){
            root = replaceNode;
        }else if(node==node.parent.left){
            node.parent.left = replaceNode;
        }else{
            node.parent.right = replaceNode;
        }
        if(replaceNode==null){
            //删除度为0的元素
            afterRemove(node);
        }else{
            //删除度为1的元素
            afterRemove(replaceNode);
        }
       /* if(node.isLeaf()){
            //如果是叶子节点
            if(node.parent==null){
                root = null;
            }else if(node==node.parent.left){
                node.parent.left = null;
            }else{
                node.parent.right=null;
            }
        }else{
            //如果有一个节点.
            Node<E> replaceNode = node.left!=null?node.left:node.right;
            replaceNode.parent = node.parent;
            if(node.parent==null){
                root = replaceNode;
            }else if(node==node.parent.left){
                node.parent.left = replaceNode;
            }else{
                node.parent.right = replaceNode;
            }
        }*/
    }
    /**
     * 删除node之后的调整
     * @param node 删除的节点
     */
    protected void afterRemove(Node<E> node) { }
    //判断是否包含
    public boolean contains(E element) {
        return false;
    }
}

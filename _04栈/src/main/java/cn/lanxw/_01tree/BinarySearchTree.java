package cn.lanxw._01tree;

import cn.lanxw.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by wolfcode-lanxw
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size = 0;
    private Node<E> root = null;
    private Comparator<E> comparator;
    public BinarySearchTree(){}
    public BinarySearchTree(Comparator<E> comparator){
        this.comparator = comparator;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty() {
        return size==0;
    }
    public void clear() {
    }
    public void add(E element) {
        elementNotNullCheck(element);
        if(root==null){
            root = new Node(element,null);
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
            Node<E> newNode = new Node<E>(element,parentNode);
            if(comp>0){
                parentNode.right = newNode;
            }else{
                parentNode.left = newNode;
            }
        }
        size++;
    }

    private int compare(E e1, E e2) {
        if(comparator!=null){
            return comparator.compare(e1,e2);
        }
        return ((Comparable)e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
    public void remove(E element) {
    }
    public boolean contains(E element) {
        return false;
    }
    private void remove(Node<E> node) { }
    interface Visitor<E>{
        void visit(E element);
    }
    public static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        public Node(E element,Node<E> parent){
            this.element = element;
            this.parent = parent;
        }
    }
    @Override
    public Object root() {
        return root;
    }
    @Override
    public Object left(Object node) {
        return ((Node)node).left;
    }
    @Override
    public Object right(Object node) {
        return ((Node)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node)node).element;
    }
    public void preOrderTreeTraversal(Visitor visitor){
        preOrderTreeTraversal(root,visitor);
    }
    private void preOrderTreeTraversal(Node<E> node,Visitor visitor){
        if(node==null){
            return;
        }
        visitor.visit(node.element);
        preOrderTreeTraversal(node.left,visitor);
        preOrderTreeTraversal(node.right,visitor);
    }
    public void inOrderTreeTraversal(Visitor visitor){
        inOrderTreeTraversal(root,visitor);
    }
    private void inOrderTreeTraversal(Node<E> node,Visitor visitor){
        if(node==null){
            return;
        }
        preOrderTreeTraversal(node.left,visitor);
        visitor.visit(node.element);
        preOrderTreeTraversal(node.right,visitor);
    }
    public void postTreeTraversal(Visitor visitor){
        preOrderTreeTraversal(root,visitor);
    }
    private void postTreeTraversal(Node<E> node,Visitor visitor){
        if(node==null){
            return;
        }
        preOrderTreeTraversal(node.left,visitor);
        preOrderTreeTraversal(node.right,visitor);
        visitor.visit(node.element);
    }
    public void levelTreeTraversal(Visitor visitor){
        if(root==null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            visitor.visit(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
           if(node.right!=null){
               queue.offer(node.right);
           }
        }
    }
    public int height(){
        return height(root);
    }
    private int height(Node<E> node){
        if(node==null){
            return 0;
        }
        return 1+Math.max(height(node.left),height(node.right));
    }
    public int height1(){
        if(root==null) return 0;
        Queue<Node> queue = new LinkedList<>();
        int nodeSize = 1;
        int height = 0;
        queue.offer(root);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            nodeSize--;
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
            if(nodeSize==0){
                nodeSize = queue.size();
                height++;
            }
        }
        return height;
    }
}

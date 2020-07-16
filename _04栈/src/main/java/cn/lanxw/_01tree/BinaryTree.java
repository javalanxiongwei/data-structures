package cn.lanxw._01tree;

import cn.lanxw.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by wolfcode-lanxw
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;
    protected static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        public Node(E element,Node<E> parent){
            this.element = element;
            this.parent = parent;
        }
        public boolean hasTwoChildren(){
            return left!=null && right!=null;
        }
        public boolean isLeaf(){
            return left==null&&right==null;
        }
        public boolean isLeftChild(){
            return parent!=null && this == parent.left;
        }
        public boolean isRightChild(){
            return parent!=null && this == parent.right;
        }
        public Node<E> sibling(){
            if(parent==null) return null;
            return isLeftChild()?parent.right:parent.left;
        }
    }
    public void clear() {
        root = null;
        size = 0;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    protected void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
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
    interface Visitor<E>{
        void visit(E element);
    }
    //前序遍历
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
    public void preOrderTreeTraversal2(Visitor visitor){
        Node node = root;
        if(node.isLeaf()) {
            visitor.visit(node.element);
            return;
        }
        LinkedList<Node> stack = new LinkedList();
        stack.push(node);
        do{
            node = stack.pop();
            visitor.visit(node.element);
            if(node.right!=null){
                stack.push(node.right);
            }
            if(node.left!=null){
                stack.push(node.left);
            }
        }while (!stack.isEmpty());
    }
    //中序遍历
    public void inOrderTreeTraversal(Visitor visitor){
        inOrderTreeTraversal(root,visitor);
    }
    private void inOrderTreeTraversal(Node<E> node,Visitor visitor){
        if(node==null){
            return;
        }
        inOrderTreeTraversal(node.left,visitor);
        visitor.visit(node.element);
        inOrderTreeTraversal(node.right,visitor);
    }
    public void inOrderTreeTraversal2(Visitor visitor){
        Node node = root;
        if(node.isLeaf()) {
            visitor.visit(node.element);
            return;
        }
        LinkedList<Node> stack = new LinkedList();
        stack.push(node);
        Node middleNode = null;
        do{
            node = node.left;
            if(node!=null){
                stack.push(node);
            }else{
                do{
                    node=null;
                    middleNode = stack.pop();
                    visitor.visit(middleNode.element);
                    if (middleNode.right != null) {
                        node = middleNode.right;
                        stack.push(node);
                    }
                }while(node==null&&!stack.isEmpty());
            }
        }while(!stack.isEmpty());
    }
    public void inOrderTreeTraversal3(Visitor visitor){
        Node node = root;
        LinkedList<Node> stack = new LinkedList();
        while(node!=null || !stack.isEmpty()){
            while (node!=null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            visitor.visit(node.element);
            node = node.right;
        }
    }
    //后序遍历
    public void postTreeTraversal(Visitor visitor){
        postTreeTraversal(root,visitor);
    }
    private void postTreeTraversal(Node<E> node,Visitor visitor){
        if(node==null){
            return;
        }
        postTreeTraversal(node.left,visitor);
        postTreeTraversal(node.right,visitor);
        visitor.visit(node.element);
    }
    public List<E> postTreeTraversal(){
        LinkedList<E> list = new LinkedList();
        LinkedList<Node> stack = new LinkedList<>();
        Node<E> node = root;
        stack.push(node);
        while (!stack.isEmpty()){
            node = stack.pop();
            list.addFirst(node.element);
            if(node.left!=null){
                stack.push(node.left);
            }
            if(node.right!=null){
                stack.push(node.right);
            }
        }
        return list;
    }
    public void postTreeTraversa2(Visitor visitor){
        Node node = root;
        if(node.isLeaf()) {
            visitor.visit(node.element);
            return;
        }
        LinkedList<Node> stack = new LinkedList();

    }
    //层序遍历
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
    //判断是否完全二叉树
    public boolean isCompleteBinaryTree(){
        LinkedList<Node> queue = new LinkedList<>();
        Node<E> node = root;
        queue.offer(node);
        boolean isLeaf = false;
        while(!queue.isEmpty()){
            node = queue.poll();
            if(isLeaf && !node.isLeaf()){
                return false;
            }
            if(node.hasTwoChildren()){
                queue.offer(node.left);
                queue.offer(node.right);
            }else if(node.left==null && node.right!=null){
                return false;
            }else{
                isLeaf = true;
                if(node.left!=null){
                    queue.offer(node.left);
                }
            }
        }
        return true;
    }

    //获取前驱节点
    public Node<E> precursor(Node<E> node){
        if(node==null) return null;
        if(node.left!=null){
            node = node.left;
            while(node.right!=null){
                node = node.right;
            }
            return node;
        }else{
            while(node.parent!=null){
                if(node == node.parent.right){
                    return node.parent;
                }
                node = node.parent;
            }
            return null;
        }
    }
    //获取后继节点
    public Node<E> successor(Node<E> node){
        if(node==null) return null;
        if(node.right!=null){
            node = node.right;
            while(node.left!=null){
                node = node.left;
            }
            return node;
        }else{
            while(node.parent!=null){
                if(node == node.parent.left){
                    return node.parent;
                }
                node = node.parent;
            }
            return null;
        }
    }
    //获取树的高度
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
    //创建节点
    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }
}

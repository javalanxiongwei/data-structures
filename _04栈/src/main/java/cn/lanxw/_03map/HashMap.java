package cn.lanxw._03map;

import cn.lanxw._00common.printer.BinaryTreeInfo;
import cn.lanxw._00common.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by wolfcode-lanxw
 */
public class HashMap<K,V> implements Map<K,V> {
    public static final boolean RED = Boolean.TRUE;
    public static final boolean BLACK = Boolean.FALSE;
    private Node<K,V>[] table;
    protected int size;
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    int threshold;
    public HashMap(){
        table = new Node[DEFAULT_INITIAL_CAPACITY];
        threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size==0;
    }
    @Override
    public void clear() {
        size=0;
        for(int i=0;i<table.length;i++){
            table[i] = null;
        }
    }
    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> node = table[index];
        if(node==null){
            node = new Node<>(key,value,null);
            table[index] = node;
        }else{
            int comp = 0;
            int h1 = hash(key);
            boolean searched = false;
            Node<K, V> result = null;
            Node<K, V> parent = null;
            while(node!=null){
                parent = node;
                if(h1>node.hash){
                    comp = 1;
                }else if(h1<node.hash){
                    comp = -1;
                }else if(key == node.key || (key!= null && key.equals(node.key))){
                        comp = 0;
                }else if(key!=null && node.key!=null
                        && key instanceof Comparable
                        && key.getClass()==node.key.getClass()
                        && (comp = ((Comparable)key).compareTo(node.key))!=0){
                    //不做任何事情
                }
                else if(searched){
                    comp = System.identityHashCode(key)-System.identityHashCode(node.key);
                }else{
                    if((node.left!=null && (result = find(node.left,key))!=null)
                            || (node.right!=null&& (result = find(node.right,key))!=null)){
                        comp = 0;
                        node = result;
                    }else{
                        searched = true;
                        comp = System.identityHashCode(key)-System.identityHashCode(node.key);
                    }
                }
                if(comp>0){
                    node = node.right;
                }else if (comp<0){
                    node = node.left;
                }else{
                    V oldVal = node.value;
                    node.key = key;
                    node.value = value;
                    return oldVal;
                }
            }
            node = createNode(key,value,parent);
            if(comp>0){
                parent.right = node;
            }else{
                parent.left = node;
            }
        }
        if(++size>threshold){
            resize();
        }
        afterPut(node);
        return null;
    }

    private void resize() {
        int oldCap = table.length;
        Node[] newTable = new Node[oldCap<<1];
        Node<K,V> e;
        for(int i = 0; i<table.length;i++){
            if((e=table[i])==null) continue;
            LinkedList<Node<K,V>> queue = new LinkedList();
            queue.offer(e);
            while(!queue.isEmpty()){
                e = queue.poll();
                if(e.left!=null){
                    queue.offer(e.left);
                }
                if(e.right!=null){
                    queue.offer(e.right);
                }
                moveNode(e,newTable);
            }
        }
        table = newTable;
        threshold = threshold<<1;
    }

    private void moveNode(Node<K, V> newNode, Node[] newTable) {
        int index;
        newNode.left = null;
        newNode.right = null;
        newNode.parent = null;
        newNode.color = RED;
        index= hash(newNode.key);
        Node node;
        if((node=newTable[index])==null){
            newTable[index] = newNode;
            afterPut(newNode);
            return;
        }
        Node<K, V> result = null;
        Node<K, V> parent = null;
        K key  = newNode.key;
        int h1 = newNode.hash;
        int comp = 0;
        while(node!=null){
            parent = node;
            if(h1>node.hash){
                comp = 1;
            }else if(h1<node.hash){
                comp = -1;
            }else if(key!=null && node.key!=null
                    && key instanceof Comparable
                    && key.getClass()==node.key.getClass()
                    && (comp = ((Comparable)key).compareTo(node.key))!=0){
                //不做任何事情
            }else{
                comp = System.identityHashCode(key)-System.identityHashCode(node.key);
            }
            if(comp>0){
                node = node.right;
            }else if (comp<0){
                node = node.left;
            }
        }
        newNode.parent = parent;
        if(comp>0){
            parent.right = newNode;
        }else{
            parent.left = newNode;
        }
        afterPut(newNode);
    }
    private Node<K, V> find(Node<K, V> node, K key) {
        int comp;
        int h1 = hash(key);
        boolean searched = false;
        Node<K, V> result = null;
        while(node!=null){
            if(h1>node.hash){
                node = node.right;
            }else if(h1<node.hash){
                node = node.left;
            }else if(key == node.key || (key!= null && key.equals(node.key))){
                return node;
            }else if(key!=null && node.key!=null
                    && key instanceof Comparable
                    && key.getClass()==node.key.getClass()
                    && (comp = ((Comparable)key).compareTo(node.key))!=0){
                //不做任何事情
                node = comp>0?node.right:node.left;
            }else if((node.right!=null && (result = find(node.right,key))!=null)){
                return result;
            }else{
                node = node.left;
            }
        }
        return null;
    }
    private int index(K key){
        int hashCode;
        return key==null?0:((hashCode= key.hashCode())^hashCode>>16)&(table.length-1);
    }
    private int index(Node<K,V> node){
        return node.hash^(table.length-1);
    }
    @Override
    public V get(K key) {
        Node<K,V> node = getNode(key);
        return node==null?null:node.value;
    }
    public Node getNode(K key){
        Node<K,V> node = table[index(key)];
        return node==null?null:find(node,key);
    }
    private int hash(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }
    @Override
    public V remove(K key) {
        Node<K,V> node = getNode(key);
        remove(node);
        return node.value;
    }

    private void remove(Node<K,V> node) {
        if(node==null) return;
        size--;
        //如果元素有两个节点，找到后继节点，替换内容.
        if(node.hasTwoChildren()){
            Node<K,V> successor = successor(node);
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }
        Node<K,V> replaceNode = node.left!=null?node.left:node.right;
        if(replaceNode!=null){
            replaceNode.parent = node.parent;
        }
        if(node.parent==null){
            table[node.hash&(table.length-1)] = replaceNode;
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
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key)!=null;
    }
    @Override
    public boolean containsValue(V value) {
        Node node;
        LinkedList<Node<K,V>> queue = new LinkedList<>();
        for(int i = 0;i<table.length;i++){
            if((node = table[i])==null) continue;
            queue.offer(node);
            while (!queue.isEmpty()){
                node = queue.poll();
                if (Objects.equals(value, node.value)) return true;
                if(node.left!=null){
                    queue.offer(node.left);
                }
                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }
    @Override
    public void traversal(Visitor<K, V> visitor) {

    }
    protected Node<K,V> createNode(K key,V value, Node<K,V> parent) {
        return new Node<>(key,value, parent);
    }
    protected void afterPut(Node<K,V> node) {
        Node<K,V> parent = node.parent;
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
        Node<K,V> grand = node.parent.parent;
        //找到叔父阶段
        Node<K,V> uncle = node.parent.sibling();
        //把祖父节点染红
        grand = red(grand);
        if(isRed(uncle)){
            //这种情况红黑红的情况，添加之后会导致上溢
            black(parent);
            black(uncle);
            afterPut(grand);
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
    protected void afterRemove(Node<K,V> node) {
        //判断删除/被替代的节点为红色的情况,如果是删除的节点是红色不用管(执行染黑动作也没关系)，如果被替代节点是红色的情况，需要把节点染黑
        if(isRed(node)){
            black(node);
            return;
        }
        Node<K,V> parent = node.parent;
        //如果删除的是根节点，不做任何处理
        if(parent==null) return;
        //剩下的情况就是删除单个黑色节点，产生下溢的情况，需要分为删除节点是左节点还是右节点.
        //仔细分析是不存在删除黑色节点没有兄弟节点的情况(BTree的特点)
        //  如果父节点是红色有三种情况, 红<--黑-->红(会有四个分叉)   红>--黑  黑-->红(会有三个分叉)  通过这种情况分析，一定会有兄弟节点
        //  如果父节点是黑色(二叉树只有一个元素),那必须有两个分叉,所以肯定是有兄弟节点的
        //node.isLeftChild()是用于处理后面递归调用，下溢并不是真正删除元素的情况.
        boolean isLeft = parent.left==null || node.isLeftChild();
        Node<K,V> sibling = isLeft?parent.right:parent.left;
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
    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }
    //节点左旋转
    public void rotateLeft(Node<K,V> grand) {
        Node<K,V> parent = grand.right;
        Node<K,V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }
    //节点右旋转
    public void rotateRight(Node<K,V> grand) {
        Node<K,V> parent = grand.left;
        Node<K,V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }
    //旋转之后的逻辑
    private void afterRotate(Node<K,V> grand, Node<K,V> parent, Node<K,V> child) {
        //维护节点的父节点
        if (child != null) {
            child.parent = grand;
        }

        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            table[grand.hash] = parent;
        }
        parent.parent = grand.parent;
        grand.parent = parent;
    }
    //获取后继节点
    public Node<K,V> successor(Node<K,V> node){
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
    protected void elementNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }
    private Node<K,V> color(Node<K,V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }
    private Node<K,V> red(Node<K,V> node) {
        return color(node, RED);
    }
    private Node<K,V> black(Node<K,V> node) {
        return color(node, BLACK);
    }
    private boolean colorOf(Node<K,V> node){
        return node == null?BLACK:node.color;
    }
    private boolean isBlack(Node<K,V> node){
        return colorOf(node)==BLACK;
    }
    private boolean isRed(Node<K,V> node){
        return colorOf(node)==RED;
    }
    protected static class Node<K,V>{
        K key;
        V value;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;
        int hash;
        boolean color = RED;
        public Node(K key,V value,Node<K,V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key==null?0:(key.hashCode()^key.hashCode()>>>16);
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
        public Node<K,V> sibling(){
            if(parent==null) return null;
            return isLeftChild()?parent.right:parent.left;
        }
        @Override
        public String toString() {
            return "Node("+(color==RED?"R":"B")+")_" + key + "_" + value;
        }
    }
    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }
                @Override
                public Object root() {
                    return root;
                }
                @Override
                public Object right(Object node) {
                    return ((Node<K, V>)node).right;
                }
                @Override
                public Object left(Object node) {
                    return ((Node<K, V>)node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }
}

package cn.lanxw._05trie;

import java.util.HashMap;

/**
 * Created by lanxw
 */
public class Trie<V> {
    private int size;
    private Node<V> root;
    public Trie(){
        root = new Node<>(null);
        root.children = new HashMap<>();
    }
    public int size() {
      return size;
    }
    public boolean isEmpty() {
        return size==0;
    }
    public void clear() {
        size = 0;
        root.children.clear();;
    }
    public V get(String key) {
        Node<V> node = node(key);
        return node==null?null:node.word?node.value:null;
    }
    public boolean contains(String key) {
        Node<V> node = node(key);
        return node!=null && node.word;
    }
    public V add(String key, V value) {
        keyCheck(key);
        Node<V> node = root;
        Node<V> childNode;
        Character c;
        HashMap<Character,Node<V>> childMap;
        for(int i = 0;i<key.length();i++){
            c = key.charAt(i);
            if((childMap=node.children)==null){
                childMap=node.children = new HashMap<>();
            }
            if(childMap.isEmpty() || (childNode = childMap.get(c))==null){
                childNode = new Node<>(node);
                childNode.character = c;
                node.children.put(c,childNode);
            }
            node = childNode;
        }
        if(node.word){
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        size++;
        node.word = true;
        node.value = value;
        return null;
    }
    public V remove(String key) {
        keyCheck(key);
        Node<V> node = node(key);
        if(node==null) return null;
        V oldValue = node.value;
        if(node.children!=null && node.children.size()>0){
            node.word = false;
            size--;
            return oldValue;
        }
        Node<V> currentNode  = null;
        do{
            currentNode = node;
            node = node.parent;
        }while(!node.word && node.children.size()==1 && node!=root);
        node.children.remove(currentNode.character);
        currentNode.parent = null;
        size--;
        return oldValue;
    }
    public boolean startsWith(String prefix) {
        Node<V> node = node(prefix);
        return node!=null;
    }
    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        Node<V> childNode;
        Character c;
        HashMap<Character,Node<V>> childMap;
        for(int i=0;i<key.length();i++){
            c = key.charAt(i);
            if((childMap=node.children)== null || childMap.isEmpty() || (childNode = childMap.get(c))==null){
                return null;
            }
            node = childNode;
        }
        return node;
    }
    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }
    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character;
        V value;
        boolean word; // 是否为单词的结尾（是否为一个完整的单词）
        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}

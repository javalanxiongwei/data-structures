package cn.lanxw._04heap;

import cn.lanxw._00common.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * Created by lanxw
 */
public class BinaryHeap<E> implements Heap<E>,BinaryTreeInfo {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private E[] elements;
    private Comparator<E> comparator;
    public BinaryHeap(){
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size ==0;
    }
    @Override
    public void clear() {
        if(size==0) return;
        for(int i=0;i<size;i++){
            elements[i] = null;
        }
        size=0;
    }

    /**
     * 添加元素
     * @param element
     */
    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        if(size==0){
            elements[size++] = element;
            return;
        }
        elements[size] = element;
        siftUp(size++);
    }

    /**
     * 上滤操作
     * @param index
     */
    private void siftUp(int index) {
        E element = elements[index];
        int parentIndex;
        E parent;
        while(index>0){
            parentIndex = (index-1) >> 1;
            parent = elements[parentIndex];
            if(comp(element,parent)<=0){
                break;
            }
            elements[index] = parent;
            index = parentIndex;
        }
        elements[index] = element;
    }

    /**
     * 比较操作
     * @param e1
     * @param e2
     * @return
     */
    private int comp(E e1, E e2) {
        return comparator==null?((Comparable)e1).compareTo(e2):comparator.compare(e1,e2);
    }

    /**
     * 检查元素是否为空
     * @param element
     */
    private void elementNotNullCheck(E element){
        if(element==null)
            throw new IllegalArgumentException("element must not be null");
    }

    /**
     * 获取堆顶元素
     * @return
     */
    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    /**
     * 删除堆顶元素
     * @return
     */
    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    /**
     * 进行下滤操作
     * @param index
     */
    private void siftDown(int index) {
        E element = elements[index];
        int half = size>>1;
        E child;
        int childIndex;
        int rightIndex;
        while(index<half){//说明肯定有子节点
            childIndex = (index << 1) + 1;//默认获取左节点索引
            child = elements[childIndex];
            rightIndex = childIndex+1;//获取右节点元素
            if(rightIndex<size && comp(elements[rightIndex],child)>0){
                //右节点更大些
                child = elements[childIndex = rightIndex];
            }
            if(comp(element,child)>=0){
                break;
            }
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = element;
    }

    /**
     * 删除堆顶元素，添加一个元素
     * @param element
     * @return
     */
    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E old = elements[0];
        elements[0] = element;
        if(size<=1){//元素只有0个或者1个的时候，不需要下滤.直接设置元素size=1就可以
            size=1;
        }else{//元素>1个，需要进行下滤
            siftDown(0);
        }
        return old;
    }

    /**
     * 判断堆是否为空
     */
    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }
    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int)node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int)node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int)node];
    }
}

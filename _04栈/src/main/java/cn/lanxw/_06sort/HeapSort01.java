package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class HeapSort01<T extends Comparable<T>> extends Sort<T> {
    private int size;
    @Override
    protected void sort() {
        size = array.length;
        heapify();
        T first,last;
        for(int i=array.length-1;i>0;i--){
            last = array[i];
            size--;
            first = replace(last);
            array[i] = first;
        }
    }
    private void heapify() {
        for(int i = (array.length>>1)-1;i>=0;i--){
            siftDown(i);
        }
    }
    private void siftDown(int index) {
        T element = array[index];
        int half = size>>1;
        T child;
        int childIndex;
        int rightIndex;
        while(index<half){//说明肯定有子节点
            childIndex = (index << 1) + 1;//默认获取左节点索引
            child = array[childIndex];
            rightIndex = childIndex+1;//获取右节点元素
            if(rightIndex<size && cmp(array[rightIndex],child)>0){
                //右节点更大些
                child = array[childIndex = rightIndex];
            }
            if(cmp(element,child)>=0){
                break;
            }
            array[index] = child;
            index = childIndex;
        }
        array[index] = element;
    }
    public T replace(T element) {
        T old = array[0];
        array[0] = element;
        if(size<=1){//元素只有0个或者1个的时候，不需要下滤.直接设置元素size=1就可以
            size=1;
        }else{//元素>1个，需要进行下滤
            siftDown(0);
        }
        return old;
    }
}

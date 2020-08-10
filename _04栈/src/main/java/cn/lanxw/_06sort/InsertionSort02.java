package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class InsertionSort02<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        int cur;
        T v;
        for(int begin=1;begin<array.length;begin++){
            cur = begin;
            v = array[cur];
            while(cur>0 && cmp(v,array[cur-1])<0){
                array[cur] = array[cur-1];
                cur--;
            }
            array[cur] = v;
        }
    }
}

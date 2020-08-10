package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class QuickSort <T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        sort(0,array.length);
    }
    private void sort(int begin,int end){
        if(end-begin<2) return;
        int pivotIndex = pivotIndex(begin,end);
        sort(begin,pivotIndex);
        sort(pivotIndex+1,end);
    }
    private int pivotIndex(int begin,int end){
        swap(begin, (int) (begin+Math.random()*(end-begin)));
        T v = array[begin];
        end--;
        while(begin<end){
            while(begin<end){
                if(cmp(array[end],v)>0){
                    end--;
                }else{
                    array[begin++] = array[end];
                    break;
                }
            }
            while(begin<end){
                if(cmp(array[begin],v)<0){
                    begin++;
                }else{
                    array[end--] = array[begin];
                    break;
                }
            }
        }
        array[begin] = v;
        return begin;
    }
}

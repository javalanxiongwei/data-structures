package cn.lanxw._06sort;

import java.util.Arrays;

/**
 * Created by lanxw
 */
public class MergeSort01 <T extends Comparable<T>> extends Sort<T>  {
    private T[] leftArray;
    @Override
    protected void sort() {
        leftArray = (T[]) new Comparable[array.length>>1];
        sort(0,array.length);
    }
    private void sort(int begin,int end){
        if(end-begin<2) return;
        int mid = (begin+end)>>1;
        sort(begin,mid);
        sort(mid,end);
        merge(begin,mid,end);
    }

    private void merge(int begin,int mid,int end) {
        for(int i=0;i<mid-begin;i++){
            leftArray[i] = array[begin+i];
        }
        int lb =0,le=mid-begin;
        int rb = mid,re=end;
        int ab = begin;
        while(lb<le){
            if(rb<re && cmp(leftArray[lb],array[rb])>0){
                array[ab++] = array[rb++];
            }else{
                array[ab++] = leftArray[lb++];
            }
        }
    }
}

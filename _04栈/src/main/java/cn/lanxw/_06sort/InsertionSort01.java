package cn.lanxw._06sort;

import cn.lanxw._06sort.Sort;

import java.util.Arrays;

/**
 * Created by lanxw
 */
public class InsertionSort01 extends Sort {
    @Override
    protected void sort() {
        int cur;
        for(int begin=1;begin<array.length;begin++){
            cur = begin;
            while(cur>0 && cmp(cur,cur-1)<0){
                swap(cur,cur-1);
                cur--;
            }
        }
    }
}

package cn.lanxw._06sort;

import cn.lanxw._06sort.tools.Integers;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lanxw
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        List<Integer> stepSequences = sedgewickStepSequence();
        for(Integer sequence:stepSequences){
            inserSort(sequence);
        }
    }
    protected void inserSort(int step) {
        int cur;
        for(int col=0;col<step;col++){
            for(int begin=col+step;begin<array.length;begin+=step){
                cur = begin;
                while(cur>col && cmp(cur,cur-step)<0){
                    swap(cur,cur-step);
                    cur-=step;
                }
            }
        }
    }
    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}

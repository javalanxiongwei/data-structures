package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class BubbleSort02<T> extends Sort {
    @Override
    protected void sort() {
        int maxIndex;
        for(int i = array.length-1;i>=0;i--){
            maxIndex=0;
            for(int j=0;j<i;j++){
                if(cmp(array[j],array[maxIndex])>0){
                    maxIndex = j;
                }
            }
            swap(maxIndex,i);
        }
    }
}

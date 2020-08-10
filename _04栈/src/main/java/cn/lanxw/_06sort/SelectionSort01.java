package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class SelectionSort01<T> extends Sort {
    @Override
    protected void sort() {
        int maxIndex;
        for(int i = array.length-1;i>=0;i--){
            maxIndex=i;
            for(int j=0;j<i;j++){
                if(cmp(array[j],array[maxIndex])>0){
                    maxIndex = j;
                }
            }
            if(maxIndex!=i)
                swap(maxIndex,i);
        }
    }
}

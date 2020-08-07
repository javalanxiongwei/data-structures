package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class BubbleSort extends Sort {
    @Override
    protected void sort() {
        for(int i = array.length-1;i>=0;i--){
            for(int j=0;j<i;j++){
                if(cmp(array[j],array[j+1])>0){
                    swap(j,j+1);
                }
            }
        }
    }
}

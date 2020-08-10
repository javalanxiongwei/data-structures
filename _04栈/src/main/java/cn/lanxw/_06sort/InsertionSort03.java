package cn.lanxw._06sort;

/**
 * Created by lanxw
 */
public class InsertionSort03<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        int index;
        for(int i=1;i<array.length;i++){
            insert(i,search(i));
        }
    }

    private void insert(int index, int searchIndex) {
        T v = array[index];
        for(int i=index;i>searchIndex;i--){
            array[i] = array[i-1];
        }
        array[searchIndex] = v;
    }

    public int search(int index){
        int start=0,end=0,mid = 0;
        end = index;
        T v = array[index];
        while(end>start){
            if(cmp(v,array[mid = (start+end)>>1])<0){
                 end = mid;
            }else {
                 start = mid + 1;
            }
        }
        return start;
    }
}

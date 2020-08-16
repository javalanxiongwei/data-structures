package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QU_S extends UnionFind {
    int[] sizes;
    public UnionFind_QU_S(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for(int i=0;i<capacity;i++){
            sizes[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        int parent;
        while(v!=parents[v]){
            v = parents[v];
        }
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int v2Parent = find(v2);
        int v1Parent = find(v1);
        if(v1Parent==v2Parent) return;
        if(sizes[v1Parent]>sizes[v2Parent]){
            parents[v2Parent] = v1Parent;
            sizes[v1Parent] +=sizes[v2Parent];
        }else{
            parents[v1Parent] = v2Parent;
            sizes[v1Parent] +=sizes[v2Parent];
        }
    }
}

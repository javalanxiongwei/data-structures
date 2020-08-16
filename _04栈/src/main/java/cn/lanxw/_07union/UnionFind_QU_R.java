package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QU_R extends UnionFind {
    int[] ranks;
    public UnionFind_QU_R(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for(int i=0;i<capacity;i++){
            ranks[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
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
        if(ranks[v1Parent]<ranks[v2Parent]){
            parents[v1Parent] = v2Parent;
        }else if(ranks[v1Parent] > ranks[v2Parent]){
            parents[v2Parent] = v1Parent;
        }else{
            parents[v1Parent] = v2Parent;
            ranks[v2Parent]++;
        }
    }
}

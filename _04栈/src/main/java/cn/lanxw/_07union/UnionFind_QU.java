package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QU extends UnionFind {

    public UnionFind_QU(int capacity) {
        super(capacity);
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
        int newParent = find(v2);
        int oldParent = find(v1);
        if(newParent==oldParent) return;
        parents[oldParent] = newParent;
    }
}

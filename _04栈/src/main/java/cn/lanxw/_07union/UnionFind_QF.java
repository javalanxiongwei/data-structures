package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QF extends UnionFind {

    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    /**
     * 把v1合并到v2中去,
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int newParent = parents[v2];
        int oldParent = parents[v1];
        if(newParent==oldParent) return;
        for(int i=0;i<parents.length;i++){
            if(parents[i]==oldParent){
                parents[i] = newParent;
            }
        }
    }
}

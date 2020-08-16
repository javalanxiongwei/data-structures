package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {
    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }
    @Override
    public int find(int v) {
        rangeCheck(v);
        while(v!=parents[v]){
            int parent = parents[v];
            parents[v] = parents[parent];
            v = parents[v];
        }
        return v;
    }
}

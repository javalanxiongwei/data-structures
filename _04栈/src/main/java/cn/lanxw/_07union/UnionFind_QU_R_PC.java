package cn.lanxw._07union;

/**
 * Created by lanxw
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R {
    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }
    @Override
    public int find(int v) {
        rangeCheck(v);
        while(v!=parents[v]){
            v = find(parents[v]);
        }
        return v;
    }
}

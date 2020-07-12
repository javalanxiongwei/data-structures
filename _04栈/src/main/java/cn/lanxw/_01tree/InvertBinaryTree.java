package cn.lanxw._01tree;

import sun.reflect.generics.tree.Tree;

import javax.swing.tree.TreeNode;
import java.util.LinkedList;

/**
 * Created by wolfcode-lanxw
 */
public class InvertBinaryTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    public TreeNode invertTree(TreeNode root) {
        if(root==null) return null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode node = null;
        TreeNode nodeTemp = null;
        while(!queue.isEmpty()){
            node = queue.poll();
            nodeTemp = node.left;
            node.left = node.right;
            node.right =nodeTemp.left;
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
        }
        return root;
    }
}


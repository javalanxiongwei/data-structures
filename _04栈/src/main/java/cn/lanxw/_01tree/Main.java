package cn.lanxw._01tree;

import cn.lanxw.printer.BinaryTrees;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wolfcode-lanxw
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //Integer[] array = new Integer[]{7,9,5,2,6,13,55};
        Random random = new Random();
        Integer[] array = new Integer[20];
        for(int i=0;i<20;i++){
            array[i] = random.nextInt(100);
        }
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0;i<array.length;i++){
            tree.add(array[i]);
        }
        BinaryTrees.println(tree);
        System.out.println(tree.height());
        System.out.println(tree.height1());
       /* tree.levelTreeTraversal(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.println(element);
            }
        });*/
    }
}

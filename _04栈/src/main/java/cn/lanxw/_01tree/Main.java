package cn.lanxw._01tree;

import cn.lanxw._00common.printer.BinaryTrees;
import org.junit.Test;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        /*Integer[] array = new Integer[]{20,25};
        *//*Random random = new Random();
        Integer[] array = new Integer[5];
        for(int i=0;i<5;i++){
            array[i] = random.nextInt(100);
        }*//*
        BST<Integer> tree = new BST<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0;i<array.length;i++){
            tree.add(array[i]);
        }
        BinaryTrees.println(tree);
        tree.remove(20);
        System.out.println("=======================================================");
        BinaryTrees.println(tree);*/

    }
    @Test
    public void testAVLTree(){
        /*Integer[] array = new Integer[]{14, 77, 27, 81, 70, 53, 71, 10, 98, 34, 45, 13};
        AVLTree<Integer> tree = new AVLTree<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0;i<array.length;i++){
            tree.add(array[i]);
        }
        BinaryTrees.println(tree);
        tree.remove(71);
        tree.remove(77);
        tree.remove(98);
        System.out.println("=======================================================");
        BinaryTrees.println(tree);*/
    }
    @Test
    public void testRBTree(){
        Integer[] array = new Integer[]{66, 35, 99, 86, 25, 87, 37, 1, 72, 2, 51, 63, 75, 26};
        RBTree<Integer> tree = new RBTree<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0;i<array.length;i++){
            tree.add(array[i]);
        }
        BinaryTrees.println(tree);
        tree.remove(1);
        tree.remove(2);
        tree.remove(26);
        tree.remove(25);
        System.out.println("=========================");
        BinaryTrees.println(tree);


     /*  int i = 0;
        for (int binCount = 0; ; ++binCount) {
            i++;
            System.out.println(i);
            if (binCount >= 7){
                break;
            }
        }*/

    }
}

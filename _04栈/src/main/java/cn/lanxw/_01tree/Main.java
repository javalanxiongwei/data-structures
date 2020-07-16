package cn.lanxw._01tree;

import cn.lanxw.printer.BinaryTrees;
import org.junit.Test;

import java.util.Comparator;

/**
 * Created by wolfcode-lanxw
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Integer[] array = new Integer[]{20,25};
        /*Random random = new Random();
        Integer[] array = new Integer[5];
        for(int i=0;i<5;i++){
            array[i] = random.nextInt(100);
        }*/
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
        BinaryTrees.println(tree);

    }
    @Test
    public void testAVLTree(){
        Integer[] array = new Integer[]{14, 77, 27, 81, 70, 53, 71, 10, 98, 34, 45, 13};
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
        BinaryTrees.println(tree);
    }
}

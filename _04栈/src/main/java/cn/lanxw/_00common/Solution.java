package cn.lanxw._00common;

import java.time.LocalDate;
import java.util.Date;
import java.util.Stack;

/**
 * Created by wolfcode-lanxw
 */
public class Solution {
    public static void main(String[] args) {
        scoreOfParentheses("(()(()))");
    }
    public static int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack();
        stack.push(0); // The score of the current frame

        for (char c: S.toCharArray()) {
            if (c == '(')
                stack.push(0);
            else {
                int v = stack.pop();
                int w = stack.pop();
                stack.push(w + Math.max(2 * v, 1));
            }
        }
        return stack.pop();
    }
}

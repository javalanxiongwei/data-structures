package cn.lanxw._02set;

import cn.lanxw.Times;
import cn.lanxw.file.FileInfo;
import cn.lanxw.file.Files;


/**
 * Created by wolfcode-lanxw
 */
public class Main {
    public static void main(String[] args) {
        FileInfo fileInfo = Files.read("D:\\JavaSource\\ideaApp",
                new String[]{"java"});

        System.out.println("文件数量：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词数量：" + words.length);

		Times.test("ListSet", new Times.Task() {
			public void execute() {
				testSet(new ListSet<>(), words);
			}
		});

        Times.test("TreeSet", new Times.Task() {
            public void execute() {
                testSet(new TreeSet<>(), words);
            }
        });
    }
    static void testSet(Set<String> set, String[] words) {
        for (int i = 0; i < words.length; i++) {
            set.add(words[i]);
        }
        System.out.println(set.size());
        for (int i = 0; i < words.length; i++) {
            set.contains(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.remove(words[i]);
        }
    }
}

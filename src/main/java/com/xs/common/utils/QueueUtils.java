package com.xs.common.utils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 队列工具类
 *
 * @author xiaotinghao
 */
public class QueueUtils {

    static LinkedList<String> list = new LinkedList<>();

    public static void main(String[] args) {
        list.add("123");
        list.addLast("1234");

        // 开始打印，若打印过程中有新增打印任务，即往list末尾添加元素，则按队列先进先出原则进行依次打印
        while (!list.isEmpty()) {
            String listFirst = list.getFirst();
            // 处理业务
            System.out.println(list.removeFirst());
        }
    }

}

package com.rh.examples.demos.test;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hui
 * @descption 分单测试
 * @date 10:05
 */
public class Allocation {

    public static void main(String[] args) {
        //需要分配的人员集合（list）
        List<String> list = createUserList(10);

        //将人员集合顺序随机打乱 因为第一人分配到的案件总金额是最大的
        Collections.shuffle(list);



        allocation(list);

    }



    /**
     * 分配
     *
     *
     * @param list
     */
    private static void allocation(List<String> list) {
        final int size = list.size();
        //将list转换成数组userList
        String[] userList = list.toArray((new String[size]));

        List<OrderInfo> orderInfos = createCaseList(100);

        //uid：用于存放人员数组的下标
        int uid;
        //循环需要分配的订单
        for (int i = 1; i <= orderInfos.size(); i++) {
            int i1 = (i + (2 * userList.length - 1)) % (2 * userList.length);
            if (i1 > userList.length - 1) {
                uid = (2 * userList.length - 1) - i1;
                //给订单的人员属性（personId）设置应该分配的对应userList[uid]的值
                orderInfos.get(i - 1).setPersonId(userList[uid]);
            } else {
                uid = i1;
                //给订单的人员属性（personId）设置应该分配的对应userList[uid]的值
                orderInfos.get(i - 1).setPersonId(userList[uid]);
            }
            System.out.println(i1 + "--" + uid);
        }

        //分配结束
//        orderInfos.forEach(caseInfo1->
//                System.out.println(caseInfo1.getName() + "-" + caseInfo1.getMoney() + "-" + caseInfo1.getPersonId()));
        Map<String, Long> collectUser = orderInfos.stream().collect(Collectors.groupingBy(OrderInfo::getPersonId, Collectors.counting()));

        Map<String, List<OrderInfo>> map = orderInfos.stream()
                .collect(Collectors.groupingBy(OrderInfo::getPersonId));

        Map<String, Long> collect = new HashMap<>(map.size());
        map.forEach((key, value) -> {
            long sum = value.stream().mapToLong(i -> i.getMoney().longValue()).sum();
            collect.put(key, sum);
        });

        System.out.println(JSON.toJSONString(collectUser));
        System.out.println("--------------------");
        System.out.println(JSON.toJSONString(collect));
    }

    /**
     * 创建用户列表
     * @param num
     * @return
     */
    public static List<String> createUserList(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("user" + i);
        }
        return list;
    }

    /**
     * 创建订单列表
     * @param num
     * @return
     */
    public static List<OrderInfo> createCaseList(int num) {
        List<OrderInfo> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            list.add(new OrderInfo(BigDecimal.valueOf(r.nextInt(10000)), "case1"));
        }
        //待分配的订单集合（orderList）按照金额由大到小排序
        list.sort((OrderInfo arg0, OrderInfo arg1) ->  arg1.getMoney().compareTo(arg0.getMoney()));
        return list;
    }

    static class OrderInfo {
        private BigDecimal money;
        private String name;
        private String personId;

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public OrderInfo(BigDecimal money, String name) {
            this.money = money;
            this.name = name;
        }
    }
}

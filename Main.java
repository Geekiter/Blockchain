package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        List<Man> manList = new ArrayList<>();

        //矿工数
        int minersCount = 20;
        //需要的矿
        int needBlockCount = 20;
        //老板数
        int bossCount = 10;
        ExecutorService executors = Executors.newFixedThreadPool(minersCount);

        int currentId = 1;
        for (int i = 0; i < minersCount; i++) {
            Miner miner = new Miner(blockchain, currentId++, needBlockCount, manList);
            manList.add(miner);
            executors.submit(miner);
        }
        for (int i = 0; i < bossCount; i++) {
            List<Man> employeeList = new ArrayList<>();

            Employee employee1 = new Employee(currentId++, manList, blockchain);
            Employee employee2 = new Employee(currentId++, manList, blockchain);
            Employee employee3 = new Employee(currentId++, manList, blockchain);

            employeeList.add(employee1);
            employeeList.add(employee3);
            employeeList.add(employee2);

            manList.add(employee1);
            manList.add(employee2);
            manList.add(employee3);


            Boss boss = new Boss(currentId++, manList, employeeList, blockchain);
            manList.add(boss);

            employee1.setBoss(boss);
            employee2.setBoss(boss);
            employee3.setBoss(boss);

            executors.submit(employee1);
            executors.submit(employee2);
            executors.submit(employee3);

            executors.submit(boss);
        }

        try {
            executors.shutdown();
            while (!executors.isTerminated())
                executors.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
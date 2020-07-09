package blockchain;

import java.util.List;
import java.util.Objects;

//雇员
public class Employee extends Man implements Runnable {
    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    private Boss boss;

    public Employee(int id, List<Man> manList ,Blockchain blockchain) {
        super(id, "employee", manList, blockchain);

    }

    @Override
    public boolean translationCondition(Man man) {
        //只要是老板，并且不是自己的老板
        return Objects.equals(man.getSocialStatus(), "boss") && man.getId() != boss.getId();
    }

    @Override
    public void run() {
        randomExpend();
    }
}

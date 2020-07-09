package blockchain;

import java.util.List;
import java.util.Objects;
import java.util.Random;

//店老板
public class Boss extends Man implements Runnable {
    private final List<Man> employeeList;

    public Boss(int id, List<Man> manList, List<Man> employeeList, Blockchain blockchain) {
        super(id, "boss", manList, blockchain);
        this.employeeList = employeeList;
    }

    @Override
    public boolean translationCondition(Man man) {
        //只要老板，并且不是自己
        return Objects.equals(man.getSocialStatus(), "boss") && man.getId() != getId();
    }

    @Override
    public void run() {
        Random rand = new Random();
        regularExpend(employeeList, 30 + rand.nextInt(5) , 60);
        randomExpend();
    }

}

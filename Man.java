package blockchain;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;


public abstract class Man {

    private final int id;
    private final String socialStatus;//社会地位
    private final Blockchain blockchain;
    private final List<Man> manList;
    private int monkey;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Man(int id, String socialStatus, List<Man> manList, Blockchain blockchain) {
        this.id = id;
        this.socialStatus = socialStatus;
        this.manList = manList;
        this.monkey = 100;
        this.blockchain = blockchain;
        try {
            Key key = new Key(1024);
            key.createKeys();
            this.privateKey = key.getPrivateKey();
            this.publicKey = key.getPublicKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public List<Man> getManList() {
        return manList;
    }

    public int getId() {
        return id;
    }

    public int getManListSize() {
        return manList.size();
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    //交易条件
    public abstract boolean translationCondition(Man man);

    //获取金钱
    public int getMoney() {
        return monkey;
    }

    public void income(int money) {
        this.monkey += money;
    }

    public void expend(int money) {
        this.monkey -= money;
    }

    //发送消息给区块链
    public synchronized void sendMessage(Man toMan, int expendMoney) {
        boolean create_status = false;
        do {
            //钱不够了，break
            if (expendMoney > getMoney())
                break;
            SignMessage sign = new SignMessage();
            int translationId = blockchain.getCurrentTranslationId();
            byte[] signature = sign.sign(String.valueOf(translationId + getId() + expendMoney + toMan.getId()), privateKey);
            TranslationMessage translationMessage = new TranslationMessage(translationId, publicKey, signature, this, toMan, expendMoney);
            if (translationId > blockchain.getNewestChatID())
                create_status = blockchain.createMessage(translationMessage);
        } while (!create_status);
    }

    //随机消费
    public synchronized void randomExpend() {
        Timer timer = new Timer();
        Random rand = new Random();

        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               int rand_num = rand.nextInt(getManListSize());
                               //随机消费对象
                               Man translationMan = getManList().get(rand_num);
                               if (translationCondition(translationMan)) {
                                   //随机金额的消费
                                   int rand_expend_money = rand.nextInt(10) + 25;
                                   sendMessage(translationMan, rand_expend_money);
                               }
                           }
                       }, 30 + rand.nextInt(10) ,
                30 + rand.nextInt(10) );

    }

    //固定消费
    public synchronized void regularExpend(List<Man> regularManList, int money, int seconds) {
        Timer timer = new Timer();
        Random rand = new Random();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               for (Man man : regularManList) {
                                   sendMessage(man, money);
                               }
                           }
                       }, seconds
                , money);
    }

}

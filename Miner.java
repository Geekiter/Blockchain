package blockchain;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Miner extends Man implements Runnable {
    private final Blockchain blockchain;
    private final int id;
    private final String zeroString;
    private final int needBlocks;
    private boolean needToMining;
    private int currentBlockId;
    private Block block;

    Miner(Blockchain blockchain, int id, int needBlocks, List<Man> manList) {
        super(id, "miner", manList, blockchain);
        this.id = id;
        this.needBlocks = needBlocks;
        this.blockchain = blockchain;
        this.zeroString = blockchain.getZeroString();
        currentBlockId = blockchain.getCurrentBlockId();
        this.needToMining = blockchain.getCurrentBlockId() <= needBlocks;
    }


    @Override
    public void run() {
        while (needToMining) {
            Random rand = new Random();
            boolean blockIsContinue = currentBlockId == blockchain.getCurrentBlockId();
            //挖到了吗？
            long startTime = System.nanoTime();
            while (blockIsContinue) {
                long magicNumber = Math.abs(rand.nextLong());
                block = new Block(this, currentBlockId, startTime, magicNumber, blockchain.getPrevHash());
                //自身先验证
                if (block.isEquel(blockchain.getZeroString()) && currentBlockId == blockchain.getCurrentBlockId()) {
                    long finishTime = System.nanoTime();
                    long generate_time = TimeUnit.SECONDS.convert(finishTime - startTime, TimeUnit.NANOSECONDS);
                    blockchain.createBlock(block, generate_time);
                }
                blockIsContinue = currentBlockId == blockchain.getCurrentBlockId();
                // 矿是否挖完了
                if (blockchain.getCurrentBlockId() > needBlocks) needToMining = false;
                else currentBlockId = blockchain.getCurrentBlockId();
            }
            randomExpend();
        }

    }

    @Override
    public boolean translationCondition(Man man) {
        //只要是老板
        return Objects.equals(man.getSocialStatus(), "boss");
    }
}

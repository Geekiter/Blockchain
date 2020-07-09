package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> blocks;
    private final List<TranslationMessage> translationMessages;
    private final List<String> blockchainContent;
    private int currentTranslationId;
    private int zeroCount;
    private int currentBlockId;
    private String zeroString;
    private String prevHash;
    private long generateTime;

    public Blockchain() {
        blocks = new ArrayList<>();
        translationMessages = new ArrayList<>();
        currentBlockId = 1;
        currentTranslationId = 1;
        blockchainContent = new ArrayList<>();
        zeroCount = 0;
        zeroString = "";
        prevHash = "0";
    }

    public int getCurrentTranslationId() {
        return currentTranslationId;
    }

    public int getZeroCount() {
        return zeroCount;
    }

    public int getCurrentBlockId() {
        return currentBlockId;
    }

    public String getZeroString() {
        return zeroString;
    }

    public String getPrevHash() {
        return prevHash;
    }

    private void updateZeroString() {
        zeroString = "0".repeat(zeroCount);
    }


    public synchronized void createBlock(Block block, long generateTime) {
        if (block.isEquel(zeroString) &&
                prevHash.equals(block.getPrevHash()) &&
                currentBlockId == block.getId()
        ) {
            StringBuilder content = new StringBuilder();
            content.append(block.toString());
            content.append("Block data: ");
            if (currentBlockId == 1 || translationMessages.isEmpty()) content.append("\nNo transactions\n");
            else {
                content.append("\n");
                for (TranslationMessage item : translationMessages) content.append(item).append("\n");
            }
            content.append("Block was generating for ").append(generateTime).append(" seconds\n");
            if (generateTime < 2) {
                if (zeroCount < 3)
                    zeroCount += 2;
                else
                    zeroCount++;
                content.append("N was increased to ").append(zeroCount).append("\n");
            } else if (generateTime > 3) {
                zeroCount--;
                content.append("N was decreased by 1\n");
            } else {
                content.append("N stays the same\n");
            }
            translationMessages.clear();
            currentBlockId++;
            prevHash = block.getHash();
            updateZeroString();
            block.getMiner().income(100);
            blocks.add(block);
            blockchainContent.add(content.toString());
            System.out.println(content.toString());
        }

    }

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        for (String item : blockchainContent) {
            sb.append(item).append("\n\n");
        }
        return sb.toString();
    }

    public synchronized boolean createMessage(TranslationMessage translationMessage) {
        if (translationMessages.isEmpty() || translationMessage.getId() > translationMessages.get(translationMessages.size() - 1).getId()) {
            VerifyMessage verify = new VerifyMessage();
            try {
                if (verify.verify(String.valueOf(translationMessage.getId()
                                + translationMessage.getExpendManId()
                                + translationMessage.getMoney()
                                + translationMessage.getIncomeManId()),
                        translationMessage.getSignature(),
                        translationMessage.getPublicKey())) {
                    //捡钱加钱
                    if (translationMessage.getExpendMan().getMoney() > translationMessage.getMoney()) {
                        translationMessages.add(translationMessage);
                        translationMessage.getIncomeMan().income(translationMessage.getMoney());
                        translationMessage.getExpendMan().expend(translationMessage.getMoney());
                        currentTranslationId++;
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public int getNewestChatID() {
        if (translationMessages.isEmpty())
            return 0;
        return translationMessages.get(translationMessages.size() - 1).getId();
    }
}
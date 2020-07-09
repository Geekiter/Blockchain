package blockchain;

import java.security.PublicKey;

public class TranslationMessage {
    private final int id;
    private final PublicKey publicKey;
    private final byte[] signature;
    private final Man expendMan;
    private final Man incomeMan;
    private final int money;
    private final int expendManMokey;
    TranslationMessage(int id, PublicKey publicKey, byte[] signature, Man expendMan, Man incomeMan, int money) {
        this.id = id;
        this.publicKey = publicKey;
        this.expendMan = expendMan;
        this.signature = signature;
        this.incomeMan = incomeMan;
        this.money = money;
        this.expendManMokey = expendMan.getMoney();
    }

    public Man getExpendMan() {
        return expendMan;
    }

    public Man getIncomeMan() {
        return incomeMan;
    }

    public int getId() {
        return id;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public int getExpendManId() {
        return expendMan.getId();
    }

    public int getIncomeManId() {
        return incomeMan.getId();
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#").append(id).append(" ").append(expendMan.getSocialStatus()).append(expendMan.getId()).append("'s money: ").append(expendManMokey).append("\n");
        sb.append(expendMan.getSocialStatus()).append(expendMan.getId()).append(" send ").append(money)
                .append(" VC TO ").append(incomeMan.getSocialStatus()).append(incomeMan.getId());
        return sb.toString();
    }
}

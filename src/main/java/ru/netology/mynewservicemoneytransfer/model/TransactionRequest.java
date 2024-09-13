package ru.netology.mynewservicemoneytransfer.model;

public class TransactionRequest {

    long operationId;
    long cardFromNumber;
    long cardToNumber;
    String cardFromCVV;
    String cardFromValidTill;
    int amount;
    String currency;
    int status = 0; // 1 - всё ок, 2 - неверные данные, 3 - ошибка подтверждения

    public TransactionRequest(long cardFromNumber, long cardToNumber, String cardFromCVV, String cardFromValidTill, int amount, String currency) {

        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
        this.currency = currency;
    }

    public long getOperationId() {
        return operationId;
    }

    public long getCardFromNumber() {
        return cardFromNumber;
    }

    public long getCardToNumber() {
        return cardToNumber;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public int getStatus() {
        return status;
    }

    public int setStatus(int status) {
        this.status = status;
        return this.status;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "operationId=" + operationId +
                ", cardFromNumber=" + cardFromNumber +
                ", cardToNumber=" + cardToNumber +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", amount=" + amount / 100 + ".00" + '\'' +
                ", currency='" + currency + '\'' +
                ", status='" + status + " (Пояснение: 1 - всё ок, 2 - неверные данные, 3 - ошибка подтверждения)" + '\'' +
                '}';
    }
}

package ru.netology.mynewservicemoneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TransferRequest {

    long cardFromNumber;
    long cardToNumber;
    String cardFromCVV;
    String cardFromValidTill;
    Amount amount;

    @Data
    public static class Amount {
        int value;
        String currency;
    }
}

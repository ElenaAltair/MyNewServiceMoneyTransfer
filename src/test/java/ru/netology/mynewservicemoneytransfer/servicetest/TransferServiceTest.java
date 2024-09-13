package ru.netology.mynewservicemoneytransfer.servicetest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.mynewservicemoneytransfer.exception.Exceptions;
import ru.netology.mynewservicemoneytransfer.model.OkResponse;
import ru.netology.mynewservicemoneytransfer.model.TransferRequest;
import ru.netology.mynewservicemoneytransfer.service.TransferService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferServiceTest {

    private static TransferRequest transferRequest;

    @BeforeAll
    public static void staticInit() {
        transferRequest = new TransferRequest();
        transferRequest.setCardFromNumber(1111111111111111L);
        transferRequest.setCardToNumber(2222222222222222L);
        transferRequest.setCardFromCVV("111");
        var amount = new TransferRequest.Amount();
        amount.setValue(444);
        amount.setCurrency("RUR");
        transferRequest.setAmount(amount);
    }

    @Test
    void transferOkTest() {
        TransferService service = new TransferService();
        OkResponse okResponse = service.transfer(transferRequest);
        assertEquals("0", okResponse.getOperationId());
    }

    @Test
    void transferErrorTest() {
        TransferService service = new TransferService();
        transferRequest.setCardToNumber(1111111111111111L);
        Exceptions exception = assertThrows(Exceptions.class, () -> service.transfer(transferRequest));
        assertEquals("Ошибка при введении данных.", exception.getMessage());
    }

}

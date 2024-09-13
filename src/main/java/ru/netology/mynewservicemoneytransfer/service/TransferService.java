package ru.netology.mynewservicemoneytransfer.service;

import org.springframework.stereotype.Service;
import ru.netology.mynewservicemoneytransfer.exception.Exceptions;
import ru.netology.mynewservicemoneytransfer.model.ConfirmRequest;
import ru.netology.mynewservicemoneytransfer.model.OkResponse;
import ru.netology.mynewservicemoneytransfer.model.TransactionRequest;
import ru.netology.mynewservicemoneytransfer.model.TransferRequest;

import static ru.netology.mynewservicemoneytransfer.log.Log.log;

@Service
public class TransferService {

    private TransactionRequest transactionRequest;

    public OkResponse transfer(TransferRequest request) {

        transactionRequest = new TransactionRequest(
                request.getCardFromNumber(),
                request.getCardToNumber(),
                request.getCardFromCVV(),
                request.getCardFromValidTill(),
                request.getAmount().getValue(),
                request.getAmount().getCurrency());

        // System.out.println(request);
        // System.out.println();
        // System.out.println(transactionRequest);

        log(" Проверяем данные. ", "Запрос на транзакцию. ", transactionRequest.toString());

        // проверяем на ошибки
        int status = transferStatus(transactionRequest);
        if (status != 1) {
            log(" Ошибка при введении данных. ", " Отмена операции. ", transactionRequest.toString());
            throw new Exceptions("Ошибка при введении данных.");
        }
        log(" Ждём подтверждения операции. ", " Данные корректны. ", transactionRequest.toString());
        return new OkResponse(Long.toString(transactionRequest.getOperationId()), null);
    }

    public OkResponse confirmOperation(ConfirmRequest request) {

        // System.out.println(request);
        // проверяем на ошибки
        int status = confirmStatus(request);
        if (status == 3) {
            log(" Ошибка подтверждения операции. ", " Отмена операции, status = " + transactionRequest.getStatus() + " (Пояснение: 1 - всё ок, 2 - неверные данные, 3 - ошибка подтверждения) ", request.toString());
            throw new Exceptions("Ошибка подтверждения операции.");
        }
        log(" Деньги переведены. ", " Операция подтверждена. ", request.toString());
        return new OkResponse(request.getOperationId(), request.getCode());
    }

    public int transferStatus(TransactionRequest request) {

        // Для теста
        return request.getCardFromNumber() == request.getCardToNumber() ? transactionRequest.setStatus(2) : transactionRequest.setStatus(1);

    }

    public int confirmStatus(ConfirmRequest request) {

        // Для теста
        //request.setCode("1111");
        return request.getCode().equals("0000") && request.getOperationId().equals("" + transactionRequest.getOperationId()) ? transactionRequest.setStatus(1) : transactionRequest.setStatus(3);
    }

}

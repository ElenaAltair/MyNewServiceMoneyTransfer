package ru.netology.mynewservicemoneytransfer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.mynewservicemoneytransfer.exception.ErrorResponse;
import ru.netology.mynewservicemoneytransfer.exception.Exceptions;
import ru.netology.mynewservicemoneytransfer.model.ConfirmRequest;
import ru.netology.mynewservicemoneytransfer.model.OkResponse;
import ru.netology.mynewservicemoneytransfer.model.TransferRequest;
import ru.netology.mynewservicemoneytransfer.service.TransferService;

import static ru.netology.mynewservicemoneytransfer.log.Log.log;

@RestController
@CrossOrigin
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/transfer")
    public OkResponse transfer(@RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }

    @PostMapping(value = "/confirmOperation")
    public OkResponse confirmOperation(@RequestBody ConfirmRequest request) {
        return transferService.confirmOperation(request);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exceptions.class)
    public ErrorResponse handleBadRequest(Exceptions e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleInternalServerError(RuntimeException e) {
        log("Внутренняя ошибка сервера", "", "");
        return new ErrorResponse("Внутренняя ошибка сервера");
    }
}

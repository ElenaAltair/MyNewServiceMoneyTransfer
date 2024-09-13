package ru.netology.mynewservicemoneytransfer.model;

import lombok.Data;

@Data
public class ConfirmRequest {
    String operationId;
    String code;
}


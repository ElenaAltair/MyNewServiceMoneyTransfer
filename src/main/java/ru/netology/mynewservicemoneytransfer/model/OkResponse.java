package ru.netology.mynewservicemoneytransfer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OkResponse {
    String operationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String code;
}

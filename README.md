# Курсовой проект «Сервис перевода денег»

## Описание проекта
Необходимо разработать приложение — REST-сервис. Сервис должен предоставить интерфейс для перевода денег с одной карты на другую по заранее описанной спецификации.

Заранее подготовленное веб-приложение (FRONT) должно подключаться к разработанному сервису без доработок и использовать его функционал для перевода денег.

## Требования к приложению
Сервис должен предоставлять REST-интерфейс для интеграции с FRONT.
Сервис должен реализовывать все методы перевода с одной банковской карты на другую, описанные в протоколе.
Все изменения должны записываться в файл — лог переводов в произвольном формате с указанием:
даты;
времени;
карты, с которой было списание;
карты зачисления;
суммы;
комиссии;
результата операции, если был.
Требования в реализации
Приложение разработано с использованием Spring Boot.
Использован сборщик пакетов gradle/maven.
Для запуска используется Docker, Docker Compose .
Код размещён на GitHub.
Код покрыт юнит-тестами с использованием mockito.
Добавлены интеграционные тесты с использованием testcontainers.

#### на FRONTе параметры запросов и ответов можно посмотреть по адресу src/services.ts 

#### services.ts
    export const TRANSFER_URL = `${process.env.REACT_APP_API_URL}/transfer`;
    export type TTransferPostData = {
        cardFromNumber: string;
        cardFromValidTill: string;
        cardFromCVV: string;
        cardToNumber: string;
        amount: {
            value: number;
            currency: string;
        };
    };
    export type TTransferPostDataResponse = {
        operationId: string,
    };

    export const CONFIRM_OPERATION_URL = `${process.env.REACT_APP_API_URL}/confirmOperation`;
    export type TConfirmOperationPostData = {
        code: string;
        operationId: string;
    };
    export type TConfirmOperationPostDataResponse = {
        operationId: string,
    };

### Вариант запроса

    POST http://localhost:5500/transfer
    Content-Type: application/json

    {
        "cardFromNumber": "1111111111111111",
        "cardToNumber": "2222222222222222",
        "cardFromCVV": "111",
        "cardFromValidTill": "09/24",
        "amount": {
            "currency": "RUR",
            "value": 333
        }
    }

Далее если запрос прошёл успешно 
в качестве ответа получим JSON:

    {
        "operationId": "0"
    }

Далее выполяется запрос на поддтверждение транзакции:

    POST http://localhost:5500/confirmOperation
    Content-Type: application/json

    {
        "operationId": "0",
        "code": "0000"
    }

Если запрос прошёл успешно (деньги будут переведены на нужную карту)
в качестве ответа получим JSON :

    {
        "operationId": "0",
        "code": "0000"
    }

### Логирование

Информация о транзакциях и ошибки записываются в File.log,
расположеный по адресу: src/main/java/ru/netology/mynewservicemoneytransfer/resources/File.log 

package com.appscooter.tripmicroservice.services.dtos.tariff.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportProfitsDTO {

    private String month;
    private String year;
    private Double totalProfits;
}

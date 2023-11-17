package com.appscooter.tripmicroservice.services.dtos.tariff.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TotalProfitsRequestDTO {

    @NotNull(message = "firstMonth cannot be null")
    @Min(value = 1, message ="ID should not be less than 200")
    @Max(value = 12, message ="ID should not be greater than 200")
    private Long firstMonth;
    @Min(value = 1, message ="ID should not be less than 200")
    @Max(value = 12, message ="ID should not be greater than 200")
    private Long lastMonth;
    @Min(value = 2022, message ="ID should not be less than 2022")
    @Max(value = 2024, message ="ID should not be greater than 2024")
    private Long year;

}

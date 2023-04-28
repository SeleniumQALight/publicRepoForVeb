package dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CurrencyExchangeDto {
    String date;
    String bank;
    int baseCurrency;
    String baseCurrencyLit;
    ExchangeRateDto[] exchangeRate;

}

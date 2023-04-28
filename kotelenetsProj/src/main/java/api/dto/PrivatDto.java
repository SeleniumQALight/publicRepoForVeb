package api.endPoints;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivatDto {
    String date;
    String bank;
    Integer baseCurrency;
    String baseCurrencyLit;
    ExchangeRateDto[] exchangeRate;
}

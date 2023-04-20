package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
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

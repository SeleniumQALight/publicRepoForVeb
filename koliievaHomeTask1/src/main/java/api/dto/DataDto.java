package api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDto {
    String date;
    String bank;
    Integer baseCurrency;
    String baseCurrencyLit;
    List<ExchangeRatesDto> exchangeRate;
}

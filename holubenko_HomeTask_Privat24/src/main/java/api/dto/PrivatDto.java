package api.endPoints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivatDto {
    String date;
    String bank;
    Integer baseCurrency;
    String baseCurrencyLit;
    List<ExchangeRateDto> exchangeRate;
}

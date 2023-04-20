package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRatesDto {
    String baseCurrency;
    String currency;
    Double saleRateNB;
    Double purchaseRateNB;
    Double saleRate;
    Double purchaseRate;
}

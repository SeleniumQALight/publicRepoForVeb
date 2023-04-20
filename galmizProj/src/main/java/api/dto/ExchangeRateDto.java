package api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {
    String baseCurrency;
    String currency;
    Double saleRateNB;
    Double purchaseRateNB;
    Double saleRate;
    Double purchaseRate;
}

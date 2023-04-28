package api.endPoints;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PbExchangeDto {
	String baseCurrency;
	String currency;
	Double saleRateNB;
	Double purchaseRateNB;
	Double saleRate;
	Double purchaseRate;
}

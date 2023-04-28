package api.endPoints;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PbDto {
	String date;
	String bank;
	Integer baseCurrency;
	String baseCurrencyLit;
	List <PbExchangeDto> exchangeRate;
}

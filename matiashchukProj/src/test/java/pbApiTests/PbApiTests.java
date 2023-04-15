package pbApiTests;

import api.dto.PbDto;
import api.dto.PbExchangeDto;
import api.endpoints.PbEndPoints;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PbApiTests {
	final String DATE = "22.03.2022";

	@Test
	public void getAllExchangeRatesTest() {
		PbDto pbDtoResponse = given()
				.contentType(ContentType.JSON)
				.queryParam("date", DATE)
				.log().all()
				.when()
				.get(PbEndPoints.EXCHANGE_RATES)
				.then()
				.statusCode(200)
				.log().all()
				.extract().response().as(PbDto.class);

		List<PbExchangeDto> listExchangeRates = List.of(
				PbExchangeDto.builder().baseCurrency("UAH").currency("AUD").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("AZN").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("BYN").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("CAD").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("CHF").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("CNY").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("CZK").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("DKK").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("EUR").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("GBP").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("GEL").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("HUF").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("ILS").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("JPY").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("KZT").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("MDL").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("NOK").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("PLN").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("SEK").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("SGD").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("TMT").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("TRY").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("UAH").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("USD").build(),
				PbExchangeDto.builder().baseCurrency("UAH").currency("UZS").build()
		);

		PbDto expectedResult =
				PbDto.builder().date(DATE).bank("PB").baseCurrency(980).baseCurrencyLit("UAH")
						.exchangeRate(listExchangeRates)
						.build();

		SoftAssertions softAssertions = new SoftAssertions();

		softAssertions.assertThat(pbDtoResponse)
				.usingRecursiveComparison()
				.ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB"
						, "exchangeRate.purchaseRate", "exchangeRate.saleRate")
				.isEqualTo(expectedResult);

		softAssertions.assertAll();
	}

	@Test
	public void getAllExchangeRatesSchema() {
		given()
				.contentType(ContentType.JSON)
				.queryParam("date", DATE)
				.log().all()
				.when()
				.get(PbEndPoints.EXCHANGE_RATES)
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body(matchesJsonSchemaInClasspath("pbJsonSchema.json"));
	}

	@Test
	public void getAllExchangeRatesIsGreaterThanZeroTest() {
		List<PbExchangeDto> actualResponse = given()
				.contentType(ContentType.JSON)
				.queryParam("date", DATE)
				.log().all()
				.when()
				.get(PbEndPoints.EXCHANGE_RATES)
				.then()
				.statusCode(200)
				.log().all()
				.extract().response()
				.jsonPath().getList("exchangeRate", PbExchangeDto.class);

		SoftAssertions softAssertions = new SoftAssertions();

		for (PbExchangeDto pbExchangeDto : actualResponse) {
			softAssertions.assertThat(pbExchangeDto.getSaleRateNB()).isGreaterThan(0);
			softAssertions.assertThat(pbExchangeDto.getPurchaseRateNB()).isGreaterThan(0);
			if (pbExchangeDto.getSaleRate() != null && pbExchangeDto.getPurchaseRate() != null) {
				softAssertions.assertThat(pbExchangeDto.getSaleRate()).isGreaterThan(0);
				softAssertions.assertThat(pbExchangeDto.getPurchaseRate()).isGreaterThan(0);
			}
		}

		softAssertions.assertAll();
	}
}

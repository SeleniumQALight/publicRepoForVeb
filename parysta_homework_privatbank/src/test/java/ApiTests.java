import dto.CurrencyExchangeDto;
import dto.ExchangeRateDto;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

import org.junit.Test;


import static api.endPoints.EndPoints.GET_EXCHANGE_RATE_BY_DATE;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiTests {
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void getExchangeRateByDate() {
        CurrencyExchangeDto currencyExchangeDto = given().
                contentType(ContentType.JSON).
                queryParam("date", "22.03.2022").
                log().all().
                when().
                get(GET_EXCHANGE_RATE_BY_DATE).then().
                statusCode(HttpStatus.SC_OK).
                log().all().
                extract().response().as(CurrencyExchangeDto.class);


        CurrencyExchangeDto expectedResult = CurrencyExchangeDto.builder()
                .date("22.03.2022")
                .bank("PB")
                .baseCurrency(980)
                .baseCurrencyLit("UAH").exchangeRate(new ExchangeRateDto[]{
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("AUD").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("AZN").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("BYN").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("CAD").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("CHF").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("CNY").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("CZK").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("DKK").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("EUR").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("GBP").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("GEL").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("HUF").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("ILS").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("JPY").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("KZT").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("MDL").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("NOK").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("PLN").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("SEK").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("SGD").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("TMT").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("TRY").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("UAH").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("USD").build(),
                        ExchangeRateDto.builder().baseCurrency("UAH").currency("UZS").build()
                })
                .build();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(currencyExchangeDto).usingRecursiveComparison().
                ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB",
                        "exchangeRate.saleRate", "exchangeRate.purchaseRate").
                isEqualTo(expectedResult);
        softAssertions.assertAll();
    }

    @Test
    public void getExchangeRateByDateScheme() {
        given().
                contentType(ContentType.JSON).
                queryParam("date", "22.03.2022").
                log().all().
                when().
                get(GET_EXCHANGE_RATE_BY_DATE).
                then().
                statusCode(200).
                log().all().
                assertThat().
                body(matchesJsonSchemaInClasspath("responseGetExchangeRateByDate.json"));
    }

    @Test
    public void getAllExchangeRatesWhichMoreThanZero(){


    }
}






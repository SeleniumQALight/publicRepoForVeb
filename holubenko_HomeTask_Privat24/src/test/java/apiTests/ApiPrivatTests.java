package apiTests;

import api.dto.ExchangeRateDto;
import api.dto.PrivatDto;
import api.endPoints.EndPoints;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiPrivatTests {
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void getAllPostsPrivatTest(){
        PrivatDto privatDtoResponse = given()
                .contentType(ContentType.JSON)
                .queryParam("date", "22.03.2022")
                .log().all()
                .when()
                .get(EndPoints.POST_BY_BANK)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().as(PrivatDto.class)
        ;
        List<ExchangeRateDto> exchangeRateList = privatDtoResponse.getExchangeRate();
        Assert.assertFalse(exchangeRateList.isEmpty());
        logger.info("Number of posts = " + privatDtoResponse);
        logger.info("Bank = " + privatDtoResponse.getBank());
        logger.info("BaseCurrency = " + privatDtoResponse.getBaseCurrency());
        logger.info("BaseCurrencyLit = " + privatDtoResponse.getBaseCurrencyLit());
        logger.info("ExchangeRate = " + privatDtoResponse.getExchangeRate().get(2));
        logger.info("ExchangeRate = " + privatDtoResponse.getExchangeRate().get(10));

        List<ExchangeRateDto> expectedExchangeRateList = new ArrayList<>();
        String[] currencies = {"AUD", "AZN", "BYN", "CAD", "CHF", "CNY", "CZK", "DKK", "EUR", "GBP", "GEL", "HUF", "ILS", "JPY", "KZT", "MDL", "NOK", "PLN", "SEK", "SGD", "TMT", "TRY", "UAH", "USD", "UZS"};

        for (String currency : currencies) {
            ExchangeRateDto exchangeRateDto = ExchangeRateDto.builder().baseCurrency("UAH").currency(currency).build();
            expectedExchangeRateList.add(exchangeRateDto);
        }

        PrivatDto expectedResult =
                PrivatDto.builder().date("22.03.2022").bank("PB").baseCurrency(980).baseCurrencyLit("UAH")
                        .exchangeRate(expectedExchangeRateList).build();

        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(privatDtoResponse)
                .usingRecursiveComparison()
                .ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB"
                        , "exchangeRate.purchaseRate", "exchangeRate.saleRate")
                .isEqualTo(expectedResult);

        softAssertions.assertAll();
    }

    @Test
    public void testAllExchangeRateFieldsArePositive() {
        List<ExchangeRateDto> getExchangeRate = given()
                .contentType(ContentType.JSON)
                .queryParam("date", "22.03.2022")
                .log().all()
                .when()
                .get(EndPoints.POST_BY_BANK)
                .then()
                .statusCode(200)
                .log().all()
                .extract().jsonPath().getList("exchangeRate", ExchangeRateDto.class);

        SoftAssertions softAssertions = new SoftAssertions();

        for (ExchangeRateDto exchangeRate : getExchangeRate) {
            softAssertions.assertThat(exchangeRate.getSaleRateNB()).isGreaterThan(0);
            softAssertions.assertThat(exchangeRate.getPurchaseRateNB()).isGreaterThan(0);
        }
    }
}

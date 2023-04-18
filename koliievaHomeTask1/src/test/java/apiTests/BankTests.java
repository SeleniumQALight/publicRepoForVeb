package apiTests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import api.dto.DataDto;
import api.dto.ExchangeRatesDto;
import api.endPoints.EndPoints;
import io.restassured.http.ContentType;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class BankTests {
    @Test
    public void getAllData(){
        DataDto dataDtoResponse = given()
            .contentType(ContentType.JSON)
            .queryParam("date", "22.03.2022")
            .log().all()
            .when()
            .get(EndPoints.ALL_URL)
            .then()
            .statusCode(200)
            .log().all()
            .extract().response().as(DataDto.class)
        ;

        DataDto expectedResult =
            DataDto.builder().date("22.03.2022").bank("PB").baseCurrency(980).baseCurrencyLit("UAH")
                   .exchangeRate(List.of(
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("AUD").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("AZN").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("BYN").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("CAD").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("CHF").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("CNY").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("CZK").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("DKK").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("EUR").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("GBP").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("GEL").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("HUF").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("ILS").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("JPY").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("KZT").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("MDL").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("NOK").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("PLN").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("SEK").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("SGD").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("TMT").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("TRY").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("UAH").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("USD").build(),
                       ExchangeRatesDto.builder().baseCurrency("UAH").currency("UZS").build()
                   )).build();


        SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(dataDtoResponse)
                          .usingRecursiveComparison()
                          .ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB",
                              "exchangeRate.saleRate", "exchangeRate.purchaseRate")
                          .isEqualTo(expectedResult);
            softAssertions.assertAll();
    }

    @Test
    public void getAllBySchema(){
        given()
            .contentType(ContentType.JSON)
            .queryParam("date", "22.03.2022")
            .log().all()
            .when()
            .get(EndPoints.ALL_URL)
            .then()
            .statusCode(200)
            .log().all()
            .assertThat().body(matchesJsonSchemaInClasspath("response.json"));
    }


    @Test
    public void allFieldsHaveRatesGreaterThanZero(){
        DataDto dataDtoResponse = given()
            .contentType(ContentType.JSON)
            .queryParam("date", "22.03.2022")
            .log().all()
            .when()
            .get(EndPoints.ALL_URL)
            .then()
            .statusCode(200)
            .log().all()
            .extract().response().as(DataDto.class);

        SoftAssertions softAssertions = new SoftAssertions();
        for(int i=0; i<dataDtoResponse.getExchangeRate().size(); i++) {
            softAssertions.assertThat(dataDtoResponse.getExchangeRate().get(i).getSaleRateNB()).isGreaterThan(0);
            softAssertions.assertThat(dataDtoResponse.getExchangeRate().get(i).getPurchaseRateNB()).isGreaterThan(0);
            if(dataDtoResponse.getExchangeRate().get(i).getSaleRate() == null)
                continue;
            softAssertions.assertThat(dataDtoResponse.getExchangeRate().get(i).getSaleRate()).isGreaterThan(0);
            softAssertions.assertThat(dataDtoResponse.getExchangeRate().get(i).getPurchaseRate()).isGreaterThan(0);
        }
        softAssertions.assertAll();
    }
}

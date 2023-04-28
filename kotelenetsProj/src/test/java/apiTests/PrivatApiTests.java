package apiTests;

import api.endPoints.ExchangeRateDto;
import api.endPoints.PrivatDto;
import api.endPoints.PrivatEndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PrivatApiTests {
    String DATE = "22.03.2022";

    @Test
    public void getAllPostsTest(){
        PrivatDto privatDtoResponse =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("date", DATE)
                        .log().all()
                        .when()
                        .get(PrivatEndPoints.POST)
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().response().as(PrivatDto.class);

        ExchangeRateDto[] expectedExchangeRate = {
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
        };

        PrivatDto expectedResult = PrivatDto.builder().date("22.03.2022").bank("PB").baseCurrency(980)
                .baseCurrencyLit("UAH").exchangeRate(expectedExchangeRate)
                .build();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(privatDtoResponse).usingRecursiveComparison()
                .ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB",
                        "exchangeRate.saleRate", "exchangeRate.purchaseRate")
                .isEqualTo(expectedResult);
        softAssertions.assertAll();
    }

    @Test
    public void getAllPostsSchema(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("date", DATE)
                .log().all()
                .when()
                .get(PrivatEndPoints.POST)
                .then()
                .statusCode(200)
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("privatResponse.json"));
    }

    @Test
    public void checkAllRateFieldGreaterThanZero(){
        Response privatDtoResponse =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("date", DATE)
                        .log().all()
                        .when()
                        .get(PrivatEndPoints.POST)
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().response();

        SoftAssertions softAssertions = new SoftAssertions();
        List<Map> actualExchangeRateList = privatDtoResponse.jsonPath().getList("exchangeRate", Map.class);
        for (int i = 0; i < actualExchangeRateList.size(); i++) {
            softAssertions.assertThat(actualExchangeRateList.get(i).get("saleRateNB"))
                    .as("Item number " + i).asString().isGreaterThan(String.valueOf(0));
            softAssertions.assertThat(actualExchangeRateList.get(i).get("purchaseRateNB"))
                    .as("Item number " + i).asString().isGreaterThan(String.valueOf(0));
            if(actualExchangeRateList.get(i).containsValue("salesRate")) {
                softAssertions.assertThat(actualExchangeRateList.get(i).get("saleRate"))
                        .as("Item number " + i).asString().isGreaterThan(String.valueOf(0));
            }
            if(actualExchangeRateList.get(i).containsValue("purchaseRate")) {
                softAssertions.assertThat(actualExchangeRateList.get(i).get("purchaseRate"))
                        .as("Item number " + i).asString().isGreaterThan(String.valueOf(0));
            }
        }
        softAssertions.assertAll();
    }
}

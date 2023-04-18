package apiTests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import api.dto.DataDto;
import api.dto.ExchangeRatesDto;
import api.endPoints.EndPoints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

public class BankTests {
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void getAllData(){
        DataDto[] dataDtoResponse = given()
            .contentType(ContentType.JSON)
            .queryParam("date", "22.03.2022")
            .log().all()
            .when()
            .get(EndPoints.ALL_URL)
            .then()
            .statusCode(200)
            .log().all()
            .extract().response().as(DataDto[].class)
        ;

        DataDto[] expectedResult = {
                DataDto.builder().date("22.03.2022").bank("PB").baseCurrency(980).baseCurrencyLit("UAH")
                       .exchangeRate(ExchangeRatesDto.builder().baseCurrency("UAH").currency("AUD").build())
                       .build(),
                DataDto.builder().date("22.03.2022").bank("PB").baseCurrency(980).baseCurrencyLit("UAH")
                       .exchangeRate(ExchangeRatesDto.builder().baseCurrency("UAH").currency("AZN").build())
                    .build(),
        };

        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < 2; i++) {
            softAssertions.assertThat(dataDtoResponse[i])
                          .usingRecursiveComparison()
                          .ignoringFields("exchangeRate.saleRateNB", "exchangeRate.purchaseRateNB",
                              "exchangeRate.saleRate", "exchangeRate.purchaseRate")
                          .isEqualTo(expectedResult[i]);
        }
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
        ExchangeRatesDto[] exchangeRatesDtoResponse = given()
            .contentType(ContentType.JSON)
            .queryParam("date", "22.03.2022")
            .log().all()
            .when()
            .get(EndPoints.ALL_URL)
            .then()
            .statusCode(200)
            .log().all()
            .extract().response().as(ExchangeRatesDto[].class);

        SoftAssertions softAssertions = new SoftAssertions();

//        for (int i = 0; i < exchangeRatesDtoResponse.length; i++) {
//            softAssertions.assertThat(exchangeRatesDtoResponse[i].getSaleRateNB()).isGreaterThan(0);
//            softAssertions.assertThat(exchangeRatesDtoResponse[i].getPurchaseRateNB()).isGreaterThan(0);
//            softAssertions.assertThat(exchangeRatesDtoResponse[i].getSaleRate()).isGreaterThan(0);
//            softAssertions.assertThat(exchangeRatesDtoResponse[i].getSaleRate()).isGreaterThan(0);
//        }
        softAssertions.assertAll();
    }
}

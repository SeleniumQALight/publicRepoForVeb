package apiTests;

import api.endPoints.ExchangeRateDto;
import api.endPoints.PrivatDto;
import api.endPoints.EndPoints;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

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
    }
}

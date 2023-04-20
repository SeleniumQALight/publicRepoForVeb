package apiTest;

import api.dto.PrivatDto;
import api.endPoints.PrivatEndPoints;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiPrivatTest {

    final String DATE = "22.03.2022";
    final String BANK = "PB";
    final Integer BASECURRENCY = 980;
    final String BASECURRENCYLIT = "UAH";
    final String ER_BASECURRENCY = "UAH";
    final String[] ER_CURRENCY = {"AUD","AZN","BYN","CAD","CHF","CNY","CZK","DKK","EUR","GBP","GEL","HUF","ILS","JPY","KZT","MDL","NOK","PLN","SEK","SGD","TMT","TRY","UAH","USD","UZS"};
    @Test
    public void getAllPostByUserTest() {
        PrivatDto privatPostDtoResponse = given()
                .contentType(ContentType.JSON)
                .queryParam("date", "22.03.2022")
                .log().all()
                .when()
                .get(PrivatEndPoints.baseUrl)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().as( PrivatDto.class);

        Assert.assertEquals("Date is matched in post "
                , DATE, privatPostDtoResponse.getDate());
        Assert.assertEquals("BANK is not matched in post "
                , BANK, privatPostDtoResponse.getBank());
        Assert.assertEquals("BASECURRENCY is not matched in post "
                , BASECURRENCY, privatPostDtoResponse.getBaseCurrency());
        Assert.assertEquals("BASECURRENCYLIT is not matched in post "
                , BASECURRENCYLIT, privatPostDtoResponse.getBaseCurrencyLit());

        for (int i = 0; i < privatPostDtoResponse.getExchangeRate().length; i++){
            Assert.assertEquals("ER_BASECURRENCY is not matched in post "
                    , ER_BASECURRENCY, privatPostDtoResponse.getExchangeRate()[i].getBaseCurrency());
            Assert.assertEquals("ER_CURRENCY is not matched in post "
                    , ER_CURRENCY[i], privatPostDtoResponse.getExchangeRate()[i].getCurrency());

            if (privatPostDtoResponse.getExchangeRate()[i].getSaleRate() != null){
            Assert.assertTrue("SaleRate is not greater than 0 "
                    , privatPostDtoResponse.getExchangeRate()[i].getSaleRate()>0);}

            Assert.assertTrue("SaleRateNB is not greater than 0 "
                    , privatPostDtoResponse.getExchangeRate()[i].getSaleRateNB()>0);

            if (privatPostDtoResponse.getExchangeRate()[i].getPurchaseRate() != null){
            Assert.assertTrue("getPurchaseRate is not greater than 0 "
                    , privatPostDtoResponse.getExchangeRate()[i].getPurchaseRate()>0);}

            if (privatPostDtoResponse.getExchangeRate()[i].getPurchaseRateNB() != null){
            Assert.assertTrue("PurchaseRateNB is not greater than 0 "
                    , privatPostDtoResponse.getExchangeRate()[i].getPurchaseRateNB()>0);}
        }


    }

    @Test
    public void getAllPostByUserSchema(){
        given()
                .contentType(ContentType.JSON)
                .queryParam("date", "22.03.2022")
                .log().all()
                .when()
                .get(PrivatEndPoints.baseUrl)
                .then()
                .statusCode(200)
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("privatResponse.json"));
    }
}

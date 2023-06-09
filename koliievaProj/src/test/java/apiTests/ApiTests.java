package apiTests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import api.dto.AuthorDto;
import api.dto.PostDto;
import api.endPoints.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

public class ApiTests {
    final String USER_NAME = "autoapi";
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void getAllPostsByUserTest(){

        PostDto[] postDtoResponse = given()
            .contentType(ContentType.JSON)
            .log().all()
            .when()
            .get(EndPoints.POST_BY_USER, USER_NAME)
            .then()
            .statusCode(200)
            .log().all()
            .extract().response().as(PostDto[].class) //взяти і розібрати як в дто на елементи і кожний елемент масива замап на те, що написано
        ;
        logger.info("Number of Posts = " + postDtoResponse.length);
        logger.info("Title[0] = " + postDtoResponse[0].getTitle());
        logger.info("User Name [0] = " + postDtoResponse[0].getAuthor().getUsername());

        for (int i = 0; i < postDtoResponse.length; i++) {
            Assert.assertEquals("UserName is not matched in post " + i
                , USER_NAME, postDtoResponse[i].getAuthor().getUsername());
        }

        PostDto[] expectedResult = {
                PostDto.builder().title("test2").body("test body2").select1("All Users").uniquePost("no")
                       .author(AuthorDto.builder().username("autoapi").build())
                       .isVisitorOwner(false)
                       .build(),
                PostDto.builder().title("test").body("test body").select1("All Users").uniquePost("no")
                       .author(AuthorDto.builder().username("autoapi").build())
                       .isVisitorOwner(false)
                    .build(),
        };

        Assert.assertEquals("Number of posts: ", expectedResult.length, postDtoResponse.length);

        SoftAssertions softAssertions = new SoftAssertions(); //накопичуєм перевірки в цьому об'єкту
        softAssertions.assertThat(postDtoResponse)
                          .usingRecursiveComparison() //перевіряє, заходячи всередину (вкладений json)
                            .ignoringFields("id", "createdDate", "author.avatar")
                            .isEqualTo(expectedResult);
        softAssertions.assertAll();


    }

    @Test
    public void negativeGetAllPostsByUserTest(){
        String actualPersopnse =
            given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(EndPoints.POST_BY_USER, "NotValidUser")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().getBody().asString();

//        Assert.assertEquals("Message in response ",
//            "\"Sorry, invalid user requested.undefined\"",
//            actualPersopnse);

        Assert.assertEquals("Message in response ",
            "Sorry, invalid user requested.undefined",
            actualPersopnse.replace("\"",""));

    }

    @Test
    public void getAllPostsByUserPath(){
        Response actualResponse =
            given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(EndPoints.POST_BY_USER, USER_NAME)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        List<String> actualTitleList = actualResponse.jsonPath().getList("title", String.class);
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < actualTitleList.size(); i++) {
            softAssertions.assertThat(actualTitleList.get(i)).as("Item number: " + i)
                .contains("test");
        }

        List<Map> actualAuthorList = actualResponse.jsonPath().getList("author", Map.class); //розібрати як мапу
        for (int i = 0; i < actualAuthorList.size(); i++) {
            softAssertions.assertThat(actualAuthorList.get(i).get("username"))
                .as("Item number: " + i ).isEqualTo(USER_NAME);
        }

        softAssertions.assertAll();

    }

    //check type of fields
    @Test
    public void getAllPostsByUserSchema(){
        given()
            .contentType(ContentType.JSON)
            .log().all()
            .when()
            .get(EndPoints.POST_BY_USER, USER_NAME)
            .then()
            .statusCode(200)
            .log().all()
            .assertThat().body(matchesJsonSchemaInClasspath("response.json"));
    }

}

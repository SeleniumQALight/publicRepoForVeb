package apiTests;

import api.dto.AuthorDto;
import api.dto.PostDto;
import api.endPoints.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiTest {
    final String USER_NAME = "autoapi";
    Logger logger = Logger.getLogger(getClass());

    @Test
    public void getAllPostsByUserTest() {
        PostDto[] postDtoResponse = given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(EndPoints.POST_BY_USER, USER_NAME)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().as(PostDto[].class);
        logger.info("Number of posts = " + postDtoResponse.length);
        logger.info("Title[0] = " + postDtoResponse[0].getTitle());
        logger.info("User Name [0] = " + postDtoResponse[0].getAuthor().getUsername());

        for (int i = 0; i < postDtoResponse.length; i++) {
            Assert.assertEquals("UserName is not matched in post " + i, USER_NAME, postDtoResponse[i].getAuthor().getUsername());
        }

        PostDto[] expectedResult = {
                PostDto.builder().title("test2")
                        .body("test body2")
                        .select1("All Users")
                        .uniquePost("no")
                        .author(AuthorDto.builder().username("autoapi").build())
                        .isVisitorOwner(false)
                        .build(),
                PostDto.builder().title("test")
                        .body("test body")
                        .select1("All Users")
                        .uniquePost("no")
                        .author(AuthorDto.builder().username("autoapi").build())
                        .isVisitorOwner(false)
                        .build()
        };

        Assert.assertEquals("Number of posts ", expectedResult.length, postDtoResponse.length);
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(postDtoResponse)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdDate", "author.avatar")
                .isEqualTo(expectedResult);
        softAssertions.assertAll();
    }

    @Test
    public void negativeGetAllPostsByUser() {
        String actualResponse =
                given()
                        .contentType(ContentType.JSON)
                        .log().all()
                        .when()
                        .get(EndPoints.POST_BY_USER, "Not validUser")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract().response().getBody().asString();
//        Assert.assertEquals("Message in response ", "Sorry,invalid user requested.undefined", actualResponse);
        Assert.assertEquals("Message in response ", "\"Sorry, invalid user requested.undefined\"", actualResponse);
        Assert.assertEquals("Message in response ", "Sorry, invalid user requested.undefined", actualResponse.replace("\"", ""));
    }

    @Test
    public void getAllPostsByUserPathTest() {
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
            softAssertions.assertThat(actualTitleList.get(i)).as("Item number " + i)
                    .contains("test");
        }

        List<Map> actualAuthorList = actualResponse.jsonPath().getList("author", Map.class);
        for (int i = 0; i < actualAuthorList.size(); i++) {
            softAssertions.assertThat(actualAuthorList.get(i).get("username"))
                    .as("Item number " + i).isEqualTo(USER_NAME);
        }
        softAssertions.assertAll();
    }

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

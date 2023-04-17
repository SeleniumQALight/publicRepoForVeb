package apiTests;

import api.endPoints.EndPoints;
import dto.AuthorDto;
import dto.PostDto;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {
    final String USER_NAME = "autoapi";
    Logger logger = Logger.getLogger(getClass());

    @Test

    public void getAllPostsByUserTest(){
        PostDto[] postDtoResponse = given().
                contentType(ContentType.JSON).
                log().all().
                when().
                get(EndPoints.POST_BY_USER, USER_NAME).
                then().
                statusCode(200).
                log().all().
                extract().response().as(PostDto[].class)
        ;
        logger.info("Number of posts = " + postDtoResponse.length);
        logger.info("Title[0] = " + postDtoResponse[0].getTitle());
        logger.info("User name [0] " + postDtoResponse[0].getAuthor().getUsername());

        for (int i = 0; i < postDtoResponse.length; i++) {
            Assert.assertEquals("User name is not matched in post" + i,
                    USER_NAME, postDtoResponse[i].getAuthor().getUsername());
        }

        PostDto[] expectedResult = {
                PostDto.builder().title("test2").body("test body2").select1("All Users").uniquePost("no").
                        author(AuthorDto.builder().username("autoapi").build()).
                        isVisitorOwner(false).
                        build(),
                PostDto.builder().title("test").body("test body").select1("All Users").uniquePost("no").
                        author(AuthorDto.builder().username("autoapi").build()).
                        isVisitorOwner(false).
                        build(),
        };
        Assert.assertEquals("Number of posts ", expectedResult.length, postDtoResponse.length);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(postDtoResponse).usingRecursiveComparison().
                ignoringFields("id", "createdDate", "author.avatar").
                isEqualTo(expectedResult);
        softAssertions.assertAll();




    }





}

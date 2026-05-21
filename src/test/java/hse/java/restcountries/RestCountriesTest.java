package hse.java.restcountries;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestCountriesTest {
    public static final String BASE_URL = "https://restcountries.com/v3.1";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void shouldReturn200WhenFetchingAllCountries() {
        given()
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThan(0)));
    }

    @Test
    public void hasCountryWithCapitalMoscow() {
        given()
                .when()
                .get("/name/russia")
                .then()
                .statusCode(200)
                .body("[0].capital", hasItem("Moscow"));

    }

    @Test
    public void hasCountryWithNameGermany() {
        given()
                .when()
                .get("/alpha/de")
                .then()
                .statusCode(200)
                .body("[0].name.common", equalTo("Germany"));

    }

    @Test
    public void notExistingCountry() {
        given()
                .when()
                .get("/name/nonexistentcountryxyz")
                .then()
                .statusCode(404);

    }

    @Test
    public void everyCountryShouldHavePositivePopulation() {
        given()
                .when()
                .get("/all")
                .then()
                .body("population", everyItem(greaterThan(0)));
    }

    @Test
    public void officialLanguageInFranceFrench() {
        given()
                .when()
                .get("/name/france")
                .then()
                .statusCode(200)
                .body("[0].languages.values()", hasItem("French"));
    }

    @Test
    public void CanadaShouldHaveOneNeighbour() {
        given()
                .when()
                .get("/name/canada")
                .then()
                .statusCode(200)
                .body("[0].borders", hasSize(1));
    }
}

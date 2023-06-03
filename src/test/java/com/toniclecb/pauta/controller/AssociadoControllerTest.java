package com.toniclecb.pauta.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.toniclecb.pauta.model.dto.AssociadoRequestDTO;
import com.toniclecb.pauta.model.dto.AssociadoResponseDTO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class AssociadoControllerTest {
	private RequestSpecification specification;
	
	@Value(value="${local.server.port}")
	private int port;

	@BeforeAll
	public void setup() {
		specification = new RequestSpecBuilder()
				.setBasePath("/api/v1/associado")
				.setPort(port)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
    public void testInsertAssociado() {
		AssociadoRequestDTO request = new AssociadoRequestDTO("Zeltrano", "36599334067");
		
		ExtractableResponse<Response> extract = given().spec(specification)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
            .then()
                .statusCode(201)
                .extract();
        AssociadoResponseDTO response = extract
                .body().as(AssociadoResponseDTO.class);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getCpf(), response.getCpf());
	}
}

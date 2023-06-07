package com.toniclecb.pauta.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.toniclecb.pauta.model.dto.PautaRequestDTO;
import com.toniclecb.pauta.model.dto.PautaResponseDTO;
import com.toniclecb.pauta.repository.PautaRepository;

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
@TestMethodOrder(OrderAnnotation.class)
public class PautaControllerTest {
	private RequestSpecification specification;
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Mock
	private PautaRepository pautaRepository;

	@BeforeAll
	public void setup() {
		specification = new RequestSpecBuilder()
				.setUrlEncodingEnabled(false)
				.setBasePath("/api/v1/pauta")
				.setPort(port)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
    public void testInsertPauta() {
		PautaRequestDTO request = getPautaRequestDTO();
		PautaResponseDTO response = insertPauta(request);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getDescricao(), response.getDescricao());
	}

	private PautaResponseDTO insertPauta(PautaRequestDTO request) {
		ExtractableResponse<Response> extract = given().spec(specification)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post()
            .then()
                .statusCode(201)
                .extract();
        PautaResponseDTO response = extract
                .body().as(PautaResponseDTO.class);
		return response;
	}

	private PautaRequestDTO getPautaRequestDTO() {
		return new PautaRequestDTO(
				"Adidionar uma cafeteira em todas as salas",
				"É uma pauta essencial para todas as ocasiões");
	}
	
	@Test
	@Order(2)
	public void testGetPauta() {
		PautaRequestDTO request = getPautaRequestDTO();
		PautaResponseDTO pautaResponse = insertPauta(request);
		
		specification.pathParam("id", 1L);
		ExtractableResponse<Response> extract = given().spec(specification)
                .when()
                .get("/{id}")
            .then()
                .statusCode(200)
                .extract();
        PautaResponseDTO response = extract
                .body().as(PautaResponseDTO.class);
        
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(1L, response.getId());
        assertEquals(request.getNome(), response.getNome());
        assertEquals(request.getDescricao(), response.getDescricao());
	}
}

package com.tecsup.petclinic.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import ch.qos.logback.classic.Logger;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {
	private static final Logger logger
		= LoggerFactory.getLogger(OwnerControllerTest.class);
	
	private static final ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc
	
	@Test
	public void testGetOwners() throws Exception {
		int ID_FIRST_RECORD = 1;
		
		this.mockMvc.perform(get("/owners"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	
	
	public void testCreateOwner throws Exception {
	String first_name  = "Diego";
	String last_name = "Isla";
	String address = "Calle Chepen 698";
	String city = "Pacasmayo";
	String telephone = "995892707";
	
	OwnerDTO newOwner = new OwnerDTO(first_name, last_name, address, city, telephone);
	
	logger.info(newOwner.toString());
	logger.info(om.writeValueAsString(newOwner));
	
	mockMvc.perform(post("/owners")
			.content(om.writeValueAsString(newOwner))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.first_name", is(FIRST_NAME)))
			.andExpect(jsonPath("$.last_name", is(LAST_NAME)))
			.andExpect(jsonPath("$.address", is(ADDRESS)))
			.andExpect(jsonPath("$.city", is(CITY)))
			.andExpect(jsonPath("$.telephone", is(TELEPHONE)));
			
	}
	
	@Test
	public void testDeleteOwner() throws Exception {
		String first_name  = "Diego";
		String last_name = "Isla";
		String address = "Calle Chepen 698";
		String city = "Pacasmayo";
		String telephone = "995892707";
		
		OwnerDTO newOwner = new OwnerDTO(first_name, last_name, address, city, telephone);

		ResultActions mvcActions = mocMvc.perform(post("/owners")
			.content(om.writeValueAsString(newOwner))
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
		
		String response = mvcActions.andReturn().getResponse().getContentAsString();
		
		Integer id = JsonPath.parse(response).read("$.id");
		
		mockMvc.perform(delete("/owners/"+ id))
				.andExpect(status().isOk());
	}
}









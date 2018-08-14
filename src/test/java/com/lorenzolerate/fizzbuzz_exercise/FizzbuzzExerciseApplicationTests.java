package com.lorenzolerate.fizzbuzz_exercise;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class FizzbuzzExerciseApplicationTests {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void fizzbuzzControllerTest() throws Exception {
		// input equals 1, expected status 200 OK
		this.mockMvc.perform(get("/fizzbuzz/1").accept("text/plain")).andExpect(status().isOk());
		// input equals to Integer.MAX_VALUE, expected status 200 OK
		this.mockMvc.perform(get("/fizzbuzz/" + Integer.MAX_VALUE).accept("text/plain")).andExpect(status().isOk());
		// input greater than Integer.MAX_VALUE, expected status 400 Bad Request
		this.mockMvc.perform(get("/fizzbuzz/" + Integer.MAX_VALUE + 1).accept("text/plain"))
				.andExpect(status().isBadRequest());
	}
}

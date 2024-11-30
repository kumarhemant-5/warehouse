package com.warehouseapi;

import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsStringIgnoringCase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class WarehouseapiApplicationTests {
    @Autowired
	private MockMvc mvc;

    @Test
	@Order(1)
	public  void getnullProduct() throws Exception{
		mvc.perform(get("/product/get")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(400));

        mvc.perform(get("/product/vendor").param("vendor","wrong")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(400));
	}

	@Test
	@Order(2)
	public void createProducts() throws Exception{
		mvc.perform(post("/product/add")
				        .contentType(MediaType.APPLICATION_JSON_VALUE)
			        	.content(getProductDetails("pen","Ball point, Ink pen","parker",1010,5,"EUR","https://via.placeholder.com/150","SKU001").toString()))
				.andExpect(MockMvcResultMatchers.status().is(201));

		mvc.perform(post("/product/add")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(getProductDetails("Pencil","Provided with eraser backside","Apsara",510,10,"USD","https://via.placeholder.com/152","SKU002").toString()))
				.andExpect(MockMvcResultMatchers.status().is(201));

		mvc.perform(post("/product/add")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(getProductDetails("NoteBooks","Underlined","Classic",3300,14,"EUR","https://via.placeholder.com/153","SKU003").toString()))
				.andExpect(MockMvcResultMatchers.status().is(201));

		mvc.perform(post("/product/add")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(getProductDetails("Scale","Transparent","Classic",700,70,"USD","https://via.placeholder.com/154","SKU004").toString()))
				.andExpect(MockMvcResultMatchers.status().is(201));
						
	}

	public JSONObject getProductDetails(String name, String description, String vendor, int price, int stock, String currency, String image_url, String sku) {
		Map<String,Object> map = new HashMap<>();
		map.put("name",name);
		map.put("description",description);
		map.put("vendor",vendor);
		map.put("price",price);
		map.put("stocks",stock);
		map.put("currency",currency);
		map.put("image_url",image_url);
		map.put("sku",sku);
		return new JSONObject(map);
	}

	@Test
	@Order(3)
	public void getFullProduct() throws Exception{
		mvc.perform(get("/product/get")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		// "Pen", "Ball point, Ink pen", "parker", 1010, 5, "EUR", "https://via.placeholder.com/150", "SKU001"
		        .andExpect(jsonPath("$.[0].name", containsStringIgnoringCase("Pen")))
		        .andExpect(jsonPath("$.[0].description", containsStringIgnoringCase("Ball point, Ink pen")))
	         	.andExpect(jsonPath("$.[0].vendor", containsStringIgnoringCase("Parker")))
				.andExpect(jsonPath("$.[0].price", Matchers.is(1010)))
		        .andExpect(jsonPath("$.[0].stocks",  Matchers.is(5)))
		        .andExpect(jsonPath("$.[0].currency",  containsStringIgnoringCase("EUR")))
		        .andExpect(jsonPath("$.[0].image_url",  containsStringIgnoringCase("https://via.placeholder.com/150")))
		        .andExpect(jsonPath("$.[0].sku",  containsStringIgnoringCase("SKU001")))

	    // "Pencil", "Provided with eraser backside", "Apsara", 510, 10, "USD", "https://via.placeholder.com/152", "SKU002"

				.andExpect(jsonPath("$.[1].name", containsStringIgnoringCase("Pencil")))
				.andExpect(jsonPath("$.[1].description", containsStringIgnoringCase("Provided with eraser backside")))
				.andExpect(jsonPath("$.[1].vendor", containsStringIgnoringCase("Apsara")))
				.andExpect(jsonPath("$.[1].price", Matchers.is(510)))
				.andExpect(jsonPath("$.[1].stocks",  Matchers.is(10)))
				.andExpect(jsonPath("$.[1].currency",  containsStringIgnoringCase("USD")))
				.andExpect(jsonPath("$.[1].image_url",  containsStringIgnoringCase("https://via.placeholder.com/152")))
				.andExpect(jsonPath("$.[1].sku",  containsStringIgnoringCase("SKU002")))

		// "NoteBooks", "Underlined", "Classic", 3300, 14, "EUR", "https://via.placeholder.com/153", "SKU003"

				.andExpect(jsonPath("$.[2].name", containsStringIgnoringCase("NoteBooks")))
				.andExpect(jsonPath("$.[2].description", containsStringIgnoringCase("Underlined")))
				.andExpect(jsonPath("$.[2].vendor", containsStringIgnoringCase("Classic")))
				.andExpect(jsonPath("$.[2].price", Matchers.is(3300)))
				.andExpect(jsonPath("$.[2].stocks",  Matchers.is(14)))
				.andExpect(jsonPath("$.[2].currency",  containsStringIgnoringCase("EUR")))
				.andExpect(jsonPath("$.[2].image_url",  containsStringIgnoringCase("https://via.placeholder.com/153")))
				.andExpect(jsonPath("$.[2].sku",  containsStringIgnoringCase("SKU003")))

		// "Scale", "Transparent", "Classic", 700, 70, "USD", "https://via.placeholder.com/154", "SKU004"
				.andExpect(jsonPath("$.[3].name", containsStringIgnoringCase("Scale")))
				.andExpect(jsonPath("$.[3].description", containsStringIgnoringCase("Transparent")))
				.andExpect(jsonPath("$.[3].vendor", containsStringIgnoringCase("Classic")))
				.andExpect(jsonPath("$.[3].price", Matchers.is(700)))
				.andExpect(jsonPath("$.[3].stocks",  Matchers.is(70)))
				.andExpect(jsonPath("$.[3].currency",  containsStringIgnoringCase("USD")))
				.andExpect(jsonPath("$.[3].image_url",  containsStringIgnoringCase("https://via.placeholder.com/154")))
				.andExpect(jsonPath("$.[3].sku",  containsStringIgnoringCase("SKU004")));

	}

	@Test
	@Order(4)
	public void getProductWithVendor() throws Exception{
		mvc.perform(get("/product/vendor").param("vendor","Classic")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		  .andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].name", containsStringIgnoringCase("NoteBooks")))
				.andExpect(jsonPath("$.[0].description", containsStringIgnoringCase("Underlined")))
				.andExpect(jsonPath("$.[0].vendor", containsStringIgnoringCase("Classic")))
				.andExpect(jsonPath("$.[0].price", Matchers.is(3300)))
				.andExpect(jsonPath("$.[0].stocks",  Matchers.is(14)))
				.andExpect(jsonPath("$.[0].currency",  containsStringIgnoringCase("EUR")))
		        .andExpect(jsonPath("$.[0].image_url",  containsStringIgnoringCase("https://via.placeholder.com/153")))
		        .andExpect(jsonPath("$.[0].sku",  containsStringIgnoringCase("SKU003")))
				//	3	EUR	Underlined	https://via.placeholder.com/153	NoteBooks	3300	SKU003	14	Classic

		// "Scale", "Transparent", "Classic", 700, 70, "USD", "https://via.placeholder.com/154", "SKU004"
				.andExpect(jsonPath("$.[1].name", containsStringIgnoringCase("Scale")))
				.andExpect(jsonPath("$.[1].description", containsStringIgnoringCase("Transparent")))
				.andExpect(jsonPath("$.[1].vendor", containsStringIgnoringCase("Classic")))
				.andExpect(jsonPath("$.[1].price", Matchers.is(700)))
				.andExpect(jsonPath("$.[1].stocks",  Matchers.is(70)))
				.andExpect(jsonPath("$.[1].currency",  containsStringIgnoringCase("USD")))
				.andExpect(jsonPath("$.[1].image_url",  containsStringIgnoringCase("https://via.placeholder.com/154")))
				.andExpect(jsonPath("$.[1].sku",  containsStringIgnoringCase("SKU004")));
		//4	USD	Transparent	https://via.placeholder.com/154	Scale	700	SKU004	70	Classic
	}

	@Test
	@Order(5)
	public void putProductwithId() throws Exception{
		mvc.perform(put("/product/3")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(UpdateProduct(2000,8)
						.toJSONString())).andExpect(MockMvcResultMatchers.status().is(200));
		mvc.perform(get("/product/get")
				.contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(jsonPath("$.[2].name", containsStringIgnoringCase("NoteBooks")))
				.andExpect(jsonPath("$.[2].description", containsStringIgnoringCase("Underlined")))
				.andExpect(jsonPath("$.[2].vendor", containsStringIgnoringCase("Classic")))
				.andExpect(jsonPath("$.[2].price", Matchers.is(2000)))
				.andExpect(jsonPath("$.[2].stocks",  Matchers.is(8)))
				.andExpect(jsonPath("$.[2].currency",  containsStringIgnoringCase("EUR")))
				.andExpect(jsonPath("$.[2].image_url",  containsStringIgnoringCase("https://via.placeholder.com/153")))
				.andExpect(jsonPath("$.[2].sku",  containsStringIgnoringCase("SKU003")));

    }

	private JSONObject UpdateProduct(int price, int stocks) {
		Map<String,Object> map = new HashMap<>();
		map.put("price",price);
		map.put("stocks",stocks);
		return new JSONObject(map);
	}

	@Test
	@Order(6)
	public void deleteproductbyId() throws Exception{
		mvc.perform(delete("/product/4")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();

		mvc.perform(delete("/product/2")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	@Order(7)
	public void deleteAndPutProductbyBadId() throws Exception {
		mvc.perform(delete("/product/6")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(400))
				.andReturn();

		mvc.perform(delete("/product/7")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(400))
				.andReturn();

		mvc.perform(put("/product/9")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(UpdateProduct(3900,17)
						.toJSONString()))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}


}

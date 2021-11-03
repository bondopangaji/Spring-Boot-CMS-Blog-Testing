/**

MIT License

Copyright (c) [2021] [bondopangaji]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

**/

package io.github.bondopangaji.cmsapp.userintegrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.github.bondopangaji.cmsapp.models.Author;
import net.bytebuddy.utility.RandomString;

/**
 * @author bondopangaji
 *
 */
@DisplayName("Integration test for logout activity")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogoutTest {

	@Autowired
	private MockMvc mockMvc;
	
	Author author = new Author();
	Author authorLogin = new Author();
	
	@BeforeEach
	void setUp() throws Exception {
		String name = "Test";
		String email = RandomString.make(10).toLowerCase() 
				+ "@testlogout.com";
		String password = "password";

		author.setName(name);
		author.setEmail(email);
		author.setPassword(password);

		authorLogin.setName(name);
		authorLogin.setEmail(email);
		authorLogin.setPassword(password);
	}
	
	@Test
	@Order(1)
	void testLogout() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));

		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
		mockMvc.perform(get("/logout")
				.sessionAttrs(sessionAttribute))
				.andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
				.andExpect(MockMvcResultMatchers.flash()
						.attributeExists("success"))
				.andExpect(MockMvcResultMatchers.flash()
						.attribute("success", "Successfully log out!"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    System.out.println("Execution of JUNIT test file done");
	}
}

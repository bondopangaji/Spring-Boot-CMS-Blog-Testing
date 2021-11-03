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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@DisplayName("Integration test for register activity")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

	@Autowired
	private MockMvc mockMvc;

	Author author = new Author();
	Author authorWithoutName = new Author();
	Author authorWithoutEmail = new Author();
	Author authorWithoutPassword = new Author();

	@BeforeEach
	void setUpCredentials() {
		String name = "Test";
		String email = RandomString.make(10).toLowerCase() 
				+ "@testregister.com";
		String password = "password";
		String emptyValue = "";

		author.setName(name);
		author.setEmail(email);
		author.setPassword(password);

		authorWithoutName.setName(emptyValue);
		authorWithoutName.setEmail(email);
		authorWithoutName.setPassword(password);

		authorWithoutEmail.setName(name);
		authorWithoutEmail.setEmail(emptyValue);
		authorWithoutEmail.setPassword(password);

		authorWithoutPassword.setName(name);
		authorWithoutPassword.setEmail(email);
		authorWithoutPassword.setPassword(emptyValue);
	}

	@Test
	@Order(1)	
	void testRegisterWithRightCredentials() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(content()
						.string(containsString("Register a new account")));
		
		mockMvc.perform(post("/register")
				.flashAttr("author", author))
				.andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
	}

	@Test
	@Order(2)
	void testRegisterWithoutName() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(content()
				.string(containsString("Register a new account")));
	
		mockMvc.perform(post("/register")
				.flashAttr("author", authorWithoutName))
				.andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
				.andExpect(MockMvcResultMatchers.flash()
						.attributeExists("error"))
				.andExpect(MockMvcResultMatchers.flash()
						.attribute("error", "Name cannot be blank!"));
	}

	@Test
	@Order(3)
	void testRegisterWithoutEmail() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(content()
						.string(containsString("Register a new account")));

		mockMvc.perform(post("/register")
				.flashAttr("author", authorWithoutEmail))
				.andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
				.andExpect(MockMvcResultMatchers.flash()
						.attributeExists("error"))
				.andExpect(MockMvcResultMatchers.flash()
						.attribute("error", "Email cannot be blank!"));
	}

	@Test
	@Order(4)
	void testRegisterWithoutPassword() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(content()
						.string(containsString("Register a new account")));
		
		mockMvc.perform(post("/register")
					.flashAttr("author", authorWithoutPassword))
				.andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
				.andExpect(MockMvcResultMatchers.flash()
						.attributeExists("error"))
				.andExpect(MockMvcResultMatchers.flash()
						.attribute("error", "Password cannot be blank!"));
	}
}

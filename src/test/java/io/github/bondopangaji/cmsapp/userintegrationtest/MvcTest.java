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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import io.github.bondopangaji.cmsapp.models.Category;
import io.github.bondopangaji.cmsapp.models.Post;
import net.bytebuddy.utility.RandomString;

/**
 * @author bondopangaji
 *
 */
@DisplayName("Integration test for mvc activity")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {

	@Autowired
    private MockMvc mockMvc;
	
	Author author = new Author();
	Author authorLogin = new Author();
	
	@BeforeEach
	void setUp() throws Exception {
		String name = "Test";
		String email = RandomString.make(10).toLowerCase() 
				+ "@testdashboard.com";
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
	void testDashboardView() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
		
        mockMvc.perform(get("/author-dashboard")
                .sessionAttrs(sessionAttribute))
                .andExpect(status().isOk());
	}
	

		
	@Test
	@Order(2)
	void testPostListView() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/post-list")
        		.sessionAttrs(sessionAttribute))
        		.andExpect(status().isOk())
        		.andExpect(content()
        				.string(containsString("Post List")));
	}
	
	@Test
	@Order(3)
	void testAddPostView() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-post")
        		.sessionAttrs(sessionAttribute))
        		.andExpect(status().isOk())
        		.andExpect(content()
        				.string(containsString("Add New Post")));
	}
	
	@Test
	@Order(4)
	void testCreatePost() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute =
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-post")
        		.sessionAttrs(sessionAttribute));
        
        String title = "TITLE: " 
        		+ RandomString.make(8).toUpperCase();
        String content = "Content: " + 
        		RandomString.make(1000).toUpperCase();
        
        Category category = new Category();
        category.setId(4);
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        
        mockMvc.perform(post("/author-dashboard/add-post/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("post", post))
        		.andExpect(status().is3xxRedirection())
        		.andExpect(MockMvcResultMatchers
        				.redirectedUrl("/author-dashboard/post-list"))
        		.andDo(print());
	}
	
	@Test
	@Order(5)
	void testEditPost() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute =
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-post")
        		.sessionAttrs(sessionAttribute));
        
        String title = "TITLE: " + 
        		RandomString.make(8).toUpperCase();
        String content = "CONTENT: " + 
        		RandomString.make(1000).toUpperCase();
        
        Category category = new Category();
        category.setId(4);
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        
        mockMvc.perform(post("/author-dashboard/add-post/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("post", post));
        
        Long postId = post.getId();
        
        String editedTitle = "TITLE: " +
        		RandomString.make(8).toUpperCase();
        String editedContent = "CONTENT: " +
        		RandomString.make(1000).toUpperCase();
        
        Post editedPost = new Post();
        editedPost.setTitle(editedTitle);
        editedPost.setContent(editedContent);
        editedPost.setCategory(category);
        
        mockMvc.perform(get("/author-dashboard/edit-post/{id}", postId)
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("post", post))
		        .andExpect(status().isOk())
				.andExpect(content()
						.string(containsString("Edit Post")));
        
        mockMvc.perform(post("/author-dashboard/edit-post/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("post", editedPost))
		        .andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers
						.redirectedUrl("/author-dashboard/post-list"))
				.andDo(print());
	}
	
	@Test
	@Order(6)
	void testDeletePost() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-post")
        		.sessionAttrs(sessionAttribute));
        
        String title = "TITLE: " +
        		RandomString.make(8).toUpperCase();
        String content = "CONTENT: " +
        		RandomString.make(1000).toUpperCase();
        
        Category category = new Category();
        category.setId(4);
        
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        
        mockMvc.perform(post("/author-dashboard/add-post/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("post", post));
        
        Long postId = post.getId();
        
        mockMvc.perform(post("/author-dashboard/delete-post/{id}", postId)
        		.sessionAttrs(sessionAttribute))
		        .andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers
						.redirectedUrl("/author-dashboard/post-list"))
				.andDo(print());
	}
	
	@Test
	@Order(7)
	void testCategoryListView() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/category-list")
        		.sessionAttrs(sessionAttribute))
        		.andExpect(status().isOk())
        		.andExpect(content()
        				.string(containsString("Category List")));
	}
	
	@Test
	@Order(8)
	void testAddCategoryView() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-category")
        		.sessionAttrs(sessionAttribute))
        		.andExpect(status().isOk())
        		.andExpect(content()
        				.string(containsString("Add New Category")));
	}
	
	@Test
	@Order(9)
	void testCreateCategory() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-category")
        		.sessionAttrs(sessionAttribute));
        
        String name = "NAME: " + 
        		RandomString.make(8).toUpperCase();
        String description = "DESCRIPTION: " + 
        		RandomString.make(25).toUpperCase();
                
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        
        mockMvc.perform(post("/author-dashboard/add-category/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("category", category))
        		.andExpect(status().is3xxRedirection())
        		.andExpect(MockMvcResultMatchers
        				.redirectedUrl("/author-dashboard/category-list"))
        		.andDo(print());
	}
	
	@Test
	@Order(10)
	void testEditCategory() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
        mockMvc.perform(get("/author-dashboard/add-category")
        		.sessionAttrs(sessionAttribute));
        
        String name = "NAME: " + 
        		RandomString.make(8).toUpperCase();
        String description = "DESCRIPTION: " + 
        		RandomString.make(25).toUpperCase();
                
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        
        mockMvc.perform(post("/author-dashboard/add-category/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("category", category));
        
        Long categoryId = category.getId();
        
        String editedName = "TITLE: " + 
        		RandomString.make(8).toUpperCase();
        String editedDescription = "DESCRIPTION: " 
        		+ RandomString.make(25).toUpperCase();
        
        Category editedCategory = new Category();
        editedCategory.setName(editedName);
        editedCategory.setDescription(editedDescription);
        
        mockMvc.perform(get("/author-dashboard/edit-category/{id}", categoryId)
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("category", editedCategory))
		        .andExpect(status().isOk())
				.andExpect(content()
						.string(containsString("Edit Category")));
        
        mockMvc.perform(post("/author-dashboard/edit-category/saved")
        		.sessionAttrs(sessionAttribute)
        		.flashAttr("category", editedCategory))
		        .andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers
						.redirectedUrl("/author-dashboard/category-list"))
				.andDo(print());
	}
	
	@Test
	@Order(11)
	void testIndexBeforeLogin() throws Exception {
        mockMvc.perform(get("/"))
		        .andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
				.andDo(print());
	}
	
	@Test
	@Order(12)
	void testIndexAfterLogin() throws Exception {
		mockMvc.perform(post("/register").flashAttr("author", author));
		mockMvc.perform(post("/login").flashAttr("author", authorLogin));
		
		HashMap<String, Object> sessionAttribute = 
				new HashMap<String, Object>();
		sessionAttribute.put("id", author.getId());
		sessionAttribute.put("name", author.getName());
		sessionAttribute.put("email", author.getEmail());
		sessionAttribute.put("loggedIn", true);
		
		
        mockMvc.perform(get("/")
                .sessionAttrs(sessionAttribute))
		        .andExpect(status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers
						.redirectedUrl("/author-dashboard"))
				.andDo(print());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    System.out.println("Execution of JUNIT test file done");
	}

}
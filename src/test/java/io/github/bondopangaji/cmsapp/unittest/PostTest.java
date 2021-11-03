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

package io.github.bondopangaji.cmsapp.unittest;

import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.bondopangaji.cmsapp.models.Author;
import io.github.bondopangaji.cmsapp.models.Category;
import io.github.bondopangaji.cmsapp.models.Post;
import io.github.bondopangaji.cmsapp.repositories.PostRepository;
import io.github.bondopangaji.cmsapp.services.PostService;

/**
 * @author bondopangaji
 *
 */
@DisplayName("Test case for Post object")
@ExtendWith(MockitoExtension.class)
public class PostTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;
	
	Post expectedPost = new Post();
	Author author = new Author();
	Category category = new Category();
	
	Throwable e = null;
	String expectedMessage = null;
	
	@BeforeEach
	void setUp() {
		author.setId(1999L);
		author.setName("Author");
		author.setEmail("author@unittesting.com");
		author.setPassword("password");
		
		category.setId(1999L);
		category.setName("Category");
		category.setDescription("Description");
		
		expectedPost.setId(1999L);
		expectedPost.setTitle("Title");
		expectedPost.setContent("Content");
		expectedPost.setAuthor(author);
		expectedPost.setCategory(category);
	}
	
	@Test
	@Order(1)
	void testNewPost() throws Exception {
	
		postService.store(expectedPost);

		when(postRepository.getById(1999L))
			.thenReturn(expectedPost);
		
		Post actualPost = this.postRepository.getById(1999L);
		
		Assertions.assertEquals(expectedPost, actualPost);
	}

	@Test
	@Order(2)
	@SuppressWarnings("null")
	void testNewPostWithEmptyId() throws NullPointerException {
		Assertions.assertThrows(NullPointerException.class, () -> {
			expectedPost.setId((Long) null);
		});
	}

	@Test
	@Order(3)
	@SuppressWarnings("null")
	void testNewPostWithEmptyTitle() {
		String actualMessage = "Post title cannot be blank!";
		try {
			expectedPost.setTitle("");
			
			when(postRepository.save(expectedPost))
				.thenThrow(new RuntimeException(actualMessage));
			postService.store(expectedPost);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@Order(4)
	void testNewPostWithEmptyContent() throws Exception {
		String actualMessage = "Post content cannot be blank!";
		try {
			expectedPost.setContent("");

			when(postRepository.save(expectedPost))
				.thenThrow(new RuntimeException(actualMessage));
			postService.store(expectedPost);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@Order(5)
	void testPostWithEmptyAuthor() throws Exception {
		String actualMessage = "Post author cannot be null!";
		try {
			expectedPost.setAuthor(null);

			when(postRepository.save(expectedPost))
				.thenThrow(new RuntimeException(actualMessage));
			postService.store(expectedPost);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@Order(6)
	void testPostWithEmptyCategory() throws Exception {
		String actualMessage = "Post category cannot be null!";
		try {
			expectedPost.setCategory(null);

			when(postRepository.save(expectedPost))
				.thenThrow(new RuntimeException(actualMessage));
			postService.store(expectedPost);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    System.out.println("Execution of JUNIT test file done");
	}
}

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
import io.github.bondopangaji.cmsapp.repositories.AuthorRepository;
import io.github.bondopangaji.cmsapp.services.AuthorService;

/**
 * @author bondopangaji
 *
 */
@DisplayName("Test case for Author object")
@ExtendWith(MockitoExtension.class)
public class AuthorTest {
	
	@Mock
	private AuthorRepository authorRepository;

	@InjectMocks
	private AuthorService authorService;
	
	Author expectedAuthor = new Author();
	
	Throwable e = null;
	String expectedMessage = null;
	
	@BeforeEach
	void setUp() {
		expectedAuthor.setId(1999L);
		expectedAuthor.setName("Author");
		expectedAuthor.setEmail("author@unittesting.com");
		expectedAuthor.setPassword("password");
	}
	
	@Test
	@Order(1)
	void testCreateAuthor() throws Exception {
	
		authorService.register(expectedAuthor);

		when(authorRepository.getById(1999L))
			.thenReturn(expectedAuthor);
		
		Author actualAuthor = this.authorRepository.getById(1999L);
		
		Assertions.assertEquals(expectedAuthor, actualAuthor);
	}

	@Test
	@Order(2)
	@SuppressWarnings("null")
	void testCreateAuthorWithEmptyId() throws NullPointerException {
		Assertions.assertThrows(NullPointerException.class, () -> {
			expectedAuthor.setId((Long) null);
		});
	}

	@Test
	@Order(3)
	@SuppressWarnings("null")
	void testCreateAuthorWithEmptyName() throws Exception {
		String actualMessage = "Author name cannot be blank!";
		try {
			expectedAuthor.setName("");
			
			when(authorRepository.save(expectedAuthor))
				.thenThrow(new RuntimeException(actualMessage));
			
			authorService.register(expectedAuthor);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}

	@Test
	@Order(4)
	void testCreateAuthorWithEmptyEmail() throws Exception {
		String actualMessage = "Author email cannot be blank!";
		try {
			expectedAuthor.setEmail("");

			when(authorRepository.save(expectedAuthor))
				.thenThrow(new RuntimeException(actualMessage));
			authorService.register(expectedAuthor);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@Order(5)
	void testCreateAuthorWithEmptyPassword() throws Exception {
		String actualMessage = "Author password cannot be blank!";
		try {
			expectedAuthor.setPassword("");

			when(authorRepository.save(expectedAuthor))
				.thenThrow(new RuntimeException(actualMessage));
			authorService.register(expectedAuthor);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@Order(6)
	void testLoginAuthor() throws Exception {
		authorService.register(expectedAuthor);
		authorService.auth(expectedAuthor.getEmail(), expectedAuthor.getPassword());

		when(authorRepository.getById(1999L))
			.thenReturn(expectedAuthor);
		
		Author actualAuthor = this.authorRepository.getById(1999L);
		
		Assertions.assertEquals(expectedAuthor, actualAuthor);
	}

	
	@Test
	@Order(7)
	void testLoginAuthorWithEmptyEmail() throws Exception {
		String actualMessage = "Author email cannot be blank!";
		try {
			expectedAuthor.setEmail("");

			when(authorService.auth(expectedAuthor.getEmail(), 
					expectedAuthor.getPassword()))
					.thenThrow(new RuntimeException(actualMessage));
			
			authorService.auth(expectedAuthor.getEmail(), 
					expectedAuthor.getPassword());
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@Order(8)
	void testLoginAuthorWithEmptyPassword() throws Exception {
		String actualMessage = "Author password cannot be blank!";
		try {
			expectedAuthor.setPassword("");

			when(authorService.auth(expectedAuthor.getEmail(),
					expectedAuthor.getPassword()))
					.thenThrow(new RuntimeException(actualMessage));
			
			authorService.auth(expectedAuthor.getEmail(),
					expectedAuthor.getPassword());			
			
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

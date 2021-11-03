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

import io.github.bondopangaji.cmsapp.models.Category;
import io.github.bondopangaji.cmsapp.repositories.CategoryRepository;
import io.github.bondopangaji.cmsapp.services.CategoryService;

/**
 * @author bondopangaji
 *
 */
@DisplayName("Test case for Category object")
@ExtendWith(MockitoExtension.class)
public class CategoryTest {
	
	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;
	
	Category expectedCategory = new Category();
	
	Throwable e = null;
	String expectedMessage = null;
	
	@BeforeEach
	void setUp() {
		expectedCategory.setId(1999L);
		expectedCategory.setName("Category");
		expectedCategory.setDescription("Description");
	}
	
	@Test
	@Order(1)
	void testNewCategory() throws Exception {
	
		categoryService.store(expectedCategory);

		when(categoryRepository.getById(1999L))
			.thenReturn(expectedCategory);
		
		Category actualCategory = this.categoryRepository.getById(1999L);
		
		Assertions.assertEquals(expectedCategory, actualCategory);
	}

	@Test
	@Order(2)
	@SuppressWarnings("null")
	void testNewCategoryWithEmptyId() throws NullPointerException {
		Assertions.assertThrows(NullPointerException.class, () -> {
			expectedCategory.setId((Long) null);
		});
	}

	@Test
	@Order(3)
	@SuppressWarnings("null")
	void testNewCategoryWithEmptyName() {
		String actualMessage = "Category name cannot be blank!";
		try {
			expectedCategory.setName("");
			
			when(categoryRepository.save(expectedCategory))
				.thenThrow(new RuntimeException(actualMessage));
			categoryService.store(expectedCategory);
			
		} catch (RuntimeException rex) {
			e = rex;
			expectedMessage = e.getMessage();
		}
		Assertions.assertTrue(e instanceof Exception);
		Assertions.assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	@Order(4)
	void testNewCategoryWithEmptyDescription() throws Exception {
		String actualMessage = "Category description cannot be blank!";
		try {
			expectedCategory.setDescription("");

			when(categoryRepository.save(expectedCategory))
				.thenThrow(new RuntimeException(actualMessage));
			categoryService.store(expectedCategory);
			
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

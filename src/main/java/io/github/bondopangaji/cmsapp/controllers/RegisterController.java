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

package io.github.bondopangaji.cmsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.bondopangaji.cmsapp.interfaces.AuthorInterface;
import io.github.bondopangaji.cmsapp.models.Author;

/**
 * @author bondopangaji
 *
 */
@Controller
public class RegisterController {
	@Autowired
	private AuthorInterface authorInterfae;

	@GetMapping("/register")
	public String index(Model model) {

		Author author = new Author();
		model.addAttribute("author", author);

		return "register";
	}

	@PostMapping("/register")
	public String store(@ModelAttribute("author") Author author, RedirectAttributes ra) throws Exception {

		if (author.getName().equals("")) {
			ra.addFlashAttribute("error", "Name cannot be blank!");
			return "redirect:/register";
		}
		
		if (author.getEmail().equals("")) {
			ra.addFlashAttribute("error", "Email cannot be blank!");
			return "redirect:/register";
		}

		if (author.getPassword().equals("")) {
			ra.addFlashAttribute("error", "Password cannot be blank!");
			return "redirect:/register";
		}

		authorInterfae.register(author);
		return "redirect:/login";
	}
}

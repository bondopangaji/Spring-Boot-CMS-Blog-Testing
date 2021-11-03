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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.bondopangaji.cmsapp.interfaces.CategoryInterface;
import io.github.bondopangaji.cmsapp.interfaces.PostInterface;
import io.github.bondopangaji.cmsapp.models.Author;
import io.github.bondopangaji.cmsapp.models.Category;
import io.github.bondopangaji.cmsapp.models.Post;

/**
 * @author bondopangaji
 *
 */
@Controller
public class AuthorDashboardController {

    @Autowired
    private PostInterface postInterface;
    
    @Autowired
    private CategoryInterface categoryInterface;
    
    /**
     * Dashboard
     * 
     */
    
    @GetMapping("/author-dashboard")
    public String authorDashboardView() {
    	return "/author-dashboard/dashboard"; 
    }
    
	/**
	 * Post
	 *
	 */
    @GetMapping
    ("/author-dashboard/post-list")
    public String postListView(Model model) {
    	model.addAttribute("list", postInterface.getAll());
        return "/author-dashboard/post-list";
    }
    
    @GetMapping("/author-dashboard/add-post")
    public String addPostView(Model model) {
        List<Category> category = categoryInterface.getAll();
        model.addAttribute("category", category);
    	Post post = new Post();
        model.addAttribute("post", post);
        return "/author-dashboard/add-post";
    }

    @PostMapping
    ("/author-dashboard/add-post/saved")
    public String addPostStore(@ModelAttribute("post") Post post, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        
        Author authorObj = new Author();
        authorObj.setId((long) session.getAttribute("id"));
        
        post.setAuthor(authorObj);

        postInterface.store(post);
        return "redirect:/author-dashboard/post-list";
    }

    @GetMapping
    ("/author-dashboard/edit-post/{id}")
    public String editPostView(@PathVariable(value = "id") long id, Model model) {
        List<Category> category = categoryInterface.getAll();
        model.addAttribute("category", category);
        
        Post post = postInterface.getById(id);

        model.addAttribute("post", post);
        return "/author-dashboard/edit-post";
    }
    
    @PostMapping
    ("/author-dashboard/edit-post/saved")
    public String editPostStore(@ModelAttribute("post") Post post, HttpServletRequest request) throws Exception{       
    	 HttpSession session = request.getSession(true);
         
         Author authorObj = new Author();
         authorObj.setId((long) session.getAttribute("id"));
         
         post.setAuthor(authorObj);

         postInterface.store(post);
        return "redirect:/author-dashboard/post-list";
    }

    @PostMapping("/author-dashboard/delete-post/{id}")
    public String deletePost(@PathVariable(value = "id") long id) {
        postInterface.delete(id);
        return "redirect:/author-dashboard/post-list";
    }
    
    /**
	 * Category
	 * 
	 */
    @GetMapping
    ("/author-dashboard/category-list")
    public String categoryListView(Model model) {
    	model.addAttribute("list", categoryInterface.getAll());
        return "/author-dashboard/category-list";
    }
    
    @GetMapping("/author-dashboard/add-category")
    public String addCategoryView(Model model) {
    	Category category = new Category();
        model.addAttribute("category", category);
        return "/author-dashboard/add-category";
    }

    @PostMapping
    ("/author-dashboard/add-category/saved")
    public String addCategoryStore(@ModelAttribute("category") Category category, HttpServletRequest request) {
        categoryInterface.store(category);
        return "redirect:/author-dashboard/category-list";
    }

    @GetMapping
    ("/author-dashboard/edit-category/{id}")
    public String editCategoryView(@PathVariable(value = "id") long id, Model model) {      
        Category category = categoryInterface.getById(id);

        model.addAttribute("category", category);
        return "/author-dashboard/edit-category";
    }
    
    @PostMapping
    ("/author-dashboard/edit-category/saved")
    public String editCategoryStore(@ModelAttribute("category") Category category, HttpServletRequest request) throws Exception {
    	categoryInterface.store(category);
        return "redirect:/author-dashboard/category-list";
    }
    
    /**
     * Index
     * 
     */
    @GetMapping("/")
    public String index(HttpServletRequest request) {
    		return "redirect:/author-dashboard"; 
    }
}

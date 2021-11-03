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

package io.github.bondopangaji.cmsapp.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.bondopangaji.cmsapp.interfaces.PostInterface;
import io.github.bondopangaji.cmsapp.models.Post;
import io.github.bondopangaji.cmsapp.repositories.PostRepository;

/**
 * @author bondopangaji
 *
 */
@Service
public class PostService implements PostInterface {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> getAll() {
		return postRepository.findAll();
	}

	@Override
	public void store(Post post) {
		this.postRepository.save(post);
	}

	@Override
	public Post getById(long id) {
		Optional<Post> optional = postRepository.findById(id);
		
		Post post = optional.get();
		return post;
	}

	@Override
	public void delete(long id) {
		this.postRepository.deleteById(id);
	}
}

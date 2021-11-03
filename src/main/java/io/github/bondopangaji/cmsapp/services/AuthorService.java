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

import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.bondopangaji.cmsapp.interfaces.AuthorInterface;
import io.github.bondopangaji.cmsapp.models.Author;
import io.github.bondopangaji.cmsapp.repositories.AuthorRepository;

/**
 * @author bondopangaji
 */
@Service
public class AuthorService implements AuthorInterface {

	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public void register(Author author) throws Exception {
		String hashed = this.hash(author.getPassword());
		author.setPassword(hashed);

		this.authorRepository.save(author);
	}

	@Override
	public Author auth(String email, String password) throws Exception {
		Author user = authorRepository.findByEmail(email);

		if (user == null) {
			return null;
		}

		this.match(user.getPassword(), password);
		
		return user;
		
	}

	private String hash(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] messageDiggest = md.digest(password.getBytes());

		BigInteger no = new BigInteger(1, messageDiggest);

		String hashText = no.toString(16);

		return hashText;
	}

	private boolean match(String password, String rawPassword) throws Exception {
		rawPassword = this.hash(rawPassword);
		return password.equals(rawPassword);
	}
}
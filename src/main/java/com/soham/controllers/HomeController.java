package com.soham.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.soham.models.Book;
import com.soham.models.BookOffer;
import com.soham.models.Offer;
import com.soham.models.User;
import com.soham.repositories.BookOfferRepository;
import com.soham.repositories.BookRepository;
import com.soham.repositories.OfferRepository;
import com.soham.repositories.UserRepository;

@Controller
public class HomeController {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public BookRepository bookRepository;

	@Autowired
	public OfferRepository offerRepository;

	@Autowired
	public BookOfferRepository bookOfferRepository;

	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			OAuth2Authentication oauth = (OAuth2Authentication) principal;
			HashMap detail = (HashMap) oauth.getUserAuthentication().getDetails();
			String name = (String) detail.get("login");
			String avatarUrl = (String) detail.get("avatar_url");
			User user = userRepository.findByName(name);
			if (user == null) {
				user = new User(name, avatarUrl);
			} else {
				user.setAvatar(avatarUrl);
			}
			userRepository.save(user);
			session.setAttribute("user", user);
			List<Book> books = bookRepository.findOthersBooks(name);
			List<ArrayList<Book>> book2d = make2d(books);
			model.addAttribute("book2d", book2d);
			model.addAttribute("user", user);
		}
		return "index";
	}

	@RequestMapping(value = "/selectBooksRequested", method = RequestMethod.POST)
	public String selectBooksRequested(HttpSession session, @RequestParam("bookIds") String[] bookIds, Model model) {
		session.setAttribute("booksRequested", bookIds);
		User user = (User) session.getAttribute("user");
		List<Book> userBooks = bookRepository.findUserBooks(user.getName());
		List<ArrayList<Book>> book2d = make2d(userBooks);
		model.addAttribute("book2d", book2d);
		model.addAttribute("user", user);
		return "bookOffer";
	}

	@RequestMapping(value = "/selectBooksOffered", method = RequestMethod.POST)
	public String selectBooksOffered(HttpSession session, @RequestParam("bookIds") String[] bookIds, Model model) {
		String[] requestedBooks = (String[]) session.getAttribute("booksRequested");
		String[] offeredBooks = bookIds;
		User user = (User) session.getAttribute("user");
		Offer offer = new Offer(user);
		offer = offerRepository.save(offer);

		for (String bookId : requestedBooks) {
			Book book = bookRepository.findById(Long.parseLong(bookId));
			BookOffer bookOffer = new BookOffer(offer, book);
			bookOfferRepository.save(bookOffer);
		}

		for (String bookId : offeredBooks) {
			Book book = bookRepository.findById(Long.parseLong(bookId));
			BookOffer bookOffer = new BookOffer(offer, book);
			bookOfferRepository.save(bookOffer);
		}

		model.addAttribute("user", user);
		return "redirect:offers";
	}

	@RequestMapping("/offers")
	public String offers(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Offer> myOffers = offerRepository.findMyOffers(user.getId());
		List<Offer> othersOffers = offerRepository.findOthersOffers(user.getId());
		model.addAttribute("othersOffers", othersOffers);
		model.addAttribute("myOffers", myOffers);
		return "offers";
	}

	@RequestMapping("/deleteOffer/{offerId}")
	public String deleteOffer(@PathVariable(value = "offerId") Long offerId, HttpSession session, Model model) {
		offerRepository.deleteById(offerId);
		return "redirect:/offers";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}

	public List<ArrayList<Book>> make2d(List<Book> books) {
		double size = books.size();
		int rows = (int) Math.ceil(size / 3.0);
		List<ArrayList<Book>> book2d = new ArrayList<ArrayList<Book>>();

		for (int i = 0; i < rows; i++) {
			ArrayList<Book> innerBook = new ArrayList<Book>();
			for (int t = 0; t < 3; t++) {
				int index = (i * 3) + t;
				if (index >= size) {
					break;
				}
				innerBook.add(books.get(index));
			}
			book2d.add(innerBook);
		}
		return book2d;
	}

}

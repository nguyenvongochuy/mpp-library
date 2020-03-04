package mpplibrary.service;

import mpplibrary.BookNotFoundException;
import mpplibrary.dto.AuthorDto;
import mpplibrary.dto.BookCopyDto;
import mpplibrary.dto.BookDto;
import mpplibrary.model.*;
import mpplibrary.repository.BookCopyRepository;
import mpplibrary.repository.BookRepository;
import mpplibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public BookDto addBook(BookDto bookDto, AuthorDto authorDto) {
		// TODO Unique isbn
		Book newBook = new Book();
		newBook.setIsbn(bookDto.getIsbn());
		newBook.setTitle(bookDto.getTitle());
		newBook.setMaxCheckoutDate(bookDto.getMaxCheckoutPeriodInDays());

		Author author = new Author();
		author.setFirstName(authorDto.getFirstName());
		author.setLastName(authorDto.getLastName());
		author.setPhone(author.getPhone());
		author.setShortBio(author.getShortBio());

		Address address = new Address();
		address.setStreet(authorDto.getStreet());
		address.setCity(authorDto.getCity());
		address.setState( authorDto.getState());
		address.setZip(authorDto.getZipCode());

		author.setAddress(address);
		newBook.setAuthor(author);

		BookCopy bookCopy = new BookCopy();
		bookCopy.setBook(newBook);
		List<BookCopy> bookCopies = new ArrayList<>(Collections.singletonList(bookCopy));
		newBook.setBookCopies(bookCopies);
		newBook = bookRepository.save(newBook);

		bookDto.setId(newBook.getId());
		return bookDto;
	}

    public List<BookCopyDto> getBookCopiesByIsbn(String isbn) {
		List< BookCopy> bookCopies = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new).getBookCopies();
		return bookCopies.stream().map(bookCopy -> {
			return new BookCopyDto(bookCopy.getId(), bookCopy.getBook().getTitle(), isbn, bookCopy.isAvailable() );
		}).collect(Collectors.toList());
    }
}

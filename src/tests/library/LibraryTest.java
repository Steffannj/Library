package tests.library;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import project.library.Book;
import project.library.Library;
import project.library.User;

public class LibraryTest {
	Book b = new Book(1, "BookBook");
	User u = new User(1, "Stefan Njegomirovic", 0);
	Library library = new Library();
	ArrayList<Book> listOfBooks = new ArrayList<>();
	ArrayList<User> listOfUsers = new ArrayList<>();
	Path booksPath = Paths.get("booksTest.txt");
	Path usersPath = Paths.get("usersTest.txt");
	
	@Before
	public void setUp() throws IOException {
		listOfBooks.clear();
		listOfUsers.clear();
		library.writeUsers(listOfUsers, usersPath);
		library.writeBooks(listOfBooks, booksPath);
	}
	@Test
	public void shouldReturnBookDataIfBookIsWrittenToFile() throws IOException {
		listOfBooks.add(b);
		assertEquals("1,BookBook,false,null", library.writeBooks(listOfBooks, booksPath));
	}
	
	@Test
	public void shouldReturnBookDataIfBooksAreLoadedFromFile() throws ParseException, IOException {
		listOfBooks.add(b);
		library.writeBooks(listOfBooks, booksPath);
		assertEquals("1,BookBook,false", library.loadBooks(booksPath));
	}
	
	@Test
	public void shouldReturnUserDataIfUserIsWrittenToFile() throws IOException {
		listOfUsers.add(u);
		assertEquals("1 Stefan Njegomirovic 0", library.writeUsers(listOfUsers, usersPath));
	}
	
	@Test
	public void shouldReturnUserDataIfUsersAreLoadedFromFile() throws IOException {
		listOfUsers.add(u);
		library.writeUsers(listOfUsers, usersPath);
		assertEquals("1 Stefan Njegomirovic 0", library.loadUsers(usersPath));
	}
	
	@Test
	public void shouldReturnTrueIfUserIsCreated() throws IOException {
		boolean result = library.createUser(1, "Stefan Njegomirovic", listOfUsers, usersPath);
		assertTrue(result);
	}
	
	@Test
	public void shouldReturnFalseIfUserAlreadyExists() throws IOException {
		library.createUser(1, "Stefan Njegomirovic", listOfUsers, usersPath);
		boolean result = library.createUser(1, "Stefan Njegomirovic", listOfUsers, usersPath);
		assertFalse(result);
	}
	
	@Test
	public void shouldReturnFalseIfUserIdIsNegativeNum() throws IOException {
		assertFalse(library.createUser(-2, "Stefan Njegomirovic", listOfUsers, usersPath));
	}
	
	@Test
	public void shouldReturnTrueIfBookIsCreated() throws IOException {
		assertTrue(library.createBook(1, "Book Book", listOfBooks, booksPath));
	}
	
	@Test
	public void shouldReturnFalseIfBookIdAlreadyExists() throws IOException {
		library.createBook(1, "Book Book", listOfBooks, booksPath);
		assertFalse(library.createBook(1, "Book Book", listOfBooks, booksPath));
	}
	
	@Test
	public void shouldReturnFalseIfBookIdIsNegativeNum() throws IOException {
		assertFalse(library.createBook(-1, "Book Book", listOfBooks, booksPath));
	}
	
	@Test
	public void shouldReturnTrueIfBookIsSuccessfullyBorrowed() throws IOException {
		listOfBooks.add(b);
		listOfUsers.add(u);
		assertTrue(library.borrowBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath));
	}
	
	@Test
	public void shouldReturnFalseIfBookIsAlreadyBorrowed() throws IOException {
		listOfBooks.add(b);
		listOfUsers.add(u);
		library.borrowBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath);
		assertFalse(library.borrowBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath));
	}
	
	@Test
	public void shouldReturnTrueIfBookIsSuccessfullyReturned() throws IOException {
		listOfBooks.add(b);
		listOfUsers.add(u);
		library.borrowBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath);
		assertTrue(library.returnBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath));
	}
	
	@Test
	public void shouldReturnFalseIfBookIsNotBorrowedByTheUser() throws IOException {
		listOfBooks.add(b);
		listOfUsers.add(u);
		assertFalse(library.returnBook(1, 1, listOfBooks, listOfUsers, booksPath, usersPath));
	}
	
	@Test
	public void shouldReturnUsersDetails() {
		listOfUsers.add(u);
		assertEquals(u.toString(), library.usersDetails(listOfUsers));
	}
	
	@Test
	public void shouldReturnBooksDetails() {
		listOfBooks.add(b);
		assertEquals(b.toString(), library.booksDetails(listOfBooks));
	}
}

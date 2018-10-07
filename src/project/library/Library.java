package project.library;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class Library {
	
	ArrayList<Book> listOfBooks = new ArrayList<>();
	ArrayList<User> listOfUsers = new ArrayList<>();
	
	
	public String writeBooks(ArrayList<Book> booksList, Path booksPath) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(booksPath);
		String output = "";
		
		for(Book b: booksList) {
			output += b.getBookId()+","+b.getBookName()+","+b.isStatus()+","+b.getBorrowingDate();
			writer.write(output);
			writer.newLine();
		}
		
		writer.flush();
		return output;
	}
	
	public String loadBooks(Path booksPath) throws ParseException, IOException {
		Scanner reader = new Scanner(booksPath);
		listOfBooks.clear();
		reader.useDelimiter(",");
		String output = ""; 
		String line = "";
		
		while((reader.hasNextLine())) {
			line = reader.nextLine();
			Scanner ls = new Scanner(line);
			ls.useDelimiter(",");
			int bookId = ls.nextInt();
			String bookName = ls.next();
			boolean status = ls.nextBoolean();
			String date = ls.next();
			if (status) {
				SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Date dateFormat = formatter.parse(date);
				Book b = new Book(bookId, bookName, status, dateFormat);
				listOfBooks.add(b);
				output += bookId+","+bookName+","+status+","+date;
			}else {
				Book b = new Book(bookId, bookName, status);
				listOfBooks.add(b);
				output += bookId+","+bookName+","+status;
			}
			ls.close();
		}
		
		reader.close();
		return output;
	}
	
	public String writeUsers(ArrayList<User> usersList, Path usersPath) throws IOException{
		BufferedWriter writer = Files.newBufferedWriter(usersPath);
		String output = "";
		
		for(User u: usersList) {
			output += u.getUserId()+" "+u.getUserName()+" "+u.getNumOfBooks();
			writer.write(output);
			writer.newLine();
		}
		writer.flush();
		return output;
	}
	
	public String loadUsers(Path usersPath) throws IOException {
		Scanner reader = new Scanner(usersPath);
		String output = "";
		listOfUsers.clear();
		while (reader.hasNext()) {
			int userId = reader.nextInt();
			String userName = reader.next();
			while (reader.hasNext("[a-zA-Z']+")) {
				userName += " " + reader.next();
			}
			int numOfBooks = reader.nextInt();
			User u = new User(userId, userName, numOfBooks);
			output+=userId+" "+userName+" "+numOfBooks;
			listOfUsers.add(u);
		}

		reader.close();
		return output;
	}
	
	public boolean createUser(int userId, String userName, ArrayList<User> checkoutList, Path pathToWrite) throws IOException {
		User u = new User(userId, userName);
		boolean created = true;
		boolean validUser = true;
		
		for(User e: checkoutList) {
			if(e.getUserId() == userId) {
				validUser = false;
				System.out.println("User with ID "+userId+" already exists.");
				created =  false;
			}
		}
		
		if(userId < 0) {
			validUser = false;
			System.out.println("Can't create user with negative ID.");
			created =  false;
		}
		
		if(validUser) {
			checkoutList.add(u);
			writeUsers(checkoutList, pathToWrite);
			System.out.println("User successfully created.");
			created =  true;
		}
		return created;
	}
	
	public boolean createBook(int bookId, String bookName, ArrayList<Book> checkoutList, Path pathToWrite) throws IOException {
		Book book = new Book(bookId, bookName);
		boolean validBook = true;
		boolean created = true;
		
		for (Book e : checkoutList) {
			if (e.getBookId() == bookId) {
				validBook = false;
				System.out.println("Book with ID " + bookId + " already exists.");
				created = false;
			}
		}
		
		if (bookId < 0) {
			validBook = false;
			System.out.println("Can't create book with negative ID.");
			created = false;
		}
		
		if (validBook) {
			checkoutList.add(book);
			writeBooks(checkoutList, pathToWrite);
			System.out.println("Book successfully created.");
		}
		return created;
	}
	
	public boolean borrowBook(int userId, int bookId, ArrayList<Book> booksCheckoutList, ArrayList<User> usersCheckoutList, Path bookPath, Path userPath) throws IOException {
		boolean validBorrow = false;
		boolean validUser = false;
		boolean isBorrowed = false;
		int index = 0;
		
		for (Book e : booksCheckoutList) {
			if (e.getBookId() == bookId) {
				validBorrow = true;
				if (e.isStatus()) {
					validBorrow = false;
					System.out.println("The book is already borrowed.");
				}
				for (User u : usersCheckoutList) {
					if (u.getUserId() == userId && u.getNumOfBooks() < 3) {
						validUser = true;
					}
				}
			}
		}
		
		if (validUser && validBorrow) {
			java.util.Date borrowingDate = new java.util.Date();
			for (Book e : booksCheckoutList) {
				if (e.getBookId() == bookId) {
					e.setStatus(true);
					e.setBorrowingDate(borrowingDate);
				}
			}
			for (User e : usersCheckoutList) {
				if (e.getUserId() == userId) {
					e.setNumOfBooks(e.getNumOfBooks() + 1);
					index = usersCheckoutList.indexOf(e);
				}
			}
			System.out.println("A book is successfully borrowed by user: " + usersCheckoutList.get(index).getUserName());
			writeBooks(booksCheckoutList, bookPath);
			writeUsers(usersCheckoutList, userPath);
			isBorrowed = true;
		} else {
			System.out.println("A book can't be borrowed.");
		}
		
		return isBorrowed;
	}

	public boolean returnBook(int userId, int bookId, ArrayList<Book> bookCheckoutList, ArrayList<User> userCheckoutList, Path bp, Path up) throws IOException {
		boolean validReturn = false;
		boolean validUser = false;
		boolean isReturned = false;
		for (Book e : bookCheckoutList) {
			if (e.getBookId()== bookId) {
				validReturn = true;
				if (!e.isStatus()) {
					validReturn = false;
					System.out.println("A book isn't borrowed to this user.");
				}
				for (User u : userCheckoutList) {
					if (u.getUserId() == userId) {
						validUser = true;
					}
				}
			}
		}
		if (validUser && validReturn) {
			for (Book e : bookCheckoutList) {
				if (e.getBookId() == bookId) {
					e.setStatus(false);
				}
			}
			for (User e : userCheckoutList) {
				if (e.getUserId() == userId) {
					e.setNumOfBooks(e.getNumOfBooks() - 1);
				}
			}
			System.out.println("A book is successfully returned.");
			writeBooks(bookCheckoutList, bp);
			writeUsers(userCheckoutList, up);
			isReturned = true;
		} else {
			System.out.println("Wrong input.");
		}
		return isReturned;
	}
	
	public String usersDetails(ArrayList<User> usersList) {
		String details = "";
		for(User e: usersList) {
			details += e.toString();
		}
		return details;
	}
	
	public String booksDetails(ArrayList<Book> booksList) {
		String details = "";
		for(Book e: booksList) {
			details += e.toString();
		}
		return details;
	}
}

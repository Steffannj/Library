package project.library;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryMain {

	public static void main(String[] args) throws ParseException, IOException {
		Scanner input = new Scanner(System.in);
		Library l = new Library();
		Path usersPath = Paths.get("users.txt");
		Path booksPath = Paths.get("books.txt");

		l.loadBooks(booksPath);
		l.loadUsers(usersPath);
		int choice = 0;

		System.out.println("Welcome to library.");
		while (choice != 7) {
			try {
				System.out.println(
						"\nChoose one option: \n1. Create an user acount" + "\n2. Create a book\n3. Borrow a book"
								+ "\n4. Return a book\n5. Print details of existing books"
								+ "\n6. Print details of existing accounts" + "\n7. Exit");
				choice = input.nextInt();
				
				switch (choice) {
				case 1:
					System.out.println("Enter user ID: ");
					int userId = input.nextInt();
					input.nextLine();
					
					System.out.println("Enter user name: ");
					String userName = input.nextLine();
					l.createUser(userId, userName, l.listOfUsers, usersPath);
					break;
					
				case 2:
					System.out.println("Enter book ID: ");
					int bookId = input.nextInt();
					input.nextLine();
					
					System.out.println("Enter book name: ");
					String bookName = input.nextLine();
					l.createBook(bookId, bookName, l.listOfBooks, booksPath);
					break;
			
				case 3:
					System.out.println("Enter user ID: ");
					userId = input.nextInt();
					input.nextLine();
				
					System.out.println("Enter book ID ");
					bookId = input.nextInt();
					l.borrowBook(userId, bookId, l.listOfBooks, l.listOfUsers, booksPath, usersPath);
					break;
				
				case 4:
					System.out.println("Enter user ID: ");
					userId = input.nextInt();
					input.nextLine();
				
					System.out.println("Unesite broj knjige");
					bookId = input.nextInt();
					l.returnBook(userId, bookId, l.listOfBooks, l.listOfUsers, booksPath, usersPath);
					break;
		
				case 5:
					System.out.println("Details of existing books: ");
					System.out.println(l.booksDetails(l.listOfBooks));
					System.out.println();
					break;
			
				case 6:
					System.out.println("Details of existing users: ");
					System.out.println(l.usersDetails(l.listOfUsers));
					System.out.println();
					break;
				
				case 7:
					System.out.println("Goodbye.");
					break;
		
				default:
					System.out.println("Wrong choice.");
				}
			
			} catch (InputMismatchException e) {
				System.out.println("Wrong input.");
				input.nextLine();
			}
		}

		input.close();
	}

}

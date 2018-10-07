package project.library;
import java.util.Date;

public class Book {
	private int bookId;
	private String bookName;
	private boolean status;
	private Date borrowingDate;
	
	
	public Book() {
		
	}

	public Book(int bookId, String bookName) {
		this.bookId = bookId;
		this.bookName = bookName;
	}
	
	public Book(int bookId, String bookName, boolean status) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.status = status;
	}
	
	public Book(int bookId, String bookName, boolean status, Date borrowingDate) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.status = status;
		this.borrowingDate = borrowingDate;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(Date borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	@Override
	public String toString() {		
		if (status) {
			return 	"Book id: " + bookId+"\nBook name: " + bookName+"\nBook is borrowed "+
		borrowingDate+"\n";
		}else {
			return 	"Book id: " + bookId+"\nBook name: " + bookName+"\nBook isn't borrowed.\n";
		}
	}
	
	
}

package project.library;

public class User {
	private int userId;
	private String userName;
	private int numOfBooks;
	
	public User() {
		
	}

	public User(int userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public User(int userId, String userName, int numOfBooks) {
		this.userId = userId;
		this.userName = userName;
		this.numOfBooks = numOfBooks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getNumOfBooks() {
		return numOfBooks;
	}

	public void setNumOfBooks(int numOfBooks) {
		this.numOfBooks = numOfBooks;
	}

	@Override
	public String toString() {
		return 	"User id: " + userId+"\nUser name: " + userName+
				"\nNumber of books: " + numOfBooks+"\n";
	}
	
	
}

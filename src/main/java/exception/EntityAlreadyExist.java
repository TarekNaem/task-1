package exception;

public class EntityAlreadyExist extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityAlreadyExist(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EntityAlreadyExist(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EntityAlreadyExist(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}

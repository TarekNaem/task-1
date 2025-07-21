package exception;

public class EntityBadRequest extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityBadRequest(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EntityBadRequest(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EntityBadRequest(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}

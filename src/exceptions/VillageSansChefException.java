package exceptions;

public class VillageSansChefException extends NullPointerException {
	
	private static final long serialVersionUID = 1L;

	public VillageSansChefException() {
	}
	
	public VillageSansChefException(String message) {
		super(message);
	}
}

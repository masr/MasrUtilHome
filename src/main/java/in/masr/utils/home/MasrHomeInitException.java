package in.masr.utils.home;

public class MasrHomeInitException extends RuntimeException {
	public MasrHomeInitException() {
	}

	public MasrHomeInitException(String msg) {
		super(msg);
	}

	public MasrHomeInitException(String msg, Exception ex) {
		super(msg, ex);
	}
}

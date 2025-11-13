package atm.exception;

/**
 * Exception ném ra khi số tiền không hợp lệ
 */
public class InvalidAmountException extends Exception {
    
    private double invalidAmount;
    
    public InvalidAmountException(String message, double invalidAmount) {
        super(message);
        this.invalidAmount = invalidAmount;
    }
    
    public double getInvalidAmount() {
        return invalidAmount;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + 
               String.format(" (Số tiền: %,.0f VND)", invalidAmount);
    }
}
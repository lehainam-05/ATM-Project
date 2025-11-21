package atm.view;

import atm.entity.Account;
import atm.exception.InvalidCredentialsException;
import atm.service.ATMService;
import java.util.Scanner;

/**
 * UI component cho chức năng đổi mã PIN
 */
public class ChangePinUI {

    private Scanner scanner;
    private ATMService atmService;

    public ChangePinUI(Scanner scanner, ATMService atmService) {
        this.scanner = scanner;
        this.atmService = atmService;
    }

    /**
     * Đổi mã PIN
     */
    public void changePin(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│              ĐỔI MÃ PIN             │");
        System.out.println("└─────────────────────────────────────┘");

        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        try {
            //Nhập PIN cũ (có giới hạn số lần thử)
            String oldPin = null;
            while (attempts < MAX_ATTEMPTS) {
                System.out.print("\nNhập mã PIN hiện tại: ");
                oldPin = scanner.next();

                try {
                    if (!account.isCorrectPin(oldPin)) {
                        throw new InvalidCredentialsException("Mã PIN hiện tại không đúng!");
                    }
                    break; // PIN đúng, thoát vòng lặp

                } catch (InvalidCredentialsException e) {
                    attempts++;
                    System.out.println("\n✗ " + e.getMessage());

                    if (attempts < MAX_ATTEMPTS) {
                        System.out.println("→ Vui lòng thử lại (còn " + (MAX_ATTEMPTS - attempts) + " lần)\n");
                    } else {
                        System.out.println("\n✗ Bạn đã nhập sai quá nhiều lần!");
                        System.out.println("✗ Giao dịch bị hủy. Vui lòng thử lại sau.");
                        return;
                    }
                }
            }

            //Nhập PIN mới
            attempts = 0; // Reset số lần thử
            String newPin = null;
            while (attempts < MAX_ATTEMPTS) {
                System.out.print("Nhập mã PIN mới (4-6 chữ số): ");
                newPin = scanner.next();

                try {
                    // GỌI SERVICE để validate
                    atmService.validateNewPin(newPin, oldPin);
                    break; // Hợp lệ, thoát vòng lặp

                } catch (IllegalArgumentException e) {
                    attempts++;
                    System.out.println("\n✗ " + e.getMessage());

                    if (attempts < MAX_ATTEMPTS) {
                        System.out.println("→ Vui lòng thử lại (còn " + (MAX_ATTEMPTS - attempts) + " lần)\n");
                    } else {
                        System.out.println("\n✗ Bạn đã nhập sai quá nhiều lần!");
                        System.out.println("✗ Giao dịch bị hủy. Vui lòng thử lại sau.");
                        return;
                    }
                }
            }

            //Xác nhận PIN mới
            attempts = 0; // Reset số lần thử
            while (attempts < MAX_ATTEMPTS) {
                System.out.print("Xác nhận lại mã PIN mới: ");
                String confirmPin = scanner.next();

                if (!newPin.equals(confirmPin)) {
                    attempts++;
                    System.out.println("\n✗ Mã PIN xác nhận không khớp!");

                    if (attempts < MAX_ATTEMPTS) {
                        System.out.println("→ Vui lòng thử lại (còn " + (MAX_ATTEMPTS - attempts) + " lần)\n");
                    } else {
                        System.out.println("\n✗ Bạn đã nhập sai quá nhiều lần!");
                        System.out.println("✗ Giao dịch bị hủy. Vui lòng thử lại sau.");
                        return;
                    }
                } else {
                    // Xác nhận khớp, thoát vòng lặp
                    break;
                }
            }

            //Lưu PIN mới (GỌI SERVICE)
            atmService.changePin(account, oldPin, newPin);

            System.out.println("\n✓ Đổi mã PIN thành công!");
            System.out.println("✓ Vui lòng ghi nhớ mã PIN mới của bạn");
            System.out.println("✓ Không chia sẻ mã PIN với bất kỳ ai!");

        } catch (Exception e) {
            System.out.println("\n✗ Lỗi: " + e.getMessage());
            scanner.nextLine(); // Clear buffer
        }
    }
}
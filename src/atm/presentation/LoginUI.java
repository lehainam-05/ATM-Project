package atm.presentation;

import atm.data.entity.Account;
import atm.data.exception.InvalidCredentialsException;
import atm.business.ATMService;
import java.util.Scanner;
import java.io.Console;

/**
 * UI component xử lý đăng nhập
 */
public class LoginUI {

    private Scanner scanner;
    private ATMService atmService;

    public LoginUI(Scanner scanner, ATMService atmService) {
        this.scanner = scanner;
        this.atmService = atmService;
    }

    /**
     * Xử lý đăng nhập
     */
    public Account login() {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│         ĐĂNG NHẬP HỆ THỐNG          │");
        System.out.println("└─────────────────────────────────────┘");

        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            try {
                System.out.print("Nhập số tài khoản: ");
                String accountNumber = scanner.next();

                System.out.print("Nhập mã PIN: ");
                String pin = scanner.next();

                Account account = atmService.login(accountNumber, pin);

                System.out.println("\n✓ Đăng nhập thành công!");
                System.out.println("✓ Xin chào, " + account.getAccountHolderName() + "!");

                return account;

            } catch (InvalidCredentialsException e) {
                attempts++;
                System.out.println("\n✗ " + e.getMessage());

                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("✗ Bạn còn " + (MAX_ATTEMPTS - attempts) + " lần thử");
                } else {
                    System.out.println("✗ Bạn đã nhập sai quá " + MAX_ATTEMPTS + " lần!");
                    System.out.println("✗ Vui lòng liên hệ ngân hàng để được hỗ trợ.");
                    return null;
                }
            }
        }

        return null;
    }
}
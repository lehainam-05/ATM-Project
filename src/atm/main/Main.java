package atm.main;

import atm.entity.Account;
import atm.entity.Transaction;
import atm.exception.InsufficientFundsException;
import atm.exception.InvalidAmountException;
import atm.exception.InvalidCredentialsException;
import atm.service.ATMService;

import java.util.List;
import java.util.Scanner;

/**
 * Class chính chạy ứng dụng ATM
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static ATMService atmService = new ATMService();
    
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║      CHÀO MỪNG ĐẾN VỚI HỆ THỐNG ATM       ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");
        
        // Vòng lặp chính của ATM
        while (true) {
            Account loggedInAccount = login();
            
            if (loggedInAccount != null) {
                showATMMenu(loggedInAccount);
            }
            
            System.out.print("\nBạn có muốn tiếp tục sử dụng ATM? (c/k): ");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("k")) {
                System.out.println("\n✓ Cảm ơn bạn đã sử dụng dịch vụ ATM!");
                System.out.println("✓ Hẹn gặp lại!\n");
                break;
            }
        }
        
        scanner.close();
    }
    
    /**
     * Xử lý đăng nhập
     */
    private static Account login() {
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
    
    /**
     * Hiển thị menu ATM
     */
    private static void showATMMenu(Account account) {
        boolean continueSession = true;
        
        while (continueSession) {
            System.out.println("\n");
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║              MENU CHÍNH ATM               ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║  1. Kiểm tra số dư                        ║");
            System.out.println("║  2. Rút tiền                              ║");
            System.out.println("║  3. Nạp tiền                              ║");
            System.out.println("║  4. Xem lịch sử 5 giao dịch gần nhất      ║");
            System.out.println("║  5. Đăng xuất                             ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.print("Chọn chức năng (1-5): ");
            
            try {
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        checkBalance(account);
                        break;
                    case 2:
                        withdrawMoney(account);
                        break;
                    case 3:
                        depositMoney(account);
                        break;
                    case 4:
                        viewTransactionHistory(account);
                        break;
                    case 5:
                        continueSession = false;
                        System.out.println("\n✓ Đăng xuất thành công!");
                        System.out.println("✓ Đừng quên lấy thẻ của bạn!");
                        break;
                    default:
                        System.out.println("\n✗ Lựa chọn không hợp lệ! Vui lòng chọn từ 1-5.");
                }
                
            } catch (Exception e) {
                System.out.println("\n✗ Lỗi: Vui lòng nhập số từ 1-5!");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    /**
     * Kiểm tra số dư
     */
    private static void checkBalance(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│          KIỂM TRA SỐ DỦ             │");
        System.out.println("└─────────────────────────────────────┘");
        
        double balance = atmService.checkBalance(account.getAccountNumber());
        System.out.printf("Số dư hiện tại: %,15.0f VND\n", balance);
    }
    
    /**
     * Rút tiền
     */
    private static void withdrawMoney(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│             RÚT TIỀN                │");
        System.out.println("└─────────────────────────────────────┘");

        System.out.printf("Số dư hiện tại: %,15.0f VND\n\n", account.getBalance());

        // Menu chọn số tiền
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║           CHỌN SỐ TIỀN MUỐN RÚT           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║  1. 100,000 VND                           ║");
        System.out.println("║  2. 500,000 VND                           ║");
        System.out.println("║  3. 1,000,000 VND                         ║");
        System.out.println("║  4. 1,500,000 VND                         ║");
        System.out.println("║  5. 2,000,000 VND                         ║");
        System.out.println("║  6. Số khác                               ║");
        System.out.println("║  0. Hủy                                   ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.print("Chọn (0-6): ");

        try {
            int choice = scanner.nextInt();
            double amount = 0;

            switch (choice) {
                case 1:
                    amount = 100000;
                    break;
                case 2:
                    amount = 500000;
                    break;
                case 3:
                    amount = 1000000;
                    break;
                case 4:
                    amount = 1500000;
                    break;
                case 5:
                    amount = 2000000;
                    break;
                case 6:
                    // Nhập số tiền khác
                    System.out.println("\nLưu ý: Số tiền rút phải là bội số của 50,000 VND");
                    System.out.print("Nhập số tiền cần rút: ");
                    amount = scanner.nextDouble();
                    break;
                case 0:
                    System.out.println("\n✓ Đã hủy giao dịch");
                    return;
                default:
                    System.out.println("\n✗ Lựa chọn không hợp lệ!");
                    return;
            }

            // Kiểm tra số tiền > 0
            if (amount <= 0) {
                System.out.println("\n✗ Số tiền phải lớn hơn 0!");
                return;
            }

            // Thực hiện rút tiền
            atmService.withdraw(account, amount);

            System.out.println("\n✓ Rút tiền thành công!");
            System.out.printf("✓ Số tiền đã rút: %,15.0f VND\n", amount);
            System.out.printf("✓ Số dư còn lại: %,15.0f VND\n", account.getBalance());
            System.out.println("\n✓ Vui lòng nhận tiền của bạn!");

        } catch (InvalidAmountException e) {
            System.out.println("\n✗ " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("\n✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Lỗi: Vui lòng nhập số hợp lệ!");
            scanner.nextLine(); // Clear buffer
        }
    }
    
    /**
     * Nạp tiền
     */
    private static void depositMoney(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│             NẠP TIỀN                │");
        System.out.println("└─────────────────────────────────────┘");
        
        System.out.printf("Số dư hiện tại: %,15.0f VND\n", account.getBalance());
        System.out.print("Nhập số tiền cần nạp: ");
        
        try {
            double amount = scanner.nextDouble();
            
            atmService.deposit(account, amount);
            
            System.out.println("\n✓ Nạp tiền thành công!");
            System.out.printf("✓ Số tiền đã nạp: %,15.0f VND\n", amount);
            System.out.printf("✓ Số dư mới: %,15.0f VND\n", account.getBalance());
            
        } catch (InvalidAmountException e) {
            System.out.println("\n✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Lỗi: Vui lòng nhập số hợp lệ!");
            scanner.nextLine(); // Clear buffer
        }
    }
    
    /**
     * Xem lịch sử giao dịch
     */
    private static void viewTransactionHistory(Account account) {
        System.out.println("\n");
        System.out.println("┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                    LỊCH SỬ 5 GIAO DỊCH GẦN NHẤT                     │");
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
        
        List<Transaction> transactions = atmService.getTransactionHistory(account.getAccountNumber(), 5);
        
        if (transactions.isEmpty()) {
            System.out.println("\n✗ Chưa có giao dịch nào!");
        } else {
            System.out.println("\n");
            System.out.println("┌───────────────────┬─────────────┬──────────────────┬─────────────────────┐");
            System.out.println("│     Thời gian     │   Loại GD   │      Số tiền     │    Số dư sau GD     │");
            System.out.println("├───────────────────┼─────────────┼──────────────────┼─────────────────────┤");

            for (Transaction t : transactions) {
                System.out.println("│ " + t.toTableRow() + " │");
            }

            System.out.println("└───────────────────┴─────────────┴──────────────────┴─────────────────────┘");
        }
    }
}
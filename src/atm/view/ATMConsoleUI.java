package atm.view;

import atm.entity.Account;
import atm.service.ATMService;
import java.util.Scanner;

/**
 * View layer chính: điều phối các UI components
 */
public class ATMConsoleUI {

    private Scanner scanner;
    private ATMService atmService;
    private LoginUI loginUI;
    private MainMenuUI mainMenuUI;

    public ATMConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.atmService = new ATMService();
        this.loginUI = new LoginUI(scanner, atmService);
        this.mainMenuUI = new MainMenuUI(scanner, atmService);
    }

    public void start() {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║      CHÀO MỪNG ĐẾN VỚI HỆ THỐNG ATM       ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");

        // Vòng lặp chính của ATM
        while (true) {
            Account loggedInAccount = loginUI.login();

            if (loggedInAccount != null) {
                mainMenuUI.showATMMenu(loggedInAccount);
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
}
package atm.app;

import atm.view.ATMConsoleUI;

/**
 * Lớp khởi chạy ứng dụng ATM
 * Chỉ chịu trách nhiệm khởi tạo UI và chạy vòng lặp chính
 */
public class Main {
    public static void main(String[] args) {
        ATMConsoleUI ui = new ATMConsoleUI();
        try {
            ui.start();
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
            System.exit(1);
        }
    }
}
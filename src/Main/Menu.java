package Main;

import Manager.ManagePhone;
import Phone.Phone;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    ManagePhone managePhone = new ManagePhone();

    public Menu() {
    }

    public static void menu() {
        while (true) {
            try {
                System.out.println("----CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ----");
                System.out.println("Chọn chức năng theo số (để tiếp tục):");
                System.out.println("1. Xem danh sách");
                System.out.println("2. Thêm mới");
                System.out.println("3. Cập nhật");
                System.out.println("4. Xóa");
                System.out.println("5. Tìm kiếm");
                System.out.println("6. Đọc từ file");
                System.out.println("7. Ghi vào file");
                System.out.println("8. Thoát");
                System.out.println("Chọn chức năng:");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    System.out.println();
                    System.out.println("⛔ Lựa chọn không tồn tại, mời bạn nhập lại !!!");
                    System.out.println("--------------------");
                }
                switch (choice) {
                    case 1:
                        ManagePhone.displayAll();
                        break;
                    case 2:
                        ManagePhone.addPhone();
                        break;
                    case 3:
                        System.out.println("▹ Nhập số điện thoại cần sửa (+84)-933334444:");
                        String phoneEdit = scanner.nextLine();
                        if (phoneEdit.equals("")) {
                            menu();
                        } else {
                            ManagePhone.updatePhone(phoneEdit);
                        }
                        break;
                    case 4:
                        System.out.println("▹ Nhập số điện thoại cần sửa (+84)-933334444:");
                        String phoneDelete = scanner.nextLine();
                        if (phoneDelete.equals("")) {
                            menu();
                        } else {
                            ManagePhone.deletePhone(phoneDelete);
                        }
                        break;
                    case 5:
                        System.out.println("▹ Nhập từ khóa:");
                        String keyword = scanner.nextLine();
                        ManagePhone.searchPhoneByNameOrPhone(keyword);
                        break;
                    case 6:
                        ArrayList<Phone> phoneArrayList = ManagePhone.readFile(ManagePhone.PATH_NAME);
                        phoneArrayList.forEach(System.out::println);
                        System.out.println("⛔ Read file successfully !");
                        break;
                    case 7:
                        ManagePhone.writeFile(ManagePhone.getPhoneList(), ManagePhone.PATH_NAME);
                        break;
                    case 8:
                        System.exit(8);
                }

            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println();
                System.out.println("⛔ Bạn nhập sai dữ liệu, mời nhập lại !!!");
                System.out.println("--------------------");
                System.out.println();
                menu();
            }
        }
    }
}

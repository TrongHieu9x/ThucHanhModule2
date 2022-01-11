package Manager;

import Phone.Phone;
import Phone.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagePhone {
    public static final String PATH_NAME = "src/phone.csv";
    private static ArrayList<Phone> phoneList = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Validate validate = new Validate();


    public ManagePhone() {
        if (new File(PATH_NAME).length() == 0) {
            this.phoneList = new ArrayList<>();
        } else {
            this.phoneList = readFile(PATH_NAME);
        }
    }

    public static ArrayList<Phone> getPhoneList() {
        return phoneList;
    }

    public static String getGender(int choice) {
        String gender = "";
        switch (choice) {
            case 1:
                gender = "Male";
                break;
            case 2:
                gender = "Femsle";
                break;
        }
        return gender;
    }

    public static void addPhone() {
//        System.out.println("Nhập thông tịn");
        System.out.println("Nhập số điện thoại");
        String phoneNumber = enterPhoneNumber();
        System.out.println("* Nhập tên nhóm: ");
        String group = scanner.nextLine();
        System.out.println("* Nhập họ tên: ");
        String name = scanner.nextLine();
        System.out.println("* Nhập giới tính: ");
        System.out.println("1. Male");
        System.out.println("2. Female");
        int gender = Integer.parseInt(scanner.nextLine());
        System.out.println("* Nhập địa chỉ: ");
        String address = scanner.nextLine();
        System.out.println("* Nhập ngày sinh(dd - mm - yyyy):");
        String date = scanner.nextLine();
        DateTimeFormatter formatter;
        LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
        String email = enterEmail();
        if (getGender(gender).equals("")) {
            System.err.println("-Nhập sai rồi \n +" +
                               "-Nhập lại đê!!!");
            return;
        }
        for (Phone phone : phoneList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.err.println("Số điện thoại trùng\n +" +
                                     "Nhập lại đi!!!");
                return;
            }
        }

        Phone phone = new Phone(phoneNumber, group, name,getGender(gender), address, dateOfBirth, email);
        phoneList.add(phone);
        System.out.println(" * * * Thêm thành công * * * ");
    }

    public static void updatePhone(String phoneNumber) {
        Phone editPhone = null;
        for (Phone phone : phoneList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                editPhone = phone;
            }
        }
        if (editPhone != null) {
            int index = phoneList.indexOf(editPhone);
            System.out.println("▹ Nhập tên nhóm mới:");
            editPhone.setGroup(scanner.nextLine());
            System.out.println("▹ Nhập Họ tên mới:");
            editPhone.setName(scanner.nextLine());
            System.out.println("▹ Nhập giới tính mới:");
            System.out.println("▹ 1. Male");
            System.out.println("▹ 2. Female");
            int gender = Integer.parseInt(scanner.nextLine());
            editPhone.setGender(getGender(gender));
            System.out.println("▹ Nhập địa chỉ mới:");
            editPhone.setAddress(scanner.nextLine());
            System.out.println("▹ Nhập ngày sinh mới(dd-mm-yyyy):");
            String date = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            editPhone.setDateOfBirth(dateOfBirth);
            editPhone.setEmail(enterEmail());
            phoneList.set(index, editPhone);
            System.out.println("* * * Thêm thành công * * *");
        } else {
            System.err.println("Không có số trong danh bạ");
        }
    }

    public static void deletePhone(String phoneNumber) {
        Phone deletePhone = null;
        for (Phone phone : phoneList) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                deletePhone = phone;
            }
        }
        if (deletePhone != null) {
            System.out.println(" Nhập xác nhận:");
            System.out.println(" Y: Xóa");
            System.out.println(" Ký tự khác: Thoát");
            String confirm = scanner.next();
            if (confirm.equalsIgnoreCase("y")) {
                phoneList.remove(deletePhone);
//                writeFile(contactList, PATH_NAME);
                System.out.println(" * * * Xóa thành công * * * ");
            }
        } else {
            System.err.println(" Không tìm thấy danh bạ với số điện thoại trên !");
        }
    }

    public static void searchPhoneByNameOrPhone(String keyword) {
        ArrayList<Phone> phones= new ArrayList<>();
        for (Phone phone : phoneList) {
            if (validate.validatePhoneOrName(keyword, phone.getPhoneNumber()) || validate.validatePhoneOrName(keyword, phone.getName())) {
                phones.add(phone);
            }
        }
        if (phones.isEmpty()) {
            System.err.println(" Không tìm thấy danh bạ với từ khóa trên !");
        } else {
            System.out.println("Danh bạ cần tìm:");
            phones.forEach(System.out::println);
        }
    }

    public static void displayAll() {
      System.out.printf( "Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ");
      for (Phone phone : phoneList) {
            System.out.printf( phone.getPhoneNumber(), phone.getGroup(), phone.getName(), phone.getGender(), phone.getAddress());
        }
    }

    public static String enterPhoneNumber() {
        String phoneNumber;
        while (true) {
            System.out.print(" Nhập số điện thoại: ");
            String phone = scanner.nextLine();
            if (!validate.validatePhone(phone)) {
                System.err.println(" Số điện thoại không hợp lệ !!!");
                System.err.println("[Chú ý]: Số điện thoại phải có 10 số (0 - 9) định dạng: (+84)-911112222");
            } else {
                phoneNumber = phone;
                break;
            }
        }
        return phoneNumber;
    }

    private static String enterEmail() {
        String email;
        while (true) {
            System.out.print(" Nhập email: ");
            String inputEmail = scanner.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.err.println(" Email không hợp lệ !!!");
                System.err.println("[Chú ý]: Email phải có dạng: abc.company@yahoo.com/abc12.company@gmail.vn/...");
            } else {
                email = inputEmail;
                break;
            }
        }
        return email;
    }

    public static void writeFile(ArrayList<Phone> phoneList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (Phone phone : phoneList) {
                bufferedWriter.write(phone.getPhoneNumber() + "," + phone.getGroup() + "," + phone.getName() + "," + phone.getGender() + ","
                        + phone.getAddress() + "," + phone.getDateOfBirth() + "," + phone.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println(" Write file successfully !");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Phone> readFile(String path) {
        ArrayList<Phone> phones = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                phones.add(new Phone(strings[0], strings[1], strings[2], strings[3], strings[4], LocalDate.parse(strings[5], DateTimeFormatter.ISO_LOCAL_DATE), strings[6]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return phones;
    }
}

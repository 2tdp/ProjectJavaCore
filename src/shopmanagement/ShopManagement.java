/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kl100
 */
public class ShopManagement {

    ArrayList<Categories> lstCat = new ArrayList();
    ArrayList<Product> lstPr = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ShopManagement shopManager = new ShopManagement();

        try {
            shopManager.createFile();
            shopManager.readFile();

            shopManager.menu();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menu() {
        System.out.println("****************************MENU****************************"
                + "\n1. Quan ly danh muc"
                + "\n2. Quan ly san pham"
                + "\n3. Thoat");
        int choice;
        do {
            choice = checkChoice();
            switch (choice) {
                case 1:
                    menuCategory();
                    break;
                case 2:
                    menuProduct();
                    break;
                case 3:
                    try {
                        writeFile();
                    } catch (IOException ex) {
                        Logger.getLogger(ShopManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                    break;
                default:
                    System.err.println("Nhap sai. Moi ban nhap lai!");
                    break;
            }
        } while (choice < 0 || choice > 4);
    }

    private void menuCategory() {
        int choice;
        do {
            System.out.println("===========================Quan Ly Danh Muc==========================="
                    + "\n1. Danh sach danh muc"
                    + "\n2. Them danh muc"
                    + "\n3. Xoa danh muc"
                    + "\n4. Tim kiem danh muc"
                    + "\n5. Quay lai");
            choice = checkChoice();
            switch (choice) {
                case 1:
                    menuListCategory();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    searchCategory();
                    break;
                case 5:
                    menu();
                    break;
            }
        } while (choice > 0 || choice < 6);
    }

    private void searchCategory() {
        Scanner sc = new Scanner(System.in);
        String searchCat;
        boolean check = false;

        System.out.println("Nhap vao ten danh muc can tim kiem: ");
        while (check) {
            try {
                searchCat = sc.nextLine();
                for (Categories cat : lstCat) {
                    if (cat.getCatalogName().contains(searchCat)) {
                        cat.displayData();
                    }
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }
    }

    private void deleteCategory() {
        Scanner sc = new Scanner(System.in);
        int delCat;
        boolean check = false;

        System.out.println("Nhap vao ma danh muc can xoa: ");
        while (!check) {
            try {
                delCat = sc.nextInt();
                for (int i = 0; i < lstCat.size(); i++) {
                    if (lstCat.get(i).getCatalogID() == delCat) {
                        lstCat.remove(i);
                        System.out.println("Da xoa danh muc!");
                        check = true;
                    }
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }
    }

    private void addCategory() {
        Scanner sc = new Scanner(System.in);
        int n;
        boolean check = false;

        System.out.println("Nhap tong so danh muc muon them: ");
        while (!check) {
            try {
                n = sc.nextInt();
                for (int i = 0; i < n; i++) {
                    System.out.println("Danh muc thu " + (i + 1) + ": ");
                    Categories cat = new Categories();
                    cat.inputData(lstCat);
                    lstCat.add(cat);
                    check = true;
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }
    }

    private void menuListCategory() {
        int choice;

        do {
            System.out.println("**************************Danh Sach Danh Muc***************************"
                    + "\n1. Danh sach cay danh muc"
                    + "\n2. Thong tin chi tiet danh muc"
                    + "\n3. Quay lai");
            choice = checkChoice();
            switch (choice) {
                case 1:
                    printMenuCatWithLevel();
                    break;
                case 2:
                    printMenuCatDetail();
                    break;
                case 3:
                    menuCategory();
                    break;
                default:
                    System.err.println("Lua chon sai. Moi b chon lai!");
                    break;
            }
        } while (choice > 0 || choice < 4);
    }

    private void printMenuCatWithLevel() {
        if (lstCat.isEmpty()) {
            System.err.println("Chua co danh muc. Hay tao moi danh muc!");
            menuCategory();
        } else {
            printMenuMultiLevel(0, "");
        }
    }

    private void printMenuCatDetail() {
        Scanner sc = new Scanner(System.in);
        String search;
        int dem = 0;

        if (lstCat.isEmpty()) {
            System.err.println("Chua co danh muc. Hay tao moi danh muc!");
            menuCategory();
        } else {
            System.out.println("Nhap vao ten danh muc can xem thong tin: ");
            do {
                search = sc.nextLine();
                System.out.println("------------------------------------------------");
                for (Categories cat : lstCat) {
                    if (cat.getCatalogName().equalsIgnoreCase(search)) {
                        cat.displayData();
                        printDetailCat(cat.getCatalogID());
                        dem++;
                    }
                }
                if (dem == 0) {
                    System.out.println("Khong ton tai ten danh muc. Moi nhap lai!");
                }
            } while (dem == 0);
        }
    }

    private void menuProduct() {
        int choice;
        do {
            System.out.println("===========================Quan Ly San Pham============================"
                    + "\n1. Them san pham moi"
                    + "\n2. Tinh loi nhuan san pham"
                    + "\n3. Hien thi thong tin san pham"
                    + "\n4. Sap xep san pham"
                    + "\n5. Cap nhat thong tin san pham"
                    + "\n6. Cap nhan trang thai san pham"
                    + "\n7. Quay lai");
            choice = checkChoice();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    calProfit();
                    break;
                case 3:
                    menuInfoProduct();
                    break;
                case 4:
                    sortProduct();
                    break;
                case 5:
                    updateProduct();
                    break;
                case 6:
                    updateStatusProduct();
                    break;
                case 7:
                    menu();
                    break;
            }
        } while (choice > 0 || choice < 8);

    }

    private void addProduct() {
        Scanner sc = new Scanner(System.in);

        int n;
        boolean check = false;
        System.out.println("Nhap tong so san pham can them: ");
        while (!check) {
            try {
                n = sc.nextInt();
                for (int i = 0; i < n; i++) {
                    System.out.println("San pham thu " + (i + 1) + ": ");
                    Product pr = new Product();
                    pr.inputData(lstCat, lstPr);
                    lstPr.add(pr);
                    check = true;
                    System.out.println("----------------------------------------");
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }
    }

    private void calProfit() {
        System.out.println("Dang tinh toan loi nhuan...");
        if (lstPr.isEmpty()) {
            System.err.println("Chua co san pham. Hay them san pham truoc!");
            menuProduct();
        } else {
            for (Product pr : lstPr) {
                pr.calProfit();
            }
            System.out.println("Da tinh loi nhuan san pham xong!");
        }
    }

    private void menuInfoProduct() {
        int choice;
        if (lstPr.isEmpty()) {
            System.err.println("Chua co san pham. Hay them moi san pham!");
        } else {
            do {
                System.out.println("********************Thong Tin San Pham**********************"
                        + "\n1. Hien thi san pham theo danh muc"
                        + "\n2. Hien thi chi tiet san pham"
                        + "\n3. Quay lai");

                choice = checkChoice();
                switch (choice) {
                    case 1:
                        printPrWithCattegory();
                        break;
                    case 2:
                        printDetailProduct();
                        break;
                    case 3:
                        menuProduct();
                        break;
                    default:
                        break;
                }
            } while (choice > 0 || choice < 4);
        }
    }

    private void printPrWithCattegory() {
        int dem = 0;
        ArrayList<Integer> lstIDPr = new ArrayList();

        for (Categories cat : lstCat) {
            for (Product pr : lstPr) {
                if (cat.getCatalogID() == pr.getCatalogID()) {
                    lstIDPr.add(pr.getCatalogID());
                    dem++;
                }
            }
            if (dem != 0) {
                int i = 1;
                System.out.println("Thong tin san pham co trong danh muc " + "'" + cat.getCatalogName() + "': ");
                for (Product pr : lstPr) {
                    for (Integer idPr : lstIDPr) {
                        if (pr.getCatalogID() == idPr) {
                            System.out.println(i + ". " + pr.getProductName());
                            i++;
                        }
                    }
                }
                System.out.println("=> Co tong so " + dem + " san pham trong danh muc " + cat.getCatalogName());
                dem = 0;
            }
            lstIDPr.clear();
        }
    }

    private void printDetailProduct() {
        Scanner sc = new Scanner(System.in);
        String search;
        boolean check = false;

        while (!check) {
            System.out.println("Nhap ten san pham can xem: ");
            try {
                search = sc.nextLine();
                for (Product pr : lstPr) {
                    if (pr.getProductName().equalsIgnoreCase(search)) {
                        pr.displayData();
                        check = true;
                    }
                }
                if (check == false) {
                    System.err.println("Khong co san pham can tim! Moi nhap lai!");
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }
    }

    private void sortProduct() {
        int choice;
        do {
            System.out.println("*****************Sap Xep San Pham*******************"
                    + "\n1. Sap xep san pham theo gia ban tang dan"
                    + "\n2. Sap xep san pham theo loi nhuan giam dan"
                    + "\n3. Quay lai");
            choice = checkChoice();
            switch (choice) {
                case 1:
                    sortByExPort();
                    break;
                case 2:
                    sortByProfit();
                    break;
                case 3:
                    menuProduct();
                    break;
                default:
                    System.err.println("Nhap sai. Moi nhap lai!");
                    break;
            }
        } while (choice > 0 || choice < 4);
    }

    private void sortByExPort() {
        if (lstPr.isEmpty()) {
            System.err.println("Chua co san pham. Hay them san pham moi!");
            menuProduct();
        } else {
            System.out.println("Dang sap xep san pham theo gia ban tang dan...");
            System.out.println("...Da xong! "
                    + "\nDanh sach san pham sau khi sap xep: ");
            Collections.sort(lstPr, (Product pr1, Product pr2) -> (pr1.getExportPrice() > pr2.getExportPrice() ? 1 : -1));
            int i = 1;
            for (Product pr : lstPr) {
                System.out.println(i + ". ");
                pr.displayData();
                i++;
            }
        }
    }

    private void sortByProfit() {
        if (lstPr.isEmpty()) {
            System.err.println("Chua co san pham. Hay them san pham moi!");
            menuProduct();
        } else if (lstPr.get(0).getProfit() == 0f) {
            System.err.println("Ban chua tinh loi nhuan. Vui long tinh loi nhuan truoc!");
            menuProduct();
        } else {
            System.out.println("Dang sap xep san pham theo loi nhuan giam dan...");
            System.out.println("...Da xong! "
                    + "\nDanh sach san pham sau khi sap xep: ");
            Collections.sort(lstPr, (Product pr1, Product pr2) -> pr1.getProfit() < pr2.getProfit() ? 1 : -1);
            int i = 1;
            for (Product pr : lstPr) {
                System.out.println(i + ". ");
                pr.displayData();
                i++;
            }
        }
    }

    private void updateProduct() {
        Scanner sc = new Scanner(System.in);
        String prID, choice;
        boolean check = false;

        System.out.println("Nhap ma san pham can cap nhat thong tin: ");
        while (!check) {
            prID = sc.nextLine();
            for (Product pr : lstPr) {
                if (pr.getProductID().equals(prID)) {
                    System.out.println("Thong tin san pham can chinh sua: ");
                    pr.displayData();
                    System.out.println("--------------------------------------------");
                    System.out.println("Moi sua thong tin: ");
                    pr = new Product();
                    pr.inputData(lstCat, lstPr);
                    lstPr.add(pr);
                    check = true;
                    System.out.println("......Da chinh sua xong!");
                    break;
                }
            }
            if (check == false) {
                System.err.println("Khong co san pham can chinh sua!");
                System.out.println("Ban muon chinh sua tiep khong?(Y/N)");
                while (!check) {
                    choice = sc.nextLine().toUpperCase();
                    switch (choice) {
                        case "Y":
                            updateProduct();
                            check = true;
                            break;
                        case "N":
                            menuProduct();
                            check = true;
                            break;
                        default:
                            System.err.println("Nhap sai. Moi nhap lai!");
                            break;
                    }
                }
            }
        }
    }

    private void updateStatusProduct() {
        Scanner sc = new Scanner(System.in);
        String prID, choice;
        int choiceSt;
        boolean check = false;

        System.out.println("Nhap ma san pham can cap nhat trang thai: ");
        while (!check) {
            prID = sc.nextLine();
            for (Product pr : lstPr) {
                if (pr.getProductID().equals(prID)) {
                    System.out.println("Thong tin san pham can chinh sua: ");
                    pr.displayData();
                    System.out.println("--------------------------------------------");
                    do {
                        System.out.println("Moi cap nhat trang thai: "
                                + "\n1. Hoat dong"
                                + "\n2. Khong hoat dong");
                        choiceSt = sc.nextInt();
                        switch (choiceSt) {
                            case 1:
                                pr.setProductStatus(true);
                                break;
                            case 2:
                                pr.setProductStatus(false);
                                break;
                            default:
                                System.err.println("Lua chon sai. Moi chon lai!");
                                break;
                        }
                    } while (choiceSt < 1 || choiceSt > 2);
                    System.out.println("......Da chinh sua xong!");
                    check = true;
                    break;
                }
            }
            if (check == false) {
                System.err.println("Khong co san pham can chinh sua!");
                System.out.println("Ban muon chinh sua tiep khong?(Y/N)");
                while (!check) {
                    choice = sc.nextLine().toUpperCase();
                    switch (choice) {
                        case "Y":
                            updateStatusProduct();
                            check = true;
                            break;
                        case "N":
                            menuProduct();
                            check = true;
                            break;
                        default:
                            System.err.println("Nhap sai. Moi nhap lai!");
                            break;
                    }
                }
            }
        }
    }

    private Integer checkChoice() {
        Scanner sc = new Scanner(System.in);
        int chooseMenu = 0;
        boolean checkMenu = false;
        System.out.println("Nhap lua chon cua ban: ");
        while (!checkMenu) {
            try {
                chooseMenu = sc.nextInt();
                checkMenu = true;
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi ban nhap lai!");
                sc.nextLine();
            }
        }
        System.out.println("----------------------------------------------------");
        return chooseMenu;
    }

    private void printMenuMultiLevel(int prId, String subtitle) {
        int cnt = 1;
        for (int i = 0; i < lstCat.size(); i++) {
            if (lstCat.get(i).getParentID() == prId) {
                System.out.println(subtitle + cnt + ". " + lstCat.get(i).getCatalogName());
                String sub = "  " + subtitle + cnt + ".";
                printMenuMultiLevel(lstCat.get(i).getCatalogID(), sub);
                cnt++;
            }
        }
    }

    private void printDetailCat(int prId) {
        for (int i = 0; i < lstCat.size(); i++) {
            if (lstCat.get(i).getParentID() == prId) {
                lstCat.get(i).displayData();
                printDetailCat(lstCat.get(i).getCatalogID());
            }
        }
    }

    private void createFile() throws IOException {
        String filePathCategory = "Category.txt";
        String filePathProduct = "Product.txt";

        File fileCat = new File(filePathCategory);
        if (!fileCat.exists()) {
            fileCat.createNewFile();
        }

        File filePr = new File(filePathProduct);
        if (!filePr.exists()) {
            filePr.createNewFile();
        }
    }

    private void writeFile() throws FileNotFoundException, IOException {

        FileOutputStream fosCat = new FileOutputStream("Category.txt");
        BufferedWriter bwCat = new BufferedWriter(new OutputStreamWriter(fosCat));
        for (int i = 0; i < lstCat.size(); i++) {
            bwCat.write(lstCat.get(i).writeCat());
            bwCat.newLine();
        }
        bwCat.flush();
        bwCat.close();
        fosCat.close();

        FileOutputStream fosPr = new FileOutputStream("Product.txt");
        BufferedWriter bwPr = new BufferedWriter(new OutputStreamWriter(fosPr));
        for (int i = 0; i < lstPr.size(); i++) {
            bwPr.write(lstPr.get(i).writePr());
            bwPr.newLine();
        }
        bwPr.flush();
        bwPr.close();
        fosPr.close();

    }

    private void readFile() throws IOException, ClassNotFoundException {

        FileInputStream fisCat = new FileInputStream("Category.txt");
        BufferedReader brCat = new BufferedReader(new InputStreamReader(fisCat));
        String lineCat;
        while ((lineCat = brCat.readLine()) != null) {
            String[] parts = lineCat.split("-");
            Categories cat = new Categories();

            cat.setCatalogID(Integer.valueOf(parts[0]));
            cat.setCatalogName(parts[1].trim());
            cat.setDescriptions(parts[2].trim());
            if ("Hoat dong".equals(parts[3].trim())) {
                cat.setCatalogStatus(true);
            } else if ("Khong hoat dong".equals(parts[3].trim())) {
                cat.setCatalogStatus(false);
            }
            cat.setParentID(Integer.valueOf(parts[4]));
            lstCat.add(cat);
        }

        FileInputStream fisPr = new FileInputStream("Product.txt");
        BufferedReader brPr = new BufferedReader(new InputStreamReader(fisPr));
        String linePr;
        while ((linePr = brPr.readLine()) != null) {
            String[] parts = linePr.split("-");
            Product pr = new Product();

            pr.setProductID(parts[0].trim());
            pr.setProductName(parts[1].trim());
            pr.setTitle(parts[2].trim());
            pr.setImportPrice(Float.valueOf(parts[3]));
            pr.setExportPrice(Float.valueOf(parts[4]));
            pr.setProfit(Float.valueOf(parts[5]));
            pr.setDescriptions(parts[6].trim());
            if ("Hoat dong".equals(parts[7].trim())) {
                pr.setProductStatus(true);
            } else if ("Khong hoat dong".equals(parts[7].trim())) {
                pr.setProductStatus(false);
            }
            pr.setCatalogID(Integer.valueOf(parts[8]));
            lstPr.add(pr);
        }
    }

    private void fakeData() {
        // Danh mục gốc
        lstCat.add(new Categories(1, "Do dien", "aaaaaaaaa", true, 0));
        lstCat.add(new Categories(2, "Thực phẩm", "aaaaaaaaaaaa", true, 0));
        lstCat.add(new Categories(3, "Vệ sinh", "aaaaaaaa", false, 0));

        // Danh mục cấp 1
        lstCat.add(new Categories(4, "Quạt", "aaaaaaaaa", true, 1));
        lstCat.add(new Categories(5, "Tủ lạnh", "aaaaaaaaa", true, 1));
        lstCat.add(new Categories(6, "Bánh", "aaaaaaaaa", true, 2));
        lstCat.add(new Categories(7, "Bàn chải", "aaaaaaaaa", true, 3));
        lstCat.add(new Categories(8, "Kem đánh răng", "aaaaaaaaa", true, 3));

        // Danh mục cấp 2
        lstCat.add(new Categories(9, "Tủ đá", "aaaaaaaaa", true, 5));
        lstCat.add(new Categories(10, "Tủ lạnh 3 ngăn", "aaaaaaaaa", true, 5));
        lstCat.add(new Categories(11, "Bàn chải điện", "aaaaaaaaa", true, 7));
        lstCat.add(new Categories(12, "Bàn chải từ", "aaaaaaaaa", true, 7));
        lstCat.add(new Categories(13, "Bàn chải siêu âm", "aaaaaaaaa", true, 7));

        // Danh mục cấp 3
        lstCat.add(new Categories(14, "Chạy pin", "aaaaaaaaa", true, 11));
        lstCat.add(new Categories(15, "Sạc USB", "aaaaaaaaa", true, 11));

        //Product
        lstPr.add(new Product("C111", "Gucci Mode", "aaaaaaaa", 150, 180, 0, "aaaaaaaaaaaaaa", true, 10));
        lstPr.add(new Product("C115", "Luis Vuiton", "aaaaaaaa", 120, 170, 0, "aaaaaaaaaaaaaa", false, 5));
        lstPr.add(new Product("C120", "MLB Mode", "aaaaaaaa", 100, 180, 0, "aaaaaaaaaaaaaa", false, 12));
        lstPr.add(new Product("C125", "Banh dau xanh", "aaaaaaaa", 320, 600, 0, "aaaaaaaaaaaaaa", true, 15));
        lstPr.add(new Product("C130", "Banh gai", "aaaaaaaa", 150, 180, 0, "aaaaaaaaaaaaaa", false, 8));
    }
}

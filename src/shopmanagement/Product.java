/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kl100
 */
public class Product implements IProduct, Serializable {

    private String productID;
    private String productName;
    private String title;
    private float importPrice;
    private float exportPrice;
    private float profit;
    private String descriptions;
    private boolean productStatus;
    private int catalogID;

    public Product() {
    }

    public Product(String productID, String productName, String title, float importPrice, float exportPrice, float profit, String descriptions, boolean productStatus, int catalogID) {
        this.productID = productID;
        this.productName = productName;
        this.title = title;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.descriptions = descriptions;
        this.productStatus = productStatus;
        this.catalogID = catalogID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(float importPrice) {
        this.importPrice = importPrice;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    @Override
    public void inputData(ArrayList<Categories> lstCategories, ArrayList<Product> lstProduct) {
        Scanner sc = new Scanner(System.in);

        boolean checkID = true, checkName = true, checkImportPrice = false, checkExportPrice = false, checkCatalogID = false;
        String chooseStPr, chooseStIDPr;
        int dem;

        System.out.println("Nhap ma san pham(4 ky tu): ");
        do {
            dem = 0;
            System.out.print("C");
            productID = "C" + sc.nextLine();
            if (productID.length() > 4) {
                System.err.println("Nhap sai. Moi nhap lai!");
            } else if (!lstProduct.isEmpty()) {
                for (Product pr : lstProduct) {
                    if (pr.getProductID().equalsIgnoreCase(productID)) {
                        System.err.println("Ma da ton tai. Moi nhap lai!");
                        checkID = false;
                        dem++;
                    }
                }
                if (dem == 0) {
                    checkID = true;
                }
            } else {
                checkID = true;
            }
        } while (productID.length() > 4 || checkID == false);

        System.out.println("Nhap ten san pham(6 - 50 ky tu): ");
        do {
            dem = 0;
            productName = sc.nextLine();
            if (productName.length() < 6 || productName.length() > 50) {
                System.err.println("Nhap sai. Moi nhap lai!");
            } else if (!lstProduct.isEmpty()) {
                for (Product pr : lstProduct) {
                    if (pr.getProductName().equalsIgnoreCase(productName)) {
                        System.err.println("Ten da ton tai. Moi nhap lai!");
                        checkName = false;
                        dem++;
                    }
                }
                if (dem == 0) {
                    checkName = true;
                }
            } else {
                checkName = true;
            }
        } while (productName.length() < 6 || productName.length() > 50 || checkName == false);

        System.out.println("Nhap tieu de san pham(6 - 30 ky tu): ");
        do {
            title = sc.nextLine();
            if (title.length() < 6 || title.length() > 30) {
                System.err.println("Nhap sai. Moi nhap lai!");
            }
        } while (title.length() < 6 || title.length() > 30);

        System.out.println("Nhap gia nhap san pham: ");
        while (!checkImportPrice) {
            try {
                importPrice = sc.nextFloat();
                checkImportPrice = true;
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }

        System.out.println("Nhap gia ban san pham: ");
        while (!checkExportPrice) {
            try {
                exportPrice = sc.nextFloat();
                if (exportPrice - importPrice < importPrice * MIN_INTEREST_RATE) {
                    System.err.println("Gia ban phai lon hon gia nhap it nhat 0.2 lan! Moi nhap lai gia!");
                } else {
                    checkExportPrice = true;
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi nhap lai!");
                sc.nextLine();
            }
        }

        System.out.println("Nhap mo ta san pham: ");
        sc.nextLine();
        do {
            descriptions = sc.nextLine();
            if (descriptions.length() == 0) {
                System.err.println("Khong duoc de trong mo ta!");
            }
        } while (descriptions.length() == 0);

        System.out.println("Nhap trang thai san pham(Y/N): ");
        while (true) {
            chooseStPr = sc.nextLine().toUpperCase();
            switch (chooseStPr) {
                case "Y":
                    productStatus = true;
                    break;
                case "N":
                    productStatus = false;
                    break;
                default:
                    System.err.println("Nhap sai. Moi nhap lai!");
                    break;
            }
            if (chooseStPr.equals("Y") || chooseStPr.equals("N")) {
                break;
            }
        }

        while (!checkCatalogID) {
            System.out.println("Nhap ma danh muc cua san pham: ");
            catalogID = sc.nextInt();
            if (!lstCategories.isEmpty()) {
                for (Categories cate : lstCategories) {
                    if (cate.getCatalogID() == catalogID) {
                        System.out.println("Danh muc ban chon la " + "'" + cate.getCatalogName() + "'" + ", vui long xac nhan(Y/N): ");
                        sc.nextLine();
                        chooseStIDPr = sc.nextLine().toUpperCase();
                        switch (chooseStIDPr) {
                            case "Y":
                                checkCatalogID = true;
                                break;
                            case "N":
                                checkCatalogID = false;
                                break;
                            default:
                                System.err.println("Lua chon sai. Moi chon lai!");
                                break;
                        }
                        if (chooseStIDPr.equals("Y") || chooseStIDPr.equals("N")) {
                            break;
                        }
                    }
                }
            } else {
                System.err.println("Chua co danh muc. Hay tao danh muc truoc!");
                checkCatalogID = false;
            }
        }
    }

    @Override
    public void displayData() {
        System.out.println("Ma san pham: " + productID + "\tTen san pham: " + productName + "\tTieu de: " + title
                + "\nGia nhap: " + importPrice + "\tGia ban: " + exportPrice + "\tLoi nhuan: " + profit
                + "\nMo ta: " + descriptions
                + "\nTrang thai: " + (productStatus ? "Hoat dong" : "Khong hoat dong") + "\nMa danh muc: " + catalogID);
    }

    @Override
    public void calProfit() {
        profit = exportPrice - importPrice;
    }

    public String writePr() {
        return (productID + "-" + productName + "-" + title + "-" + importPrice + "-" + exportPrice + "-" + profit + "-"
                + descriptions + "-" + (productStatus ? "Hoat dong" : "Khong hoat dong") + "-" + catalogID);
    }
}

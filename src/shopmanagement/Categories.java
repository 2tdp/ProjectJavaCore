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
public class Categories implements ICategories, Serializable {

    private int catalogID;
    private String catalogName;
    private String descriptions;
    private boolean catalogStatus;
    private int parentID;

    public Categories() {
        super();
    }

    public Categories(int catalogID, String catalogName, String descriptions, boolean catalogStatus, int parentID) {
        super();
        this.catalogID = catalogID;
        this.catalogName = catalogName;
        this.descriptions = descriptions;
        this.catalogStatus = catalogStatus;
        this.parentID = parentID;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    @Override
    public void inputData(ArrayList<Categories> lstCategories) {
        Scanner sc = new Scanner(System.in);

        boolean checkID = false, checkPrID = false;
        String choose;
        int dem = 0, b;

        System.out.println("Nhap ma danh muc(lon hon 0): ");
        while (!checkID) {
            try {
                catalogID = sc.nextInt();
                if (catalogID <= 0) {
                    System.err.println("Nhap sai. Moi ban nhap lai!");
                } else if (!lstCategories.isEmpty()) {
                    for (Categories cat : lstCategories) {
                        if (cat.getCatalogID() == catalogID) {
                            System.err.println("Ma danh muc da ton tai. Moi ban nhap lai!");
                        }
                    }
                    checkID = true;
                } else {
                    checkID = true;
                }
            } catch (Exception e) {
                System.err.println("Nhap sai. Moi ban nhap lai!");
                sc.nextLine();
            }
        }

        System.out.println("Nhap ten danh muc(6 - 30 ky tu): ");
        sc.nextLine();
        do {
            catalogName = sc.nextLine();
            if (catalogName.length() < 6 || catalogName.length() > 30) {
                System.err.println("Nhap sai. Moi nhap lai!");
            }
        } while (catalogName.length() < 6 || catalogName.length() > 30);

        System.out.println("Nhap mo ta danh muc: ");
        do {
            descriptions = sc.nextLine();
            if (descriptions.length() == 0) {
                System.err.println("Ban khong duoc de trong mo ta!");
            }
        } while (descriptions.length() == 0);

        System.out.println("Nhap trang thai danh muc(Y/N): ");
        while (true) {
            choose = sc.nextLine().toUpperCase();
            switch (choose) {
                case "Y":
                    catalogStatus = true;
                    break;
                case "N":
                    catalogStatus = false;
                    break;
                default:
                    System.err.println("Nhap sai. Moi nhap lai!");
                    break;
            }
            if (choose.equals("Y") || choose.equals("N")) {
                break;
            }
        }

        System.out.println("Nhap ma danh muc cha: ");
        while (!checkPrID) {
            parentID = sc.nextInt();
            for (Categories cate : lstCategories) {
                if (cate.getCatalogID() == parentID) {
                    dem++;
                }
            }
            if (dem != 0) {
                break;
            } else {
                System.out.println("Ma cha khong ton tai. Moi lua chon: "
                        + "\n1. Nhap lai ma cha"
                        + "\n2. Chi dinh lam ma cha");
                sc.nextLine();
                b = sc.nextInt();
                switch (b) {
                    case 1:
                        System.out.println("Nhap lai ma cha: ");
                        break;
                    case 2:
                        parentID = 0;
                        checkPrID = true;
                        break;
                    default:
                        System.err.println("Chon sai. Moi chon lai!");
                        break;
                }
            }

        }
    }

    @Override
    public void displayData() {
        System.out.println("Ma danh muc: " + catalogID + " - Ten danh muc: " + catalogName + "\nMo ta: " + descriptions
                + "\nTrang thai: " + (catalogStatus ? "Hoat dong" : "Khong hoat dong") + " - Ma danh muc cha: " + parentID);
        System.out.println("----------------------------------------------------");
    }

    public String writeCat() {
        return (catalogID + "-" + catalogName + "-" + descriptions + "-" + (catalogStatus ? "Hoat dong" : "Khong hoat dong") + "-" + parentID);
    }

}

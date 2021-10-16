/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kl100
 */
public interface IProduct {

    public float MIN_INTEREST_RATE = 0.2f;

    public void inputData(ArrayList<Categories> categories, ArrayList<Product> product);

    public void displayData();

    public void calProfit();
}

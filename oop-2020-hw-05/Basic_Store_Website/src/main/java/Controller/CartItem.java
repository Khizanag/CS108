package Controller;

import Model.Product;

public class CartItem {

    private Product product;
    private int count;

    public CartItem(Product product, int count){
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

package Controller;

import Model.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Session {

    private HttpSession session;
    private Map<String, CartItem> cart;

    public Session(HttpSession session){
        cart = new HashMap<>();
        this.session = session;
    }

    public Map<String, CartItem> getCartProducts(){
        System.out.println("map size: " + cart.size());
        return cart;
    }

    public double getTotalCost(){
        double cost = 0;
        for(String productID : cart.keySet()){
            Product product = cart.get(productID).getProduct();
            int count = cart.get(productID).getCount();
            cost += count * product.getPrice();
        }
        return cost;
    }

    public void updateCart(HttpServletRequest request){
        Map<String, CartItem> newCart = new HashMap<>();

        for(String productID : cart.keySet()) {
            CartItem item = cart.get(productID);
            Product product = item.getProduct();

            int count = item.getCount();
            try {
                count = Integer.parseInt(request.getParameter(productID));
            } catch (NumberFormatException ignored) {
                System.out.println("Number didnt parsed");
            }

            if (count != 0) {
                cart.get(productID).setCount(count);
                newCart.put(productID, cart.get(productID));
            }
        }
        cart = newCart;
    }

    public void addToCart(Product product){
       if(cart.containsKey(product.getProductID())){
           CartItem newItem = new CartItem(
                   product,
                   cart.get(product.getProductID()).getCount()+1);
           cart.put(product.getProductID(), newItem);
       } else {
           CartItem newItem = new CartItem(product, 1);
           cart.put(product.getProductID(), newItem);
       }
    }
}

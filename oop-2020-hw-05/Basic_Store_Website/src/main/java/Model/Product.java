package Model;

public class Product {

    private String productid;
    private String name;
    private String imagefile;
    private double price;

    public Product(String productid, String name, String imagefile, double price){
        this.productid = productid;
        this.name = name;
        this.imagefile = imagefile;
        this.price = price;
    }

    public String getProductID(){ return productid; }
    public String getName(){ return name; }
    public String getImagefile(){ return imagefile; }
    public double getPrice(){ return price; }

    @Override
    public boolean equals(Object o){
        Product other = (Product)o;
        return this.productid == other.productid;
    }

}

package com.example.solemates;

public class POJOGetCategoryWiseProduct {

    String id,categoryname,productimage,shopname,rating,shoesname,productdiscription,productprice,productoffer;

    public POJOGetCategoryWiseProduct(String id, String categoryname, String productimage,
                                      String shopname, String rating, String shoesname,
                                      String productdiscription, String productprice, String productoffer) {
        this.id = id;
        this.categoryname = categoryname;
        this.productimage = productimage;
        this.shopname = shopname;
        this.rating = rating;
        this.shoesname = shoesname;
        this.productdiscription = productdiscription;
        this.productprice = productprice;
        this.productoffer = productoffer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getShoesname() {
        return shoesname;
    }

    public void setShoesname(String shoesname) {
        this.shoesname = shoesname;
    }

    public String getProductdiscription() {
        return productdiscription;
    }

    public void setProductdiscription(String productdiscription) {
        this.productdiscription = productdiscription;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductoffer() {
        return productoffer;
    }

    public void setProductoffer(String productoffer) {
        this.productoffer = productoffer;
    }
}

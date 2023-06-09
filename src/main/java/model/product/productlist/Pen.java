package model.product.productlist;

import model.product.type_of_products.Stationary;

public class Pen extends Stationary {
    private String color;

    public Pen(String name, long price, String country, String color, int availableProducts) {
        super(name, price, country, availableProducts);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        String string = super.toString() + "color:" + color;
        return string;
    }
}

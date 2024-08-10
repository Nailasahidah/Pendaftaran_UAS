package com.nailasahidah.cashier.query;

public class ProductQuery {
    public static final String  UPDATE_PRODUCT_IMAGE_QUERY =
            "UPDATE Product " +
                    "SET image_url = :imageUrl " +
                    "WHERE id = :id";
}

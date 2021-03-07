package com.example.inventoryapp;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCursorWrapper  extends CursorWrapper {
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct() {
        String uuidString = getString(getColumnIndex(ProductContract.ProductEntry.UUID));
        String productTitle = getString(getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_TITLE));
        int quantity = getInt(getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY));
        BigDecimal price = new BigDecimal(getString(getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE)));
        String supplierName = getString(getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME));
        String supplierEmail = getString(getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL));

        Product product = new Product(UUID.fromString(uuidString));
        product.setTitle(productTitle);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setSupplierName(supplierName);
        product.setSupplierEmail(supplierEmail);

        return product;
    }
}

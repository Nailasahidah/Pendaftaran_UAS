package com.nailasahidah.pendaftaran.query;

public class CustomerQuery {
    public static final String STATS_QUERY =
            "SELECT c.total_customers, d.total_products,i.total_invoices, inv.total_billed " +
                    "FROM " +
                    "(SELECT COUNT(*) total_customers FROM customer) c, " +
                    "(SELECT COUNT(*) total_products FROM product) d, " +
                    "(SELECT COUNT(*) total_invoices FROM invoice) i, " +
                    "(SELECT ROUND(SUM(total)) total_billed FROM invoice) inv;";
}

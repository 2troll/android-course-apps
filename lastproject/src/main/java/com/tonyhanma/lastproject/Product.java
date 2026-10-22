package com.tonyhanma.lastproject;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

/** Un dulce de la tienda: nombre, precio por pieza y el diálogo con su descripción. */
final class Product {

    /** Mismo orden que las filas de activity_main_page.xml y activity_cart.xml. */
    static final Product[] CATALOG = {
            new Product(R.string.strawberry_daifuku_mochi, 5, R.layout.strawberry_dialog),
            new Product(R.string.fruit_roll_cake, 7, R.layout.fruit_dialog),
            new Product(R.string.dango, 10, R.layout.dango_dialog),
            new Product(R.string.taiyaki, 3, R.layout.taiyaki_dialog),
            new Product(R.string.matcha_cake_with_red_beans, 25, R.layout.matcha_dialog),
            new Product(R.string.wagashi, 5, R.layout.wagashi_dialog),
    };

    @StringRes final int name;
    final int price;
    @LayoutRes final int descriptionLayout;

    private Product(@StringRes int name, int price, @LayoutRes int descriptionLayout) {
        this.name = name;
        this.price = price;
        this.descriptionLayout = descriptionLayout;
    }
}

package com.tonyhanma.lastproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.IdRes;

public class MainPage extends ShopActivity {

    // Una entrada por producto, en el orden de Product.CATALOG
    private static final int[] IMAGES = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6};
    private static final int[] COUNTS = {R.id.productcount1, R.id.productcount2, R.id.productcount3,
            R.id.productcount4, R.id.productcount5, R.id.productcount6};
    private static final int[] SUBTOTALS = {R.id.productnum1, R.id.productnum2, R.id.productnum3,
            R.id.productnum4, R.id.productnum5, R.id.productnum6};
    private static final int[] MINUS = {R.id.subtractButton1, R.id.subtractButton2, R.id.subtractButton3,
            R.id.subtractButton4, R.id.subtractButton5, R.id.subtractButton6};
    private static final int[] PLUS = {R.id.addButton1, R.id.addButton2, R.id.addButton3,
            R.id.addButton4, R.id.addButton5, R.id.addButton6};

    private CartStorage cart;
    private int[] counts;
    private final TextView[] countViews = new TextView[IMAGES.length];
    private final TextView[] subtotalViews = new TextView[IMAGES.length];

    @Override
    protected int menuRes() {
        return R.menu.menu_mainpage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        cart = new CartStorage(this);
        counts = cart.load();

        User user = new UserStorage(this).load();
        TextView hello = findViewById(R.id.main_hello);
        hello.setText(getString(R.string.hello_name, user == null ? "" : user.name));

        for (int i = 0; i < Product.CATALOG.length; i++) {
            bindProduct(i);
        }
        findViewById(R.id.main_cancel_btn).setOnClickListener(v -> clearCounts());
        findViewById(R.id.main_cart_btn).setOnClickListener(v -> {
            cart.save(counts);
            switchTo(Cart.class);
        });
    }

    private void bindProduct(int index) {
        Product product = Product.CATALOG[index];
        countViews[index] = findViewById(COUNTS[index]);
        subtotalViews[index] = findViewById(SUBTOTALS[index]);
        view(IMAGES[index]).setOnLongClickListener(v -> {
            Dialogs.showLayout(this, product.descriptionLayout);
            return true;
        });
        view(MINUS[index]).setOnClickListener(v -> changeCount(index, -1));
        view(PLUS[index]).setOnClickListener(v -> changeCount(index, +1));
        showCount(index);
    }

    private void changeCount(int index, int delta) {
        counts[index] = Math.max(0, counts[index] + delta);
        showCount(index);
    }

    private void clearCounts() {
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
            showCount(i);
        }
    }

    private void showCount(int index) {
        countViews[index].setText(String.valueOf(counts[index]));
        subtotalViews[index].setText(getString(R.string.price_amount, counts[index] * Product.CATALOG[index].price));
    }

    private android.view.View view(@IdRes int id) {
        return findViewById(id);
    }
}

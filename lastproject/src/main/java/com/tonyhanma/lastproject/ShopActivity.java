package com.tonyhanma.lastproject;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/** Menú superior común a la tienda, el carrito y el perfil (antes copiado en las tres pantallas). */
abstract class ShopActivity extends AppCompatActivity {

    @MenuRes
    protected abstract int menuRes();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            switchTo(MainPage.class);
        } else if (id == R.id.action_cart) {
            switchTo(Cart.class);
        } else if (id == R.id.action_profile) {
            switchTo(Profile.class);
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, About.class));
        } else if (id == R.id.action_logout) {
            logOut();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void switchTo(Class<?> screen) {
        startActivity(new Intent(this, screen));
        finish();
    }

    private void logOut() {
        new CartStorage(this).clear();
        switchTo(Login.class);
    }
}

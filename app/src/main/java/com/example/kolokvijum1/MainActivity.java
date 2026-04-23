package com.example.kolokvijum1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        startService(new android.content.Intent(this, MyService.class));

        // DEFAULT fragment (Zadaci)
        loadFragment(new ZadaciFragment());

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
        }
    }

    // UBACIVANJE MENIJA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // KLIK NA MENI
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_zadaci) {
            loadFragment(new ZadaciFragment());
            return true;
        }

        if (item.getItemId() == R.id.menu_korisnici) {
            loadFragment(new KorisniciFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // METODA ZA PROMENU FRAGMENTA
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
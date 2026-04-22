package com.example.kolokvijum1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kolokvijum1.databinding.FragmentZadaciBinding;

import java.util.ArrayList;
import java.util.List;

public class ZadaciFragment extends Fragment {

    private FragmentZadaciBinding binding;
    private List<Zadatak> lista;
    private ZadatakAdapter adapter;

    private android.content.BroadcastReceiver fabReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentZadaciBinding.inflate(inflater, container, false);

        lista = new ArrayList<>();
        adapter = new ZadatakAdapter(lista);

        binding.recyclerViewZadaci.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewZadaci.setAdapter(adapter);

        // FAB klik
        binding.fabAdd.setOnClickListener(v -> {
            showDialog();
        });

        // ✅ RECEIVER DEFINICIJA + REGISTRACIJA (OVDE ide!)
        fabReceiver = new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context context, android.content.Intent intent) {

                int broj = intent.getIntExtra("broj", 0);

                if (broj % 2 == 0) {

                    AppState.isFabRed = false;

                    binding.fabAdd.setBackgroundTintList(
                            android.content.res.ColorStateList.valueOf(
                                    android.graphics.Color.BLUE
                            )
                    );

                } else {

                    AppState.isFabRed = true;

                    binding.fabAdd.setBackgroundTintList(
                            android.content.res.ColorStateList.valueOf(
                                    android.graphics.Color.RED
                            )
                    );
                }
            }
        };

        requireContext().registerReceiver(
                fabReceiver,
                new android.content.IntentFilter("UPDATE_FAB_COLOR"),
                android.content.Context.RECEIVER_NOT_EXPORTED
        );

        return binding.getRoot();
    }

    private void showDialog() {

        android.app.Dialog dialog = new android.app.Dialog(getContext());
        dialog.setContentView(R.layout.dialog_novi_zadatak);

        android.widget.EditText etNaziv = dialog.findViewById(R.id.etNaziv);
        android.widget.Button btnVreme = dialog.findViewById(R.id.btnVreme);
        android.widget.Button btnPotvrdi = dialog.findViewById(R.id.btnPotvrdi);
        android.widget.Button btnOdustani = dialog.findViewById(R.id.btnOdustani);

        final String[] izabranoVreme = {""};

        // TIME PICKER
        btnVreme.setOnClickListener(v -> {

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int sat = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            int minut = calendar.get(java.util.Calendar.MINUTE);

            android.app.TimePickerDialog timePicker = new android.app.TimePickerDialog(
                    getContext(),
                    (view, hourOfDay, minute) -> {
                        izabranoVreme[0] = hourOfDay + ":" + minute;
                        btnVreme.setText("Vreme: " + izabranoVreme[0]);
                    },
                    sat,
                    minut,
                    true
            );

            timePicker.show();
        });

        // POTVRDI
        btnPotvrdi.setOnClickListener(v -> {
            String naziv = etNaziv.getText().toString();

            if (!naziv.isEmpty() && !izabranoVreme[0].isEmpty()) {
                lista.add(new Zadatak(naziv, izabranoVreme[0]));
                adapter.notifyDataSetChanged();

                // BROADCAST
                android.content.Intent intent = new android.content.Intent(TaskReceiver.ACTION);
                intent.putExtra("broj", lista.size());
                requireContext().sendBroadcast(intent);

                dialog.dismiss();
            }
        });

        // ODUSTANI
        btnOdustani.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    //  OVDE ide onDestroyView (NA KRAJU KLASE)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (fabReceiver != null) {
            requireContext().unregisterReceiver(fabReceiver);
        }

        binding = null;
    }
}
package com.example.midespensa.ui.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.midespensa.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LocationFragment extends Fragment {

    private MaterialCardView cardFridge;
    private MaterialCardView cardFreezer;
    private MaterialCardView cardPantry;
    private FloatingActionButton fabAddLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardFridge = view.findViewById(R.id.cardFridge);
        cardFreezer = view.findViewById(R.id.cardFreezer);
        cardPantry = view.findViewById(R.id.cardPantry);
        fabAddLocation = view.findViewById(R.id.fabAddLocation);
        //Para que salte el cartel y comprobar que funcionan los botones
        cardFridge.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Nevera", Toast.LENGTH_SHORT).show()
        );

        cardFreezer.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Congelador", Toast.LENGTH_SHORT).show()
        );

        cardPantry.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Despensa", Toast.LENGTH_SHORT).show()
        );

        fabAddLocation.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Nueva ubicación", Toast.LENGTH_SHORT).show()
        );
    }
}
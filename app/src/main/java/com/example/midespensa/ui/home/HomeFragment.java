package com.example.midespensa.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.midespensa.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private MaterialCardView btnHomeAdd;
    private MaterialCardView btnHomeSubtract;

    // Simulación temporal de productos en la base de datos
    private final List<String> existingProducts = new ArrayList<>(Arrays.asList(
            "Leche Entera", "Huevos", "Pan de Molde", "Arroz", "Aceite de Oliva", "Yogur"
    ));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnHomeAdd = view.findViewById(R.id.btnHomeAdd);
        btnHomeSubtract = view.findViewById(R.id.btnHomeSubtract);

        btnHomeAdd.setOnClickListener(v -> openStockDialog(true));
        btnHomeSubtract.setOnClickListener(v -> openStockDialog(false));
    }

    private void openStockDialog(boolean isAdding) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_modify_stock, null);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
        MaterialAutoCompleteTextView actvProductName = dialogView.findViewById(R.id.actvProductName);
        MaterialAutoCompleteTextView actvLocation = dialogView.findViewById(R.id.actvLocation);
        TextInputEditText etQuantity = dialogView.findViewById(R.id.etDialogQuantity);

        // Contenedores para controlar la visibilidad
        TextInputLayout tilLocation = dialogView.findViewById(R.id.tilLocation);
        TextInputLayout tilExpiryDate = dialogView.findViewById(R.id.tilExpiryDate);
        TextInputEditText etExpiryDate = dialogView.findViewById(R.id.etDialogExpiryDate);

        tvDialogTitle.setText(isAdding ? "Añadir Alimento" : "Restar Alimento");

        // Para que al restar se oculten y  solo queden Producto y Cantidad
        tilLocation.setVisibility(isAdding ? View.VISIBLE : View.GONE);
        tilExpiryDate.setVisibility(isAdding ? View.VISIBLE : View.GONE);

        // Adaptador con sugerencias de productos existentes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                existingProducts
        );
        actvProductName.setAdapter(adapter);

        new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .setPositiveButton(isAdding ? "Añadir" : "Restar", (dialog, which) -> {
                    String name = actvProductName.getText() != null ? actvProductName.getText().toString().trim() : "";
                    String qtyStr = etQuantity.getText() != null ? etQuantity.getText().toString().trim() : "1";

                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), "Introduce un nombre de producto", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int quantity = qtyStr.isEmpty() ? 1 : Integer.parseInt(qtyStr);

                    // Comprobamos si el producto existe o es nuevo
                    boolean exists = false;
                    for (String prod : existingProducts) {
                        if (prod.equalsIgnoreCase(name)) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        String action = isAdding ? "Añadidas " : "Restadas ";
                        Toast.makeText(requireContext(), action + quantity + " u. a " + name, Toast.LENGTH_SHORT).show();
                    } else {
                        if (isAdding) {
                            // Si es nuevo y le damos a añadir, lo registramos como nuevo producto
                            existingProducts.add(name);
                            Toast.makeText(requireContext(), "Nuevo producto registrado: " + name + " (" + quantity + " u.)", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(requireContext(), "El producto '" + name + "' no existe en el inventario", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
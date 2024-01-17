package com.example.manu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText etKilometrageDepart = view.findViewById(R.id.etKilometrageDepart);
        EditText etKilometrageArrivee = view.findViewById(R.id.etKilometrageArrivee);
        Button btnVerifEntretien = view.findViewById(R.id.btnVerifEntretien);

        btnVerifEntretien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String departStr = etKilometrageDepart.getText().toString();
                String arriveeStr = etKilometrageArrivee.getText().toString();

                if (departStr.isEmpty() || arriveeStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir les deux champs de kilométrage.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double depart = Double.parseDouble(departStr);
                    double arrivee = Double.parseDouble(arriveeStr);

                    if (depart > arrivee) {
                        afficherPopupErreur();
                    } else if (doitEffectuerEntretien(depart, arrivee)) {
                        afficherPopupEntretien();
                    } else {
                        afficherPopupNonNecessaire();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Veuillez entrer des nombres valides.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private boolean doitEffectuerEntretien(double depart, double arrivee) {
        double distance = arrivee - depart;
        return distance >= 1000; // Remplacez 1000 par le seuil d'entretien
    }

    private void afficherPopupEntretien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Entretien requis");
        builder.setMessage("Il est temps d'effectuer l'entretien de votre voiture.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void afficherPopupNonNecessaire() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Entretien non requis");
        builder.setMessage("Le kilométrage n'est pas encore atteint pour effectuer l'entretien.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void afficherPopupErreur() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Erreur de saisie");
        builder.setMessage("Le kilométrage d'arrivée doit être supérieur au kilométrage de départ.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

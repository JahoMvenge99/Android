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
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
                // Récupération des valeurs des EditText
                String marque = ((EditText) view.findViewById(R.id.Marque)).getText().toString();
                String vitesse = ((EditText) view.findViewById(R.id.vitesse)).getText().toString();

                // Récupération du choix du bouton radio
                RadioGroup radioGroup = view.findViewById(R.id.radioGroupCarburant);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(selectedId);
                String typeCarburant = radioButton.getText().toString();

//                // Affichage du popup
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Informations du véhicule");
//                builder.setMessage("Marque: " + marque + "\nVitesse: " + vitesse + "\nType de carburant: " + typeCarburant);
//                builder.setPositiveButton("OK", null);
//                AlertDialog dialog = builder.create();
//                dialog.show();
                if (departStr.isEmpty() || arriveeStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir les deux champs de kilométrage.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double depart = Double.parseDouble(departStr);
                    double arrivee = Double.parseDouble(arriveeStr);

                    if (depart > arrivee) {
                        afficherPopupErreur(marque, vitesse, typeCarburant);
                    } else if (doitEffectuerEntretien(depart, arrivee)) {
                        afficherPopupEntretien(marque, vitesse, typeCarburant);
                    } else {
                        afficherPopupNonNecessaire(marque, vitesse, typeCarburant);
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

    private void afficherPopupEntretien(String marque, String vitesse, String typeCarburant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogRed);
        builder.setTitle("Entretien requis");
        builder.setMessage("Marque: " + marque + "\nVitesse: " + vitesse + "\nType de carburant: " + typeCarburant + "\nIl est temps d'effectuer l'entretien de votre voiture.");
//        builder.setMessage("Il est temps d'effectuer l'entretien de votre voiture.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void afficherPopupNonNecessaire(String marque, String vitesse, String typeCarburant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogGreen);
        builder.setTitle("Entretien non requis");
        builder.setMessage("Marque: " + marque + "\nVitesse: " + vitesse + "\nType de carburant: " + typeCarburant + "\nLe kilométrage n'est pas encore atteint pour effectuer l'entretien.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void afficherPopupErreur(String marque, String vitesse, String typeCarburant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogYellow);
        builder.setTitle("Erreur de saisie");
        builder.setMessage("Marque: " + marque + "\nVitesse: " + vitesse + "\nType de carburant: " + typeCarburant + "\nLe kilométrage d'arrivée doit être supérieur au kilométrage de départ.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

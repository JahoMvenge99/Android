package com.example.manu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

import android.widget.RadioButton;
import android.widget.Toast;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        EditText etMarqueVehicule = view.findViewById(R.id.Marque); // Ajout de la référence pour la marque
        EditText etKilometrageDepart = view.findViewById(R.id.etKilometrageDepart);
        EditText etKilometrageArrivee = view.findViewById(R.id.etKilometrageArrivee);
        Button btnVerifEntretien = view.findViewById(R.id.btnVerifEntretien);
        EditText etVitesse = view.findViewById(R.id.vitesse);
        RadioButton rbEssence = view.findViewById(R.id.rbEssence);
        RadioButton rbDiesel = view.findViewById(R.id.rbDiesel);
        RadioButton rbElectrique = view.findViewById(R.id.rbElectrique);


        btnVerifEntretien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marque = etMarqueVehicule.getText().toString(); // Récupération de la marque
                String departStr = etKilometrageDepart.getText().toString();
                String arriveeStr = etKilometrageArrivee.getText().toString();
                String vitesseStr = etVitesse.getText().toString();
                String typeCarburant;
                if (rbEssence.isChecked()) {
                    typeCarburant = "Essence";
                } else if (rbDiesel.isChecked()) {
                    typeCarburant = "Diesel";
                } else if (rbElectrique.isChecked()) {
                    typeCarburant = "Electrique";
                } else {
                    Toast.makeText(getActivity(), "Veuillez sélectionner un type de carburant.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (marque.isEmpty() || departStr.isEmpty() || arriveeStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vitesseStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir le champ de vitesse.", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    double depart = Double.parseDouble(departStr);
                    double arrivee = Double.parseDouble(arriveeStr);
                    double vitesse = Double.parseDouble(vitesseStr);


                    if (depart > arrivee) {
                        afficherPopupErreur();
                    } else if (doitEffectuerEntretien(depart, arrivee)) {
                        afficherPopupEntretien(marque, depart, arrivee, vitesse, typeCarburant); // Mise à jour des arguments
                    } else {
                        afficherPopupNonNecessaire(marque, arrivee, vitesse, typeCarburant, depart); // Mise à jour des arguments
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Veuillez entrer des nombres valides.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    public boolean doitEffectuerEntretien(double depart, double arrivee) {
        double distance = arrivee - depart;
        return distance >= 1000; // Remplacez 1000 par le seuil d'entretien
    }

    private void afficherPopupEntretien(String marque, double depart, double arrivee, double vitesse, String typeCarburant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogRed);
        builder.setTitle("Entretien requis");
        builder.setMessage("Il est temps d'effectuer l'entretien de votre " + marque + ".\nKilométrage actuel : " + arrivee + " km.\nVitesse : " + vitesse + " km/h.\nType de carburant : " + typeCarburant + ".");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private void afficherPopupNonNecessaire(String marque, double depart, double vitesse, String typeCarburant, double arrivee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogGreen);
        builder.setTitle("Entretien non requis");
        builder.setMessage("Le kilométrage n'est pas encore atteint pour effectuer l'entretien de votre"+ marque +".\nAncien KM : "+ depart + ".\nKM actuel : " + arrivee + " km.\nVitesse : " + vitesse + " km/h.\nType de carburant : " + typeCarburant + ".");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void afficherPopupErreur() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogYellow);
        builder.setTitle("Erreur de saisie");
        builder.setMessage("Le kilométrage d'arrivée doit être supérieur au kilométrage de départ.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

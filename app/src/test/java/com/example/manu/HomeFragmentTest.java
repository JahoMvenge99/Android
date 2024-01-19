package com.example.manu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import com.example.manu.HomeFragment;

public class HomeFragmentTest {

    private HomeFragment homeFragment;

    @Before
    public void setUp() {
        // Initialisez votre fragment ici si nécessaire
        homeFragment = new HomeFragment();
    }

    @Test
    public void testDoitEffectuerEntretien_QuandDistanceSuperieureSeuil_RetourneTrue() {
        // Teste la méthode doitEffectuerEntretien lorsque la distance est supérieure au seuil
        boolean result = homeFragment.doitEffectuerEntretien(5000, 6000);
        assertTrue(result);
    }

    @Test
    public void testDoitEffectuerEntretien_QuandDistanceInferieureSeuil_RetourneFalse() {
        // Teste la méthode doitEffectuerEntretien lorsque la distance est inférieure au seuil
        boolean result = homeFragment.doitEffectuerEntretien(5000, 550);
        assertFalse(result);
    }

    @Test
    public void testDoitEffectuerEntretien_QuandDistanceEgaleSeuil_RetourneFalse() {
        // Teste la méthode doitEffectuerEntretien lorsque la distance est égale au seuil
        boolean result = homeFragment.doitEffectuerEntretien(5000, 5000);
        assertFalse(result);
    }


    // Écrivez d'autres méthodes de test pour d'autres fonctionnalités de la classe HomeFragment

}

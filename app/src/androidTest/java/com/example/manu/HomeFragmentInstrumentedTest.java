package com.example.manu;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class HomeFragmentInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifierEntretien_AffichePopup() {
        // Supposant que MainActivity est l'activité hôte du fragment
        onView(withId(R.id.etKilometrageDepart)).perform(typeText("1000"));
        onView(withId(R.id.etKilometrageArrivee)).perform(typeText("2000"));
        onView(withId(R.id.btnVerifEntretien)).perform(click());

        // Vérifiez que le popup s'affiche
        onView(withText("Entretien requis")).check(matches(isDisplayed()));
    }
}

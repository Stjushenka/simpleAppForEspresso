package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

import android.content.Intent;
import android.webkit.WebView;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.kkuzmichev.simpleappforespresso.ui.EspressoIdlingResources;


@RunWith(AndroidJUnit4.class)
public class NewTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityTestRule =
            new  ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testName() {
        ViewInteraction mainText = onView(
                withId(R.id.text_home)
        );
        mainText.check(
                matches(
                        withText("This is home fragment")
                )
        );
    }

    @Test
    public void Intent() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        ViewInteraction settingsInteraction = onView(withText("Settings"));
        settingsInteraction.check(matches(isDisplayed()));
        settingsInteraction.perform(click());
        ViewInteraction chromeInteraction =onView(withText("Chrome"));

        //Intents... Если вы решили реализовать проверку с подписыванием на Intent
        chromeInteraction.perform(click()); //Для запуска intent
        Intents.intended(allOf(hasData("https://google.com"), hasAction(Intent.ACTION_VIEW)));//Проверяем intent, он должен передавать url и action
        //Intents..
    }


    @Before
    public void beforeIdleTest() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idling_resource);
    }

    @After
    public void afterIdleTest() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idling_resource);
    }

    @Test
    public void idleListTest() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_gallery));
        onView(allOf(withId(R.id.item_number), withText("7"))).check(matches(isDisplayed()));
    }
}

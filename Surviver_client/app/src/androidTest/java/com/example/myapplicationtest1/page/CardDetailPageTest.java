package com.example.myapplicationtest1.page;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.StartPage;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CardDetailPageTest {

    static {
        CardDetailPage.selectingOwnCard = new Cache.OwnCard();
        CardDetailPage.selectingOwnCard.card = new Cache.Card();
    }
    @Rule
    public ActivityTestRule<CardDetailPage> rule = new ActivityTestRule<>(CardDetailPage.class);

    @Before
    public void setUp() {
        Utils.identifyUser(StartPage.staticActivity, "SuperUser", "111111");
    }

    @Test
    public void uiCreateTest() {
        onView(withId(R.id.return_button)).check(matches(isDisplayed()));
    }

    @Test
    public void editUpgPtTest() {
        onView(withId(R.id.addHP)).perform(ViewActions.click());
        onView(withId(R.id.subHP)).perform(ViewActions.click());
        onView(withId(R.id.return_button)).perform(ViewActions.click());
    }
}
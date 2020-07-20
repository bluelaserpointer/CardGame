package com.example.myapplicationtest1.page;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplicationtest1.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ItemStoragePageTest {

    @Rule
    public ActivityTestRule<ItemStoragePage> rule = new ActivityTestRule<>(ItemStoragePage.class);

    @Test
    public void uiCreateTest() {
        onView(withId(R.id.toTeam_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.toCardStorage_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.toGallery_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.return_button)).check(matches(isDisplayed()));
    }
}
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

public class ShopPageTest {

    @Rule
    public ActivityTestRule<ShopPage> rule = new ActivityTestRule<>(ShopPage.class);

    @Test
    public void uiCreateTest() {
        onView(withId(R.id.return_button)).check(matches(isDisplayed()));
    }
}
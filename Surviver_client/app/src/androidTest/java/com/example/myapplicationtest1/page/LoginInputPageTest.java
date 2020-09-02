package com.example.myapplicationtest1.page;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.StartPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class LoginInputPageTest {

    @Rule
    public ActivityTestRule<LoginInputPage> rule = new ActivityTestRule<>(LoginInputPage.class);

    @Test
    public void uiCreateTest() {
        onView(withId(R.id.usernameText)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordText)).check(matches(isDisplayed()));
        onView(withId(R.id.loginInput_button)).check(matches(isDisplayed()));
        onView(withId(R.id.register_button)).check(matches(isDisplayed()));
    }

    @Test
    public void onLoginClickTest() {
        //wrong input
        onView(withId(R.id.loginInput_button)).perform(ViewActions.click());
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser");
        onView(withId(R.id.loginInput_button)).perform(ViewActions.click());
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser");
        ((EditText)rule.getActivity().findViewById(R.id.passwordText)).setText("dummyPassword");
        onView(withId(R.id.loginInput_button)).perform(ViewActions.click());
        //correct input
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser");
        ((EditText)rule.getActivity().findViewById(R.id.passwordText)).setText("111111");
        onView(withId(R.id.loginInput_button)).perform(ViewActions.click());
    }
    @Test
    public void onRegisterClickTest1() {
        Page.jump(rule.getActivity(), LoginInputPage.class);
        //wrong input
        onView(withId(R.id.register_button)).perform(ViewActions.click());
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser");
        onView(withId(R.id.register_button)).perform(ViewActions.click());
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser");
        ((EditText)rule.getActivity().findViewById(R.id.passwordText)).setText("duplicatedUser");
        onView(withId(R.id.register_button)).perform(ViewActions.click());
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("");
        ((EditText)rule.getActivity().findViewById(R.id.passwordText)).setText("dummyPassword");
        onView(withId(R.id.register_button)).perform(ViewActions.click());
        //correct input
        Page.jump(rule.getActivity(), LoginInputPage.class);
        ((EditText)rule.getActivity().findViewById(R.id.usernameText)).setText("SuperUser" + System.currentTimeMillis());
        ((EditText)rule.getActivity().findViewById(R.id.passwordText)).setText("111111");
        onView(withId(R.id.register_button)).perform(ViewActions.click());
    }
}
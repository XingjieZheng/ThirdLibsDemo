package tests;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.xj.rxdemo.MainActivity;
import com.xj.rxdemo.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by XingjieZheng
 * on 2016/3/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void sayHello() {
//        onView(withId(R.id.toolbar)).perform(typeText("hello test"));
//
//        onView(withText("Say hello!")).perform(click()); //line 2
//
//        onView(withId(R.id.toolbar)).check(matches(withText("hello test"))); //line 3
        onView(withText("Hello World!")).check(matches(withText("Hello World!")));

        onView(withId(R.id.txtHelloWorld)).check(matches(withText("Hello World!")));
    }
}
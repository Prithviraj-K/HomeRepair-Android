package repair_services.com.segf18_proj;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity =mActivityTestRule.getActivity();
    }

    @Test
    public void buttonIsEnabled() {
        onView(withId(R.id. LoginButton)).check(matches(isEnabled()));
        onView(withId(R.id. createUserAccount)).check(matches(isEnabled()));
        onView(withId(R.id. createServiceProviderAccount)).check(matches(isEnabled()));
    }

    @Test
    public void buttonIsDisplayed() {
        onView(withId(R.id. LoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id. createUserAccount)).check(matches(isDisplayed()));
        onView(withId(R.id. createServiceProviderAccount)).check(matches(isDisplayed()));

    }

    @Test
    public void buttonIsClickable() {
        onView(withId(R.id. LoginButton)).check(matches(not(isClickable())));
        onView(withId(R.id. createUserAccount)).check(matches(not(isClickable())));
        onView(withId(R.id. createServiceProviderAccount)).check(matches(not(isClickable())));
    }

    @Test
    public void buttonWithText() {
        onView(withId(R.id.LoginButton)).check(matches(withText(R.string.login)));
        onView(withId(R.id.createUserAccount)).check(matches(withText(R.string.user)));
        onView(withId(R.id.createServiceProviderAccount)).check(matches(withText(R.string.service)));
    }



    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }
}
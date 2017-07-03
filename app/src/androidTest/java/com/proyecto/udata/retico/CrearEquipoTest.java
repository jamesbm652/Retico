package com.proyecto.udata.retico;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import android.support.test.rule.ActivityTestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CrearEquipoTest {
    private String nombre;
    private String localizacion;
    private String contrasena;

    @Rule
    public ActivityTestRule<CreacionEquipos> mActivityRule = new ActivityTestRule<>(
            CreacionEquipos.class);

    @Before
    public void initValidString(){
        nombre = "Super Campeones";
        localizacion = "Heredia";
        contrasena = "campeones";
    }

    @Test
    public void casoEspaciosVacios() {
        nombre = "";
        localizacion = "";
        contrasena = "";
        onView(withId(R.id.txtNombreEquipo))
                .perform(typeText(nombre), closeSoftKeyboard());

        onView(withId(R.id.txtLocalizacionEquipo))
                .perform(typeText(localizacion), closeSoftKeyboard());

        onView(withId(R.id.txtContrasenaEquipo))
                .perform(typeText(contrasena), closeSoftKeyboard());

        onView(withId(R.id.btnCrearEquipo)).perform(click());



    }

    @Test
    public void casoNombreVacio() {
        nombre = "";
        onView(withId(R.id.txtNombreEquipo))
                .perform(typeText(nombre), closeSoftKeyboard());

        onView(withId(R.id.txtLocalizacionEquipo))
                .perform(typeText(localizacion), closeSoftKeyboard());

        onView(withId(R.id.txtContrasenaEquipo))
                .perform(typeText(contrasena), closeSoftKeyboard());

        onView(withId(R.id.btnCrearEquipo)).perform(click());
    }

    @Test
    public void casoLocalizacionVacia() {
        localizacion = "";
        onView(withId(R.id.txtNombreEquipo))
                .perform(typeText(nombre), closeSoftKeyboard());

        onView(withId(R.id.txtLocalizacionEquipo))
                .perform(typeText(localizacion), closeSoftKeyboard());

        onView(withId(R.id.txtContrasenaEquipo))
                .perform(typeText(contrasena), closeSoftKeyboard());

        onView(withId(R.id.btnCrearEquipo)).perform(click());
    }

    @Test
    public void casoContrasenaVacia() {
        contrasena = "";
        onView(withId(R.id.txtNombreEquipo))
                .perform(typeText(nombre), closeSoftKeyboard());

        onView(withId(R.id.txtLocalizacionEquipo))
                .perform(typeText(localizacion), closeSoftKeyboard());

        onView(withId(R.id.txtContrasenaEquipo))
                .perform(typeText(contrasena), closeSoftKeyboard());

        onView(withId(R.id.btnCrearEquipo)).perform(click());
    }

    @Test
    public void casoCrearEquipoCorrecto() {
        // Type text and then press the button.
        onView(withId(R.id.txtNombreEquipo))
                .perform(typeText(nombre), closeSoftKeyboard());

        onView(withId(R.id.txtLocalizacionEquipo))
                .perform(typeText(localizacion), closeSoftKeyboard());

        onView(withId(R.id.txtContrasenaEquipo))
                .perform(typeText(contrasena), closeSoftKeyboard());

        onView(withId(R.id.btnCrearEquipo)).perform(click());
    }

}

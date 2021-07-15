package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class LancesLeilaoTelaTeste extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity = new ActivityTestRule(ListaLeilaoActivity.class, true, false);

    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
        limpaBancoDeDadosInterno();
    }

    @Test
    public void test_AtualizarLancesLeilao_QuandoReceberUmLance() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Usuários não encontrados"))
                .check(matches(isDisplayed()));

        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."))
                .check(matches(isDisplayed()));

        onView(withText("Cadastrar Usuário"))
                .perform(click());

        onView(
                allOf(withId(R.id.lista_usuario_fab_adiciona),
                        isDescendantOfA(withId(android.R.id.content)),
                        isDisplayed()))
                .perform(click());

        onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.form_usuario_nome),
                                0),
                        0),
                        isDisplayed()))
                .perform(typeText("Breno"), closeSoftKeyboard());

        onView(
                allOf(withId(android.R.id.button1), withText("Adicionar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)))
                .perform(scrollTo(), click());

        Espresso.pressBack();

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));


        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(), typeText("200"), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());

        onData(is(new Usuario(1, "Breno")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText("Propor"))
                .perform(click());

        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(200)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(200)), isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(200) + " - (1) Breno\n"), isDisplayed())));
    }

    @Test
    public void test_AtualizarLancesLeilao_QuandoAdicionaTresTestes() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Usuários não encontrados"))
                .check(matches(isDisplayed()));

        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."))
                .check(matches(isDisplayed()));

        onView(withText("Cadastrar Usuário"))
                .perform(click());

        onView(
                allOf(withId(R.id.lista_usuario_fab_adiciona),
                        isDescendantOfA(withId(android.R.id.content)),
                        isDisplayed()))
                .perform(click());

        onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.form_usuario_nome),
                                0),
                        0),
                        isDisplayed()))
                .perform(typeText("Breno"), closeSoftKeyboard());

        onView(
                allOf(withId(android.R.id.button1), withText("Adicionar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)))
                .perform(scrollTo(), click());

        onView(
                allOf(withId(R.id.lista_usuario_fab_adiciona),
                        isDescendantOfA(withId(android.R.id.content)),
                        isDisplayed()))
                .perform(click());

        onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.form_usuario_nome),
                                0),
                        0),
                        isDisplayed()))
                .perform(typeText("Fran"), closeSoftKeyboard());

        onView(
                allOf(withId(android.R.id.button1), withText("Adicionar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)))
                .perform(scrollTo(), click());

        Espresso.pressBack();

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(), typeText("200"), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());

        onData(is(new Usuario(1, "Breno")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText("Propor"))
                .perform(click());

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(), typeText("300"), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());

        onData(is(new Usuario(2, "Fran")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText("Propor"))
                .perform(click());

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(), typeText("400"), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());

        onData(is(new Usuario(1, "Breno")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText("Propor"))
                .perform(click());

        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(400)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(200)), isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(400)+" - (1) Breno\n"+formatador.formata(300)+" - (2) Fran\n"+formatador.formata(200)+" - (1) Breno\n"), isDisplayed())));
    }

    @Test
    public void test_AtualizarLancesLeilao_QuandoReceberUmLanceMuitoGrande() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Usuários não encontrados"))
                .check(matches(isDisplayed()));

        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."))
                .check(matches(isDisplayed()));

        onView(withText("Cadastrar Usuário"))
                .perform(click());

        onView(
                allOf(withId(R.id.lista_usuario_fab_adiciona),
                        isDescendantOfA(withId(android.R.id.content)),
                        isDisplayed()))
                .perform(click());

        onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.form_usuario_nome),
                                0),
                        0),
                        isDisplayed()))
                .perform(typeText("Breno"), closeSoftKeyboard());

        onView(
                allOf(withId(android.R.id.button1), withText("Adicionar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)))
                .perform(scrollTo(), click());

        Espresso.pressBack();

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));


        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(), typeText("1000000000"), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());

        onData(is(new Usuario(1, "Breno")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText("Propor"))
                .perform(click());

        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(1000000000)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(1000000000)), isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(1000000000)+ " - (1) Breno\n"), isDisplayed())));
    }

    @After
    public void tearDown() throws IOException {
        limpaBancoDeDadosDaApi();
        limpaBancoDeDadosInterno();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}

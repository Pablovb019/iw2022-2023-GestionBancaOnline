package es.uca.iw.biwan.views.login;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("/themes/biwan/login.css")
@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){

        super.setClassName("main");
        login.setI18n(Login());

        //NEW
        VerticalLayout layoutLogin = new VerticalLayout();
        HorizontalLayout layoutHorLogin = new HorizontalLayout();

        //ADD
        layoutLogin.add(HeaderView.Header());
        layoutHorLogin.add(login);
        layoutLogin.add(layoutHorLogin);
        layoutLogin.add(FooterView.Footer());

        //ALIGNMENT
        layoutLogin.expand(layoutHorLogin);
        layoutLogin.setSizeFull();
        layoutHorLogin.setAlignItems(Alignment.CENTER);
        layoutLogin.setAlignItems(Alignment.CENTER);

        add(layoutLogin);
    }

    private LoginI18n Login(){

        LoginI18n loginDefault = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = loginDefault.getForm();

        i18nForm.setTitle("Iniciar Sesión");
        i18nForm.setUsername("Usuario (DNI o correo electrónico)");
        i18nForm.setPassword("Contraseña");
        i18nForm.setSubmit("Iniciar sesión");
        i18nForm.setForgotPassword("¿Has olvidado tu contraseña?");
        loginDefault.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = loginDefault.getErrorMessage();
        i18nErrorMessage.setTitle("Nombre de usuario o contraseña incorrectos");
        i18nErrorMessage.setMessage("Comprueba que tu nombre de usuario y contraseña son correctos y vuelve a intentarlo.");
        loginDefault.setErrorMessage(i18nErrorMessage);

        setSizeFull();

        return loginDefault;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
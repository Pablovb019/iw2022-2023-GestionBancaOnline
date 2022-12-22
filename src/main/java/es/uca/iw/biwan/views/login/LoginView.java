package es.uca.iw.biwan.views.login;

import com.sun.jna.platform.win32.OaIdl;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@CssImport("./themes/biwan/login.css")
@Route("login")
@PageTitle("Formulario de inicio de sesión")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    public LoginView() {

        //NEW
        VerticalLayout layoutLogin = new VerticalLayout();
        HorizontalLayout layoutHorLogin = new HorizontalLayout();

        //ADD
        layoutHorLogin.add(Login());
        layoutLogin.add(HeaderView.Header(), layoutHorLogin, FooterView.Footer());

        //ALIGNMENT
        layoutHorLogin.setWidth("30%");
        layoutLogin.expand(layoutHorLogin);
        layoutHorLogin.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Login());
        layoutHorLogin.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutLogin.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutLogin.setSizeFull();

        add(layoutLogin);
    }

    private FormLayout Login() {

        //NEW
        EmailField email = new EmailField("Correo electrónico");
        PasswordField password = new PasswordField("Contraseña");
        Anchor registration = new Anchor("registration", "¿No tienes cuenta? Regístrate");
        Anchor resetPassword = new Anchor("reset-password", "¿Has olvidado tu contraseña?");
        email.setErrorMessage("Introduce una dirección de correo electrónico válida");
        Button submit = new Button("Iniciar de sesión");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitRegistration");
        H1 Titulo = new H1("Iniciar sesión");
        Titulo.setClassName("TituloLogin");
        FormLayout formLayout = new FormLayout();

        //ADD
        formLayout.add(Titulo, email, password,registration,resetPassword, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("", 2));
        formLayout.setColspan(Titulo, 2);
        formLayout.setColspan(email, 2); // Stretch the email field over 2 columns
        formLayout.setColspan(password, 2); // Stretch the password field over 2 columns
        formLayout.setColspan(submit, 2); // Stretch the submit button over 2 columns
        setSizeFull();

        submit.addClickListener(event -> {
            if (email.isEmpty() || password.isEmpty()) {
                ConfirmDialog error = new ConfirmDialog("Error", "Rellena todos los campos", "Aceptar", null);
                error.open();
            } else {
                Usuario user = usuarioService.findUserByEmail(email.getValue());
                CheckUser(user);
            }
        });
        return formLayout;
    }

    private void CheckUser(Usuario user) {
        try {
            if (user != null) {
                // Coger el usuario logueado
                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute("nombre", user.getNombre());
                session.setAttribute("rol", user.getRole().toString());

                if (user.getRole().equals(Role.CLIENTE.toString())) {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                } else if (user.getRole().equals(Role.GESTOR.toString())) {
                    UI.getCurrent().navigate("pagina-principal-gestor");
                } else if (user.getRole().equals(Role.ENCARGADO_COMUNICACIONES.toString())) {
                    UI.getCurrent().navigate("pagina-principal-encargado");
                } else if (user.getRole().equals(Role.ADMINISTRADOR.toString())) {
                    UI.getCurrent().navigate("");
                }
            } else {
                ConfirmDialog error = new ConfirmDialog("Error", "El usuario no existe", "Aceptar", null);
                error.open();
            }
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

}
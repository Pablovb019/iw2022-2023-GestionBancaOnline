package es.uca.iw.biwan.views.login;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@CssImport("./themes/biwan/login.css")
@Route("login")
@PageTitle("Formulario de inicio de sesión")
public class LoginView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ya has iniciado sesión", "Volver", event -> {
                if (session.getAttribute(Cliente.class) != null) {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                } else if (session.getAttribute(Gestor.class) != null) {
                    UI.getCurrent().navigate("pagina-principal-gestor");
                } else if (session.getAttribute(EncargadoComunicaciones.class) != null) {
                    UI.getCurrent().navigate("pagina-principal-encargado");
                } else if (session.getAttribute(Administrador.class) != null) {
                    UI.getCurrent().navigate("pagina-principal-admin");
                }
            });
            error.open();
        } else {
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
    }

    private FormLayout Login() {

        Binder<Usuario> binderLogin = new Binder<>(Usuario.class);

        //NEW
        EmailField email = new EmailField("Correo electrónico");
        PasswordField password = new PasswordField("Contraseña");
        Anchor registration = new Anchor("registration", "¿No tienes cuenta? Regístrate");
        email.setErrorMessage("Introduce una dirección de correo electrónico válida");
        Button submit = new Button("Iniciar sesión");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitRegistration");
        H1 Titulo = new H1("Iniciar sesión");
        Titulo.setClassName("TituloLogin");

        binderLogin.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .bind(Usuario::getEmail, Usuario::setEmail);

        binderLogin.forField(password)
                .asRequired("La contraseña es obligatoria")
                .bind(Usuario::getPassword, Usuario::setPassword);

        FormLayout formLayout = new FormLayout();
        //ADD
        formLayout.add(Titulo, email, password,registration, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("", 2));
        formLayout.setColspan(Titulo, 2);
        formLayout.setColspan(email, 2); // Stretch the email field over 2 columns
        formLayout.setColspan(password, 2); // Stretch the password field over 2 columns
        formLayout.setColspan(submit, 2); // Stretch the submit button over 2 columns
        setSizeFull();

        //ADD CLASS NAME
        formLayout.addClassName("formLayout");

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(event -> {
            if (binderLogin.validate().isOk()) {
                Usuario user = usuarioService.findUserByEmail(email.getValue());
                CheckUser(user, password.getValue());
            }
        });
        return formLayout;
    }

    private void CheckUser(Usuario user, String passwordForm) {
        try {
            if (user != null && passwordEncoder.matches(passwordForm, user.getPassword())) {
                // Declaramos el rol del usuario
                String role = usuarioService.getRole(user.getUUID());
                user.setRol(Role.valueOf(role));
                // Coger el usuario logueado
                VaadinSession session = VaadinSession.getCurrent();

                if (Objects.equals(user.getRol(), Role.CLIENTE.toString())) {
                    session.setAttribute(Cliente.class, (Cliente) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-cliente");

                } else if (Objects.equals(user.getRol(), Role.GESTOR.toString())) {
                    session.setAttribute(Gestor.class, (Gestor) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-gestor");

                } else if (Objects.equals(user.getRol(), Role.ENCARGADO_COMUNICACIONES.toString())) {
                    session.setAttribute(EncargadoComunicaciones.class, (EncargadoComunicaciones) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-encargado");

                } else if (Objects.equals(user.getRol(), Role.ADMINISTRADOR.toString())) {
                    session.setAttribute(Administrador.class, (Administrador) user);
                    Notification notification = new Notification("Bienvenido " + user.getNombre() + " " + user.getApellidos(), 1000);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().navigate("pagina-principal-admin");

                }
            } else {
                Notification errorEmailPassword = Notification.show("El correo electrónico o la contraseña son incorrectos");
                errorEmailPassword.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

}
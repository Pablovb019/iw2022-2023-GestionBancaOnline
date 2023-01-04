package es.uca.iw.biwan.views.registration;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@CssImport("./themes/biwan/registration.css")
@PageTitle("Formulario de registro")
@Route("registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ya has iniciado sesión", "Volver", event -> {
                UI.getCurrent().navigate("");
            });
            error.open();
        } else {
            //NEW
            VerticalLayout layoutRegistration = new VerticalLayout();
            HorizontalLayout layoutHorRegistration = new HorizontalLayout();

            //ADD
            layoutHorRegistration.add(Registration());
            layoutRegistration.add(HeaderView.Header(), layoutHorRegistration, FooterView.Footer());

            //ALIGNMENT
            layoutHorRegistration.setWidth("30%");
            layoutRegistration.expand(layoutHorRegistration);
            layoutHorRegistration.setVerticalComponentAlignment(Alignment.CENTER, Registration());
            layoutHorRegistration.setAlignItems(Alignment.CENTER);
            layoutRegistration.setAlignItems(Alignment.CENTER);
            layoutRegistration.setSizeFull();

            add(layoutRegistration);
        }
    }

    private FormLayout Registration() {

        Binder<Usuario> binderForm = new Binder<>(Usuario.class);

        //NEW
        TextField firstName = new TextField("Nombre");
        TextField lastName = new TextField("Apellidos");
        NumberField phoneNumber = new NumberField("Teléfono");
        TextField dni = new TextField("DNI");
        DatePicker birthDate = new DatePicker("Fecha de Nacimiento");
        PasswordField password = new PasswordField("Contraseña");
        PasswordField confirmPassword = new PasswordField("Confirmar contraseña");
        EmailField email = new EmailField();
        email.setLabel("Correo electrónico");
        email.getElement().setAttribute("name", "email");
        email.setClearButtonVisible(true);
        Anchor login = new Anchor("login", "¿Ya registrado? Inicia sesión");
        Button submit = new Button("Crear cuenta");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitRegistration");
        H1 Titulo = new H1("Crear cuenta");
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("formRegister");

       binderForm.forField(firstName)
                .asRequired("El nombre es obligatorio")
                .bind(Usuario::getNombre, Usuario::setNombre);

        binderForm.forField(lastName)
                .asRequired("El apellido es obligatorio")
                .bind(Usuario::getApellidos, Usuario::setApellidos);

        binderForm.forField(phoneNumber)
                .asRequired("El teléfono es obligatorio")
                .bind(Usuario::getTelefono, Usuario::setTelefono);

        binderForm.forField(dni)
                .asRequired("El DNI es obligatorio")
                .withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
                .withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
                .bind(Usuario::getDni, Usuario::setDni);

        binderForm.forField(birthDate)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(Usuario::getFechaNacimiento, Usuario::setFechaNacimiento);

        binderForm.forField(password)
                .asRequired("La contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .bind(Usuario::getPassword, Usuario::setPassword);

        binderForm.forField(confirmPassword)
                .asRequired("La confirmación de la contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .withValidator(password1 -> password1.equals(confirmPassword.getValue()), "Las contraseñas no coinciden")
                .bind(Usuario::getPassword, Usuario::setPassword);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Usuario::getEmail, Usuario::setEmail);

        //ADD CLASS NAME
        Titulo.addClassName("CrearCuenta");

        //ADD
        formLayout.add(Titulo, firstName, lastName, phoneNumber, dni, birthDate, email, password, confirmPassword, submit, login);

        //ALIGNMENT
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));

        formLayout.setColspan(Titulo, 2);
        setSizeFull();
        formLayout.setHeight("650px");

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(event -> {
            if(binderForm.validate().isOk()) {
                Cliente cliente = new Cliente();
                cliente.setUUID(UUID.randomUUID());
                cliente.setNombre(firstName.getValue());
                cliente.setApellidos(lastName.getValue());
                cliente.setTelefono(phoneNumber.getValue());
                cliente.setDni(dni.getValue());
                cliente.setFechaNacimiento(birthDate.getValue());
                cliente.setEmail(email.getValue());
                cliente.setPassword(passwordEncoder.encode(password.getValue()));
                cliente.setRol(Role.CLIENTE);
                ArrayList<Usuario> gestores = usuarioService.findUsuarioByRol(Role.GESTOR.toString());
                if(gestores.size() == 0) {
                    cliente.setGestor_id(null);
                } else {
                    Random random = new Random();
                    int randomGestor = random.nextInt(gestores.size());
                    cliente.setGestor_id(usuarioService.findUsuarioByRol(Role.GESTOR.toString()).get(randomGestor).getUUID());
                }
                boolean correcto = ComprobarDatos(cliente);
                if(correcto) { CreateRequest(cliente); }
            }
        });
        return formLayout;
    }

    private boolean ComprobarDatos(Cliente cliente) {
        if (usuarioService.findUserByTelefono(cliente.getTelefono()) != null) {
            Notification errorTelefono = new Notification("El teléfono ya está en uso", 3000);
            errorTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorTelefono.open();
            return false;
        }

        if (usuarioService.findUserByDni(cliente.getDni()) != null) {
            Notification errorDni = new Notification("El DNI ya está en uso", 3000);
            errorDni.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorDni.open();
            return false;
        }

        if (usuarioService.findUserByEmail(cliente.getEmail()) != null) {
            Notification errorEmail = new Notification("El correo electrónico ya está en uso", 3000);
            errorEmail.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorEmail.open();
            return false;
        }
        return true;
    }

    private void CreateRequest(Cliente cliente) {
        try {
            usuarioService.saveCliente(cliente);
            ConfirmDialog confirmRequest = new ConfirmDialog("Registro Correcto", "Registro realizado correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/login");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

}
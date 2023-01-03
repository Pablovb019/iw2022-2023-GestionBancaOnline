package es.uca.iw.biwan.views.crearGestor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
@CssImport("./themes/biwan/crearGestor.css")
@PageTitle("Crear gestor")
@Route("crear-gestor")
public class crearGestorView extends VerticalLayout{
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public crearGestorView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Administrador.class) != null) {
            if (!session.getAttribute(Administrador.class).getRol().contentEquals("ADMINISTRADOR")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un administrador", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            }
            else {
                //NEW
                VerticalLayout layoutCrearGestor = new VerticalLayout();
                HorizontalLayout layoutHorCrearGestor = new HorizontalLayout();

                //ADD
                layoutHorCrearGestor.add(crearEncargadoComunicacion());
                layoutCrearGestor.add(HeaderUsuarioLogueadoView.Header(), layoutHorCrearGestor, FooterView.Footer());

                //ALIGNMENT
                layoutHorCrearGestor.setWidth("30%");
                layoutCrearGestor.expand(layoutHorCrearGestor);
                layoutHorCrearGestor.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, crearEncargadoComunicacion());
                layoutHorCrearGestor.setAlignItems(FlexComponent.Alignment.CENTER);
                layoutCrearGestor.setAlignItems(FlexComponent.Alignment.CENTER);
                layoutCrearGestor.setSizeFull();

                add(layoutCrearGestor);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private FormLayout crearEncargadoComunicacion() {

        Binder<Gestor> binderForm = new Binder<>(Gestor.class);

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
        Button submit = new Button("Crear cuenta");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitCrearGestor");
        H2 Titulo = new H2("Crear cuenta de gestor");
        FormLayout formLayout = new FormLayout();

        binderForm.forField(firstName)
                .asRequired("El nombre es obligatorio")
                .bind(Gestor::getNombre, Gestor::setNombre);

        binderForm.forField(lastName)
                .asRequired("El apellido es obligatorio")
                .bind(Gestor::getApellidos, Gestor::setApellidos);

        binderForm.forField(phoneNumber)
                .asRequired("El teléfono es obligatorio")
                .bind(Gestor::getTelefono, Gestor::setTelefono);

        binderForm.forField(dni)
                .asRequired("El DNI es obligatorio")
                .withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
                .withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
                .bind(Gestor::getDni, Gestor::setDni);

        binderForm.forField(birthDate)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(Gestor::getFechaNacimiento, Gestor::setFechaNacimiento);

        binderForm.forField(password)
                .asRequired("La contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .bind(Gestor::getPassword, Gestor::setPassword);

        binderForm.forField(confirmPassword)
                .asRequired("La confirmación de la contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .withValidator(password1 -> password1.equals(confirmPassword.getValue()), "Las contraseñas no coinciden")
                .bind(Gestor::getPassword, Gestor::setPassword);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Gestor::getEmail, Gestor::setEmail);

        //ADD CLASS NAME
        Titulo.addClassName("CrearCuentaGestor");

        //ADD
        formLayout.add(Titulo, firstName, lastName, phoneNumber, dni, birthDate, email, password, confirmPassword, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));

        formLayout.setColspan(Titulo, 2);
        setSizeFull();
        formLayout.setHeight("650px");

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(event -> {
            if (binderForm.validate().isOk()) {
                Gestor gestor = new Gestor();
                gestor.setUUID(UUID.randomUUID());
                gestor.setNombre(firstName.getValue());
                gestor.setApellidos(lastName.getValue());
                gestor.setTelefono(phoneNumber.getValue());
                gestor.setDni(dni.getValue());
                gestor.setFechaNacimiento(birthDate.getValue());
                gestor.setEmail(email.getValue());
                gestor.setPassword(passwordEncoder.encode(password.getValue()));
                gestor.setRol(Role.GESTOR);
                boolean correcto = ComprobarDatos(gestor);
                if (correcto) { CreateRequest(gestor); }
            }
        });
        return formLayout;
    }

    private boolean ComprobarDatos(Gestor gestor) {
        if (usuarioService.findUserByTelefono(gestor.getTelefono()) != null) {
            Notification errorTelefono = new Notification("El teléfono ya está en uso", 3000);
            errorTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorTelefono.open();
            return false;
        }

        if (usuarioService.findUserByDni(gestor.getDni()) != null) {
            Notification errorDni = new Notification("El DNI ya está en uso", 3000);
            errorDni.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorDni.open();
            return false;
        }

        if (usuarioService.findUserByEmail(gestor.getEmail()) != null) {
            Notification errorEmail = new Notification("El correo electrónico ya está en uso", 3000);
            errorEmail.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorEmail.open();
            return false;
        }
        return true;
    }

    private void CreateRequest(Gestor gestor) {
        try {
            usuarioService.saveGestor(gestor);
            ConfirmDialog confirmRequest = new ConfirmDialog("Registro Correcto", "Gestor creado correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/pagina-principal-admin");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al adminsitrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
}

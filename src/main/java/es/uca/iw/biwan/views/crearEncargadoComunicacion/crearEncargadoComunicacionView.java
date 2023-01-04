package es.uca.iw.biwan.views.crearEncargadoComunicacion;

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
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@CssImport("./themes/biwan/crearEncargadoComunicacion.css")
@PageTitle("Crear encargado de comunicación")
@Route("crear-encargado-comunicacion")
public class crearEncargadoComunicacionView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public crearEncargadoComunicacionView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un administrador", "Aceptar", event -> {
                    if (session.getAttribute(Cliente.class) != null){
                        UI.getCurrent().navigate("pagina-principal-cliente");
                    }else if (session.getAttribute(Gestor.class) != null){
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    } else if (session.getAttribute(EncargadoComunicaciones.class) != null){
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    }
                });
                error.open();
            } else {
                //NEW
                VerticalLayout layoutCrearEncargado = new VerticalLayout();
                HorizontalLayout layoutHorCrearEncargado = new HorizontalLayout();

                //ADD
                layoutHorCrearEncargado.add(crearEncargadoComunicacion());
                layoutCrearEncargado.add(HeaderUsuarioLogueadoView.Header(), layoutHorCrearEncargado, FooterView.Footer());

                //ALIGNMENT
                layoutHorCrearEncargado.setWidth("30%");
                layoutCrearEncargado.expand(layoutHorCrearEncargado);
                layoutHorCrearEncargado.setVerticalComponentAlignment(Alignment.CENTER, crearEncargadoComunicacion());
                layoutHorCrearEncargado.setAlignItems(Alignment.CENTER);
                layoutCrearEncargado.setAlignItems(Alignment.CENTER);
                layoutCrearEncargado.setSizeFull();

                add(layoutCrearEncargado);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private FormLayout crearEncargadoComunicacion() {

        Binder<EncargadoComunicaciones> binderForm = new Binder<>(EncargadoComunicaciones.class);

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
        submit.setClassName("ButtonSubmitCrearEncargadoComunicacion");
        H2 Titulo = new H2("Crear cuenta de encargado de comunicación");
        FormLayout formLayout = new FormLayout();

        binderForm.forField(firstName)
                .asRequired("El nombre es obligatorio")
                .bind(EncargadoComunicaciones::getNombre, EncargadoComunicaciones::setNombre);

        binderForm.forField(lastName)
                .asRequired("El apellido es obligatorio")
                .bind(EncargadoComunicaciones::getApellidos, EncargadoComunicaciones::setApellidos);

        binderForm.forField(phoneNumber)
                .asRequired("El teléfono es obligatorio")
                .bind(EncargadoComunicaciones::getTelefono, EncargadoComunicaciones::setTelefono);

        binderForm.forField(dni)
                .asRequired("El DNI es obligatorio")
                .withValidator(dni1 -> dni1.length() == 9, "El DNI debe tener 9 caracteres")
                .withValidator(dni1 -> dni1.matches("[0-9]{8}[A-Za-z]"), "El DNI debe tener 8 números y una letra")
                .bind(EncargadoComunicaciones::getDni, EncargadoComunicaciones::setDni);

        binderForm.forField(birthDate)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(EncargadoComunicaciones::getFechaNacimiento, EncargadoComunicaciones::setFechaNacimiento);

        binderForm.forField(password)
                .asRequired("La contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .bind(EncargadoComunicaciones::getPassword, EncargadoComunicaciones::setPassword);

        binderForm.forField(confirmPassword)
                .asRequired("La confirmación de la contraseña es obligatoria")
                .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                .withValidator(password1 -> password1.equals(confirmPassword.getValue()), "Las contraseñas no coinciden")
                .bind(EncargadoComunicaciones::getPassword, EncargadoComunicaciones::setPassword);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(EncargadoComunicaciones::getEmail, EncargadoComunicaciones::setEmail);

        //ADD CLASS NAME
        Titulo.addClassName("CrearCuentaEncargado");

        //ADD
        formLayout.add(Titulo, firstName, lastName, phoneNumber, dni, birthDate, email, password, confirmPassword, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));

        formLayout.setColspan(Titulo, 2);
        formLayout.setHeight("650px");
        setSizeFull();

        submit.addClickShortcut(Key.ENTER);
        submit.addClickListener(event -> {
            if (binderForm.validate().isOk()) {
                EncargadoComunicaciones encargadoComunicaciones = new EncargadoComunicaciones();
                encargadoComunicaciones.setUUID(UUID.randomUUID());
                encargadoComunicaciones.setNombre(firstName.getValue());
                encargadoComunicaciones.setApellidos(lastName.getValue());
                encargadoComunicaciones.setTelefono(phoneNumber.getValue());
                encargadoComunicaciones.setDni(dni.getValue());
                encargadoComunicaciones.setFechaNacimiento(birthDate.getValue());
                encargadoComunicaciones.setEmail(email.getValue());
                encargadoComunicaciones.setPassword(passwordEncoder.encode(password.getValue()));
                encargadoComunicaciones.setRol(Role.ENCARGADO_COMUNICACIONES);
                boolean correcto = ComprobarDatos(encargadoComunicaciones);
                if (correcto) { CreateRequest(encargadoComunicaciones); }
            }
        });
        return formLayout;
    }

    private boolean ComprobarDatos(EncargadoComunicaciones encargado) {
        if (usuarioService.findUserByTelefono(encargado.getTelefono()) != null) {
            Notification errorTelefono = new Notification("El teléfono ya está en uso", 3000);
            errorTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorTelefono.open();
            return false;
        }

        if (usuarioService.findUserByDni(encargado.getDni()) != null) {
            Notification errorDni = new Notification("El DNI ya está en uso", 3000);
            errorDni.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorDni.open();
            return false;
        }

        if (usuarioService.findUserByEmail(encargado.getEmail()) != null) {
            Notification errorEmail = new Notification("El correo electrónico ya está en uso", 3000);
            errorEmail.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorEmail.open();
            return false;
        }
        return true;
    }

    private void CreateRequest(EncargadoComunicaciones encargado) {
        try {
            usuarioService.saveEncargado(encargado);
            ConfirmDialog confirmRequest = new ConfirmDialog("Registro Correcto", "Encargado de comunicación creado correctamente", "Aceptar", event1 -> {
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

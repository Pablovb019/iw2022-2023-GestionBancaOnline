package es.uca.iw.biwan.views.crearEncargadoComunicacion;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
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
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;
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
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("ADMINISTRADOR")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un administrador", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            }
                else {
                //NEW
                VerticalLayout layoutCrearEncargado = new VerticalLayout();
                HorizontalLayout layoutHorCrearEncargado = new HorizontalLayout();

                //ADD
                layoutHorCrearEncargado.add(crearEncargadoComunicacion());
                layoutCrearEncargado.add(HeaderView.Header(), layoutHorCrearEncargado, FooterView.Footer());

                //ALIGNMENT
                layoutHorCrearEncargado.setWidth("30%");
                layoutCrearEncargado.expand(layoutHorCrearEncargado);
                layoutHorCrearEncargado.setVerticalComponentAlignment(Alignment.CENTER, crearEncargadoComunicacion());
                layoutHorCrearEncargado.setAlignItems(Alignment.CENTER);
                layoutCrearEncargado.setAlignItems(Alignment.CENTER);
                layoutCrearEncargado.setSizeFull();

                add(layoutCrearEncargado);
            }
        }
    }

    private FormLayout crearEncargadoComunicacion() {

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
        Button submit = new Button("Crear cuenta");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitCrearEncargadoComunicacion");
        H1 Titulo = new H1("Crear cuenta de encargado de comunicación");
        FormLayout formLayout = new FormLayout();

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
        Titulo.addClassName("CrearCuentaEncargado");

        //ADD
        formLayout.add(Titulo, firstName, lastName, phoneNumber, dni, birthDate, email, password, confirmPassword, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("", 2));
        formLayout.setColspan(Titulo, 2);
        formLayout.setColspan(email, 2);
        setSizeFull();

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
                CreateRequest(encargadoComunicaciones);
            }
        });
        return formLayout;
    }

    private void CreateRequest(Usuario user) {
        try {
            usuarioService.save(user);
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

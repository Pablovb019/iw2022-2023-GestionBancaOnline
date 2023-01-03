package es.uca.iw.biwan.views.usuarios.ajustesUsuario;

import com.vaadin.flow.component.Component;
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
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Route("ajustes-gestor")
@PageTitle("Ajustes Gestor")
@CssImport("./themes/biwan/ajustesUsuario.css")
public class AjustesGestorView extends VerticalLayout {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    private TextField nombre = new TextField("Nombre");
    private TextField apellidos = new TextField("Apellidos");
    private DatePicker fechaNacimiento = new DatePicker("Fecha de Nacimiento");
    private NumberField telefono = new NumberField("Teléfono");
    private EmailField email = new EmailField("Correo Electrónico");
    private PasswordField password = new PasswordField("Contraseña");
    private PasswordField confirmPassword = new PasswordField("Confirmar contraseña");
    private Button atras = new Button("Atrás");
    private Button save = new Button("Guardar");

    public AjustesGestorView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Gestor.class) != null) {
            add(HeaderUsuarioLogueadoView.Header());
            add(crearTitulo());
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Volver", event -> {
                UI.getCurrent().navigate("");
            });
            error.open();
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Información Personal Gestor");
        VerticalLayout vlTitulo = new VerticalLayout(titulo);
        vlTitulo.setAlignItems(Alignment.CENTER);
        vlTitulo.addClassName("vlTitulo");
        titulo.addClassName("tituloColor");
        return vlTitulo;
    }

    private Component crearFormulario() {
        FormLayout flForm = new FormLayout();
        email.setErrorMessage("Por favor, introduzca un correo electrónico válido");
        flForm.add(nombre, apellidos, fechaNacimiento, telefono, email, password, confirmPassword, crearBotones());
        flForm.setColspan(email, 2);
        flForm.addClassName("Formulario");

        Binder<Gestor> binderForm = new Binder<>(Gestor.class);
        Gestor gestor = VaadinSession.getCurrent().getAttribute(Gestor.class);
        gestor.setPassword(""); // Por defecto si la contraseña no se modifica, se deja vacía
        binderForm.setBean(gestor);

        Gestor gestorBD = (Gestor) usuarioService.findUserByEmail(gestor.getEmail());

        binderForm.forField(nombre)
                .asRequired("El nombre es obligatorio")
                .bind(Gestor::getNombre, Gestor::setNombre);

        binderForm.forField(apellidos)
                .asRequired("El apellido es obligatorio")
                .bind(Gestor::getApellidos, Gestor::setApellidos);

        binderForm.forField(fechaNacimiento)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(Gestor::getFechaNacimiento, Gestor::setFechaNacimiento);

        binderForm.forField(telefono)
                .asRequired("El teléfono es obligatorio")
                .bind(Gestor::getTelefono, Gestor::setTelefono);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Gestor::getEmail, Gestor::setEmail);

        save.addClickListener(event -> {

            if(!password.getValue().isEmpty() || !confirmPassword.getValue().isEmpty()) {
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
            }

            if(binderForm.validate().isOk()){
                Gestor gestorForm = binderForm.getBean();

                if(Objects.equals(gestorForm.getNombre(), gestorBD.getNombre()) &&
                        Objects.equals(gestorForm.getApellidos(), gestorBD.getApellidos()) &&
                        Objects.equals(gestorForm.getFechaNacimiento(), gestorBD.getFechaNacimiento()) &&
                        Objects.equals(gestorForm.getTelefono(), gestorBD.getTelefono()) &&
                        Objects.equals(gestorForm.getEmail(), gestorBD.getEmail())) {
                    Notification errEqual = new Notification("No se han realizado cambios", 3000);
                    errEqual.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    errEqual.open();
                } else {

                    if(gestorForm.getPassword().isEmpty()) {
                        gestorForm.setPassword(gestorBD.getPassword());
                    } else {
                        gestorForm.setPassword(passwordEncoder.encode(gestorForm.getPassword()));
                    }

                    boolean correcta = ComprobarDatos(gestorForm);
                    if (correcta) {
                        usuarioService.updateGestor(gestorForm);
                        ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Los datos se han actualizado correctamente", "Confirmar", event1 -> {
                            UI.getCurrent().navigate("pagina-principal-gestor");
                        });
                        confirmDialog.open();
                    }
                }
            }
        });

        return flForm;
    }

    private boolean ComprobarDatos(Usuario user) {
        if (usuarioService.findUserByTelefono(user.getTelefono()) != null) {
            if (!Objects.equals(user.getDni(), usuarioService.findUserByTelefono(user.getTelefono()).getDni())) {
                Notification errTelefono = new Notification("El teléfono ya está en uso", 3000);
                errTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
                errTelefono.open();
                return false;
            }
        }

        if (usuarioService.findUserByEmail(user.getEmail()) != null) {
            if (!Objects.equals(user.getDni(), usuarioService.findUserByEmail(user.getEmail()).getDni())) {
                Notification errEmail = new Notification("El correo electrónico ya está en uso", 3000);
                errEmail.addThemeVariants(NotificationVariant.LUMO_ERROR);
                errEmail.open();
                return false;
            }
        }

        return true;
    }

    private Component crearBotones() {
        save.addClassName("BotonGuardar");
        atras.addClassName("BotonAtras");
        HorizontalLayout hlButtons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        hlButtons.add(save);
        hlButtons.add(atras);
        VerticalLayout vlButtons = new VerticalLayout(hlButtons);
        vlButtons.setAlignItems(Alignment.CENTER);
        vlButtons.addClassName("Botones");
        // Evento para volver a la pagina principal
        atras.addClickListener(e -> {
            VaadinSession session = VaadinSession.getCurrent();
            if(session.getAttribute(Gestor.class) != null) { UI.getCurrent().navigate("pagina-principal-gestor"); }
            else {
                ConfirmDialog error = new ConfirmDialog("Error", "El usuario no esta logueado", "Aceptar", null);
                error.open();
                UI.getCurrent().navigate("");
            }
        });
        return vlButtons;
    }

    @PostConstruct
    public void init() {
        add(crearFormulario());
        add(FooterView.Footer());
    }
}

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
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Route("ajustes-usuario")
@PageTitle("Ajustes Usuario")
@CssImport("./themes/biwan/ajustesUsuario.css")
public class AjustesUsuarioView extends VerticalLayout {

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

    public AjustesUsuarioView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            add(HeaderUsuarioLogueadoView.Header());
            add(crearTitulo());
            add(crearFormulario());
            add(FooterView.Footer());
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Volver", event -> {
                UI.getCurrent().navigate("");
            });
            error.open();
        }
    }

    private Component crearTitulo() {
        var titulo = new H2("Información Personal");
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

        Binder<Usuario> binderForm = new Binder<>(Usuario.class);
        Usuario usuario = VaadinSession.getCurrent().getAttribute(Usuario.class);
        usuario.setPassword(""); // Por defecto si la contraseña no se modifica, se deja vacía
        binderForm.setBean(usuario);

        binderForm.forField(nombre)
                .asRequired("El nombre es obligatorio")
                .bind(Usuario::getNombre, Usuario::setNombre);

        binderForm.forField(apellidos)
                .asRequired("El apellido es obligatorio")
                .bind(Usuario::getApellidos, Usuario::setApellidos);

        binderForm.forField(fechaNacimiento)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(Usuario::getFechaNacimiento, Usuario::setFechaNacimiento);

        binderForm.forField(telefono)
                .asRequired("El teléfono es obligatorio")
                .bind(Usuario::getTelefono, Usuario::setTelefono);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Usuario::getEmail, Usuario::setEmail);


        // binder of password, only check if is not empty
        if(!password.getValue().isBlank() || !confirmPassword.getValue().isBlank()) {
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
        }

        save.addClickListener(event -> {
            if(binderForm.validate().isOk()){
                Usuario usuario1 = binderForm.getBean();    // Nuevo usuario con los datos del formulario
                Usuario usuario2 = usuarioService.findUserByEmail(usuario1.getEmail()); // Usuario con los datos de la base de datos
                usuario2.setRol(Role.valueOf(usuario1.getRol()));

                // Si todos los cambios, excepto la constraseña ya que esta cifrada, son iguales, no se hace nada
                if(Objects.equals(usuario1.getNombre(), usuario2.getNombre()) &&
                        Objects.equals(usuario1.getApellidos(), usuario2.getApellidos()) &&
                        Objects.equals(usuario1.getFechaNacimiento(), usuario2.getFechaNacimiento()) &&
                        Objects.equals(usuario1.getTelefono(), usuario2.getTelefono()) &&
                        Objects.equals(usuario1.getEmail(), usuario2.getEmail())){
                    Notification sinCambios = new Notification("No se han realizado cambios", 3000);
                    sinCambios.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    sinCambios.open();
                } else {
                    if(!Objects.equals(usuario2.getNombre(), usuario1.getNombre())){
                        usuario2.setNombre(usuario1.getNombre());
                    }

                    if(!Objects.equals(usuario2.getApellidos(), usuario1.getApellidos())){
                        usuario2.setApellidos(usuario1.getApellidos());
                    }

                    if(!Objects.equals(usuario2.getFechaNacimiento(), usuario1.getFechaNacimiento())){
                        usuario2.setFechaNacimiento(usuario1.getFechaNacimiento());
                    }

                    if(!Objects.equals(usuario2.getTelefono(), usuario1.getTelefono())){
                        usuario2.setTelefono(usuario1.getTelefono());
                    }

                    if(!Objects.equals(usuario2.getEmail(), usuario1.getEmail())){
                        usuario2.setEmail(usuario1.getEmail());
                    }

                    if(!Objects.equals(usuario1.getPassword(), "") && !passwordEncoder.matches(usuario1.getPassword(), usuario2.getPassword())) {
                        usuario2.setPassword(passwordEncoder.encode(usuario1.getPassword()));
                    }

                    boolean correcto = ComprobarDatos(usuario2);
                    if (correcto) {
                        usuarioService.update(usuario2);
                        ConfirmDialog ConfigUpdated = new ConfirmDialog("Cambios guardados", "Los cambios se han guardado correctamente", "Aceptar", event1 -> {

                            if (usuario2.getRol().equals(Role.CLIENTE.toString())) { UI.getCurrent().navigate("pagina-principal-cliente"); }
                            else if (usuario2.getRol().equals(Role.GESTOR.toString())) { UI.getCurrent().navigate("pagina-principal-gestor"); }
                            else if (usuario2.getRol().equals(Role.ENCARGADO_COMUNICACIONES.toString())) { UI.getCurrent().navigate("pagina-principal-encargado"); }
                            else if (usuario2.getRol().equals(Role.ADMINISTRADOR.toString())) { UI.getCurrent().navigate("pagina-principal-administrador"); }

                        });
                        ConfigUpdated.open();
                    }
                }
            }
        });

        return flForm;
    }

    private boolean ComprobarDatos(Usuario user) {
        if (usuarioService.findUserByTelefono(user.getTelefono()) != null && Double.compare(user.getTelefono(), usuarioService.findUserByTelefono(user.getTelefono()).getTelefono()) != 0) {
            Notification telefono = new Notification("El teléfono ya está en uso", 3000);
            telefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            telefono.open();
            return false;
        }

        if (usuarioService.findUserByEmail(user.getEmail()) != null && user.getEmail().compareTo(usuarioService.findUserByEmail(user.getEmail()).getEmail()) != 0) {
            Notification email = new Notification("El email ya está en uso", 3000);
            email.addThemeVariants(NotificationVariant.LUMO_ERROR);
            email.open();
            return false;
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
            if(session.getAttribute(Usuario.class) != null) {
                if (session.getAttribute(Usuario.class).getRol().contentEquals("ADMINISTRADOR")) {
                    UI.getCurrent().navigate("");
                } else if (session.getAttribute(Usuario.class).getRol().contentEquals("CLIENTE")) {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                } else if (session.getAttribute(Usuario.class).getRol().contentEquals("GESTOR")) {
                    UI.getCurrent().navigate("pagina-principal-gestor");
                } else if (session.getAttribute(Usuario.class).getRol().contentEquals("ENCARGADO_COMUNICACIONES")) {
                    UI.getCurrent().navigate("pagina-principal-encargado");
                } else {
                    ConfirmDialog error = new ConfirmDialog("Error", "El usuario no tiene rol", "Aceptar", null);
                    error.open();
                    UI.getCurrent().navigate("");
                }
            } else {
                ConfirmDialog error = new ConfirmDialog("Error", "El usuario no esta logueado", "Aceptar", null);
                error.open();
                UI.getCurrent().navigate("");
            }
        });
        return vlButtons;
    }
}


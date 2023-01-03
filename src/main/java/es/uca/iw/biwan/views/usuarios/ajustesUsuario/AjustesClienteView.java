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
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Route("ajustes-cliente")
@PageTitle("Ajustes Cliente")
@CssImport("./themes/biwan/ajustesUsuario.css")
public class AjustesClienteView extends VerticalLayout {

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

    public AjustesClienteView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null) {
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
        var titulo = new H2("Información Personal Cliente");
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

        Binder<Cliente> binderForm = new Binder<>(Cliente.class);
        Cliente cliente = VaadinSession.getCurrent().getAttribute(Cliente.class);
        cliente.setPassword(""); // Por defecto si la contraseña no se modifica, se deja vacía
        binderForm.setBean(cliente);

        Cliente clienteBD = (Cliente) usuarioService.findUserByEmail(cliente.getEmail());

        binderForm.forField(nombre)
                .asRequired("El nombre es obligatorio")
                .bind(Cliente::getNombre, Cliente::setNombre);

        binderForm.forField(apellidos)
                .asRequired("El apellido es obligatorio")
                .bind(Cliente::getApellidos, Cliente::setApellidos);

        binderForm.forField(fechaNacimiento)
                .asRequired("La fecha de nacimiento es obligatoria")
                .withValidator(birthDate1 -> birthDate1.isBefore((java.time.LocalDate.now().minusYears(18).plusDays(1))), "El usuario ha de ser mayor de edad")
                .bind(Cliente::getFechaNacimiento, Cliente::setFechaNacimiento);

        binderForm.forField(telefono)
                .asRequired("El teléfono es obligatorio")
                .bind(Cliente::getTelefono, Cliente::setTelefono);

        binderForm.forField(email)
                .asRequired("El correo electrónico es obligatorio")
                .withValidator(email1 -> email1.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "El correo electrónico no es válido")
                .bind(Cliente::getEmail, Cliente::setEmail);

        System.out.println("Contraseña vacia: " + password.isEmpty());
        System.out.println("Confirmar Contraseña vacia: " + confirmPassword.isEmpty());

        save.addClickListener(event -> {

            if(!password.getValue().isEmpty() || !confirmPassword.getValue().isEmpty()) {
                binderForm.forField(password)
                        .asRequired("La contraseña es obligatoria")
                        .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                        .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                        .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                        .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                        .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                        .bind(Cliente::getPassword, Cliente::setPassword);

                binderForm.forField(confirmPassword)
                        .asRequired("La confirmación de la contraseña es obligatoria")
                        .withValidator(password1 -> password1.length() >= 8, "La contraseña debe tener al menos 8 caracteres")
                        .withValidator(password1 -> password1.matches(".*[A-Z].*"), "La contraseña debe tener al menos una mayúscula")
                        .withValidator(password1 -> password1.matches(".*[a-z].*"), "La contraseña debe tener al menos una minúscula")
                        .withValidator(password1 -> password1.matches(".*[0-9].*"), "La contraseña debe tener al menos un número")
                        .withValidator(password1 -> password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"), "La contraseña debe tener al menos un caracter especial")
                        .withValidator(password1 -> password1.equals(confirmPassword.getValue()), "Las contraseñas no coinciden")
                        .bind(Cliente::getPassword, Cliente::setPassword);
            }

            if(binderForm.validate().isOk()){
                Cliente clienteForm = binderForm.getBean();

                if(Objects.equals(clienteForm.getNombre(), clienteBD.getNombre()) &&
                        Objects.equals(clienteForm.getApellidos(), clienteBD.getApellidos()) &&
                        Objects.equals(clienteForm.getFechaNacimiento(), clienteBD.getFechaNacimiento()) &&
                        Objects.equals(clienteForm.getTelefono(), clienteBD.getTelefono()) &&
                        Objects.equals(clienteForm.getEmail(), clienteBD.getEmail()) &&
                        password.isEmpty() && confirmPassword.isEmpty()) {
                    Notification errEqual = new Notification("No se han realizado cambios", 3000);
                    errEqual.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    errEqual.open();
                } else {
                    if(clienteForm.getPassword().isEmpty()) {
                        clienteForm.setPassword(clienteBD.getPassword());
                    } else {
                        clienteForm.setPassword(passwordEncoder.encode(clienteForm.getPassword()));
                    }

                    boolean correcta = ComprobarDatos(clienteForm);
                    if (correcta) {
                        usuarioService.updateCliente(clienteForm);
                        ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Los datos se han actualizado correctamente", "Confirmar", event1 -> {
                            UI.getCurrent().navigate("pagina-principal-cliente");
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
            if(session.getAttribute(Cliente.class) != null) { UI.getCurrent().navigate("pagina-principal-cliente"); }
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


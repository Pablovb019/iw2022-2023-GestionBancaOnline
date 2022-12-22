package es.uca.iw.biwan.views.usuarios.ajustesCliente;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@Route("ajustes-cliente")
@PageTitle("Ajustes Cliente")
@CssImport("./themes/biwan/ajustesCliente.css")
public class AjustesClienteView extends VerticalLayout {
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
        add(HeaderUsuarioLogueadoView.Header());
        add(crearTitulo());
        add(crearFormulario());
        add(FooterView.Footer());
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
        return flForm;
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
                if (session.getAttribute(Usuario.class).getRole().contentEquals("ADMINISTRADOR")) {
                    UI.getCurrent().navigate("");
                } else if (session.getAttribute(Usuario.class).getRole().contentEquals("CLIENTE")) {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                } else if (session.getAttribute(Usuario.class).getRole().contentEquals("GESTOR")) {
                    UI.getCurrent().navigate("pagina-principal-gestor");
                } else if (session.getAttribute(Usuario.class).getRole().contentEquals("ENCARGADO_COMUNICACIONES")) {
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


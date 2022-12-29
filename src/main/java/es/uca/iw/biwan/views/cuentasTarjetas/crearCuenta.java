package es.uca.iw.biwan.views.cuentasTarjetas;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.util.UUID;

@Route("crear-cuenta-gestor")
@PageTitle("Crear Cuenta")
@CssImport("./themes/biwan/crearCuenta.css")
public class crearCuenta extends VerticalLayout{

    private CuentaService cuentaService;
    private static Usuario usuarioSeleccionado;

    // Setter para coger la información que depende del usuario que hayamos seleccionado para crear cuenta
    public static void setUsuarioSeleccionado(Usuario usuario) {
        usuarioSeleccionado = usuario;
    }

    public crearCuenta(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("GESTOR")) {
                UI.getCurrent().navigate("");
            } else {
                //NEW
                VerticalLayout layoutCrearCuenta = new VerticalLayout();

                //ADD
                layoutCrearCuenta.add(HeaderUsuarioLogueadoView.Header(), LayoutPrincipalCrearCuenta(), FooterView.Footer());

                //ALIGNMENT
                layoutCrearCuenta.expand(LayoutPrincipalCrearCuenta());
                layoutCrearCuenta.setAlignItems(Alignment.CENTER);
                layoutCrearCuenta.setSizeFull();

                add(layoutCrearCuenta);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private VerticalLayout LayoutPrincipalCrearCuenta() {

        Binder<Cuenta> binderCrearCuenta = new Binder<>(Cuenta.class);

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre() + " " + session.getAttribute(Usuario.class).getApellidos();

        //NEW
        VerticalLayout layoutPrincipalCrearCuenta = new VerticalLayout();
        H1 Titulo = new H1("Bienvenido Gestor: " + nombre);
        H3 NombreClienteSeleccionado = new H3("Creación de cuenta para el cliente: " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellidos());
        TextField IBAN = new TextField("Número de Cuenta Bancaria \"IBAN\"", "ESXX XXXX XXXX XXXX XXXX XXXX");
        IBAN.setClearButtonVisible(true);
        NumberField Balance = new NumberField();
        Balance.setLabel("Balance");
        Balance.setPlaceholder("00.00");
        Balance.setMin(0.00);
        Balance.setValue(0.00);
        Balance.setClearButtonVisible(true);
        Balance.setErrorMessage("El valor debe ser 0 o mayor");
        Button CrearCuentaSubmit = new Button("Crear Cuenta");
        CrearCuentaSubmit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        CrearCuentaSubmit.addClickShortcut(Key.ENTER);

        //BINDER
        binderCrearCuenta.forField(IBAN)
                .asRequired("El IBAN es obligatorio")
                .withValidator(iban -> iban.length() == 29, "El IBAN debe tener 24 caracteres con espacio cada 4")
                .withValidator(iban -> iban.matches("^ES[0-9]{2}(\\s[0-9]{4}){5}$"), "No es un formato correcto, revisa que está bien escrito")
                .bind(Cuenta::getIBAN, Cuenta::setIBAN);

        binderCrearCuenta.forField(Balance)
                .asRequired("El balance es obligatorio")
                .withValidator(value -> value >= 0, "El balance no puede ser negativo")
                .bind(Cuenta::getBalance, Cuenta::setBalance);

        //ADD CLASS NAME
        Titulo.addClassName("TituloCrearCuenta");
        layoutPrincipalCrearCuenta.addClassName("layoutPrincipalCrearCuenta");
        IBAN.addClassName("Campo");
        Balance.addClassName("Campo");
        CrearCuentaSubmit.addClassName("CrearCuentaSubmit");

        //ALIGNMENT
        layoutPrincipalCrearCuenta.setHorizontalComponentAlignment(Alignment.START, Titulo, NombreClienteSeleccionado);
        layoutPrincipalCrearCuenta.setHorizontalComponentAlignment(Alignment.CENTER, IBAN, Balance, CrearCuentaSubmit);
        layoutPrincipalCrearCuenta.setHeightFull();

        //ADD
        layoutPrincipalCrearCuenta.add(Titulo, NombreClienteSeleccionado, IBAN, Balance, CrearCuentaSubmit);

        CrearCuentaSubmit.addClickListener(event -> {
            if (binderCrearCuenta.validate().isOk()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIBAN(IBAN.getValue());
                cuenta.setBalance(Balance.getValue());
                boolean correcto = ComprobarDatos(cuenta);
                if(correcto) { CreateRequest(cuenta); }
            }
        });

        return layoutPrincipalCrearCuenta;
    }

    private boolean ComprobarDatos(Cuenta cuenta) {
        if (cuentaService.findCuentaByIban(cuenta.getIBAN()) != null) {
            Notification errorTelefono = new Notification("El IBAN ya está en uso", 3000);
            errorTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorTelefono.open();
            return false;
        }
        return true;
    }

    private void CreateRequest(Cuenta cuenta) {
        try {
            cuentaService.save(cuenta);
            ConfirmDialog confirmRequest = new ConfirmDialog("Cuenta Creada", "Cuenta creada correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/pagina-principal-gestor");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al administrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }
}

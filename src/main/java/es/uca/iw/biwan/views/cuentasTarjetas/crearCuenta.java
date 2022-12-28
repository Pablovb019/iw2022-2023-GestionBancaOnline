package es.uca.iw.biwan.views.cuentasTarjetas;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@Route("crear-cuenta-gestor")
@PageTitle("Crear Cuenta")
@CssImport("./themes/biwan/crearCuenta.css")
public class crearCuenta extends VerticalLayout {

    public crearCuenta() {
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

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre();

        //NEW
        VerticalLayout layoutPrincipalCrearCuenta = new VerticalLayout();
        H1 Titulo = new H1("Bienvenido Gestor: " + nombre);
        H3 NombreClienteSeleccionado = new H3("Creación de cuenta para el cliente: [Implementar nombre cliente]");
        TextField IBAN = new TextField("Número de Cuenta Bancaria \"IBAN\"", "ESXX XXXX XXXX XXXX XXXX XXXX");
        IBAN.setPattern("^ES[0-9]{2}(\\s[0-9]{4}){5}$");
        IBAN.setMaxLength(29);
        IBAN.setErrorMessage("No es un formato correcto, revisa que está bien escrito");
        IBAN.setClearButtonVisible(true);
        NumberField Balance = new NumberField();
        Balance.setLabel("Balance");
        Balance.setPlaceholder("00.00");
        Balance.setMin(0);
        Balance.setValue(0.00);
        Balance.setClearButtonVisible(true);
        Balance.setErrorMessage("El valor debe ser 0 o mayor");
        Button CrearCuentaSubmit = new Button("Crear Cuenta");
        CrearCuentaSubmit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        CrearCuentaSubmit.addClickShortcut(Key.ENTER);

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

        return layoutPrincipalCrearCuenta;
    }
}

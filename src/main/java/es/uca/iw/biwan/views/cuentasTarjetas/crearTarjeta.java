package es.uca.iw.biwan.views.cuentasTarjetas;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

@Route("crear-tarjeta-gestor")
@PageTitle("Crear Tarjeta")
@CssImport("./themes/biwan/crearTarjeta.css")
public class crearTarjeta extends VerticalLayout {

    public crearTarjeta() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("GESTOR")) {
                UI.getCurrent().navigate("");
            } else {
                //NEW
                VerticalLayout layoutCrearTarjeta = new VerticalLayout();

                //ADD
                layoutCrearTarjeta.add(HeaderUsuarioLogueadoView.Header(), LayoutPrincipalCrearTarjeta(), FooterView.Footer());

                //ALIGNMENT
                layoutCrearTarjeta.expand(LayoutPrincipalCrearTarjeta());
                layoutCrearTarjeta.setAlignItems(Alignment.CENTER);
                layoutCrearTarjeta.setSizeFull();

                add(layoutCrearTarjeta);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "El usuario no esta logueado", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private VerticalLayout LayoutPrincipalCrearTarjeta() {

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre();

        //NEW
        VerticalLayout layoutPrincipalCrearTarjeta = new VerticalLayout();
        H1 Titulo = new H1("Bienvenido Gestor: " + nombre);
        H3 NombreClienteSeleccionado = new H3("Creación de tarjeta para el cliente: [Implementar nombre cliente]");
        TextField NumeroTarjeta = new TextField("Número de Tarjeta", "XXXX XXXX XXXX XXXX");
        NumeroTarjeta.setPattern("^(\\s*\\t*[0-9]){16}$");
        NumeroTarjeta.setMaxLength(19);
        NumeroTarjeta.setAllowedCharPattern("[\\d\\s]");
        NumeroTarjeta.setErrorMessage("No es un formato correcto");
        NumeroTarjeta.setClearButtonVisible(true);
        TextField FechaCaducidad = new TextField("Fecha de Caducidad", "mm/aa");
        FechaCaducidad.setAllowedCharPattern("[0-9/]");
        FechaCaducidad.setPattern("(0[1-9]|1[0-2])\\/([2-9][3-9]|[3-9]\\d)");
        FechaCaducidad.setMaxLength(5);
        FechaCaducidad.setClearButtonVisible(true);
        FechaCaducidad.setErrorMessage("Fecha o formato no válidos");
        TextField CVV = new TextField("CVV", "XXX");
        CVV.setAllowedCharPattern("\\d");
        CVV.setPattern("\\d{3}");
        CVV.setMaxLength(3);
        CVV.setErrorMessage("Formato no válido");
        CVV.setClearButtonVisible(true);
        HorizontalLayout layoutFechaCaducidadCVV = new HorizontalLayout();
        Button CrearCuentaSubmit = new Button("Crear Tarjeta");
        CrearCuentaSubmit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        CrearCuentaSubmit.addClickShortcut(Key.ENTER);

        //ADD CLASS NAME
        Titulo.addClassName("TituloCrearTarjeta");
        layoutPrincipalCrearTarjeta.addClassName("layoutPrincipalCrearTarjeta");
        NumeroTarjeta.addClassName("Campo");
        FechaCaducidad.addClassName("FechaCaducidad");
        CVV.addClassName("CVV");
        CrearCuentaSubmit.addClassName("CrearCuentaSubmit");

        //ALIGNMENT
        layoutPrincipalCrearTarjeta.setHorizontalComponentAlignment(Alignment.START, Titulo, NombreClienteSeleccionado);
        layoutPrincipalCrearTarjeta.setHorizontalComponentAlignment(Alignment.CENTER, NumeroTarjeta, layoutFechaCaducidadCVV, CrearCuentaSubmit);
        layoutPrincipalCrearTarjeta.setHeightFull();

        //ADD
        layoutFechaCaducidadCVV.add(FechaCaducidad, CVV);
        layoutPrincipalCrearTarjeta.add(Titulo, NombreClienteSeleccionado, NumeroTarjeta, layoutFechaCaducidadCVV, CrearCuentaSubmit);

        return layoutPrincipalCrearTarjeta;
    }
}

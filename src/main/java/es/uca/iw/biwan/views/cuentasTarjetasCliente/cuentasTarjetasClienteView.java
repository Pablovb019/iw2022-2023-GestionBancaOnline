package es.uca.iw.biwan.views.cuentasTarjetasCliente;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Route("cuentas-tarjetas-cliente")
@CssImport("./themes/biwan/cuentasTarjetasCliente.css")
@CssImport(value = "./styles/components/vaadin-checkbox.css", themeFor = "vaadin-checkbox")
@PageTitle("Cuentas y Tarjetas")

public class cuentasTarjetasClienteView extends VerticalLayout {
    public cuentasTarjetasClienteView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null) {
            if (!session.getAttribute(Cliente.class).getRol().contentEquals("CLIENTE")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(CuentasTarjetasCliente());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private Component CuentasTarjetasCliente() {
        //Creacion de los apartados
        H1 Titulo = new H1("Cuentas y Tarjetas");
        H2 TituloCuentas = new H2("Cuentas");
        H2 TituloTarjetas = new H2("Tarjetas");

        //CSS
        Titulo.addClassName("TituloPrincipalCuentaTarjeta");
        Titulo.setWidthFull();

        TituloCuentas.addClassName("TituloSecundarioCuentaTarjeta");
        TituloCuentas.setWidthFull();

        TituloTarjetas.addClassName("TituloSecundarioCuentaTarjeta");
        TituloTarjetas.setWidthFull();

        //Creacion de la pagina
        // Layout de Cuenta y Tarjetas

        // Inicializacion de la tabla de Cuentas
        Cuenta cuenta1 = new Cuenta();
        Cuenta cuenta2 = new Cuenta();
        Cuenta cuenta3 = new Cuenta();
        Cuenta cuenta4 = new Cuenta();

        Grid<Cuenta> gridCuentas = new Grid<>(Cuenta.class, false);
        gridCuentas.addClassName("TablaCuentaTarjeta");
        gridCuentas.addColumn(Cuenta::getIBAN).setHeader("IBAN").setTextAlign(ColumnTextAlign.CENTER);
        gridCuentas.addColumn(cuenta -> String.format("%,.2f €", cuenta.getBalance())).setHeader("Balance").setTextAlign(ColumnTextAlign.CENTER);

        gridCuentas.setItems(cuenta1, cuenta2, cuenta3, cuenta4);
        gridCuentas.setWidthFull();
        var vlCuentas = new VerticalLayout(TituloCuentas, gridCuentas);

        DateFormat df = new SimpleDateFormat("MM/yy");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2024);
        cal1.set(Calendar.MONTH, Calendar.FEBRUARY);
        Date date1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2023);
        cal2.set(Calendar.MONTH, Calendar.DECEMBER);
        Date date2 = cal2.getTime();

        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.YEAR, 2021);
        cal2.set(Calendar.MONTH, Calendar.APRIL);
        Date date3 = cal3.getTime();

        Calendar cal4 = Calendar.getInstance();
        cal4.set(Calendar.YEAR, 2026);
        cal4.set(Calendar.MONTH, Calendar.AUGUST);
        Date date4 = cal4.getTime();

        // Inicializacion de la tabla de Tarjetas

        Grid<Tarjeta> gridTarjetas = new Grid<>(Tarjeta.class, false);
        gridTarjetas.addClassName("TablaCuentaTarjeta");
        gridTarjetas.addColumn(Tarjeta::getNumeroTarjeta).setHeader("Número de Tarjeta").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addColumn(tarjeta -> df.format(tarjeta.getFechaCaducidad())).setHeader("Fecha Caducidad").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addColumn(tarjeta -> String.format("%03d", tarjeta.getCVV())).setHeader("CVV").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addColumn(tarjeta -> String.format("%,.2f €", tarjeta.getLimiteGasto())).setHeader("Limite").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addComponentColumn(tarjeta -> {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setValue(tarjeta.getActiva());
            return toggleButton;
        }).setHeader("Estado").setTextAlign(ColumnTextAlign.CENTER);
        //gridTarjetas.setItems(tarjeta1, tarjeta2, tarjeta3, tarjeta4);
        gridTarjetas.setWidthFull();

        var vlTarjetas = new VerticalLayout(TituloTarjetas, gridTarjetas);
        var vlTitulo = new VerticalLayout(Titulo);


        var hlCuentasTarjetas = new HorizontalLayout(vlCuentas, vlTarjetas);
        hlCuentasTarjetas.setWidthFull();
        hlCuentasTarjetas.addClassName("hlCuentasTarjetas");

        var vlCuentasTarjetas = new VerticalLayout(vlTitulo, hlCuentasTarjetas);
        vlCuentasTarjetas.addClassName("vlCuentasTarjetas");

        return vlCuentasTarjetas;
    }
}

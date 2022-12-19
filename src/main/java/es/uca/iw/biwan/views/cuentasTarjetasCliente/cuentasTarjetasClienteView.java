package es.uca.iw.biwan.views.cuentasTarjetasCliente;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Route("cuentas-tarjetas-cliente")
@CssImport("/themes/biwan/cuentasTarjetasCliente.css")
@CssImport(value = "styles/components/vaadin-checkbox.css", themeFor = "vaadin-checkbox")
@PageTitle("Cuentas y Tarjetas")

public class cuentasTarjetasClienteView extends VerticalLayout {
    public cuentasTarjetasClienteView(){
        add(HeaderUsuarioLogueadoView.Header());
        add(CuentasTarjetasCliente());
        add(FooterView.Footer());
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
        Cuenta cuenta1 = new Cuenta(1000, "ES1234567890123456789012");
        Cuenta cuenta2 = new Cuenta(2000, "ES1234567890123456789013");
        Cuenta cuenta3 = new Cuenta(3000, "ES1234567890123456789014");
        Cuenta cuenta4 = new Cuenta(4000, "ES1234567890123456789015");

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
        Tarjeta tarjeta1 = new Tarjeta("1234567890123456", date1, 512, 2000, true);
        Tarjeta tarjeta2 = new Tarjeta("2345678901234567", date2, 278, 4000, true);
        Tarjeta tarjeta3 = new Tarjeta("3456789012345678", date3, 49, 1250, false);
        Tarjeta tarjeta4 = new Tarjeta("0123456789012345", date4, 623, 500, true);

        Grid<Tarjeta> gridTarjetas = new Grid<>(Tarjeta.class, false);
        gridTarjetas.addClassName("TablaCuentaTarjeta");
        gridTarjetas.addColumn(Tarjeta::getNumero).setHeader("Numero").setTextAlign(ColumnTextAlign.CENTER).setWidth("150px");
        gridTarjetas.addColumn(tarjeta -> df.format(tarjeta.getCaducidad())).setHeader("Fecha Caducidad").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addColumn(tarjeta -> String.format("%03d", tarjeta.getCVV())).setHeader("CVV").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addColumn(tarjeta -> String.format("%,.2f €", tarjeta.getLimiteGasto())).setHeader("Limite").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.addComponentColumn(tarjeta -> {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setValue(tarjeta.getActiva());
            return toggleButton;
        }).setHeader("Estado").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetas.setItems(tarjeta1, tarjeta2, tarjeta3, tarjeta4);
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

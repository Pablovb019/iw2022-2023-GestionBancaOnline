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
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.TarjetaService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Route("cuentas-tarjetas-cliente")
@CssImport("./themes/biwan/cuentasTarjetasCliente.css")
@CssImport(value = "./styles/components/vaadin-checkbox.css", themeFor = "vaadin-checkbox")
@PageTitle("Cuentas y Tarjetas")

public class cuentasTarjetasClienteView extends VerticalLayout {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TarjetaService tarjetaService;

    private Grid<Cuenta> gridCuentasCliente;
    private Grid<Tarjeta> gridTarjetasCliente;
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


        try {
            ArrayList<Cuenta> cuentasCliente = cuentaService.findCuentaByCliente(VaadinSession.getCurrent().getAttribute(Cliente.class));

            if(cuentasCliente.size() == 0) {
                ConfirmDialog errorCuentas = new ConfirmDialog("Error", "No tienes cuentas", "Aceptar", event -> {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                });
                errorCuentas.open();
            } else {
                gridCuentasCliente = new Grid<>();
                gridCuentasCliente.setItems(cuentasCliente);
                gridCuentasCliente.addClassName("TablaCuentaTarjeta");
                gridCuentasCliente.addColumn(Cuenta::getIBAN).setHeader("IBAN").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
                gridCuentasCliente.addColumn(cuenta -> String.format("%,.2f €", cuenta.getBalance())).setHeader("Balance").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
                gridCuentasCliente.setWidthFull();
            }
        } catch (Exception ignored) { }

        var vlCuentas = new VerticalLayout(TituloCuentas, gridCuentasCliente);

        DateFormat df = new SimpleDateFormat("MM/yy");

        // Inicializacion de la tabla de Tarjetas

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

        try {
            ArrayList<Tarjeta> tarjetasCliente = tarjetaService.findTarjetaByUUID(VaadinSession.getCurrent().getAttribute(Cliente.class).getUUID());

            gridTarjetasCliente = new Grid<>();
            gridTarjetasCliente.setItems(tarjetasCliente);
            gridTarjetasCliente.addClassName("TablaCuentaTarjeta");
            gridTarjetasCliente.addColumn(Tarjeta::getNumeroTarjeta).setHeader("Numero").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.addColumn(tarjeta -> tarjeta.getFechaCaducidad().format(formatter)).setHeader("Fecha Caducidad").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.addColumn(Tarjeta::getCVV).setHeader("CVV").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.addColumn(Tarjeta::getPIN).setHeader("PIN").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.addColumn(tarjeta -> String.format("%,.2f €", tarjeta.getLimiteGasto())).setHeader("Limite").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.addComponentColumn(tarjeta -> {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.setValue(tarjeta.getActiva());
                toggleButton.getElement().addEventListener("click", event -> {
                    tarjeta.setActiva(toggleButton.getValue());
                    tarjetaService.update(tarjeta);
                    ConfirmDialog confirmacion = new ConfirmDialog("Confirmación", "Se ha actualizado el estado de la tarjeta", "Aceptar", event1 -> {
                        UI.getCurrent().navigate("cuentas-tarjetas-cliente");
                    });
                    confirmacion.open();
                });
                return toggleButton;
            }).setHeader("Estado").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasCliente.setWidthFull();
        } catch (Exception ignored) { }

        var vlTarjetas = new VerticalLayout(TituloTarjetas, gridTarjetasCliente);
        var vlTitulo = new VerticalLayout(Titulo);

        var hlCuentasTarjetas = new HorizontalLayout(vlCuentas, vlTarjetas);
        vlCuentas.getStyle().set("flex-grow", "1");
        vlCuentas.getStyle().set("width", "40%");
        vlTarjetas.getStyle().set("flex-grow", "3");
        vlTarjetas.getStyle().set("width", "60%");
        hlCuentasTarjetas.setWidthFull();
        hlCuentasTarjetas.addClassName("hlCuentasTarjetas");

        var vlCuentasTarjetas = new VerticalLayout(vlTitulo, hlCuentasTarjetas);
        vlCuentasTarjetas.addClassName("vlCuentasTarjetas");

        return vlCuentasTarjetas;
    }

    @PostConstruct
    public void init() {
        try {
        add(CuentasTarjetasCliente());
        add(FooterView.Footer());
    }
    catch (Exception ignored) { }
    }
}

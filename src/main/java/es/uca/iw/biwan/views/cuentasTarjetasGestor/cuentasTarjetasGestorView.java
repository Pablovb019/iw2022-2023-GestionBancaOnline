package es.uca.iw.biwan.views.cuentasTarjetasGestor;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Route("cuentas-tarjetas-gestor")
@CssImport(value = "./styles/components/vaadin-checkbox.css", themeFor = "vaadin-checkbox")
@CssImport("./themes/biwan/cuentasTarjetasGestor.css")
@PageTitle("Cuentas y Tarjetas")

public class cuentasTarjetasGestorView extends VerticalLayout {
    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;
    private final TarjetaService tarjetaService;
    private Grid<Cuenta> gridCuentasClienteSeleccionado;
    private Grid<Tarjeta> gridTarjetasClienteSeleccionado;

    public static Cliente cliente;

    @Autowired
    public cuentasTarjetasGestorView(UsuarioService usuarioService, CuentaService cuentaService, TarjetaService tarjetaService) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        this.tarjetaService = tarjetaService;
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
            if (session.getAttribute(Cliente.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un gestor", "Aceptar", event -> {
                    if (session.getAttribute(Cliente.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-cliente");
                    } else if (session.getAttribute(EncargadoComunicaciones.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
                });
                error.open();
            } else {
                add(CuentasTarjetasGestor());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private Component WhiteSpace() {
        // White space
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeight("610px");
        return verticalLayout;
    }

    private Component CuentasTarjetasGestor() {
        add(HeaderUsuarioLogueadoView.Header());

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
        ArrayList<Cuenta> cuentasCliente = cuentaService.findCuentaByCliente(cliente);

        if (cuentasCliente.size() == 0) {
            add(WhiteSpace());
            ConfirmDialog error = new ConfirmDialog("Error", "No hay cuentas para este cliente. Por favor, cree una cuenta", "Aceptar", event -> {
                UI.getCurrent().navigate("pagina-principal-gestor");
            });
            error.open();
        } else {
            gridCuentasClienteSeleccionado = new Grid<>();
            gridCuentasClienteSeleccionado.setItems(cuentasCliente);
            gridCuentasClienteSeleccionado.addClassName("TablaCuentaTarjeta");
            gridCuentasClienteSeleccionado.addColumn(Cuenta::getIBAN).setHeader("IBAN").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridCuentasClienteSeleccionado.addColumn(cuenta -> String.format("%,.2f €", cuenta.getBalance())).setHeader("Balance").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridCuentasClienteSeleccionado.addComponentColumn(cuenta -> {
                Button EliminarCuenta = new Button("Eliminar");
                EliminarCuenta.addClassName("EliminarCuentaTarjeta");
                EliminarCuenta.getElement().addEventListener("click", event -> {
                    if (tarjetaService.findTarjetaByCuentaUUID(cuenta.getUUID()) == 0) {
                        ConfirmDialog ConfirmarEliminarCuenta = new ConfirmDialog();
                        ConfirmarEliminarCuenta.open();
                        ConfirmarEliminarCuenta.setHeader("Eliminar cuenta");
                        ConfirmarEliminarCuenta.setText("¿Estás seguro de que quieres eliminar esta cuenta?");
                        ConfirmarEliminarCuenta.setCancelable(true);
                        ConfirmarEliminarCuenta.setConfirmButtonTheme("error primary");
                        ConfirmarEliminarCuenta.setConfirmText("Eliminar");
                        ConfirmarEliminarCuenta.addConfirmListener(event2 -> {
                            cuentaService.delete(cuenta);
                            ConfirmDialog AceptarYRecargar = new ConfirmDialog();
                            AceptarYRecargar.open();
                            AceptarYRecargar.setHeader("Cuenta eliminada");
                            AceptarYRecargar.setText("La cuenta ha sido eliminada correctamente");
                            AceptarYRecargar.setConfirmText("Aceptar");
                            AceptarYRecargar.addConfirmListener(event3 -> {
                                UI.getCurrent().getPage().reload();
                            });
                        });
                    } else
                        new ConfirmDialog("No permitido", "La cuenta no se puede eliminar si tiene asignada alguna tarjeta", "Aceptar", null).open();
                });
                return EliminarCuenta;
            }).setHeader("Eliminar").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridCuentasClienteSeleccionado.setWidthFull();

            var vlCuentas = new VerticalLayout(TituloCuentas, gridCuentasClienteSeleccionado);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

            ArrayList<Tarjeta> tarjetasCliente = tarjetaService.findTarjetaByUUID(cliente.getUUID());

            gridTarjetasClienteSeleccionado = new Grid<>();
            gridTarjetasClienteSeleccionado.setItems(tarjetasCliente);
            gridTarjetasClienteSeleccionado.addClassName("TablaCuentaTarjeta");
            gridTarjetasClienteSeleccionado.addColumn(Tarjeta::getNumeroTarjeta).setHeader("Numero").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addColumn(tarjeta -> tarjeta.getFechaCaducidad().format(formatter)).setHeader("Fecha Caducidad").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addColumn(Tarjeta::getCVV).setHeader("CVV").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addColumn(Tarjeta::getPIN).setHeader("PIN").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addColumn(tarjeta -> String.format("%,.2f €", tarjeta.getLimiteGasto())).setHeader("Limite").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addComponentColumn(tarjeta -> {
                ToggleButton toggleButton = new ToggleButton();
                toggleButton.setValue(tarjeta.getActiva());
                toggleButton.getElement().addEventListener("click", event -> {
                    tarjeta.setActiva(toggleButton.getValue());
                    tarjetaService.update(tarjeta);
                    ConfirmDialog confirmacion = new ConfirmDialog("Confirmación", "Se ha actualizado el estado de la tarjeta", "Aceptar", event1 -> {
                        UI.getCurrent().navigate("cuentas-tarjetas-gestor");
                    });
                    confirmacion.open();
                });
                return toggleButton;
            }).setHeader("Estado").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.addComponentColumn(tarjeta -> {
                Button EliminarTarjeta = new Button("Eliminar");
                EliminarTarjeta.addClassName("EliminarCuentaTarjeta");
                EliminarTarjeta.getElement().addEventListener("click", event -> {
                    String ibanTarjetaAEliminar = tarjetaService.findIbanByNumeroTarjeta(tarjeta.getNumeroTarjeta());
                    ConfirmDialog ConfirmarEliminarTarjeta = new ConfirmDialog();
                    ConfirmarEliminarTarjeta.open();
                    ConfirmarEliminarTarjeta.setHeader("Eliminar tarjeta");
                    ConfirmarEliminarTarjeta.setText("¿Estás seguro de que quieres eliminar esta tarjeta? Pertenece " +
                            "a la cuenta " + ibanTarjetaAEliminar);
                    ConfirmarEliminarTarjeta.setCancelable(true);
                    ConfirmarEliminarTarjeta.setConfirmButtonTheme("error primary");
                    ConfirmarEliminarTarjeta.setConfirmText("Eliminar");
                    ConfirmarEliminarTarjeta.addConfirmListener(event2 -> {
                        tarjetaService.delete(tarjeta);
                        ConfirmDialog AceptarYRecargar = new ConfirmDialog();
                        AceptarYRecargar.open();
                        AceptarYRecargar.setHeader("Tarjeta eliminada");
                        AceptarYRecargar.setText("La tarjeta ha sido eliminada correctamente");
                        AceptarYRecargar.setConfirmText("Aceptar");
                        AceptarYRecargar.addConfirmListener(event3 -> {
                            UI.getCurrent().getPage().reload();
                        });
                    });
                });
                return EliminarTarjeta;
            }).setHeader("Eliminar").setTextAlign(ColumnTextAlign.CENTER).setAutoWidth(true);
            gridTarjetasClienteSeleccionado.setWidthFull();

            var vlTarjetas = new VerticalLayout(TituloTarjetas, gridTarjetasClienteSeleccionado);
            var vlTitulo = new VerticalLayout(Titulo);


            var hlCuentasTarjetas = new HorizontalLayout(vlCuentas, vlTarjetas);
            hlCuentasTarjetas.getStyle().set("display", "flex");
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
        return new VerticalLayout();
    }
}
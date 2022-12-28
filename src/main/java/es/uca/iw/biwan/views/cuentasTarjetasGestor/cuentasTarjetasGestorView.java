package es.uca.iw.biwan.views.cuentasTarjetasGestor;

import com.vaadin.componentfactory.ToggleButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
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
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Route("cuentas-tarjetas-gestor")
@CssImport(value = "./styles/components/vaadin-checkbox.css", themeFor = "vaadin-checkbox")
@PageTitle("Cuentas y Tarjetas")

public class cuentasTarjetasGestorView extends VerticalLayout {
    private UsuarioService usuarioService;
    private CuentaService cuentaService;
    private TarjetaService tarjetaService;
    private ComboBox<Usuario> comboBoxUsuarioCliente;
    private Grid<Cuenta> gridCuentasClienteSeleccionado;
    private Grid<Tarjeta> gridTarjetasClienteSeleccionado;
    private static Usuario usuarioSeleccionado;

    // Setter para coger la información que depende del usuario que hayamos seleccionado para ver sus cuentas y tarjetas
    public static void setUsuarioSeleccionado(Usuario usuario) {
        usuarioSeleccionado = usuario;
    }
@Autowired
    public cuentasTarjetasGestorView(UsuarioService usuarioService, CuentaService cuentaService, TarjetaService tarjetaService){
    this.usuarioService = usuarioService;
    this.cuentaService = cuentaService;
    this.tarjetaService = tarjetaService;
    VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("GESTOR")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un gestor", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(DesplegableCliente());
                add(WhiteSpace());
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

    private Component DesplegableCliente(){
        ComboBox<Usuario> comboBoxCliente = generateComboBoxCliente();
        comboBoxCliente.addValueChangeListener(event -> {
            removeAll();
            add(HeaderUsuarioLogueadoView.Header());
            if(event.getValue() != null) {
                add(DesplegableCliente(event.getValue()));
                add(CuentasTarjetasGestor(event.getValue()));
            }
            add(FooterView.Footer());
        });

        return comboBoxCliente;
    }

    private Component DesplegableCliente(Usuario cliente){
        ComboBox<Usuario> comboBoxCliente = generateComboBoxCliente();
        comboBoxCliente.setValue(cliente);
        comboBoxCliente.addValueChangeListener(event -> {
            removeAll();
            add(HeaderUsuarioLogueadoView.Header());
            if(event.getValue() != null) {
                add(DesplegableCliente(event.getValue()));
                add(CuentasTarjetasGestor(event.getValue()));
            }
            add(FooterView.Footer());

            if(event.getValue() == null){
                removeAll();
                add(HeaderUsuarioLogueadoView.Header());
                add(DesplegableCliente());
                add(WhiteSpace());
                add(FooterView.Footer());
            }
        });

        return comboBoxCliente;
    }

    private Component WhiteSpace(){
        // White space
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setHeight("525px");
        return verticalLayout;
    }

    private Component CuentasTarjetasGestor(Usuario clienteSeleccionado) {
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
        ArrayList<Cuenta> cuentasCliente = cuentaService.findCuentaByUUID(clienteSeleccionado.getUUID());

        gridCuentasClienteSeleccionado = new Grid<>();
        gridCuentasClienteSeleccionado.setItems(cuentasCliente);
        gridCuentasClienteSeleccionado.addClassName("TablaCuentaTarjeta");
        gridCuentasClienteSeleccionado.addColumn(Cuenta::getIBAN).setHeader("IBAN").setTextAlign(ColumnTextAlign.CENTER);
        gridCuentasClienteSeleccionado.addColumn(cuenta -> String.format("%,.2f €", cuenta.getBalance())).setHeader("Balance").setTextAlign(ColumnTextAlign.CENTER);
        gridCuentasClienteSeleccionado.setWidthFull();

        var vlCuentas = new VerticalLayout(TituloCuentas, gridCuentasClienteSeleccionado);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

        ArrayList<Tarjeta> tarjetasCliente = tarjetaService.findTarjetaByUUID(clienteSeleccionado.getUUID());

        gridTarjetasClienteSeleccionado = new Grid<>();
        gridTarjetasClienteSeleccionado.setItems(tarjetasCliente);
        gridTarjetasClienteSeleccionado.addClassName("TablaCuentaTarjeta");
        gridTarjetasClienteSeleccionado.addColumn(Tarjeta::getNumeroTarjeta).setHeader("Numero").setTextAlign(ColumnTextAlign.CENTER).setWidth("150px");
        gridTarjetasClienteSeleccionado.addColumn(tarjeta -> tarjeta.getFechaCaducidad().format(formatter)).setHeader("Fecha Caducidad").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetasClienteSeleccionado.addColumn(Tarjeta::getCVV).setHeader("CVV").setTextAlign(ColumnTextAlign.CENTER).setWidth("100px");
        gridTarjetasClienteSeleccionado.addColumn(tarjeta -> String.format("%,.2f €", tarjeta.getLimiteGasto())).setHeader("Limite").setTextAlign(ColumnTextAlign.CENTER);
        gridTarjetasClienteSeleccionado.addComponentColumn(tarjeta -> {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setValue(tarjeta.getActiva());
            toggleButton.getElement().addEventListener("click", event -> {
                tarjeta.setActiva(toggleButton.getValue());
                tarjetaService.update(tarjeta);
            });
            return toggleButton;
        }).setHeader("Estado").setTextAlign(ColumnTextAlign.CENTER);
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

    private ComboBox<Usuario> generateComboBoxCliente() {

        ArrayList<Usuario> clientes = usuarioService.findUsuarioByRol(Role.CLIENTE.toString());
        // Create a combo box with clientes
        comboBoxUsuarioCliente = new ComboBox<>();;
        comboBoxUsuarioCliente.setItems(clientes);
        comboBoxUsuarioCliente.setItemLabelGenerator(usuario -> usuario.getNombre() + " " + usuario.getApellidos());
        comboBoxUsuarioCliente.setValue(usuarioSeleccionado);
        comboBoxUsuarioCliente.setLabel("Cliente");
        comboBoxUsuarioCliente.setClearButtonVisible(true);
        comboBoxUsuarioCliente.setRequired(true);
        comboBoxUsuarioCliente.setRequiredIndicatorVisible(true);
        comboBoxUsuarioCliente.setWidth("300px");
        comboBoxUsuarioCliente.setPlaceholder("Selecciona un cliente");
        comboBoxUsuarioCliente.setHelperText("Selecciona un cliente para ver sus cuentas y tarjetas");
        // posiciona el combobox en el centro
        comboBoxUsuarioCliente.getStyle().set("margin-left", "auto");
        comboBoxUsuarioCliente.getStyle().set("margin-right", "auto");
        comboBoxUsuarioCliente.getStyle().set("marging-top", "20px");


        return comboBoxUsuarioCliente;
    }

    @PostConstruct
    public void init() {
        add(DesplegableCliente());
        add(FooterView.Footer());
    }
}

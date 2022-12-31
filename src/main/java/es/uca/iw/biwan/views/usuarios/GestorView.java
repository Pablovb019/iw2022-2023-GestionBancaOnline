package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.TarjetaService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.cuentasTarjetasGestor.cuentasTarjetasGestorView;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Objects;

@CssImport("./themes/biwan/paginaPrincipalGestor.css")
@PageTitle("Página Principal Gestor")
@Route("pagina-principal-gestor")
public class GestorView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TarjetaService tarjetaService;

    public static Cliente _cliente;

    public GestorView(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Gestor.class) != null) {
            if (!session.getAttribute(Gestor.class).getRol().contentEquals("GESTOR")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un gestor", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                //NEW
                VerticalLayout layoutGestor = new VerticalLayout();
                VerticalLayout layoutGestionConsultas = new VerticalLayout();
                layoutGestionConsultas.add(GestorConsultas());

                //ADD
                layoutGestor.add(HeaderUsuarioLogueadoView.Header(), layoutGestionConsultas, FooterView.Footer());

                //ALIGNMENT
                layoutGestor.expand(layoutGestionConsultas);
                layoutGestor.setAlignItems(Alignment.CENTER);
                layoutGestor.setSizeFull();

                add(layoutGestor);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private VerticalLayout GestorConsultas() {

        //NEW
        VerticalLayout layoutVerGestorPrincipal = new VerticalLayout();
        VerticalLayout layoutVerGestorTabla = new VerticalLayout();

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Gestor.class).getNombre() + " " + session.getAttribute(Gestor.class).getApellidos();
        H1 Titulo = new H1("Bienvenido Gestor: " + nombre);

        ArrayList<Usuario> clientes = usuarioService.findUsuarioByRol(Role.CLIENTE.toString());

        ArrayList<Component> MenuPorCliente = new ArrayList<>();
        for(Usuario cliente : clientes) {
            if(Objects.equals(cliente.getGestor_id(), session.getAttribute(Gestor.class).getUUID())) {
                HorizontalLayout layoutComponenteTabla = new HorizontalLayout();
                Anchor NombreCliente = new Anchor("");
                NombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidos());
                Anchor CuentasYTarjetasButton = new Anchor("cuentas-tarjetas-gestor", "Cuentas y tarjetas");

                CuentasYTarjetasButton.getElement().addEventListener("click", event -> {
                    cuentasTarjetasGestorView.cliente = (Cliente) cliente;
                });
                Button CrearCuentaButton = new Button("Crear cuenta");
                CrearCuentaButton.getElement().getStyle().set("cursor", "pointer");

                Button CrearTarjetaButton = new Button("Crear tarjeta");
                CrearTarjetaButton.getElement().getStyle().set("cursor", "pointer");

                CrearCuentaButton.addClickListener(event -> {
                    Cuenta nuevaCuenta = new Cuenta();
                    cuentaService.save(nuevaCuenta, (Cliente) cliente);
                    new ConfirmDialog("Cuenta creada", "La cuenta ha sido creada correctamente", "Aceptar", null).open();
                });

                CrearTarjetaButton.addClickListener(event -> {
                    Dialog dialog = new Dialog();
                    dialog.setHeaderTitle("Crear tarjeta");

                    VerticalLayout dialogLayout = new VerticalLayout();
                    ComboBox<Cuenta> cuentas = new ComboBox<>("Cuentas");
                    cuentas.setItems(cuentaService.findCuentaByCliente(cliente));
                    cuentas.setItemLabelGenerator(Cuenta::getIBAN);
                    cuentas.setRequired(true);
                    cuentas.setRequiredIndicatorVisible(true);

                    Button crearTarjeta = new Button("Crear tarjeta");
                    crearTarjeta.getElement().getStyle().set("cursor", "pointer");
                    crearTarjeta.addClickListener(event1 -> {
                        Tarjeta nuevaTarjeta = new Tarjeta();
                        tarjetaService.save(nuevaTarjeta, cuentas.getValue());
                        new ConfirmDialog("Tarjeta creada", "La tarjeta ha sido creada correctamente", "Aceptar", null).open();
                        dialog.close();
                    });

                    dialogLayout.add(cuentas, crearTarjeta);
                    dialog.add(dialogLayout);
                    dialog.open();
                });

                Anchor ConsultaOnlineButton = new Anchor("consultas-online-gestor", "Consulta Online");
                Span counterOnline = new Span("1");
                Anchor ConsultaOfflineButton = new Anchor("consultas-offline-gestor", "Consulta Offline");
                Span counterOffline = new Span("3");
                ConsultaOfflineButton.getElement().addEventListener("click", event -> {
                    _cliente = (Cliente) cliente;
                });

                //ADD CLASS
                NombreCliente.addClassNames("NombreClienteAnchor", "Separacion");
                CuentasYTarjetasButton.addClassNames("Separacion", "AnchorButton");
                CrearCuentaButton.addClassNames("Separacion", "BotonGestor");
                CrearTarjetaButton.addClassNames("Separacion", "BotonGestor");
                ConsultaOnlineButton.addClassNames("Separacion", "BotonGestor");
                ConsultaOfflineButton.addClassNames("Separacion", "BotonGestor");
                layoutComponenteTabla.addClassName("layoutGestionCliente");
                counterOnline.addClassName("counter");
                counterOffline.addClassName("counter");

                //ALIGNMENT
                layoutComponenteTabla.expand(NombreCliente);
                layoutComponenteTabla.setJustifyContentMode(JustifyContentMode.BETWEEN);

                //ADD
                ConsultaOnlineButton.add(counterOnline);
                ConsultaOfflineButton.add(counterOffline);
                layoutComponenteTabla.add(NombreCliente, CuentasYTarjetasButton, CrearCuentaButton, CrearTarjetaButton, ConsultaOnlineButton, ConsultaOfflineButton);
                MenuPorCliente.add(layoutComponenteTabla);
            }
        }

        //ADD CLASS
        Titulo.addClassName("Titulo");
        layoutVerGestorPrincipal.addClassName("layoutVerGestor");

        //ADD
        for(Component menu : MenuPorCliente) {
            layoutVerGestorTabla.add(menu);
        }
        layoutVerGestorPrincipal.add(Titulo, layoutVerGestorTabla);

        return  layoutVerGestorPrincipal;
    }
}
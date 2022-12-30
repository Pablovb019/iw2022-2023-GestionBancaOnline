package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.rol.Role;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.cuentasTarjetas.crearCuenta;
import es.uca.iw.biwan.views.cuentasTarjetas.crearTarjeta;
import es.uca.iw.biwan.views.cuentasTarjetasGestor.cuentasTarjetasGestorView;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.util.ArrayList;

@CssImport("./themes/biwan/paginaPrincipalGestor.css")
@PageTitle("Página Principal Gestor")
@Route("pagina-principal-gestor")
public class GestorView extends VerticalLayout {

    private UsuarioService usuarioService;

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
            HorizontalLayout layoutComponenteTabla = new HorizontalLayout();
            Anchor NombreCliente = new Anchor("");
            NombreCliente.setText(cliente.getNombre() + " " + cliente.getApellidos());
            Anchor CuentasYTarjetasButton = new Anchor("cuentas-tarjetas-gestor", "Cuentas y tarjetas");
            CuentasYTarjetasButton.getElement().addEventListener("click", event -> {
                cuentasTarjetasGestorView.setUsuarioSeleccionado(cliente);
            });
            Anchor CrearCuentaButton = new Anchor("crear-cuenta-gestor", "Crear Cuenta");
            CrearCuentaButton.getElement().addEventListener("click", event -> {
                crearCuenta.setUsuarioSeleccionado(cliente);
            });
            Anchor CrearTarjetaButton = new Anchor("crear-tarjeta-gestor", "Crear Tarjeta");
            CrearTarjetaButton.getElement().addEventListener("click", event -> {
                crearTarjeta.setUsuarioSeleccionado(cliente);
            });
            Anchor ConsultaOnlineButton = new Anchor("consultas-online-gestor", "Consulta Online");
            Anchor ConsultaOfflineButton = new Anchor("consultas-offline-gestor", "Consulta Offline");
            Span counterOnline = new Span("1");
            Span counterOffline = new Span("3");

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
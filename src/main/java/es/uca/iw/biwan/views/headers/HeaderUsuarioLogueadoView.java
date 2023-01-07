package es.uca.iw.biwan.views.headers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.*;

@CssImport("./themes/biwan/headerUsuarioLogueado.css")
public class HeaderUsuarioLogueadoView {

    public static HorizontalLayout Header() {
        //NEW
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Biwan = new Anchor("", new Image("images/logo.png", "Biwan"));
        Anchor MasInfo = new Anchor("mas-informacion", "Más información");
        String nombre;
        String rol;
        Anchor AjustesUsuario;

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null) {
            nombre = session.getAttribute(Cliente.class).getNombre();
            rol = session.getAttribute(Cliente.class).getRol();
            AjustesUsuario = new Anchor("ajustes-cliente", "Ajustes");

        } else if (session.getAttribute(Gestor.class) != null) {
            nombre = session.getAttribute(Gestor.class).getNombre();
            rol = session.getAttribute(Gestor.class).getRol();
            AjustesUsuario = new Anchor("ajustes-gestor", "Ajustes");

        } else if (session.getAttribute(EncargadoComunicaciones.class) != null) {
            nombre = session.getAttribute(EncargadoComunicaciones.class).getNombre();
            rol = session.getAttribute(EncargadoComunicaciones.class).getRol();
            AjustesUsuario = new Anchor("ajustes-encargado", "Ajustes");

        } else if (session.getAttribute(Administrador.class) != null) {
            nombre = session.getAttribute(Administrador.class).getNombre();
            rol = session.getAttribute(Administrador.class).getRol();
            AjustesUsuario = new Anchor("ajustes-admin", "Ajustes");
        } else throw new IllegalStateException("No hay usuario logueado");

        // Crear menú de usuario
        AjustesUsuario.addClassName("AnchorMenuItem");
        Anchor CerrarSesion = new Anchor("", "Cerrar sesión");
        CerrarSesion.addClassName("AnchorMenuItem");

        MenuBar MenuPrincipal = new MenuBar();
        MenuPrincipal.getStyle().set("padding-top", "5px");
        Icon iconMenu = new Icon(VaadinIcon.MENU);
        Icon PaginaPrincipalIcon = new Icon(VaadinIcon.HOME);

        //MENU
        MenuItem itemPrincipal = MenuPrincipal.addItem("Menú");
        itemPrincipal.getElement().appendChild(iconMenu.getElement());
        itemPrincipal.getElement().getStyle().set("color", "black");
        itemPrincipal.getElement().getStyle().set("cursor",  "pointer");

        itemPrincipal.addComponentAtIndex(1, iconMenu);
        SubMenu MenuSecundario = itemPrincipal.getSubMenu();

        //MENU AJUSTES
        MenuBar MenuAjustes = new MenuBar();
        MenuAjustes.getStyle().set("padding-top", "5px");
        Icon iconAjustesUsuario = new Icon(VaadinIcon.USER_CHECK);
        MenuItem itemAjustes = MenuAjustes.addItem(nombre);
        itemAjustes.getElement().getStyle().set("color", "black");
        itemAjustes.getElement().getStyle().set("cursor",  "pointer");

        itemAjustes.addComponentAtIndex(1, iconAjustesUsuario);
        SubMenu SubMenuAjustes = itemAjustes.getSubMenu();
        SubMenuAjustes.addItem(AjustesUsuario);
        SubMenuAjustes.addItem(CerrarSesion);

        String pagRol = "";
        switch (rol) {
            case "ADMINISTRADOR":
                pagRol = "pagina-principal-admin";

                Anchor AñadirGestor = new Anchor("crear-gestor", "Añadir Gestor");
                AñadirGestor.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(AñadirGestor);

                Anchor AñadirEncargadoComunicacion = new Anchor("crear-encargado-comunicacion", "Añadir Encargado de Comunicación");
                AñadirEncargadoComunicacion.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(AñadirEncargadoComunicacion);
                break;
            case "CLIENTE": {
                pagRol = "pagina-principal-cliente";

                Anchor CuentasTarjetasMenuItem = new Anchor("cuentas-tarjetas-cliente", "Cuentas y Tarjetas");
                CuentasTarjetasMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(CuentasTarjetasMenuItem);

                Anchor MovimientosRealizadosMenuItem = new Anchor("movimientos", "Movimientos realizados");
                MovimientosRealizadosMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(MovimientosRealizadosMenuItem);

                Anchor RecibosDomiciliadosMenuItem = new Anchor("pagos-tarjeta", "Pagos con Tarjeta");
                RecibosDomiciliadosMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(RecibosDomiciliadosMenuItem);

                Anchor TransferenciasTraspasosMenuItem = new Anchor("transferencias-traspasos", "Transferencias y Traspasos");
                TransferenciasTraspasosMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(TransferenciasTraspasosMenuItem);

                Anchor ConsultasOnlineMenuItem = new Anchor("consultas-online-cliente", "Consultas Online");
                ConsultasOnlineMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(ConsultasOnlineMenuItem);

                Anchor ConsultasOfflineMenuItem = new Anchor("consultas-offline-cliente", "Consultas Offline");
                ConsultasOfflineMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(ConsultasOfflineMenuItem);
                break;
            }
            case "GESTOR": {
                pagRol = "pagina-principal-gestor";

                Anchor CuentasTarjetasMenuItem = new Anchor("cuentas-tarjetas-gestor", "Cuentas y Tarjetas");
                CuentasTarjetasMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(CuentasTarjetasMenuItem);

                Anchor ConsultasOnlineMenuItem = new Anchor("consultas-online-gestor", "Consultas Online");
                ConsultasOnlineMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(ConsultasOnlineMenuItem);

                Anchor ConsultasOfflineMenuItem = new Anchor("consultas-offline-gestor", "Consultas Offline");
                ConsultasOfflineMenuItem.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(ConsultasOfflineMenuItem);
                break;
            }
            case "ENCARGADO_COMUNICACIONES":
                pagRol = "pagina-principal-encargado";

                Anchor AñadirNoticia = new Anchor("add-noticia-encargado", "Añadir Noticia");
                AñadirNoticia.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(AñadirNoticia);

                Anchor AñadirOferta = new Anchor("add-oferta-encargado", "Añadir Oferta");
                AñadirOferta.addClassName("AnchorMenuItem");
                MenuSecundario.addItem(AñadirOferta);
                break;
        }

        Anchor PaginaPrincipalAnchor = new Anchor(pagRol, "Página principal");

        header.addClassName("header");
        PaginaPrincipalAnchor.addClassName("AnchorMainPageLogged");
        MasInfo.addClassName("AnchorMoreInfoLogged");
        headerRight.addClassName("HeaderSpacing");
        headerLeft.addClassName("HeaderSpacing");
        iconAjustesUsuario.addClassName("iconAjustesUsuarioLogged");
        PaginaPrincipalIcon.addClassName("PaginaPrincipalIconLogged");
        MenuPrincipal.addClassName("MenuPrincipalLogged");

        //ADD HEADERS
        header.add(headerLeft, headerMiddle, headerRight);
        headerLeft.add(Biwan, PaginaPrincipalAnchor, MasInfo);
        headerRight.add(MenuAjustes, MenuPrincipal);

        //ADJUSTMENTS
        PaginaPrincipalAnchor.addComponentAsFirst(PaginaPrincipalIcon);
        iconMenu.getElement().setAttribute("part", "iconMenu");
        //SearchField.setPrefixComponent(iconBusqueda);  //Cambiar icono en un field

        //ALIGNMENT
        header.setWidth("100%");
        header.setVerticalComponentAlignment(FlexComponent.Alignment.END, PaginaPrincipalAnchor, MasInfo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Biwan);
        headerLeft.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.expand(headerMiddle);

        CerrarSesion.getElement().addEventListener("click", e -> {
            if (session.getAttribute(Cliente.class) != null) { session.setAttribute(Cliente.class, null); }
            else if (session.getAttribute(Gestor.class) != null) { session.setAttribute(Gestor.class, null); }
            else if (session.getAttribute(EncargadoComunicaciones.class) != null) { session.setAttribute(EncargadoComunicaciones.class, null); }
            else if (session.getAttribute(Administrador.class) != null) { session.setAttribute(Administrador.class, null); }
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().getHistory().pushState(null, "");
        });
        return header;
    }
}


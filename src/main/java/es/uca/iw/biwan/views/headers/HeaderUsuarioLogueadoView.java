package es.uca.iw.biwan.views.headers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.UUID;

@CssImport("./themes/biwan/headerUsuarioLogueado.css")
public class HeaderUsuarioLogueadoView {
    private static UsuarioService usuarioService;

    public static HorizontalLayout Header() {
        //NEW
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Biwan = new Anchor("", new Image("images/logo.png", "Biwan"));
        Anchor MasInfo = new Anchor("", "Más información");

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre();
        String rol = session.getAttribute(Usuario.class).getRole().toString();

        // Crear menú de usuario
        Anchor AjustesUsuario = new Anchor("ajustes-cliente", "Ajustes");
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
        if(rol.equals("ADMINISTRADOR")) {

        } else if (rol.equals("CLIENTE")) {
            pagRol = "pagina-principal-cliente";

            Anchor CuentasTarjetasMenuItem = new Anchor("cuentas-tarjetas-cliente", "Cuentas y Tarjetas");
            CuentasTarjetasMenuItem.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(CuentasTarjetasMenuItem);

            Anchor MovimientosRealizadosMenuItem = new Anchor("movimientos", "Movimientos realizados");
            MovimientosRealizadosMenuItem.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(MovimientosRealizadosMenuItem);

            Anchor RecibosDomiciliadosMenuItem = new Anchor("recibos-domiciliados", "Recibos domiciliados");
            RecibosDomiciliadosMenuItem.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(RecibosDomiciliadosMenuItem);

            Anchor TransferenciasTraspasosMenuItem = new Anchor("transferencias-traspasos", "Transferencias y Traspasos");
            TransferenciasTraspasosMenuItem.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(TransferenciasTraspasosMenuItem);
        } else if (rol.equals("GESTOR")) {
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
        } else if (rol.equals("ENCARGADO_COMUNICACIONES")) {
            pagRol = "pagina-principal-encargado";

            Anchor AñadirNoticia = new Anchor("añadir-noticia", "Añadir Noticia");
            AñadirNoticia.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(AñadirNoticia);

            Anchor AñadirOferta = new Anchor("añadir-oferta", "Añadir Oferta");
            AñadirOferta.addClassName("AnchorMenuItem");
            MenuSecundario.addItem(AñadirOferta);
        }

        Anchor PaginaPrincipalAnchor = new Anchor(pagRol, "Página principal");

        header.addClassName("header_footer");
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
            session.setAttribute(Usuario.class, null);
            UI.getCurrent().navigate("");
        });
        return header;
    }
}


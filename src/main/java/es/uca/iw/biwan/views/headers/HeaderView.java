package es.uca.iw.biwan.views.headers;

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
import com.vaadin.flow.server.auth.AnonymousAllowed;

@CssImport("./themes/biwan/header.css")
@AnonymousAllowed
public class HeaderView {
    public static HorizontalLayout Header() {

        //NEW
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Biwan = new Anchor("", new Image("images/logo.png", "Biwan"));
        Anchor PaginaPrincipalAnchor = new Anchor("login", "Página principal");
        Anchor MasInfo = new Anchor("mas-informacion", "Más información");
        Anchor HazteCliente = new Anchor("registration", "+ Hazte cliente");
        Anchor Acceso = new Anchor("login", "Acceso");
        Anchor CuentasTarjetasMenuItem = new Anchor("login", "Cuentas y tarjetas");
        Anchor MovimientosRealizadosMenuItem = new Anchor("login", "Movimientos realizados");
        Anchor RecibosDomiciliadosMenuItem = new Anchor("login", "Recibos domiciliados");
        Anchor TransferenciasTraspasosMenuItem = new Anchor("login", "Transferencias y Traspasos");
        Anchor ConsultasOnlineMenuItem = new Anchor("login", "Consultas Online");
        Anchor ConsultasOfflineMenuItem = new Anchor("login", "Consultas Offline");
        MenuBar MenuPrincipal = new MenuBar();
        Icon iconMenu = new Icon(VaadinIcon.MENU);
        Icon iconHazteCliente = new Icon(VaadinIcon.USER);
        Icon PaginaPrincipalIcon = new Icon(VaadinIcon.HOME);

        //ADD CLASS NAME
        Biwan.addClassNames("banner");
        header.addClassName("header");
        PaginaPrincipalAnchor.addClassName("AnchorMainPage");
        MasInfo.addClassName("AnchorMoreInfo");
        HazteCliente.addClassName("AnchorHazteCliente");
        Acceso.addClassName("AnchorAcceso");
        headerRight.addClassName("HeaderSpacing");
        headerLeft.addClassName("HeaderSpacing");
        iconHazteCliente.addClassName("iconHazteCliente");
        PaginaPrincipalIcon.addClassName("PaginaPrincipalIcon");
        CuentasTarjetasMenuItem.addClassName("AnchorMenuItem");
        MovimientosRealizadosMenuItem.addClassName("AnchorMenuItem");
        RecibosDomiciliadosMenuItem.addClassName("AnchorMenuItem");
        TransferenciasTraspasosMenuItem.addClassName("AnchorMenuItem");
        ConsultasOnlineMenuItem.addClassName("AnchorMenuItem");
        ConsultasOfflineMenuItem.addClassName("AnchorMenuItem");

        //MENU
        MenuItem itemPrincipal = MenuPrincipal.addItem("Menú");
        itemPrincipal.getElement().getStyle().set("color", "black");
        itemPrincipal.getElement().getStyle().set("cursor", "pointer");

        itemPrincipal.addComponentAtIndex(1, iconMenu);
        SubMenu MenuSecundario = itemPrincipal.getSubMenu();
        MenuSecundario.addItem(CuentasTarjetasMenuItem);
        MenuSecundario.addItem(MovimientosRealizadosMenuItem);
        MenuSecundario.addItem(RecibosDomiciliadosMenuItem);
        MenuSecundario.addItem(TransferenciasTraspasosMenuItem);
        MenuSecundario.addItem(ConsultasOnlineMenuItem);
        MenuSecundario.addItem(ConsultasOfflineMenuItem);

        //ADD HEADERS
        header.add(headerLeft, headerMiddle, headerRight);
        headerLeft.add(Biwan, PaginaPrincipalAnchor, MasInfo);
        headerRight.add(HazteCliente, Acceso, MenuPrincipal);

        //ADJUSTMENTS
        PaginaPrincipalAnchor.addComponentAsFirst(PaginaPrincipalIcon);
        HazteCliente.addComponentAsFirst(iconHazteCliente);
        iconMenu.getElement().setAttribute("part", "iconMenu");
        MenuPrincipal.getStyle().set("padding-top", "5px");
        //SearchField.setPrefixComponent(iconBusqueda);  //Cambiar icono en un field

        //ALIGNMENT
        header.setWidth("100%");
        header.setVerticalComponentAlignment(FlexComponent.Alignment.END, PaginaPrincipalAnchor, MasInfo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Biwan, HazteCliente, Acceso);
        headerLeft.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.expand(headerMiddle);

        return header;
    }
}

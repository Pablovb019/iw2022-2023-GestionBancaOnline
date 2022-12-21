package es.uca.iw.biwan.views.headers;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
        Anchor Titulo = new Anchor("", "BIWAN");
        Anchor PaginaPrincipalAnchor = new Anchor("pagina-principal-cliente", "Página principal");
        Anchor MasInfo = new Anchor("", "Más información");
        Anchor HazteCliente = new Anchor("registration", "+ Hazte cliente");
        Anchor Acceso = new Anchor("login", "Acceso");
        Anchor CuentasTarjetasMenuItem = new Anchor("", "Cuentas y tarjetas");
        Anchor MovimientosRealizadosMenuItem = new Anchor("movimientos", "Movimientos realizados");
        Anchor RecibosDomiciliadosMenuItem = new Anchor("recibos-domiciliados", "Recibos domiciliados");
        Anchor TransferenciasTraspasosMenuItem = new Anchor("transferencias-traspasos", "Transferencias y Traspasos");
        Anchor ConsultasOnlineMenuItem = new Anchor("consultas-online-gestor", "Consultas Online");
        Anchor ConsultasOfflineMenuItem = new Anchor("consultas-offline-gestor", "Consultas Offline");
        MenuBar MenuPrincipal = new MenuBar();
        Icon iconMenu = new Icon(VaadinIcon.MENU);
        Icon iconHazteCliente = new Icon(VaadinIcon.USER);
        Icon PaginaPrincipalIcon = new Icon(VaadinIcon.HOME);
        //CuentasTarjetasButton.addClickListener(e -> CuentasTarjetasButton.getUI().ifPresent(ui -> ui.navigate("")));

        //ADD CLASS NAME
        Titulo.addClassNames("tittleBiwan");
        header.addClassName("header_footer");
        PaginaPrincipalAnchor.addClassName("Anchor");
        MasInfo.addClassName("Anchor");
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
        headerLeft.add(Titulo, PaginaPrincipalAnchor, MasInfo);
        headerRight.add(HazteCliente, Acceso, MenuPrincipal);

        //ADJUSTMENTS
        PaginaPrincipalAnchor.addComponentAsFirst(PaginaPrincipalIcon);
        HazteCliente.addComponentAsFirst(iconHazteCliente);
        iconMenu.getElement().setAttribute("part", "iconMenu");
        //SearchField.setPrefixComponent(iconBusqueda);  //Cambiar icono en un field

        //ALIGNMENT
        header.setWidth("100%");
        header.setVerticalComponentAlignment(FlexComponent.Alignment.END, PaginaPrincipalAnchor, MasInfo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Titulo, HazteCliente, Acceso);
        headerLeft.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.expand(headerMiddle);

        return header;
    }
}

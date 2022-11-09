package es.uca.iw.biwan.views.headers;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class HeaderView {
    public static HorizontalLayout Header() {

        //NEW
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Titulo = new Anchor("", "BIWAN");
        Anchor CuentasTarjetasAnchor = new Anchor("", "Cuentas y tarjetas");
        Anchor MasInfo = new Anchor("", "Más información");
        Anchor HazteCliente = new Anchor("", "+ Hazte cliente");
        Anchor Acceso = new Anchor("login", "Acceso");
        MenuBar MenuPrincipal = new MenuBar();
        Icon iconMenu = new Icon(VaadinIcon.MENU);
        TextField SearchField = new TextField();
        Icon iconBusqueda = new Icon(VaadinIcon.SEARCH);
        Icon iconHazteCliente = new Icon(VaadinIcon.USER);
        Button SearchButton = new Button();
        //CuentasTarjetasButton.addClickListener(e -> CuentasTarjetasButton.getUI().ifPresent(ui -> ui.navigate("")));

        //ADD CLASS NAME
        Titulo.addClassNames("tittleBiwan");
        header.addClassName("header_footer");
        CuentasTarjetasAnchor.addClassName("Anchor");
        MasInfo.addClassName("Anchor");
        SearchButton.addClassName("vaadin-button");
        HazteCliente.addClassName("AnchorHazteCliente");
        Acceso.addClassName("AnchorAcceso");
        headerRight.addClassName("HeaderSpacing");
        headerLeft.addClassName("HeaderSpacing");

        //MENU
        MenuItem itemPrincipal = MenuPrincipal.addItem("Menú");
        itemPrincipal.addComponentAtIndex(1, iconMenu);
        SubMenu MenuSecundario = itemPrincipal.getSubMenu();
        MenuSecundario.addItem("Cuentas y Tarjetas");
        MenuSecundario.addItem("Movimientos realizados");
        MenuSecundario.addItem("Recibos domiciliados");
        MenuSecundario.addItem("Transferencias");
        MenuSecundario.addItem("Traspasos");
        MenuSecundario.addItem("Gastos");
        MenuSecundario.addItem("Ingresos");
        MenuSecundario.addItem("Consulta Online");
        MenuSecundario.addItem("Consulta Offline");

        //HEADERS
        header.add(headerLeft, headerMiddle, headerRight);
        headerLeft.add(Titulo, CuentasTarjetasAnchor, MasInfo);
        headerMiddle.add(SearchField, SearchButton);
        headerRight.add(HazteCliente, Acceso, MenuPrincipal);

        //CSS
        SearchField.getElement().setAttribute("aria-label", "searchField");
        SearchField.setPlaceholder("Search");
        SearchField.setClearButtonVisible(true);
        SearchButton.getElement().setAttribute("aria-label", "searchButton");
        SearchButton.setIcon(iconBusqueda);
        //SearchField.setPrefixComponent(iconBusqueda);  //Cambiar icono en un field
        iconBusqueda.setColor("white");
        iconHazteCliente.setColor("black");
        iconHazteCliente.getElement().setAttribute("aria-label", "iconHazteCliente");
        HazteCliente.getElement().setAttribute("aria-label", "HazteCliente");
        HazteCliente.addComponentAsFirst(iconHazteCliente);
        iconMenu.setColor("black");
        iconMenu.setSize("40px");
        itemPrincipal.getElement().getStyle().set("color", "black");
        itemPrincipal.getElement().getStyle().set("background-color", "#27AE60");
        header.setWidth("100%");

        //ALIGNMENT
        header.setVerticalComponentAlignment(FlexComponent.Alignment.END, CuentasTarjetasAnchor, MasInfo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Titulo, SearchField, SearchButton, HazteCliente, Acceso);
        headerLeft.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.expand(headerMiddle);

        return header;
    }
}

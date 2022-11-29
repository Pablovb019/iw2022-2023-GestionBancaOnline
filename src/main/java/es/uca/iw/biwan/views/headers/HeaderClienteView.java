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
import es.uca.iw.biwan.domain.usuarios.Usuario;

@CssImport("/themes/biwan/headerCliente.css")
public class HeaderClienteView {
    public static HorizontalLayout Header() {

        //NEW
        Usuario usuario;
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Titulo = new Anchor("", "BIWAN");
        Anchor CuentasTarjetasAnchor = new Anchor("", "Cuentas y tarjetas");
        Anchor MasInfo = new Anchor("", "Más información");
        Anchor AjustesUsuario = new Anchor("ajustes-cliente", "Jose Antonio Alonso de la Huerta");
        MenuBar MenuPrincipal = new MenuBar();
        TextField SearchField = new TextField();
        Button SearchButton = new Button();
        Icon iconMenu = new Icon(VaadinIcon.MENU);
        Icon iconBusqueda = new Icon(VaadinIcon.SEARCH);
        Icon iconAjustesUsuario = new Icon(VaadinIcon.USER_CHECK);
        //CuentasTarjetasButton.addClickListener(e -> CuentasTarjetasButton.getUI().ifPresent(ui -> ui.navigate("")));

        //ADD CLASS NAME
        Titulo.addClassNames("tittleBiwan");
        header.addClassName("header_footer");
        CuentasTarjetasAnchor.addClassName("Anchor");
        MasInfo.addClassName("Anchor");
        SearchButton.addClassName("vaadin-button");
        AjustesUsuario.addClassName("AnchorAjustesUsuario");
        headerRight.addClassName("HeaderSpacing");
        headerLeft.addClassName("HeaderSpacing");
        SearchField.addClassName("searchField");
        SearchButton.addClassName("searchButton");
        iconBusqueda.addClassName("iconBusqueda");
        iconAjustesUsuario.addClassName("iconAjustesUsuario");

        //MENU
        MenuItem itemPrincipal = MenuPrincipal.addItem("Menú");
        itemPrincipal.addComponentAtIndex(1, iconMenu);
        SubMenu MenuSecundario = itemPrincipal.getSubMenu();
        MenuSecundario.addItem("Cuentas y Tarjetas");
        MenuSecundario.addItem("Movimientos realizados");
        MenuSecundario.addItem("Recibos domiciliados");
        MenuSecundario.addItem("Transferencias");
        MenuSecundario.addItem("Traspasos");
        MenuSecundario.addItem("Consulta Online");
        MenuSecundario.addItem("Consulta Offline");

        //ADD HEADERS
        header.add(headerLeft, headerMiddle, headerRight);
        headerLeft.add(Titulo, CuentasTarjetasAnchor, MasInfo);
        headerMiddle.add(SearchField, SearchButton);
        headerRight.add(AjustesUsuario, iconAjustesUsuario, MenuPrincipal);

        //ADJUSTMENTS
        SearchField.setPlaceholder("Search");
        SearchField.setClearButtonVisible(true);
        SearchButton.setIcon(iconBusqueda);
        AjustesUsuario.addComponentAtIndex(1, iconAjustesUsuario);
        iconMenu.getElement().setAttribute("part", "iconMenu");
        //SearchField.setPrefixComponent(iconBusqueda);  //Cambiar icono en un field

        //ALIGNMENT
        header.setWidth("100%");
        header.setVerticalComponentAlignment(FlexComponent.Alignment.END, CuentasTarjetasAnchor, MasInfo);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, Titulo, SearchField, SearchButton, AjustesUsuario);
        headerLeft.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        header.expand(headerMiddle);

        return header;
    }
}


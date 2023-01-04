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

        //ADD HEADERS
        header.add(headerLeft, headerMiddle, headerRight);
        headerLeft.add(Biwan, PaginaPrincipalAnchor, MasInfo);
        headerRight.add(HazteCliente, Acceso);

        //ADJUSTMENTS
        PaginaPrincipalAnchor.addComponentAsFirst(PaginaPrincipalIcon);
        HazteCliente.addComponentAsFirst(iconHazteCliente);

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

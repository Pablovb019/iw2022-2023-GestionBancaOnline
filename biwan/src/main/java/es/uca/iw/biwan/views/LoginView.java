package es.uca.iw.biwan.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.menubar.MenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@CssImport("/themes/biwan/login.css")
@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){

        super.setClassName("main");

        login.setI18n(Login());
        VerticalLayout layoutLogin = new VerticalLayout();
        HorizontalLayout layoutHorLogin = new HorizontalLayout();

        layoutLogin.add(Header());
        layoutHorLogin.add(login);
        layoutLogin.add(layoutHorLogin);
        layoutLogin.add(Footer());
        layoutLogin.expand(layoutHorLogin);

        layoutLogin.setSizeFull();
        layoutHorLogin.setAlignItems(Alignment.CENTER);
        layoutLogin.setAlignItems(Alignment.CENTER);

        add(layoutLogin);
    }


    private HorizontalLayout Header() {

        //NEW
        HorizontalLayout header = new HorizontalLayout();
        HorizontalLayout headerLeft = new HorizontalLayout();
        HorizontalLayout headerMiddle = new HorizontalLayout();
        HorizontalLayout headerRight = new HorizontalLayout();
        Anchor Titulo = new Anchor("", "BIWAN");
        Anchor CuentasTarjetasAnchor = new Anchor("", "Cuentas y tarjetas");
        Anchor MasInfo = new Anchor("", "Más información");
        Anchor HazteCliente = new Anchor("", "+ Hazte cliente");
        Anchor Acceso = new Anchor("", "Acceso");
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
        header.setVerticalComponentAlignment(Alignment.END, CuentasTarjetasAnchor, MasInfo);
        header.setVerticalComponentAlignment(Alignment.CENTER, Titulo, SearchField, SearchButton, HazteCliente, Acceso);
        headerLeft.setJustifyContentMode(JustifyContentMode.START);
        headerMiddle.setJustifyContentMode(JustifyContentMode.CENTER);
        headerRight.setJustifyContentMode(JustifyContentMode.END);
        header.expand(headerMiddle);

        return header;
    }

    private HorizontalLayout Footer() {

        //NEW
        HorizontalLayout footer = new HorizontalLayout();
        HorizontalLayout footerTitulo = new HorizontalLayout();
        HorizontalLayout footerEtc = new HorizontalLayout();
        VerticalLayout footerEtc1 = new VerticalLayout();
        VerticalLayout footerEtc2 = new VerticalLayout();
        VerticalLayout footerEtc3 = new VerticalLayout();
        Anchor Titulo = new Anchor("", "BIWAN");
        Anchor AvisoLegal = new Anchor("", "Aviso legal");
        Anchor Tarifas = new Anchor("", "Tarifas");
        Anchor Cookies = new Anchor("", "Cookies");
        Anchor InformesLegales = new Anchor("", "Informes legales");
        Anchor TablonAnuncios = new Anchor("", "Tablón de anuncios");
        Anchor NegocioResponsable = new Anchor("", "Negocio responsable");

        //ADD CLASS NAME
        footer.addClassName("header_footer");
        Titulo.addClassName("tittleBiwan");
        AvisoLegal.addClassName("AnchorFooter1");
        Tarifas.addClassName("AnchorFooter1");
        Cookies.addClassName("AnchorFooter2");
        InformesLegales.addClassName("AnchorFooter2");
        TablonAnuncios.addClassName("AnchorFooter3");
        NegocioResponsable.addClassName("AnchorFooter3");

        //FOOTERS
        footer.add(footerTitulo, footerEtc);
        footerEtc.add(footerEtc1, footerEtc2, footerEtc3);
        footerTitulo.add(Titulo);
        footerEtc1.add(AvisoLegal, Tarifas);
        footerEtc2.add(Cookies, InformesLegales);
        footerEtc3.add(TablonAnuncios, NegocioResponsable);

        //CSS
        footer.setWidth("100%");

        //ALIGNMENT
        footer.setVerticalComponentAlignment(Alignment.CENTER, footerTitulo, footerEtc);
        footerEtc1.setJustifyContentMode(JustifyContentMode.CENTER);
        footerEtc2.setJustifyContentMode(JustifyContentMode.CENTER);
        footerEtc3.setJustifyContentMode(JustifyContentMode.CENTER);
        footerTitulo.setJustifyContentMode(JustifyContentMode.START);
        footerEtc.setJustifyContentMode(JustifyContentMode.START);

        return footer;

    }

    private LoginI18n Login(){

        LoginI18n loginDefault = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = loginDefault.getForm();

        i18nForm.setTitle("Iniciar Sesión");
        i18nForm.setUsername("Usuario (DNI o correo electrónico)");
        i18nForm.setPassword("Contraseña");
        i18nForm.setSubmit("Iniciar sesión");
        i18nForm.setForgotPassword("¿Has olvidado tu contraseña?");
        loginDefault.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = loginDefault.getErrorMessage();
        i18nErrorMessage.setTitle("Nombre de usuario o contraseña incorrectos");
        i18nErrorMessage.setMessage("Comprueba que tu nombre de usuario y contraseña son correctos y vuelve a intentarlo.");
        loginDefault.setErrorMessage(i18nErrorMessage);

        setSizeFull();

        return loginDefault;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}
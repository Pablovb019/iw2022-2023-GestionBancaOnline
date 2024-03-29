package es.uca.iw.biwan.views.footers;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@CssImport("./themes/biwan/footer.css")
@AnonymousAllowed
public class FooterView {
    public static HorizontalLayout Footer() {

        //NEW
        HorizontalLayout footer = new HorizontalLayout();
        HorizontalLayout footerTitulo = new HorizontalLayout();
        HorizontalLayout footerEtc = new HorizontalLayout();
        VerticalLayout footerEtc1 = new VerticalLayout();
        VerticalLayout footerEtc2 = new VerticalLayout();
        VerticalLayout footerEtc3 = new VerticalLayout();
        Anchor Biwan = new Anchor("", new Image("images/logo.png", "Biwan"));
        Anchor AvisoLegal = new Anchor("aviso-legal", "Aviso legal");
        Anchor Tarifas = new Anchor("tarifas", "Tarifas");
        Anchor Cookies = new Anchor("cookies", "Cookies");
        Anchor InformesLegales = new Anchor("informes-legales", "Informes legales");
        Anchor TablonAnuncios = new Anchor("trabaja-con-nosotros", "Trabaja con nosotros");
        Anchor NegocioResponsable = new Anchor("negocio-responsable", "Negocio responsable");

        //ADD CLASS NAME
        Biwan.addClassNames("banner");
        footer.addClassName("footer");
        AvisoLegal.addClassName("AnchorAvisoLegal");
        Tarifas.addClassName("AnchorTarifas");
        Cookies.addClassName("AnchorCookies");
        InformesLegales.addClassName("AnchorInformesLegales");
        TablonAnuncios.addClassName("AnchorTablonAnuncios");
        NegocioResponsable.addClassName("AnchorNegocioResponsable");

        //ADD FOOTERS
        footer.add(footerTitulo, footerEtc);
        footerTitulo.add(Biwan);
        footerEtc1.add(AvisoLegal, Tarifas);
        footerEtc2.add(Cookies, InformesLegales);
        footerEtc3.add(TablonAnuncios, NegocioResponsable);
        footerEtc.add(footerEtc1, footerEtc2, footerEtc3);

        //ALIGNMENT
        footer.setWidth("100%");
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        footer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, footerTitulo, footerEtc);
        footerEtc1.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerEtc2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerEtc3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerTitulo.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        footerEtc.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        footerEtc.setWidth("40%");

        // all

        return footer;
    }
}

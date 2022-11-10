package es.uca.iw.biwan.views.footers;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FooterView {
    public static HorizontalLayout Footer() {

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

        //ADD FOOTERS
        footer.add(footerTitulo, footerEtc);
        footerEtc.add(footerEtc1, footerEtc2, footerEtc3);
        footerTitulo.add(Titulo);
        footerEtc1.add(AvisoLegal, Tarifas);
        footerEtc2.add(Cookies, InformesLegales);
        footerEtc3.add(TablonAnuncios, NegocioResponsable);

        //ALIGNMENT
        footer.setWidth("100%");
        footer.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, footerTitulo, footerEtc);
        footerEtc1.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerEtc2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerEtc3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerTitulo.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        footerEtc.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        return footer;
    }
}
package es.uca.iw.biwan.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

import java.time.format.TextStyle;

@PageTitle("Main")
@Route(value = "")
@CssImport("/themes/biwan/main.css")
public class MainView extends VerticalLayout {
    public MainView() {
        Image img1 = new Image("images/3.png", "Imagen");
        img1.setWidth("90%");
        img1.setHeight("500px");

        Image img2 = new Image("images/3.png", "Imagen");
        img2.setWidth("90%");
        img2.setHeight("500px");

        Image img3 = new Image("images/3.png", "Imagen");
        img3.setWidth("90%");
        img3.setHeight("500px");

        Image img4 = new Image("images/3.png", "Imagen");
        img4.setWidth("90%");
        img4.setHeight("500px");

        Anchor BotonHazteCliente1 = new Anchor("", "Hazte cliente");
        BotonHazteCliente1.addClassName("BotonHazteCliente1");
        Anchor BotonHazteCliente2 = new Anchor("", "Hazte cliente");
        BotonHazteCliente2.addClassName("BotonHazteCliente2");
        Anchor BotonHazteCliente3 = new Anchor("", "Hazte cliente");
        BotonHazteCliente3.addClassName("BotonHazteCliente3");

        Anchor BotonInfo1 = new Anchor("", "Más información");
        BotonInfo1.addClassName("BotonInfo1");
        Anchor BotonInfo2 = new Anchor("", "Más información");
        BotonInfo2.addClassName("BotonInfo2");
        Anchor BotonInfo3 = new Anchor("", "Más información");
        BotonInfo3.addClassName("BotonInfo3");

        Button TextoPrincipal = new Button("¡¡Bienvenido a BIWAN!!, tu gestor de banca online de confianza");
        TextoPrincipal.addClassName("TextoPrincipal");

        var vlAnuncio1 = new VerticalLayout(TextoPrincipal, img1);
        vlAnuncio1.setAlignItems(Alignment.CENTER);

        var vlAnuncio2 = new VerticalLayout(img2);
        vlAnuncio2.setAlignItems(Alignment.CENTER);
        var TextoAnuncio2 = new VerticalLayout(new H6("Texto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemplo"),
                new HorizontalLayout(BotonHazteCliente1, BotonInfo1));

        var vlAnuncio3 = new VerticalLayout(img3);
        vlAnuncio3.setAlignItems(Alignment.CENTER);
        var TextoAnuncio3 = new VerticalLayout(new H6("Texto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemplo"),
                new HorizontalLayout(BotonHazteCliente2, BotonInfo2));

        var vlAnuncio4 = new VerticalLayout(img4);
        vlAnuncio4.setAlignItems(Alignment.CENTER);
        var TextoAnuncio4 = new VerticalLayout(new H6("Texto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de " +
                "ploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemploTexto de ejemplo"),
                new HorizontalLayout(BotonHazteCliente3, BotonInfo3));

        var hlAnuncio1 = new HorizontalLayout(vlAnuncio1);
        hlAnuncio1.setWidthFull();

        var hlAnuncio2 = new HorizontalLayout(TextoAnuncio2, vlAnuncio2);
        hlAnuncio2.setWidthFull();

        var hlAnuncio3 = new HorizontalLayout(vlAnuncio3, TextoAnuncio3);
        hlAnuncio3.setWidthFull();

        var hlAnuncio4 = new HorizontalLayout(TextoAnuncio4, vlAnuncio4);
        hlAnuncio4.setWidthFull();

        add(HeaderView.Header());
        add(hlAnuncio1);
        add(hlAnuncio2);
        add(hlAnuncio3);
        add(hlAnuncio4);
        add(FooterView.Footer());
    }

}

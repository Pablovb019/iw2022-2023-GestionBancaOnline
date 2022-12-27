package es.uca.iw.biwan.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.aplication.service.AnuncioService;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.headers.HeaderView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;

@PageTitle("Main")
@Route("")
@CssImport("./themes/biwan/main.css")
@AnonymousAllowed
public class MainView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;
    public MainView() {
        // Comprobar si hay un usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Usuario.class) != null) {
            // Si hay un usuario logueado, mostrar la vista de usuario logueado
            add(HeaderUsuarioLogueadoView.Header());
        } else {
            // Si no hay un usuario logueado, mostrar la vista de usuario no logueado
            add(HeaderView.Header());
        }
        add(crearAnuncioPrincipal());
    }

    private Component crearAnuncioPrincipal() {
        Image img = new Image("images/banner.png", "Imagen");
        img.setWidth("90%");
        img.setMaxHeight("450px");

        var vlAnuncioPrincipal = new VerticalLayout(img);
        vlAnuncioPrincipal.setAlignItems(Alignment.CENTER);

        var hlAnuncioPrincipal = new HorizontalLayout(vlAnuncioPrincipal);
        hlAnuncioPrincipal.setWidthFull();
        hlAnuncioPrincipal.addClassName("hlAnuncioPrincipal");

        return hlAnuncioPrincipal;
    }

    private Component crearAnuncios() {
        //Layout de anuncios
        // Obtenemos las noticias y ofertas
        ArrayList<Noticia> noticias = anuncioService.findNoticiaByType(TipoAnuncio.NOTICIA.toString());
        ArrayList<Oferta> ofertas = anuncioService.findOfertaByType(TipoAnuncio.OFERTA.toString());

        // Creacion de noticias y ofertas
        // Noticias
        ArrayList<Component> listaNoticias = new ArrayList<>();
        for(Noticia noticia : noticias) {

            // Creacion de los elementos de la noticia
            H3 TituloNoticia = new H3(noticia.getTitulo());
            Paragraph TextoNoticia = new Paragraph(noticia.getCuerpo());
            Anchor BotonHazteCliente = new Anchor("registration", "Hazte cliente");
            Anchor BotonInfo = new Anchor("", "Más información");

            // CSS
            TituloNoticia.addClassName("tTextoAnuncio");
            TextoNoticia.addClassName("TxtAnuncio");
            BotonHazteCliente.addClassName("BotonesHazteCliente");
            BotonInfo.addClassName("BotonesInfo");

            var vlTextoAnuncio = new VerticalLayout(TituloNoticia, TextoNoticia,
                    new HorizontalLayout(BotonHazteCliente, BotonInfo));

            var hlAnuncio = new HorizontalLayout(vlTextoAnuncio);
            hlAnuncio.setWidthFull();
            hlAnuncio.addClassName("hlAnuncio");

            listaNoticias.add(hlAnuncio);
        }

        // Ofertas
        ArrayList<Component> listaOfertas = new ArrayList<>();
        for(Oferta oferta : ofertas) {
            if(oferta.getFechaFin().isAfter(LocalDate.now())) {
                // Creacion de los elementos de la oferta
                H3 TituloOferta = new H3(oferta.getTitulo());
                Paragraph TextoOferta = new Paragraph(oferta.getCuerpo());
                Anchor BotonHazteCliente = new Anchor("registration", "Hazte cliente");
                Anchor BotonInfo = new Anchor("", "Más información");

                // CSS
                TituloOferta.addClassName("tTextoAnuncio");
                TextoOferta.addClassName("TxtAnuncio");
                BotonHazteCliente.addClassName("BotonesHazteCliente");
                BotonInfo.addClassName("BotonesInfo");

                var vlTextoAnuncio = new VerticalLayout(TituloOferta, TextoOferta,
                        new HorizontalLayout(BotonHazteCliente, BotonInfo));

                var hlAnuncio = new HorizontalLayout(vlTextoAnuncio);
                hlAnuncio.setWidthFull();
                hlAnuncio.addClassName("hlAnuncio");

                listaOfertas.add(hlAnuncio);
            } else {
                anuncioService.delete(oferta);
            }
        }

        var vlAnuncios = new VerticalLayout();
        for(Component noticia : listaNoticias) {
            vlAnuncios.add(noticia);
        }
        for(Component oferta : listaOfertas) {
            vlAnuncios.add(oferta);
        }

        vlAnuncios.addClassName("vlAnunciosPagPrincipal");

        return vlAnuncios;
    }

    @PostConstruct
    public void init() {
        add(crearAnuncios());
        add(FooterView.Footer());
    }
}

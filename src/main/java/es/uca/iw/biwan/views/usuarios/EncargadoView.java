package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.AnuncioService;
import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.noticiasOfertas.EditarNoticiaView;
import es.uca.iw.biwan.views.noticiasOfertas.EditarOfertaView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("pagina-principal-encargado")
@CssImport("./themes/biwan/paginaPrincipalEncargado.css")
@PageTitle("Página Principal Encargado de Comunicaciones")

public class EncargadoView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;

    public EncargadoView() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("ENCARGADO_COMUNICACIONES")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un encargado de comunicaciones", "Volver", event -> {
                    UI.getCurrent().navigate("/");
                });
                error.open();
            } else {
                add(HeaderUsuarioLogueadoView.Header());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private Component crearPaginaPrincipal() {

        // Creacion de los apartados
        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre();
        H1 Titulo = new H1("Bienvenido Encargado de Comunicaciones: " + nombre);

        H2 TituloTablonNoticias = new H2("Noticias");
        H2 TituloTablonOfertas = new H2("Ofertas");
        Anchor AñadirNoticiaButton = new Anchor("add-noticia-encargado", "Añadir Noticia");
        Anchor AñadirOfertaButton = new Anchor("add-oferta-encargado", "Añadir Oferta");

        // CSS
        Titulo.addClassName("TituloWelcome");
        TituloTablonNoticias.addClassName("Textos");
        TituloTablonOfertas.addClassName("Textos");
        AñadirNoticiaButton.addClassName("AñadirNoticiaOfertaButtons");
        AñadirOfertaButton.addClassName("AñadirNoticiaOfertaButtons");

        // Obtenemos las noticias y ofertas
        ArrayList<Noticia> noticias = anuncioService.findNoticiaByType(TipoAnuncio.NOTICIA.toString());
        ArrayList<Oferta> ofertas = anuncioService.findOfertaByType(TipoAnuncio.OFERTA.toString());

        // Creacion de los tablones de noticias y ofertas
        // Noticias
        ArrayList<Component> listaNoticias = new ArrayList<>();
        for(Noticia noticia : noticias) {

            // Creacion de los elementos de la noticia
            H3 TituloNoticia = new H3(noticia.getTitulo());
            Paragraph TextoNoticia = new Paragraph(noticia.getCuerpo());
            Anchor NoticiaEditarButton = new Anchor("editar-noticia-encargado", "Editar");
            Anchor NoticiaEliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");

            // CSS
            TituloNoticia.addClassName("Textos");
            TextoNoticia.addClassName("Textos");
            NoticiaEditarButton.addClassName("EditarButtons");
            NoticiaEliminarButton.addClassName("EliminarButtons");

            var vlNoticia = new VerticalLayout(TituloNoticia, TextoNoticia);
            var hlNoticia = new HorizontalLayout(vlNoticia, NoticiaEditarButton, NoticiaEliminarButton);
            listaNoticias.add(hlNoticia);

            // Editar noticia
            NoticiaEditarButton.getElement().addEventListener("click", event -> {
                EditarNoticiaView.setTituloDescripcion(noticia);
            });

            // Eliminar noticia
            NoticiaEliminarButton.getElement().addEventListener("click", event -> {
                anuncioService.delete(noticia);
                ConfirmDialog confirmRequest = new ConfirmDialog("Eliminada Noticia", "Noticia eliminada correctamente", "Aceptar", event1 -> {
                    UI.getCurrent().getPage().reload();
                });
                confirmRequest.open();
            });
        }

        var vlTituloTablonNoticias = new VerticalLayout(TituloTablonNoticias);
        vlTituloTablonNoticias.setAlignItems(Alignment.CENTER);
        vlTituloTablonNoticias.getStyle().set("color", "black");
        var vlNoticias = new VerticalLayout(vlTituloTablonNoticias);
        for(Component noticia : listaNoticias) {
            vlNoticias.add(noticia);
        }
        vlNoticias.add(AñadirNoticiaButton);
        vlNoticias.addClassName("vlNoticiasOfertas");

        // Ofertas
        ArrayList<Component> listaOfertas = new ArrayList<>();
        for(Oferta oferta : ofertas) {
            if(oferta.getFechaFin().isAfter(LocalDate.now())) {
                // Creacion de los elementos de la oferta
                H3 TituloOferta = new H3(oferta.getTitulo());
                H4 FechaFinOferta = new H4("Fecha Fin: " + oferta.getFechaFin().toString());
                Paragraph TextoOferta = new Paragraph(oferta.getCuerpo());
                Anchor OfertaEditarButton = new Anchor("editar-oferta-encargado", "Editar");
                Anchor OfertaEliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");

                // CSS
                TituloOferta.addClassName("Textos");
                TextoOferta.addClassName("Textos");
                FechaFinOferta.addClassName("FechaFinOferta");
                OfertaEditarButton.addClassName("EditarButtons");
                OfertaEliminarButton.addClassName("EliminarButtons");

                var vlOferta = new VerticalLayout(TituloOferta, FechaFinOferta, TextoOferta);
                var hlOferta = new HorizontalLayout(vlOferta, OfertaEditarButton, OfertaEliminarButton);
                listaOfertas.add(hlOferta);

                // Editar oferta
                OfertaEditarButton.getElement().addEventListener("click", event -> {
                    EditarOfertaView.setTituloDescripcion(oferta);
                });

                // Eliminar oferta
                OfertaEliminarButton.getElement().addEventListener("click", event -> {
                    anuncioService.delete(oferta);
                    ConfirmDialog confirmRequest = new ConfirmDialog("Eliminada Oferta", "Oferta eliminada correctamente", "Aceptar", event1 -> {
                        UI.getCurrent().getPage().reload();
                    });
                    confirmRequest.open();
                });
            } else {
                anuncioService.delete(oferta);
            }
        }

        var vlTituloTablonOfertas = new VerticalLayout(TituloTablonOfertas);
        vlTituloTablonOfertas.setAlignItems(Alignment.CENTER);
        vlTituloTablonNoticias.getStyle().set("color", "black");
        var vlOfertas = new VerticalLayout(vlTituloTablonOfertas);
        for(Component oferta : listaOfertas) {
            vlOfertas.add(oferta);
        }
        vlOfertas.add(AñadirOfertaButton);
        vlOfertas.addClassName("vlNoticiasOfertas");

        // Layout de la Pagina Principal
        var hlNoticiasOfertas = new HorizontalLayout(vlNoticias, vlOfertas);
        hlNoticiasOfertas.addClassName("hlNoticiasOfertas");
        var vlPaginaPrincipal = new VerticalLayout(Titulo, hlNoticiasOfertas);
        vlPaginaPrincipal.addClassName("vlPaginaPrincipal");

        return vlPaginaPrincipal;
    }

    @PostConstruct
    public void init() {
        add(crearPaginaPrincipal());
        add(FooterView.Footer());
    }
}

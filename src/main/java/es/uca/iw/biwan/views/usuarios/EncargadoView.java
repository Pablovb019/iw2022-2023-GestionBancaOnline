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
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
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

        var vlNoticias = new VerticalLayout();
        var vlOfertas = new VerticalLayout();
        var hlNoticias = new HorizontalLayout();
        var hlOfertas = new HorizontalLayout();

        ArrayList<Noticia> noticias = anuncioService.findNoticiaByType("NOTICIA");
        ArrayList<Oferta> ofertas = anuncioService.findOfertaByType("OFERTA");

        // Log de las noticias
        for(Noticia noticia: noticias){
            System.out.println(noticia.getTitulo());
        }

        for(Oferta oferta: ofertas){
            System.out.println(oferta.getTitulo());
        }

        // TODO: Añadir las noticias y ofertas a los layouts
        return new VerticalLayout(Titulo);

    }

    @PostConstruct
    public void init() {
        add(crearPaginaPrincipal());
        add(FooterView.Footer());
    }
}

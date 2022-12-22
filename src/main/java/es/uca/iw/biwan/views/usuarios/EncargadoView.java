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
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.noticiasOfertas.EditarNoticiaView;
import es.uca.iw.biwan.views.noticiasOfertas.EditarOfertaView;

@Route("pagina-principal-encargado")
@CssImport("./themes/biwan/paginaPrincipalEncargado.css")
@PageTitle("Página Principal Encargado de Comunicaciones")
public class EncargadoView extends VerticalLayout {
    public EncargadoView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRole().contentEquals("ENCARGADO_COMUNICACIONES")) {
                UI.getCurrent().navigate("");
            } else {
                add(HeaderUsuarioLogueadoView.Header());
                add(crearPaginaPrincipal());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "El usuario no esta logueado", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
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
        Titulo.addClassName("Titulo");
        TituloTablonNoticias.addClassName("Textos");
        TituloTablonOfertas.addClassName("Textos");
        AñadirNoticiaButton.addClassName("AñadirNoticiaOfertaButtons");
        AñadirOfertaButton.addClassName("AñadirNoticiaOfertaButtons");

        // Creacion de los tablones de noticias y ofertas
        // Noticias
        // Noticia 1
        Anchor Noticia1EditarButton = new Anchor("editar-noticia-encargado", "Editar");
        Anchor Noticia1EliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");
        Noticia1EditarButton.addClassName("EditarButtons");
        Noticia1EliminarButton.addClassName("EliminarButtons");

        var TituloNoticia1 = new H3 ("Gracias al servicio de nuestros Gestores haremos todo por ti");
        TituloNoticia1.addClassName("Textos");
        var TextoNoticia1 = new H5 ("¿No tienes tiempo para gestionar tus cuentas y tarjetas? ¿No sabes cómo hacerlo? " +
                "¿No sabes qué hacer si te llega una factura? BIWAN te ofrece un servicio de gestión de tus cuentas y tarjetas " +
                "para que no tengas que preocuparte de nada. Nosotros nos encargamos de gestionarlo todo, " +
                "y de hacer las gestiones necesarias para que tu no tengas que preocuparte de nada. " +
                "Además, te ofrecemos un servicio de atención al cliente para que puedas resolver cualquier duda " +
                "que tengas sobre tus cuentas y tarjetas, las cuales seran respondidas por nuestros grandes gestores.");
        TextoNoticia1.addClassName("Textos");

        var vlNoticia1 = new VerticalLayout(TituloNoticia1, TextoNoticia1);
        var hlNoticia1 = new HorizontalLayout(vlNoticia1, Noticia1EditarButton, Noticia1EliminarButton);

        // Listener para el boton de editar
        Noticia1EditarButton.getElement().addEventListener("click", e -> {
            EditarNoticiaView.setTituloDescripcion(TituloNoticia1.getText(), TextoNoticia1.getText());
        });

        // Listener para el boton de eliminar
        Noticia1EliminarButton.getElement().addEventListener("click", e -> {
            hlNoticia1.setVisible(false);
        });

        // Noticia 2
        Anchor Noticia2EditarButton = new Anchor("editar-noticia-encargado", "Editar");
        Anchor Noticia2EliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");
        Noticia2EditarButton.addClassName("EditarButtons");
        Noticia2EliminarButton.addClassName("EliminarButtons");

        var TituloNoticia2 = new H3 ("Gracias al servicio de nuestros Gestores haremos todo por ti");
        TituloNoticia2.addClassName("Textos");
        var TextoNoticia2 = new H5 ("¿No tienes tiempo para gestionar tus cuentas y tarjetas? ¿No sabes cómo hacerlo? " +
                "¿No sabes qué hacer si te llega una factura? BIWAN te ofrece un servicio de gestión de tus cuentas y tarjetas " +
                "para que no tengas que preocuparte de nada. Nosotros nos encargamos de gestionarlo todo, " +
                "y de hacer las gestiones necesarias para que tu no tengas que preocuparte de nada. " +
                "Además, te ofrecemos un servicio de atención al cliente para que puedas resolver cualquier duda " +
                "que tengas sobre tus cuentas y tarjetas, las cuales seran respondidas por nuestros grandes gestores.");
        TextoNoticia2.addClassName("Textos");

        var vlNoticia2 = new VerticalLayout(TituloNoticia2, TextoNoticia2);
        var hlNoticia2 = new HorizontalLayout(vlNoticia2, Noticia2EditarButton, Noticia2EliminarButton);

        // Listener para el boton de editar
        Noticia2EditarButton.getElement().addEventListener("click", e -> {
            EditarNoticiaView.setTituloDescripcion(TituloNoticia2.getText(), TextoNoticia2.getText());
        });

        // Listener para el boton de eliminar
        Noticia2EliminarButton.getElement().addEventListener("click", e -> {
            hlNoticia2.setVisible(false);
        });

        var vlTituloTablonNoticias = new VerticalLayout(TituloTablonNoticias);
        vlTituloTablonNoticias.setAlignItems(Alignment.CENTER);
        vlTituloTablonNoticias.getStyle().set("color", "black");
        var vlNoticias = new VerticalLayout(vlTituloTablonNoticias, hlNoticia1, hlNoticia2, AñadirNoticiaButton);
        vlNoticias.addClassName("vlNoticiasOfertas");

        // Ofertas
        // Oferta 1
        Anchor Oferta1EditarButton = new Anchor("editar-oferta-encargado", "Editar");
        Anchor Oferta1EliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");
        Oferta1EditarButton.addClassName("EditarButtons");
        Oferta1EliminarButton.addClassName("EliminarButtons");

        var TituloOferta1 = new H3 ("Llévate hasta 150 € con el Plan Invita a un Amigo");
        TituloOferta1.addClassName("Textos");
        var TextoOferta1 = new H5 ("¿Quieres llevarte 15 € por cada amigo que invites a BIWAN (máximo 10 amigos)" +
                " y conseguir que ellos se lleven también 15 €?" +
                " Solo tienes que seguir estos pasos: Primero, hazte cliente con la Cuenta Online Sin Comisiones. " +
                "Accede al área privada de cliente en \"Mis promociones\" y comparte tu código con amigos y familiares." +
                " Segundo, para llevaros el premio, tus amigos tendrán que utilizar tu código al hacerse clientes y realizar una " +
                "compra superior a 15 € con su nueva tarjeta BIWAN. Consulta las condiciones de la promoción en el apartado " +
                "\"más información\".");
        TextoOferta1.addClassName("Textos");

        var vlOferta1 = new VerticalLayout(TituloOferta1, TextoOferta1);
        var hlOferta1 = new HorizontalLayout(vlOferta1, Oferta1EditarButton, Oferta1EliminarButton);

        // Listener para el boton de editar
        Oferta1EditarButton.getElement().addEventListener("click", e -> {
            EditarOfertaView.setTituloDescripcion(TituloOferta1.getText(), TextoOferta1.getText());
        });

        // Listener para el boton de eliminar
        Oferta1EliminarButton.getElement().addEventListener("click", e -> {
            hlOferta1.setVisible(false);
        });

        // Oferta 2
        Anchor Oferta2EditarButton = new Anchor("editar-oferta-encargado", "Editar");
        Anchor Oferta2EliminarButton = new Anchor("pagina-principal-encargado", "Eliminar");
        Oferta2EditarButton.addClassName("EditarButtons");
        Oferta2EliminarButton.addClassName("EliminarButtons");

        var TituloOferta2 = new H3 ("Llévate hasta 150 € con el Plan Invita a un Amigo");
        TituloOferta2.addClassName("Textos");
        var TextoOferta2 = new H5 ("¿Quieres llevarte 15 € por cada amigo que invites a BIWAN (máximo 10 amigos)" +
                " y conseguir que ellos se lleven también 15 €?" +
                " Solo tienes que seguir estos pasos: Primero, hazte cliente con la Cuenta Online Sin Comisiones. " +
                "Accede al área privada de cliente en \"Mis promociones\" y comparte tu código con amigos y familiares." +
                " Segundo, para llevaros el premio, tus amigos tendrán que utilizar tu código al hacerse clientes y realizar una " +
                "compra superior a 15 € con su nueva tarjeta BIWAN. Consulta las condiciones de la promoción en el apartado " +
                "\"más información\".");
        TextoOferta2.addClassName("Textos");

        var vlOferta2 = new VerticalLayout(TituloOferta2, TextoOferta2);
        var hlOferta2 = new HorizontalLayout(vlOferta2, Oferta2EditarButton, Oferta2EliminarButton);

        // Listener para el boton de editar
        Oferta2EditarButton.getElement().addEventListener("click", e -> {
            EditarOfertaView.setTituloDescripcion(TituloOferta2.getText(), TextoOferta2.getText());
        });

        // Listener para el boton de eliminar
        Oferta2EliminarButton.getElement().addEventListener("click", e -> {
            hlOferta2.setVisible(false);
        });

        var vlTituloTablonOfertas = new VerticalLayout(TituloTablonOfertas);
        vlTituloTablonOfertas.setAlignItems(Alignment.CENTER);
        var vlOfertas = new VerticalLayout(vlTituloTablonOfertas, hlOferta1, hlOferta2, AñadirOfertaButton);
        vlOfertas.addClassName("vlNoticiasOfertas");

        // Layout de la Pagina Principal
        var hlNoticiasOfertas = new HorizontalLayout(vlNoticias, vlOfertas);
        hlNoticiasOfertas.addClassName("hlNoticiasOfertas");
        var vlPaginaPrincipal = new VerticalLayout(Titulo, hlNoticiasOfertas);
        var hlPaginaPrincipal = new HorizontalLayout(vlPaginaPrincipal);

        return hlPaginaPrincipal;
    }
}

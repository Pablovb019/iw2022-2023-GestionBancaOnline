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
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.headers.HeaderView;

@PageTitle("Main")
@Route("")
@CssImport("./themes/biwan/main.css")
@AnonymousAllowed
public class MainView extends VerticalLayout {
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
        add(crearAnuncio1());
        add(crearAnuncio2());
        add(crearAnuncio3());
        add(FooterView.Footer());
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

    private Component crearAnuncio1() {
        Image img1 = new Image("images/1.jpg", "Imagen");
        img1.setMaxHeight("500px");
        img1.setMaxWidth("500px");

        Anchor BotonHazteCliente1 = new Anchor("registration", "Hazte cliente");
        BotonHazteCliente1.addClassName("BotonesHazteCliente");

        Anchor BotonInfo1 = new Anchor("", "Más información");
        BotonInfo1.addClassName("BotonesInfo");

        var TituloTextoAnuncio1 = new H3("Hazte cliente de BIWAN ahora y ayuda al medio ambiente");
        TituloTextoAnuncio1.addClassName("TituloTextoAnuncio1");
        var TextoAnuncio1 = new H5("Los árboles son fuente de vida. No solo en referencia a los ecosistemas naturales, " +
                "sino también para la supervivencia del ser humano. Su uso para alimentarse, calentarse y construir un sinfín " +
                "de objetos supone una explotación que, entre otros factores, dispara la deforestación y, con ella, " +
                "la destrucción del hábitat. Cada vez que un cliente llega a BIWAN, nosotros plantamos un árbol. " +
                "Estamos comprometidos contigo pero también con el medio ambiente. Por eso, y muchas otras cosas, " +
                "te invitamos a que te unas a nosotros.");
        TextoAnuncio1.addClassName("TxtAnuncio1");

        var vlImagenAnuncio1 = new VerticalLayout(img1);
        vlImagenAnuncio1.setAlignItems(Alignment.END);
        var vlTextoAnuncio1 = new VerticalLayout(TituloTextoAnuncio1, TextoAnuncio1,
                new HorizontalLayout(BotonHazteCliente1, BotonInfo1));

        var hlAnuncio1 = new HorizontalLayout(vlTextoAnuncio1, vlImagenAnuncio1);
        hlAnuncio1.setWidthFull();
        hlAnuncio1.addClassName("hlAnuncio1");

        return hlAnuncio1;
    }

    private Component crearAnuncio2() {
        Image img2 = new Image("images/2.jpg", "Imagen");
        img2.setMaxHeight("500px");
        img2.setMaxWidth("500px");

        Anchor BotonHazteCliente2 = new Anchor("registration", "Hazte cliente");
        BotonHazteCliente2.addClassName("BotonesHazteCliente");

        Anchor BotonInfo2 = new Anchor("", "Más información");
        BotonInfo2.addClassName("BotonesInfo");

        var TituloTextoAnuncio2 = new H3("Gracias al servicio de nuestros Gestores haremos todo por ti");
        TituloTextoAnuncio2.addClassName("TituloTextoAnuncio2");
        var TextoAnuncio2 = new H5("¿No tienes tiempo para gestionar tus cuentas y tarjetas? ¿No sabes cómo hacerlo? " +
                "¿No sabes qué hacer si te llega una factura? BIWAN te ofrece un servicio de gestión de tus cuentas y tarjetas " +
                "para que no tengas que preocuparte de nada. Nosotros nos encargamos de gestionarlo todo, " +
                "y de hacer las gestiones necesarias para que tu no tengas que preocuparte de nada. " +
                "Además, te ofrecemos un servicio de atención al cliente para que puedas resolver cualquier duda " +
                "que tengas sobre tus cuentas y tarjetas, las cuales seran respondidas por nuestros grandes gestores.");
        TextoAnuncio2.addClassName("TxtAnuncio2");

        var vlImagenAnuncio2 = new VerticalLayout(img2);
        vlImagenAnuncio2.setAlignItems(Alignment.START);
        var vlTextoAnuncio2 = new VerticalLayout(TituloTextoAnuncio2, TextoAnuncio2,
                new HorizontalLayout(BotonHazteCliente2, BotonInfo2));

        var hlAnuncio2 = new HorizontalLayout(vlImagenAnuncio2, vlTextoAnuncio2);
        hlAnuncio2.setWidthFull();
        hlAnuncio2.addClassName("hlAnuncio2");

        return hlAnuncio2;
    }

    private Component crearAnuncio3() {
        Image img3 = new Image("images/3.jpg", "Imagen");
        img3.setMaxHeight("500px");
        img3.setMaxWidth("500px");

        Anchor BotonHazteCliente3 = new Anchor("registration", "Hazte cliente");
        BotonHazteCliente3.addClassName("BotonesHazteCliente");

        Anchor BotonInfo3 = new Anchor("", "Más información");
        BotonInfo3.addClassName("BotonesInfo");

        var TituloTextoAnuncio3 = new H3("Llévate hasta 150 € con el Plan Invita a un Amigo");
        TituloTextoAnuncio3.addClassName("TituloTextoAnuncio3");
        var TextoAnuncio3 = new H5("¿Quieres llevarte 15 € por cada amigo que invites a BIWAN (máximo 10 amigos)" +
                " y conseguir que ellos se lleven también 15 €?" +
                " Solo tienes que seguir estos pasos: Primero, hazte cliente con la Cuenta Online Sin Comisiones. " +
                "Accede al área privada de cliente en \"Mis promociones\" y comparte tu código con amigos y familiares." +
                " Segundo, para llevaros el premio, tus amigos tendrán que utilizar tu código al hacerse clientes y realizar una " +
                "compra superior a 15 € con su nueva tarjeta BIWAN. Consulta las condiciones de la promoción en el apartado " +
                "\"más información\".");
        TextoAnuncio3.addClassName("TextoAnuncio3");

        var vlImagenAnuncio3 = new VerticalLayout(img3);
        vlImagenAnuncio3.setAlignItems(Alignment.END);
        var vlTextoAnuncio3 = new VerticalLayout(TituloTextoAnuncio3, TextoAnuncio3, new HorizontalLayout(BotonHazteCliente3, BotonInfo3));

        var hlAnuncio3 = new HorizontalLayout(vlTextoAnuncio3, vlImagenAnuncio3);
        hlAnuncio3.setWidthFull();
        hlAnuncio3.addClassName("hlAnuncio3");

        return hlAnuncio3;
    }
}

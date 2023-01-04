package es.uca.iw.biwan.views.cookies;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/cookies.css")
@Route("cookies")
@PageTitle("Política de cookies")
@AnonymousAllowed
public class CookiesView extends VerticalLayout{

    public CookiesView() {

        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null
                || session.getAttribute(Gestor.class) != null
                || session.getAttribute(EncargadoComunicaciones.class) != null
                || session.getAttribute(Administrador.class) != null) {
            // Si hay un usuario logueado, mostrar la vista de usuario logueado
            add(HeaderUsuarioLogueadoView.Header());
        } else {
            // Si no hay un usuario logueado, mostrar la vista de usuario no logueado
            add(HeaderView.Header());
        }

        //NEW
        VerticalLayout layoutCookies = new VerticalLayout();
        VerticalLayout layoutTextoCookies = new VerticalLayout();
        H1 Titulo = new H1("Política de cookies");
        H3 CapaInformativaPoliticaCookies = new H3("CAPA INFORMATIVA – POLÍTICA DE COOKIES");
        H5 TextoCapaInfPolCookies = new H5("Las Políticas de cookies de BIWAN son un conjunto de normas y " +
                "principios que rigen el uso de cookies en el sitio web del banco y en los servicios en línea. A " +
                "continuación, se presentan algunos puntos clave de estas políticas:");
        HorizontalLayout layoutQueSonLasCookies = new HorizontalLayout();
        H4 Primero = new H4("1.");
        H4 QueSonLasCookies = new H4("¿Qué son las cookies?");
        H5 TextoQueSonLasCookies = new H5("Las cookies son pequeños archivos de texto que se almacenan en el " +
                "dispositivo del usuario (ordenador, teléfono móvil, tablet, etc.) cuando visita un sitio web. Las " +
                "cookies se utilizan para almacenar información sobre la navegación del usuario en el sitio, como " +
                "sus preferencias y sus hábitos de navegación.");
        HorizontalLayout layoutPorQueCookiesBiwan = new HorizontalLayout();
        H4 Segundo = new H4("2.");
        H4 PorQueCookiesBiwan = new H4("¿Por qué utilizamos cookies en BIWAN?");
        H5 TextoPorQueCookiesBiwan = new H5("Utilizamos cookies para mejorar la experiencia del usuario en " +
                "nuestro sitio web y en nuestros servicios en línea. Por ejemplo, las cookies nos permiten recordar " +
                "las preferencias del usuario y personalizar el contenido del sitio en función de sus intereses. " +
                "También utilizamos cookies para recopilar estadísticas sobre el tráfico y la utilización del sitio," +
                " con el fin de mejorar nuestros servicios.");
        HorizontalLayout layoutTiposCookiesBiwan = new HorizontalLayout();
        H4 Tercero = new H4("3.");
        H4 TiposCookiesBiwan = new H4("¿Qué tipos de cookies utilizamos en BIWAN?");
        H5 TextoTiposCookiesBiwan = new H5("Utilizamos diferentes tipos de cookies en BIWAN, incluyendo cookies" +
                " de sesión y cookies persistentes. Las cookies de sesión se eliminan automáticamente cuando el " +
                "usuario cierra el navegador, mientras que las cookies persistentes permanecen en el dispositivo " +
                "del usuario durante un período de tiempo más largo.");
        HorizontalLayout layoutComoControlarCookiesBiwan = new HorizontalLayout();
        H4 Cuarto = new H4("4.");
        H4 ComoControlarCookiesBiwan = new H4("¿Cómo puedo controlar las cookies en BIWAN?");
        H5 TextoComoControlarCookiesBiwan = new H5("Los usuarios pueden controlar las cookies en BIWAN a través" +
                " de la configuración de privacidad de su navegador. En la mayoría de los navegadores, es posible " +
                "aceptar o rechazar todas las cookies, aceptar solo algunas o modificar la configuración para recibir" +
                " un aviso cada vez que se envía una cookie. Tenga en cuenta que algunas funcionalidades de nuestro" +
                " sitio web y servicios en línea pueden no funcionar correctamente si se bloquean o eliminan las" +
                " cookies.");
        HorizontalLayout layoutQueSaberCookiesBiwan = new HorizontalLayout();
        H4 Quinto = new H4("5.");
        H4 QueSaberCookiesBiwan = new H4("¿Qué más debo saber sobre las cookies en BIWAN?");
        H5 TextoQueSaberCookiesBiwan = new H5("En BIWAN, protegemos su privacidad y tomamos medidas de seguridad" +
                " adecuadas para proteger sus datos personales. Si tiene alguna pregunta sobre nuestras políticas de" +
                " cookies o si desea obtener más información sobre cómo utilizamos las cookies en nuestro sitio web y" +
                " servicios en línea, no dude en ponerse en contacto con nosotros.");


        //ADD
        layoutQueSonLasCookies.add(Primero, QueSonLasCookies);
        layoutPorQueCookiesBiwan.add(Segundo, PorQueCookiesBiwan);
        layoutTiposCookiesBiwan.add(Tercero, TiposCookiesBiwan);
        layoutComoControlarCookiesBiwan.add(Cuarto, ComoControlarCookiesBiwan);
        layoutQueSaberCookiesBiwan.add(Quinto, QueSaberCookiesBiwan);
        layoutTextoCookies.add(Titulo, CapaInformativaPoliticaCookies, TextoCapaInfPolCookies, layoutQueSonLasCookies, TextoQueSonLasCookies, layoutPorQueCookiesBiwan, TextoPorQueCookiesBiwan, layoutTiposCookiesBiwan, TextoTiposCookiesBiwan, layoutComoControlarCookiesBiwan, TextoComoControlarCookiesBiwan, layoutQueSaberCookiesBiwan, TextoQueSaberCookiesBiwan);
        layoutCookies.add(layoutTextoCookies, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        Primero.addClassName("Numero");
        Segundo.addClassName("Numero");
        Tercero.addClassName("Numero");
        Cuarto.addClassName("Numero");
        Quinto.addClassName("Numero");
        QueSonLasCookies.addClassName("Pregunta");
        PorQueCookiesBiwan.addClassName("Pregunta");
        TiposCookiesBiwan.addClassName("Pregunta");
        ComoControlarCookiesBiwan.addClassName("Pregunta");
        QueSaberCookiesBiwan.addClassName("Pregunta");
        TextoCapaInfPolCookies.addClassName("textoSinMargen");
        TextoQueSonLasCookies.addClassName("textoCookies");
        TextoPorQueCookiesBiwan.addClassName("textoCookies");
        TextoTiposCookiesBiwan.addClassName("textoCookies");
        TextoComoControlarCookiesBiwan.addClassName("textoCookies");
        TextoQueSaberCookiesBiwan.addClassName("textoCookies");
        layoutTextoCookies.addClassName("layoutTextoCookies");

        //ALIGNMENT
        layoutTextoCookies.setWidth("50%");
        layoutCookies.expand(layoutTextoCookies);
        layoutTextoCookies.setAlignItems(Alignment.START);
        layoutCookies.setAlignItems(Alignment.CENTER);
        layoutCookies.setSizeFull();

        add(layoutCookies);
    }
}


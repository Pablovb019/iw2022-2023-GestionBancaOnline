package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@Route("pagina-principal-cliente")
@CssImport("./themes/biwan/paginaPrincipalCliente.css")
@PageTitle("Página Principal Cliente")
public class ClienteView extends VerticalLayout {
    public ClienteView(){
        add(HeaderUsuarioLogueadoView.Header());
        add(crearPaginaPrincipal());
        add(FooterView.Footer());
    }

    private Component crearPaginaPrincipal() {
        //Creacion de los apartados
        H1 Titulo = new H1("Bienvenido Jose Antonio");
        H2 TituloBalance = new H2("Balance BIWAN");
        Anchor Balance = new Anchor("", "2500.55€");
        H2 Operacionnes = new H2("Operaciones");
        Anchor MovimientosButton = new Anchor("movimientos", "Movimientos");
        Anchor RecibosButton = new Anchor("recibos-domiciliados", "Recibos");
        Anchor CuentasTarjetasButton = new Anchor("cuentas-tarjetas-cliente", "Cuentas y Tarjetas");
        Anchor TransferenciasButton = new Anchor("transferencias-traspasos", "Realizar Transferencia o Traspaso");
        Anchor ConsultaOnlineButton = new Anchor("", "Realizar Consulta Online");
        Anchor ConsultaOfflineButton = new Anchor("", "Realizar Consulta Offline");
        H2 TablonAnuncios = new H2("Tablón de anuncios");

        //CSS
        TablonAnuncios.addClassName("TablonAnuncios");
        Titulo.addClassName("Titulos");
        TituloBalance.addClassName("Titulos");
        Operacionnes.addClassName("Titulos");
        TablonAnuncios.addClassName("Titulos");
        Balance.addClassName("Balance");
        MovimientosButton.addClassName("BotonesBalance");
        RecibosButton.addClassName("BotonesBalance");
        CuentasTarjetasButton.addClassName("BotonCuentasTarjetas");
        TransferenciasButton.addClassName("BotonesOperaciones");
        ConsultaOnlineButton.addClassName("BotonesOperaciones");
        ConsultaOfflineButton.addClassName("BotonesOperaciones");

        //Creacion de la pagina
        //Layout de Titulo, Balance y Operaciones
        var hlBotonesBalance = new HorizontalLayout(MovimientosButton, RecibosButton);
        hlBotonesBalance.setWidthFull();
        hlBotonesBalance.setAlignItems(Alignment.CENTER);
        var hlBotonCuentasTarjetas = new HorizontalLayout(CuentasTarjetasButton);
        hlBotonCuentasTarjetas.setWidthFull();
        hlBotonCuentasTarjetas.setAlignItems(Alignment.CENTER);
        var vlBalace = new VerticalLayout(TituloBalance, Balance, hlBotonesBalance, hlBotonCuentasTarjetas);
        var vlOperaciones = new VerticalLayout(Operacionnes, TransferenciasButton, ConsultaOnlineButton, ConsultaOfflineButton);
        vlOperaciones.setAlignItems(Alignment.CENTER);
        var hlBalanceOperaciones = new HorizontalLayout(vlBalace, vlOperaciones);
        hlBalanceOperaciones.addClassName("hlBalanceOperaciones");
        hlBalanceOperaciones.setWidthFull();
        var vlTituloBalanceOperaciones = new VerticalLayout(Titulo, hlBalanceOperaciones);

        //Layout de Tablon de anuncios

        // Anuncio 1
        Image imgAnuncio1 = new Image("images/2.jpg", "Anuncio 1");
        imgAnuncio1.setMaxHeight("200px");
        imgAnuncio1.setMaxWidth("200px");

        Anchor BotonInfo1 = new Anchor("", "Más información");
        BotonInfo1.addClassName("BotonesInfo");

        var TituloTextoAnuncio1 = new H3 ("Gracias al servicio de nuestros Gestores haremos todo por ti");
        TituloTextoAnuncio1.addClassName("TituloTextoAnuncio1");
        var TextoAnuncio1 = new H5 ("¿No tienes tiempo para gestionar tus cuentas y tarjetas? ¿No sabes cómo hacerlo? " +
                "¿No sabes qué hacer si te llega una factura? BIWAN te ofrece un servicio de gestión de tus cuentas y tarjetas " +
                "para que no tengas que preocuparte de nada. Nosotros nos encargamos de gestionarlo todo, " +
                "y de hacer las gestiones necesarias para que tu no tengas que preocuparte de nada. " +
                "Además, te ofrecemos un servicio de atención al cliente para que puedas resolver cualquier duda " +
                "que tengas sobre tus cuentas y tarjetas, las cuales seran respondidas por nuestros grandes gestores.");
        TextoAnuncio1.addClassName("TextoAnuncio1");

        var vlImagenAnuncio1 = new VerticalLayout(imgAnuncio1);
        vlImagenAnuncio1.setAlignItems(Alignment.START);
        vlImagenAnuncio1.setWidth("auto");
        vlImagenAnuncio1.getStyle().set("padding-right", "10px");
        var vlTextoAnuncio1 = new VerticalLayout(TituloTextoAnuncio1, TextoAnuncio1,
                new HorizontalLayout(BotonInfo1));

        var hlAnuncio1 = new HorizontalLayout(vlImagenAnuncio1, vlTextoAnuncio1);
        hlAnuncio1.addClassName("hlAnuncio1Cliente");

        // Anuncio 2
        Image imgAnuncio2 = new Image("images/3.jpg", "Anuncio 2");
        imgAnuncio2.setMaxHeight("200px");
        imgAnuncio2.setMaxWidth("200px");

        Anchor BotonInfo2 = new Anchor("", "Más información");
        BotonInfo2.addClassName("BotonesInfo");

        var TituloTextoAnuncio2 = new H3 ("Llévate hasta 150 € con el Plan Invita a un Amigo");
        TituloTextoAnuncio2.addClassName("TituloTextoAnuncio2");
        var TextoAnuncio2 = new H5 ("¿Quieres llevarte 15 € por cada amigo que invites a BIWAN (máximo 10 amigos)" +
                " y conseguir que ellos se lleven también 15 €?" +
                " Solo tienes que seguir estos pasos: Primero, hazte cliente con la Cuenta Online Sin Comisiones. " +
                "Accede al área privada de cliente en \"Mis promociones\" y comparte tu código con amigos y familiares." +
                " Segundo, para llevaros el premio, tus amigos tendrán que utilizar tu código al hacerse clientes y realizar una " +
                "compra superior a 15 € con su nueva tarjeta BIWAN. Consulta las condiciones de la promoción en el apartado " +
                "\"más información\".");
        TextoAnuncio2.addClassName("TextoAnuncio2");

        var vlImagenAnuncio2 = new VerticalLayout(imgAnuncio2);
        vlImagenAnuncio2.setAlignItems(Alignment.START);
        vlImagenAnuncio2.setWidth("auto");
        vlImagenAnuncio2.getStyle().set("padding-right", "10px");
        var vlTextoAnuncio2 = new VerticalLayout(TituloTextoAnuncio2, TextoAnuncio2,
                new HorizontalLayout(BotonInfo2));

        var hlAnuncio2 = new HorizontalLayout(vlImagenAnuncio2, vlTextoAnuncio2);
        hlAnuncio2.addClassName("hlAnuncio2Cliente");




        var vlTablonAnuncios = new VerticalLayout(TablonAnuncios, hlAnuncio1, hlAnuncio2);
        vlTablonAnuncios.setAlignItems(Alignment.CENTER);
        vlTablonAnuncios.addClassName("vlTablonAnuncios");
        vlTablonAnuncios.setMaxWidth("1000px");


        //Layout de la Pagina Principal
        var hlPaginaPrincipal = new HorizontalLayout(vlTituloBalanceOperaciones, vlTablonAnuncios);
        hlPaginaPrincipal.setWidthFull();
        hlPaginaPrincipal.addClassName("hlPaginaPrincipal");

        return hlPaginaPrincipal;
    }
}
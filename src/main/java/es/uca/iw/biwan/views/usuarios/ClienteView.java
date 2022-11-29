package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderClienteView;

@Route("pagina-principal-cliente")
@CssImport("/themes/biwan/paginaPrincipalCliente.css")
@PageTitle("Página Principal Cliente")
public class ClienteView extends VerticalLayout {
    public ClienteView(){
        add(HeaderClienteView.Header());
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
        TransferenciasButton.addClassName("BotonesOperaciones");
        ConsultaOnlineButton.addClassName("BotonesOperaciones");
        ConsultaOfflineButton.addClassName("BotonesOperaciones");

        //Creacion de la pagina
        //Layout de Titulo, Balance y Operaciones
        var hlBotonesBalance = new HorizontalLayout(MovimientosButton, RecibosButton);
        hlBotonesBalance.setWidthFull();
        hlBotonesBalance.setAlignItems(Alignment.CENTER);
        var vlBalace = new VerticalLayout(TituloBalance, Balance, hlBotonesBalance);
        var vlOperaciones = new VerticalLayout(Operacionnes, TransferenciasButton, ConsultaOnlineButton, ConsultaOfflineButton);
        vlOperaciones.setAlignItems(Alignment.CENTER);
        var hlBalanceOperaciones = new HorizontalLayout(vlBalace, vlOperaciones);
        hlBalanceOperaciones.addClassName("hlBalanceOperaciones");
        hlBalanceOperaciones.setWidthFull();
        var vlTituloBalanceOperaciones = new VerticalLayout(Titulo, hlBalanceOperaciones);

        //Layout de Tablon de anuncios
        var hlAnuncio1 = new HorizontalLayout(new Image("images/1.jpg", "Imagen"));
        var hlAnuncio2 = new HorizontalLayout(new Image("images/2.jpg", "Imagen"));
        var vlTablonAnuncios = new VerticalLayout(TablonAnuncios);
        vlTablonAnuncios.setAlignItems(Alignment.CENTER);
        vlTablonAnuncios.addClassName("vlTablonAnuncios");

        //Layout de la Pagina Principal
        var hlPaginaPrincipal = new HorizontalLayout(vlTituloBalanceOperaciones, vlTablonAnuncios);
        hlPaginaPrincipal.setWidthFull();
        hlPaginaPrincipal.addClassName("hlPaginaPrincipal");

        return hlPaginaPrincipal;
    }
}
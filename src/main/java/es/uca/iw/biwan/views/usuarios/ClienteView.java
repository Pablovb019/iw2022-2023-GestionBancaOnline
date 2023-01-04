package es.uca.iw.biwan.views.usuarios;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.AnuncioService;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;

@Route("pagina-principal-cliente")
@CssImport("./themes/biwan/paginaPrincipalCliente.css")
@PageTitle("Página Principal Cliente")
public class ClienteView extends VerticalLayout {
    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private CuentaService cuentaService;
    public ClienteView(){
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
            if (session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null){
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Aceptar", event -> {
                    if (session.getAttribute(Gestor.class) != null){
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    }else if (session.getAttribute(EncargadoComunicaciones.class) != null){
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null){
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
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
        //Creacion de los apartados
        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Cliente.class).getNombre();
        H1 Titulo = new H1("Bienvenido " + nombre);
        H1 Balance = new H1();

        H2 TituloBalance = new H2("Balance BIWAN");
        ArrayList<Cuenta> cuentas = cuentaService.findCuentaByCliente(session.getAttribute(Cliente.class));
        if (cuentas.size() > 0) {
            Balance.getElement().getStyle().set("cursor", "pointer");
            double balance = 0;
            for (Cuenta cuenta : cuentas) {
                balance += cuenta.getBalance();
            }
            Balance.setText(String.format("%.2f", balance) + " €");

            Balance.addClickListener(event -> {
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Cuentas");
                dialog.setWidth("600px");

                VerticalLayout layout = new VerticalLayout();
                Grid<Cuenta> gridBalance = new Grid<>();
                gridBalance.setItems(cuentas);
                gridBalance.addColumn(Cuenta::getIBAN).setHeader("IBAN").setWidth("300px");
                gridBalance.addColumn(cuenta -> String.format("%,.2f €", cuenta.getBalance())).setHeader("Balance").setWidth("100px");
                gridBalance.setAllRowsVisible(true);
                layout.add(gridBalance);


                Button cancelButton = new Button("Cerrar", e -> dialog.close());
                layout.add(cancelButton);
                dialog.add(layout);
                dialog.open();
            });

        } else {
            Balance = new H1("NO TIENES");
        }

        Balance.getStyle().set("margin-top", "0px");
        Balance.getStyle().set("margin-bottom", "5px");
        H2 Operacionnes = new H2("Operaciones");
        Anchor MovimientosButton = new Anchor("movimientos", "Movimientos");
        Anchor RecibosButton = new Anchor("pagos-tarjeta", "Pagos Tarjeta");
        Anchor CuentasTarjetasButton = new Anchor("cuentas-tarjetas-cliente", "Cuentas y Tarjetas");
        Anchor TransferenciasButton = new Anchor("transferencias-traspasos", "Realizar Transferencia o Traspaso");
        Anchor ConsultaOnlineButton = new Anchor("consultas-online-cliente", "Realizar Consulta Online");
        Anchor ConsultaOfflineButton = new Anchor("consultas-offline-cliente", "Realizar Consulta Offline");
        H2 TablonAnuncios = new H2("Tablón de anuncios");

        //CSS
        TablonAnuncios.addClassName("TablonAnuncios");
        Titulo.addClassName("TituloPrincipal");
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

        RecibosButton.setWidth("110px");
        RecibosButton.getStyle().set("margin-left", "45px");

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

            // CSS
            TituloNoticia.addClassName("TituloTextoAnuncio");
            TextoNoticia.addClassName("TextoAnuncio");

            var vlTextoAnuncio = new VerticalLayout(TituloNoticia, TextoNoticia);

            var hlAnuncio = new HorizontalLayout(vlTextoAnuncio);
            listaNoticias.add(hlAnuncio);
        }

        // Ofertas
        ArrayList<Component> listaOfertas = new ArrayList<>();
        for(Oferta oferta : ofertas) {
            if(oferta.getFechaFin().isAfter(LocalDate.now())) {
                // Creacion de los elementos de la oferta
                H3 TituloOferta = new H3(oferta.getTitulo());
                Paragraph TextoOferta = new Paragraph(oferta.getCuerpo());

                // CSS
                TituloOferta.addClassName("TituloTextoAnuncio");
                TextoOferta.addClassName("TextoAnuncio");

                var vlTextoAnuncio = new VerticalLayout(TituloOferta, TextoOferta);

                var hlAnuncio = new HorizontalLayout(vlTextoAnuncio);
                listaOfertas.add(hlAnuncio);
            } else {
                anuncioService.delete(oferta);
            }
        }

        var vlTablonAnuncios = new VerticalLayout(TablonAnuncios);
        vlTablonAnuncios.setAlignItems(Alignment.CENTER);
        vlTablonAnuncios.addClassName("vlTablonAnuncios");
        vlTablonAnuncios.setMaxWidth("1000px");
        var vlNoticias = new VerticalLayout();
        for(Component noticia : listaNoticias) {
            vlNoticias.add(noticia);
        }
        var vlOfertas = new VerticalLayout();
        for(Component oferta : listaOfertas) {
            vlOfertas.add(oferta);
        }
        vlTablonAnuncios.add(vlNoticias, vlOfertas);

        //Layout de la Pagina Principal
        var hlPaginaPrincipal = new HorizontalLayout(vlTituloBalanceOperaciones, vlTablonAnuncios);
        hlPaginaPrincipal.setWidthFull();
        hlPaginaPrincipal.addClassName("hlPaginaPrincipal");

        return hlPaginaPrincipal;
    }

    @PostConstruct
    public void init() {
        try {
            add(crearPaginaPrincipal());
            add(FooterView.Footer());
        } catch (Exception ignored) { }
    }

}
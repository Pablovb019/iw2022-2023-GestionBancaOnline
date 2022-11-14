package es.uca.iw.biwan.views.movimientos;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.biwan.domain.movimiento.Movimiento;
import es.uca.iw.biwan.domain.movimiento.PagoTarjeta;
import es.uca.iw.biwan.domain.movimiento.ReciboDomiciliado;
import es.uca.iw.biwan.domain.movimiento.Transferencia;
import es.uca.iw.biwan.domain.movimiento.Traspaso;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@PageTitle("Movimientos")
@Route(value = "movimientos")
public class MovimientoView extends VerticalLayout {

    //@Autowired
    //private MovimientoService movimientoService;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
    private static DecimalFormat decimalformat = new DecimalFormat("#.00");

    private static String getFormattedMovimientoDate(Movimiento movimiento) {
        LocalDateTime fecha = movimiento.getFecha();

        return fecha.format(formatter);
    }

    private static String getFormattedMovimientoImporteColor(Movimiento movimiento) {
        float importe = movimiento.getImporte();

        if(importe < 0) {
            return "red";
        }
        else {
            return "green";
        }
    }

    private static String getFormattedMovimientoImporteDecimales(Movimiento movimiento) {
        float importe = movimiento.getImporte();

        return decimalformat.format(importe);
    }

    private static String getTituloMovimiento(Movimiento movimiento) {
        if(movimiento instanceof ReciboDomiciliado) {
            ReciboDomiciliado recibo = (ReciboDomiciliado) movimiento;

            return recibo.getEmisor();
        }
        if(movimiento instanceof PagoTarjeta) {
            PagoTarjeta pago = (PagoTarjeta) movimiento;

            return pago.getEstablecimiento();
        }
        if(movimiento instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) movimiento;

            return transferencia.getBeneficiario();
        }
        if(movimiento instanceof Traspaso) {
            Traspaso traspaso = (Traspaso) movimiento;

            return traspaso.getCuentaDestino();
        }
        
        return "Movimiento desconocido";
    }

    private static String getTipoMovimiento(Movimiento movimiento) {
        if(movimiento instanceof ReciboDomiciliado) {
            return "Recibo Domiciliado";
        }
        if(movimiento instanceof PagoTarjeta) {
            return "Pago con Tarjeta";
        }
        if(movimiento instanceof Traspaso) {
            return "Traspaso";
        }
        if(movimiento instanceof Transferencia) {
            float importe = movimiento.getImporte();
            if(importe < 0) {
                return "Transferencia realizada";
            }
            else {
                return "Transferencia recibida";
            }
        }

        return "Desconocido";
    }

    private static String getSaldoRestante(Movimiento movimiento) {
        float saldoRestante = movimiento.getBalanceRestante();

        return decimalformat.format(saldoRestante);
    }

    private static Renderer<Movimiento> dateRenderer() {
        return LitRenderer.<Movimiento> of(
            "<vaadin-vertical-layout style=\"line-height: 60px;\">"
                + "  <span>${item.fecha}</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("fecha", MovimientoView::getFormattedMovimientoDate);
    }

    private static Renderer<Movimiento> importeYSaldoRenderer() {
        return LitRenderer.<Movimiento> of(
            "<vaadin-vertical-layout>"
                + "  <span style=\"color:${item.color}\">${item.importe} €</span>"
                + "  <span>Saldo: ${item.saldo} €</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("importe", MovimientoView::getFormattedMovimientoImporteDecimales)
            .withProperty("color", MovimientoView::getFormattedMovimientoImporteColor)
            .withProperty("saldo", MovimientoView::getSaldoRestante);
    }

    private static Renderer<Movimiento> tipoMovimientoYConceptoRenderer() {
        return LitRenderer.<Movimiento> of(
            "<vaadin-vertical-layout>"
                + "  <span style=\"font-weight: bold;\">${item.titulo}</span>"
                + "  <span>${item.tipo}</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("titulo", MovimientoView::getTituloMovimiento)
            .withProperty("tipo", MovimientoView::getTipoMovimiento);
            
    }

    public MovimientoView() {
        setSizeFull();

        add(HeaderView.Header());

        H1 titleLayout = new H1("Movimientos");
        titleLayout.getStyle().set("margin-top", "10px");
        titleLayout.getStyle().set("margin-bottom", "10px");
        titleLayout.getStyle().set("text-align", "center");
        titleLayout.setWidth("100%");

        add(titleLayout);

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setSizeFull();
        vLayout.setWidth("80%");
        vLayout.getStyle().set("align-self", "center");

        List<Movimiento> movimientos = generateDatosPrueba();

        Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
        grid.addColumn(dateRenderer()).setHeader("Fecha").setComparator(Movimiento::getFecha);
        grid.addColumn(tipoMovimientoYConceptoRenderer()).setHeader("Movimiento");
        grid.addColumn(importeYSaldoRenderer()).setHeader("Importe");

        GridListDataView<Movimiento> dataView = grid.setItems(movimientos);
        grid.setWidth("100%");
        grid.getStyle().set("align-self", "center");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        Select<String> ingresosGastosFilter = new Select<>();
        ingresosGastosFilter.setLabel("Filtrar por");
        ingresosGastosFilter.setItems("Todos", "Ingresos", "Gastos");
        ingresosGastosFilter.setValue("Todos");

        ingresosGastosFilter.addValueChangeListener(e -> {

            dataView.removeFilters();
            
            if(e.getValue().equals("Ingresos")) {
                dataView.addFilter(movimiento -> movimiento.getImporte() > 0);
            }
            if(e.getValue().equals("Gastos")) {
                dataView.addFilter(movimiento -> movimiento.getImporte() < 0);
            }
        });

        ingresosGastosFilter.getStyle().set("align-self", "end");
        ingresosGastosFilter.getStyle().set("margin-right", "35px");

        vLayout.add(ingresosGastosFilter, grid);
        add(vLayout);
        add(FooterView.Footer());

    }

    private List<Movimiento> generateDatosPrueba() {
        List<Movimiento> dataExample = new ArrayList<Movimiento>();

        for (int i = 0; i < 30; i++) {
            // generate random localdatetime between 2022-11-01 and 2022-11-30 from classes Pago, ReciboDomiciliado, Transferencia and Traspaso
            LocalDateTime fecha = LocalDateTime.of(2022, 11, (int) (Math.random() * 30 + 1), (int) (Math.random() * 24), (int) (Math.random() * 60));
            // generate random float between -100 and 100
            float importe = (float) (Math.random() * 200 - 100);
            
            // select random name between Paypal, Discord, RiotGames, 
            String establecimiento = "Paypal, Discord, RiotGames, Amazon, Netflix, Spotify, Apple, Google, Microsoft, Ubisoft, EpicGames, EA, Steam, Origin, Blizzard, Nintendo, Sony, Playstation, Xbox, NintendoSwitch, UbisoftConnect, EpicGamesStore, SteamStore, OriginStore, BlizzardStore, NintendoStore, SonyStore, PlaystationStore, XboxStore, NintendoSwitchStore".split(", ")[(int) (Math.random() * 30)];
            String informacion = "Informacion " + i;

            // Generate random bankacount iban with 20 digits
            String cuentaOrigen = "ES";
            for (int j = 0; j < 20; j++) {
                cuentaOrigen += (int) (Math.random() * 10);
            }

            // Generate random bankacount iban
            String cuentaDestino = "ES";
            for (int j = 0; j < 20; j++) {
                cuentaDestino += (int) (Math.random() * 10);
            }

            // Generate random concepto bewteen Universidad de Cadiz, Adeudo Vodafone, adeudo Movistar ...
            String conceptoRecibo = "Universidad de Cadiz, Adeudo Vodafone, Adeudo Movistar, Adeudo Orange, Adeudo Jazztel, Adeudo Iberdrola, Adeudo Endesa, Adeudo Gas Natural, Adeudo Telepizza, Adeudo Domino's Pizza, Adeudo Pizza Hut, Adeudo McDonald's, Adeudo Burger King, Adeudo KFC, Adeudo Subway, Adeudo Starbucks, Adeudo Dunkin' Donuts, Adeudo Costa Coffee, Adeudo Coca Cola, Adeudo Pepsi, Adeudo Heineken, Adeudo Carrefour, Adeudo Mercadona, Adeudo Lidl, Adeudo Alcampo, Adeudo Decathlon, Adeudo Zara, Adeudo H&M, Adeudo Primark, Adeudo Nike, Adeudo Adidas".split(", ")[(int) (Math.random() * 30)];
            
        
            //Generate random concepto bewteen Ropa invierno, PC Gamer, Coche, Viaje, Comida, ...
            String conceptoTransferencia = "Ropa invierno, PC Gamer, Coche, Viaje, Comida, Bebida, Cine, Pelicula, Series, Musica, Libro, Videojuego, Ordenador, Movil, Cables, Pantalla, Teclado, Raton, Auriculares, Disco Duro, Memoria, Tarjeta Grafica, Tarjeta Sonido, Tarjeta Red, Tarjeta Wifi, Tarjeta Bluetooth, Tarjeta USB, Tarjeta SD, Tarjeta MicroSD, Tarjeta MicroSDHC, Tarjeta MicroSDXC, Tarjeta CompactFlash".split(", ")[(int) (Math.random() * 30)];

            String conceptoTraspaso = "Concepto traspaso " + i;

            // Generate random beneficiario Person name bewteen John, Maria, ...
            String beneficiario = "John, Maria, Peter, Susan, Michael, Sarah, David, Karen, Richard, Lisa, James, Emma, Robert, Helen, William, Jennifer, Thomas, Susan, Charles, Margaret, Christopher, Dorothy, Daniel, Linda, Matthew, Elizabeth, Anthony, Barbara, Donald, Jessica, Mark, Sarah, Paul, Nancy, Steven, Patricia, Andrew, Linda, Kenneth, Carol, George, Sandra, Joshua, Ashley, Kevin, Kimberly, Brian, Donna, Edward, Carol, Ronald, Ruth, Timothy, Sharon, Jason, Michelle, Jeffrey, Laura, Ryan, Sarah, Jacob, Kimberly, Gary, Deborah, Nicholas, Shirley, Eric, Cynthia, Stephen, Angela, Jonathan, Melissa, Larry, Brenda, Justin, Amy, Scott, Anna, Brandon, Virginia, Benjamin, Pamela, Sam, Katherine, Frank, Deborah, Gregory, Rachel, Raymond, Carolyn, Alexander, Christine, Patrick, Janet, Jack, Carol, Dennis, Maria, Jerry, Heather, Tyler, Dorothy, Aaron, Michelle, Henry, Lori, Douglas, Andrea, Adam, Theresa, Peter, Diane, Nathan, Marie, Zachary, Julie, Walter, Julia, Harold, Frances, Carl, Joyce, Eugene, Evelyn, Arthur, Joan, Keith, Christina, Gerald, Judith, Roger, Virginia, Jeremy, Cheryl, Terry, Katherine, Sean, Hannah, Ralph, Olivia, Lawrence, Ann, Nicholas, Alice, Roy, Andrea, Bobby, Judith, Howard, Diane, Vincent, Marie, Danny, Jacqueline, Bruce, Gloria, Albert, Tanya, Harry, Nancy, Wayne, Evelyn, Eugene, Judy, Carlos, Christina, Russell, Helen, Bryan, Dorothy, Juan, Julia, Harry, Frances, Carl, Joyce, Eugene, Evelyn, Arthur, Joan, Keith, Christina, Gerald, Judith, Roger, Virginia, Jeremy, Cheryl, Terry, Katherine, Sean, Hannah, Ralph, Olivia, Lawrence, Ann, Nicholas, Alice, Roy, Andrea, Bobby, Judith, Howard, Diane, Vincent, Marie, Danny, Jacqueline, Bruce, Gloria, Albert, Tanya, Harry, Nancy, Wayne, Evelyn, Eugene, Judy, Carlos, Christina, Russell, Helen, Bryan, Dorothy, Juan, Julia, Harry, Frances, Carl, Joyce, Eugene, Evelyn, Arthur, Joan, Keith, Christina, Gerald, Judith, Roger, Virginia, Jeremy, Cheryl, Terry, Katherine, Sean".split(", ")[(int) (Math.random() * 30)];

            // Generate random balanceRestante bewteen -100 and 100
            float balanceRestante = (float) (Math.random() * 200 - 100);

            // Generate random data example
            switch ((int) (Math.random() * 4)) {
                case 0:
                    dataExample.add(new PagoTarjeta(importe, fecha, balanceRestante, informacion, establecimiento));
                    break;
                case 1:
                    dataExample.add(new ReciboDomiciliado(importe, fecha, balanceRestante, fecha, establecimiento, conceptoRecibo));
                    break;
                case 2:
                    dataExample.add(new Transferencia(importe, fecha, balanceRestante, cuentaOrigen, cuentaDestino, beneficiario, conceptoTransferencia));
                    break;
                case 3:
                    dataExample.add(new Traspaso(importe, fecha, balanceRestante, cuentaOrigen, cuentaDestino, conceptoTraspaso));
                    break;
            }
            
        }

        return dataExample;
    }
    
}
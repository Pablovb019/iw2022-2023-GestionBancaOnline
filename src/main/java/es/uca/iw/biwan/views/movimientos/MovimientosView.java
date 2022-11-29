package es.uca.iw.biwan.views.movimientos;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import es.uca.iw.biwan.domain.operaciones.ReciboDomiciliado;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@PageTitle("Movimientos")
@Route("movimientos")
@CssImport("/themes/biwan/movimientos.css")
public class MovimientosView extends VerticalLayout {

    public MovimientosView() {
        setSizeFull();

        add(HeaderUsuarioLogueadoView.Header());

        H1 titleLayout = new H1("Movimientos");
        titleLayout.getStyle().set("margin-top", "10px");
        titleLayout.getStyle().set("margin-bottom", "10px");
        titleLayout.getStyle().set("text-align", "center");
        titleLayout.setWidth("100%");

        add(titleLayout);

        VerticalLayout tableLayout = new VerticalLayout();
        tableLayout.setSizeFull();
        tableLayout.setWidth("80%");
        tableLayout.getStyle().set("align-self", "center");

        HorizontalLayout filterTableLayout = new HorizontalLayout();
        filterTableLayout.setWidth("100%");

        VerticalLayout filterTypeTableLayout = new VerticalLayout();
        filterTypeTableLayout.setWidth("100%");

        DatePicker fechaInicioPicker = new DatePicker("Fecha inicio");
        DatePicker fechaFinPicker = new DatePicker("Fecha fin");

        singleFormatI18n.setDateFormat("dd/MM/yyyy");
        fechaInicioPicker.setI18n(singleFormatI18n);
        fechaFinPicker.setI18n(singleFormatI18n);

        fechaInicioPicker.addValueChangeListener(e -> fechaFinPicker.setMin(e.getValue()));
        fechaFinPicker.addValueChangeListener(e -> fechaInicioPicker.setMax(e.getValue()));

        fechaInicioPicker.setValue(LocalDate.now().minusDays(30));
        fechaFinPicker.setValue(LocalDate.now());

        Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
        grid.addColumn(dateRenderer()).setHeader("Fecha").setComparator(Movimiento::getFecha);
        grid.addColumn(tipoMovimientoYConceptoRenderer()).setHeader("Movimiento");
        grid.addColumn(importeYSaldoRenderer()).setHeader("Importe");
        grid.setItemDetailsRenderer(createMovimientoDetailsRenderer());

        GridListDataView<Movimiento> dataView = grid.setItems(generateDatosPrueba(getFechaPickerValue(fechaInicioPicker), getFechaPickerValue(fechaFinPicker)));
        grid.setWidth("100%");
        grid.getStyle().set("align-self", "center");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        Select<String> ingresosGastosFilter = new Select<>();
        ingresosGastosFilter.setLabel("Filtrar por");
        ingresosGastosFilter.setItems("Todos", "Ingresos", "Gastos");
        ingresosGastosFilter.setValue("Todos");

        ingresosGastosFilter.addValueChangeListener(e -> {
            applyFilterIngresoGasto(dataView, ingresosGastosFilter);
        });

        Button buttonDateFilter = new Button("Filtrar");
        buttonDateFilter.addClickListener(e -> {
            grid.setItems(generateDatosPrueba(getFechaPickerValue(fechaInicioPicker), getFechaPickerValue(fechaFinPicker)));
            applyFilterIngresoGasto(dataView, ingresosGastosFilter);
        });
        buttonDateFilter.addClassName("ButtonDateFilter");

        buttonDateFilter.getStyle().set("align-self", "end");
        buttonDateFilter.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        ingresosGastosFilter.getStyle().set("align-self", "end");
        ingresosGastosFilter.getStyle().set("margin-right", "35px");

        filterTypeTableLayout.add(ingresosGastosFilter);
        filterTableLayout.add(fechaInicioPicker, fechaFinPicker, buttonDateFilter, filterTypeTableLayout);

        tableLayout.add(filterTableLayout, grid);
        add(tableLayout);
        add(FooterView.Footer());

    }

    //@Autowired
    //private MovimientoService movimientoService;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM");
    private static DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
    private static DecimalFormat decimalformat = new DecimalFormat("0.00");
    private static DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();

    private static String getFormattedMovimientoDate(Movimiento movimiento) {
        LocalDateTime fecha = movimiento.getFecha();

        return fecha.format(dateFormatter);
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

            return transferencia.getCuentaDestino();
        }
        if(movimiento instanceof Traspaso) {
            Traspaso traspaso = (Traspaso) movimiento;

            return traspaso.getCuentaDestino();
        }
        
        return "Movimiento desconocido";
    }

    private static String getConceptoMovimiento(Movimiento movimiento) {
        if(movimiento instanceof ReciboDomiciliado) {
            ReciboDomiciliado recibo = (ReciboDomiciliado) movimiento;

            return recibo.getConcepto();
        }
        if(movimiento instanceof PagoTarjeta) {
            PagoTarjeta pago = (PagoTarjeta) movimiento;

            return pago.getEstablecimiento();
        }
        if(movimiento instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) movimiento;

            return transferencia.getConcepto();
        }
        if(movimiento instanceof Traspaso) {
            Traspaso traspaso = (Traspaso) movimiento;

            return traspaso.getConcepto();
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
            .withProperty("fecha", MovimientosView::getFormattedMovimientoDate);
    }

    private static Renderer<Movimiento> importeYSaldoRenderer() {
        return LitRenderer.<Movimiento> of(
            "<vaadin-vertical-layout>"
                + "  <span style=\"color:${item.color}\">${item.importe} €</span>"
                + "  <span>Saldo: ${item.saldo} €</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("importe", MovimientosView::getFormattedMovimientoImporteDecimales)
            .withProperty("color", MovimientosView::getFormattedMovimientoImporteColor)
            .withProperty("saldo", MovimientosView::getSaldoRestante);
    }

    private static Renderer<Movimiento> tipoMovimientoYConceptoRenderer() {
        return LitRenderer.<Movimiento> of(
            "<vaadin-vertical-layout>"
                + "  <span style=\"font-weight: bold;\">${item.titulo}</span>"
                + "  <span>${item.tipo}</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("titulo", MovimientosView::getTituloMovimiento)
            .withProperty("tipo", MovimientosView::getTipoMovimiento);
            
    }

    private static LocalDateTime getFechaPickerValue(DatePicker datePicker) {
        return datePicker.getValue().atStartOfDay();
    }

    private static void applyFilterIngresoGasto(GridListDataView<Movimiento> dataView, Select<String> ingresoGastoFilter) {
        String value = ingresoGastoFilter.getValue();
        dataView.removeFilters();
        if(value.equals("Ingresos")) {
            dataView.addFilter(movimiento -> movimiento.getImporte() > 0);
        }
        else if(value.equals("Gastos")) {
            dataView.addFilter(movimiento -> movimiento.getImporte() < 0);
        }
    }

    private static ComponentRenderer<MovimientoDetailsFormLayout, Movimiento> createMovimientoDetailsRenderer() {
        return new ComponentRenderer<>(MovimientoDetailsFormLayout::new, MovimientoDetailsFormLayout::setMovimiento);
    }

    private static class MovimientoDetailsFormLayout extends FormLayout {
        private final TextField fechaTextField = new TextField("Fecha");
        private final TextField importeTextField = new TextField("Importe");
        private final TextField tipoTextField = new TextField("Movimiento");
        private final TextField conceptoTextField = new TextField("Concepto");

        public MovimientoDetailsFormLayout() {
            Stream.of(fechaTextField, importeTextField, tipoTextField, conceptoTextField)
                .forEach(textField -> {
                    textField.setReadOnly(true);
                    add(textField);
                });
            
            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(conceptoTextField, 3);
        }

        public void setMovimiento(Movimiento movimiento) {
            fechaTextField.setValue(movimiento.getFecha().format(hourFormatter));
            importeTextField.setValue(getFormattedMovimientoImporteDecimales(movimiento) + " €");
            tipoTextField.setValue(getTipoMovimiento(movimiento));
            conceptoTextField.setValue(getConceptoMovimiento(movimiento));

            setHeight("auto");
        }
    }

    private List<Movimiento> generateDatosPrueba(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Movimiento> dataExample = new ArrayList<Movimiento>();

        for (int i = 0; i < 50; i++) {
            // generate random localdatetime between fechaInicio and fechaFin
            LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(fechaInicio.toInstant(ZoneOffset.UTC).toEpochMilli(), fechaFin.toInstant(ZoneOffset.UTC).toEpochMilli())), ZoneId.systemDefault());
            // generate random float between -100 and 100
            float importe = (float) (Math.random() * 200 - 100);
            if(importe == 0) {
                importe = 1;
            }
            
            String establecimiento = "Paypal, Discord, RiotGames, Amazon, Netflix, Spotify, Apple, Google, Microsoft, Ubisoft, EpicGames, EA, Steam, Origin, Blizzard, Nintendo, Sony, Playstation, Xbox, NintendoSwitch, UbisoftConnect, EpicGamesStore, SteamStore, OriginStore, BlizzardStore, NintendoStore, SonyStore, PlaystationStore, XboxStore, NintendoSwitchStore".split(", ")[(int) (Math.random() * 30)];
            String informacion = "Informacion " + i;

            String cuentaOrigen = "ES";
            for (int j = 0; j < 22; j++) {
                cuentaOrigen += (int) (Math.random() * 10);
            }

            String cuentaDestino = "ES";
            for (int j = 0; j < 22; j++) {
                cuentaDestino += (int) (Math.random() * 10);
            }

            String conceptoRecibo = "Universidad de Cadiz, Adeudo Vodafone, Adeudo Movistar, Adeudo Orange, Adeudo Jazztel, Adeudo Iberdrola, Adeudo Endesa, Adeudo Gas Natural, Adeudo Telepizza, Adeudo Domino's Pizza, Adeudo Pizza Hut, Adeudo McDonald's, Adeudo Burger King, Adeudo KFC, Adeudo Subway, Adeudo Starbucks, Adeudo Dunkin' Donuts, Adeudo Costa Coffee, Adeudo Coca Cola, Adeudo Pepsi, Adeudo Heineken, Adeudo Carrefour, Adeudo Mercadona, Adeudo Lidl, Adeudo Alcampo, Adeudo Decathlon, Adeudo Zara, Adeudo H&M, Adeudo Primark, Adeudo Nike, Adeudo Adidas".split(", ")[(int) (Math.random() * 30)];
            String conceptoTransferencia = "Ropa invierno, PC Gamer, Coche, Viaje, Comida, Bebida, Cine, Pelicula, Series, Musica, Libro, Videojuego, Ordenador, Movil, Cables, Pantalla, Teclado, Raton, Auriculares, Disco Duro, Memoria, Tarjeta Grafica, Tarjeta Sonido, Tarjeta Red, Tarjeta Wifi, Tarjeta Bluetooth, Tarjeta USB, Tarjeta SD, Tarjeta MicroSD, Tarjeta MicroSDHC, Tarjeta MicroSDXC, Tarjeta CompactFlash".split(", ")[(int) (Math.random() * 30)];
            String beneficiario = "John, Maria, Peter, Susan, Michael, Sarah, David, Karen, Richard, Lisa, James, Emma, Robert, Helen, William, Jennifer, Thomas, Susan, Charles, Margaret, Christopher, Dorothy, Daniel, Linda, Matthew, Elizabeth, Anthony, Barbara".split(", ")[(int) (Math.random() * 28)];

            float balanceRestante = (float) (Math.random() * 200 - 100);
            if(balanceRestante < 0) {
                balanceRestante = balanceRestante * -1;
            }

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
                    dataExample.add(new Traspaso(importe, fecha, balanceRestante, cuentaOrigen, cuentaDestino, conceptoTransferencia));
                    break;
            }
            
        }

        return dataExample;
    }
    
}
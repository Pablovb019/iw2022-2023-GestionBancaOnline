package es.uca.iw.biwan.views.movimientos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.MovimientoService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.TransaccionBancaria;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

@PageTitle("Movimientos")
@Route("movimientos")
@CssImport("./themes/biwan/movimientos.css")
public class MovimientosView extends VerticalLayout {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaService cuentaService;

    public MovimientosView() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null || session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
            if (session.getAttribute(Gestor.class) != null || session.getAttribute(EncargadoComunicaciones.class) != null || session.getAttribute(Administrador.class) != null) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Aceptar", event -> {
                    if (session.getAttribute(Gestor.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-gestor");
                    } else if (session.getAttribute(EncargadoComunicaciones.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-encargado");
                    } else if (session.getAttribute(Administrador.class) != null) {
                        UI.getCurrent().navigate("pagina-principal-admin");
                    }
                });
                error.open();
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    private Component VisualizadorMovimientos() {
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

        VerticalLayout filterPrintLayout = new VerticalLayout();
        filterPrintLayout.setAlignItems(Alignment.END);

        Cliente cliente = VaadinSession.getCurrent().getAttribute(Cliente.class);
        ArrayList<Cuenta> cuentas = cuentaService.findCuentaByCliente(cliente);

        ArrayList<Movimiento> moves = new ArrayList<>();
        ArrayList<Transferencia> transferencias = new ArrayList<>();
        ArrayList<Traspaso> traspasos = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            moves.addAll(movimientoService.findAllMovimientos(cuenta));
            transferencias.addAll(movimientoService.findAllTransferencias(cuenta));
            traspasos.addAll(movimientoService.findAllTraspasos(cuenta));
        }

        ArrayList<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(moves);
        movimientos.addAll(transferencias);
        movimientos.addAll(traspasos);

        Collections.shuffle(movimientos);

        if (movimientos.size() == 0) {
            ConfirmDialog errorMovimientos = new ConfirmDialog("Error", "No hay ningún movimiento", "Volver", event -> {
                UI.getCurrent().navigate("pagina-principal-cliente");
            });
            errorMovimientos.open();
        } else {
            Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
            grid.setItems(movimientos);
            grid.addColumn(tipoMovimientoYConceptoRenderer()).setHeader("Movimiento");
            grid.addColumn(importeYSaldoRenderer()).setHeader("Importe");
            grid.setItemDetailsRenderer(createMovimientoDetailsRenderer());

            GridListDataView<Movimiento> dataView = grid.setItems(movimientos);
            grid.setWidth("100%");
            grid.getStyle().set("align-self", "center");
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
            grid.setAllRowsVisible(true);

            Button imprimir = new Button("Imprimir");
            imprimir.addClickListener(event -> {
                UI.getCurrent().getPage().executeJs("window.print();");
            });

            imprimir.getStyle().set("align-self", "start");
            imprimir.getStyle().set("margin-top", "35px");
            imprimir.getStyle().set("width", "100px");

            Select<String> ingresosGastosFilter = new Select<>();
            ingresosGastosFilter.setLabel("Filtrar por");
            ingresosGastosFilter.setItems("Todos", "Ingresos", "Gastos", "Transferencias", "Traspasos");
            ingresosGastosFilter.setValue("Todos");

            ingresosGastosFilter.addValueChangeListener(e -> {
                applyFilterIngresoGasto(dataView, ingresosGastosFilter);
            });

            ingresosGastosFilter.getStyle().set("align-self", "end");
            ingresosGastosFilter.getStyle().set("margin-right", "35px");

            HorizontalLayout filterPrint = new HorizontalLayout();
            filterPrint.add(ingresosGastosFilter, imprimir);
            filterPrintLayout.add(filterPrint);

            tableLayout.add(filterPrintLayout, grid);
        }

        return tableLayout;
    }

    private static Renderer<Movimiento> importeYSaldoRenderer() {
        return LitRenderer.<Movimiento>of(
                        "<vaadin-vertical-layout>"
                                + "  <span style=\"color:${item.color}\">${item.importe} €</span>"
                                + "</vaadin-vertical-layout>")
                .withProperty("importe", MovimientosView::getFormattedMovimientoImporteDecimales)
                .withProperty("color", MovimientosView::getFormattedMovimientoImporteColor);
    }

    private static Renderer<Movimiento> tipoMovimientoYConceptoRenderer() {
        return LitRenderer.<Movimiento>of(
                        "<vaadin-vertical-layout>"
                                + "  <span style=\"font-weight: bold;\">${item.titulo}</span>"
                                + "  <span>${item.tipo}</span>"
                                + "</vaadin-vertical-layout>")
                .withProperty("titulo", MovimientosView::getConceptoMovimiento)
                .withProperty("tipo", MovimientosView::getTipoMovimiento);

    }

    // decimal format
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private static String getFormattedMovimientoImporteColor(Movimiento movimiento) {
        if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.DEPOSIT.toString())) {
            return "green";
        } else if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.WITHDRAWAL.toString())) {
            return "red";
        } else {
            return "black";
        }
    }

    private static String getFormattedMovimientoImporteDecimales(Movimiento movimiento) {
        BigDecimal importe = movimiento.getValue();
        return decimalFormat.format(importe);
    }

    private static String getConceptoMovimiento(Movimiento movimiento) {
        if (movimiento.getConcept().startsWith("Abono de nómina")) {
            return "Abono de nómina";
        }
        if (movimiento.getConcept().startsWith("Recepción de Transferencia")) {
            return "Recepción de Transferencia";
        }

        if (movimiento.getConcept().startsWith("Recepción de Bizum")) {
            return "Recepción de Bizum";
        }

        if (movimiento.getConcept().startsWith("Envío de Bizum")) {
            return "Envío de Bizum";
        }

        if (movimiento.getConcept().startsWith("Recibo de")) {
            return "Recibo";
        }

        if (movimiento.getConcept().startsWith("Transferencia")) {
            return "Envío de Transferencia";
        }
        if (movimiento.getConcept().startsWith("Traspaso")) {
            return "Envío de Traspaso";
        }

        return "Movimiento desconocido";
    }

    private static String getTipoMovimiento(Movimiento movimiento) {
        if (movimiento.getTransactionType().equals(TransaccionBancaria.DEPOSIT.toString())) {
            return "Ingreso";
        }

        if (movimiento.getTransactionType().equals(TransaccionBancaria.WITHDRAWAL.toString())) {
            return "Gasto";
        }

        if (movimiento.getTransactionType().equals(TransaccionBancaria.TRANSFERENCIA.toString())) {
            return "Transferencia";
        }

        if (movimiento.getTransactionType().equals(TransaccionBancaria.TRASPASO.toString())) {
            return "Traspaso";
        }

        return "Desconocido";
    }

    private static void applyFilterIngresoGasto(GridListDataView<Movimiento> dataView, Select<String> ingresoGastoFilter) {
        String value = ingresoGastoFilter.getValue();
        dataView.removeFilters();
        switch (value) {
            case "Ingresos":
                dataView.addFilter(movimiento -> Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.DEPOSIT.toString()));
                break;
            case "Gastos":
                dataView.addFilter(movimiento -> Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.WITHDRAWAL.toString()));
                break;
            case "Transferencias":
                dataView.addFilter(movimiento -> Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.TRANSFERENCIA.toString()));
                break;
            case "Traspasos":
                dataView.addFilter(movimiento -> Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.TRASPASO.toString()));
                break;
        }
    }

    private static ComponentRenderer<MovimientoDetailsFormLayout, Movimiento> createMovimientoDetailsRenderer() {
        return new ComponentRenderer<>(MovimientoDetailsFormLayout::new, MovimientoDetailsFormLayout::setMovimiento);
    }

    private static class MovimientoDetailsFormLayout extends FormLayout {
        private final TextField importeTextField = new TextField("Importe");
        private final TextField tipoTextField = new TextField("Movimiento");
        private final TextField conceptoTextField = new TextField("Concepto");

        private final TextField cuentaOrigenTextField = new TextField("Cuenta Origen");

        private final TextField cuentaDestinoTextField = new TextField("Cuenta Destino");

        public MovimientoDetailsFormLayout() {
            Stream.of(importeTextField, tipoTextField, conceptoTextField, cuentaOrigenTextField, cuentaDestinoTextField)
                    .forEach(textField -> {
                        textField.setReadOnly(true);
                        add(textField);
                    });

            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(conceptoTextField, 3);
        }

        public void setMovimiento(Movimiento movimiento) {
            importeTextField.setValue(getFormattedMovimientoImporteDecimales(movimiento) + " €");
            tipoTextField.setValue(getTipoMovimiento(movimiento));
            conceptoTextField.setValue(movimiento.getConcept());

            if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.TRANSFERENCIA.toString())) {
                cuentaOrigenTextField.setValue(movimiento.getCuentaIBAN());
                Transferencia transferencia = (Transferencia) movimiento;
                cuentaDestinoTextField.setValue(transferencia.getIbanDestino());
            } else if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.TRASPASO.toString())) {
                cuentaOrigenTextField.setValue(movimiento.getCuentaIBAN());
                Traspaso traspaso = (Traspaso) movimiento;
                cuentaDestinoTextField.setValue(traspaso.getIbanDestino());
            } else if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.DEPOSIT.toString())) {
                cuentaOrigenTextField.setValue("");
                cuentaDestinoTextField.setValue(movimiento.getCuentaIBAN());
            } else if (Objects.equals(movimiento.getTransactionType(), TransaccionBancaria.WITHDRAWAL.toString())) {
                cuentaOrigenTextField.setValue(movimiento.getCuentaIBAN());
                cuentaDestinoTextField.setValue("");
            } else {
                cuentaOrigenTextField.setValue("");
                cuentaDestinoTextField.setValue("");
            }

            importeTextField.getStyle().set("color", getFormattedMovimientoImporteColor(movimiento));
            setHeight("auto");
        }
    }

    @PostConstruct
    public void init() {
        try {
            add(VisualizadorMovimientos());
            add(FooterView.Footer());
        } catch (Exception ignored) {
        }
    }
}
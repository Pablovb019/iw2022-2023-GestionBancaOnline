package es.uca.iw.biwan.views.pagosTarjeta;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import es.uca.iw.biwan.aplication.service.PagoTarjetaService;
import es.uca.iw.biwan.domain.operaciones.*;
import es.uca.iw.biwan.domain.usuarios.Cliente;
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

@PageTitle("Pagos Con Tarjeta")
@Route("pagos-tarjeta")
@CssImport("./themes/biwan/movimientos.css")
public class PagosTarjetaView extends VerticalLayout {

    @Autowired
    private PagoTarjetaService pagoTarjetaService;

    public PagosTarjetaView() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session.getAttribute(Cliente.class) != null) {
            if (!session.getAttribute(Cliente.class).getRol().contentEquals("CLIENTE")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Volver", event -> {
                    UI.getCurrent().navigate("");
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

    private Component VisualizadorPagosTarjeta() {
        setSizeFull();

        add(HeaderUsuarioLogueadoView.Header());

        H1 titleLayout = new H1("Pagos Con Tarjeta");
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

        ArrayList<PagoTarjeta> pagosTarjeta = pagoTarjetaService.findPagosTarjeta();
        Collections.shuffle(pagosTarjeta);

        if (pagosTarjeta.size() == 0) {
            ConfirmDialog errorPagoTarjeta = new ConfirmDialog("Error", "No hay ningún pago realizado con tarjeta", "Volver", event -> {
                UI.getCurrent().navigate("pagina-principal-cliente");
            });
            errorPagoTarjeta.open();
        } else {
            Grid<PagoTarjeta> grid = new Grid<>(PagoTarjeta.class, false);
            grid.setItems(pagosTarjeta);
            grid.addColumn(tipoMovimientoYConceptoRenderer()).setHeader("Pago");
            grid.addColumn(importeYSaldoRenderer()).setHeader("Importe");
            grid.setItemDetailsRenderer(createPagoTarjetaDetailsRenderer());

            GridListDataView<PagoTarjeta> dataView = grid.setItems(pagosTarjeta);
            grid.setWidth("100%");
            grid.getStyle().set("align-self", "center");
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

            Select<String> ingresosGastosFilter = new Select<>();
            ingresosGastosFilter.setLabel("Filtrar por");
            ingresosGastosFilter.setItems("Todos", "Online", "Offline");
            ingresosGastosFilter.setValue("Todos");

            ingresosGastosFilter.addValueChangeListener(e -> {
                applyFilterIngresoGasto(dataView, ingresosGastosFilter);
            });

            ingresosGastosFilter.getStyle().set("align-self", "end");
            ingresosGastosFilter.getStyle().set("margin-right", "35px");

            filterTypeTableLayout.add(ingresosGastosFilter);
            filterTableLayout.add(filterTypeTableLayout);

            tableLayout.add(filterTableLayout, grid);
        }

        return tableLayout;
    }

    private static Renderer<PagoTarjeta> importeYSaldoRenderer() {
        return LitRenderer.<PagoTarjeta>of(
                        "<vaadin-vertical-layout>"
                                + "  <span style=\"color:${item.color}\">${item.importe} €</span>"
                                + "</vaadin-vertical-layout>")
                .withProperty("importe", PagosTarjetaView::getFormattedPagoTarjetaImporteDecimales)
                .withProperty("color", PagosTarjetaView::getFormattedPagoTarjetaImporteColor);
    }

    private static Renderer<PagoTarjeta> tipoMovimientoYConceptoRenderer() {
        return LitRenderer.<PagoTarjeta>of(
                        "<vaadin-vertical-layout>"
                                + "  <span style=\"font-weight: bold;\">${item.titulo}</span>"
                                + "  <span>${item.tipo}</span>"
                                + "</vaadin-vertical-layout>")
                .withProperty("titulo", PagosTarjetaView::getTiendaPagoTarjeta)
                .withProperty("tipo", PagosTarjetaView::getTipoPagoTarjeta);

    }

    // decimal format
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private static String getFormattedPagoTarjetaImporteColor(PagoTarjeta pagoTarjeta) {
        return "black";
    }

    private static String getFormattedPagoTarjetaImporteDecimales(PagoTarjeta pagoTarjeta) {
        BigDecimal importe = pagoTarjeta.getValue();
        return decimalFormat.format(importe);
    }

    private static String getTiendaPagoTarjeta(PagoTarjeta pagoTarjeta) {
        return pagoTarjeta.getShop();
    }

    private static String getTipoPagoTarjeta(PagoTarjeta pagoTarjeta) {
        if (pagoTarjeta.getType().equals("ONLINE")) {
            return "Online";
        }

        if (pagoTarjeta.getType().equals("OFFLINE")) {
            return "Offline";
        }

        return "Desconocido";
    }

    private static void applyFilterIngresoGasto(GridListDataView<PagoTarjeta> dataView, Select<String> ingresoGastoFilter) {
        String value = ingresoGastoFilter.getValue();
        dataView.removeFilters();
        switch (value) {
            case "Online":
                dataView.addFilter(pagoTarjeta -> Objects.equals(pagoTarjeta.getType(), "ONLINE"));
                break;
            case "Offline":
                dataView.addFilter(pagoTarjeta -> Objects.equals(pagoTarjeta.getType(), "OFFLINE"));
                break;
        }
    }

    private static ComponentRenderer<PagosTarjetaView.PagoTarjetaDetailsFormLayout, PagoTarjeta> createPagoTarjetaDetailsRenderer() {
        return new ComponentRenderer<>(PagosTarjetaView.PagoTarjetaDetailsFormLayout::new, PagosTarjetaView.PagoTarjetaDetailsFormLayout::setPagoTarjeta);
    }

    private static class PagoTarjetaDetailsFormLayout extends FormLayout {
        private final TextField importeTextField = new TextField("Importe");
        private final TextField estadoTextField = new TextField("Estado");
        private final TextField tipoTextField = new TextField("Tipo");

        private final TextField tiendaTextField = new TextField("Tienda");

        private final TextField tarjetaTextField = new TextField("Tarjeta");

        public PagoTarjetaDetailsFormLayout() {
            Stream.of(importeTextField, estadoTextField, tipoTextField, tiendaTextField, tarjetaTextField)
                    .forEach(textField -> {
                        textField.setReadOnly(true);
                        add(textField);
                    });

            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(estadoTextField, 2);
        }

        public void setPagoTarjeta(PagoTarjeta pagoTarjeta) {
            importeTextField.setValue(getFormattedPagoTarjetaImporteDecimales(pagoTarjeta) + " €");
            tipoTextField.setValue(getTipoPagoTarjeta(pagoTarjeta));
            estadoTextField.setValue(pagoTarjeta.getPaymentStatus());
            tiendaTextField.setValue(pagoTarjeta.getShop());
            tarjetaTextField.setValue(pagoTarjeta.getCardNumbers());
            importeTextField.getStyle().set("color", getFormattedPagoTarjetaImporteColor(pagoTarjeta));
            setHeight("auto");
        }
    }

    @PostConstruct
    public void init() {
        add(VisualizadorPagosTarjeta());
        add(FooterView.Footer());
    }
}

package es.uca.iw.biwan.views.recibosDomiciliados;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.operaciones.ReciboDomiciliado;
import es.uca.iw.biwan.domain.operaciones.Movimiento.BalanceRestanteInvalidoException;
import es.uca.iw.biwan.domain.operaciones.Movimiento.FechaInvalidaException;
import es.uca.iw.biwan.domain.operaciones.Movimiento.ImporteInvalidoException;
import es.uca.iw.biwan.domain.operaciones.ReciboDomiciliado.EmisorInvalidoException;
import es.uca.iw.biwan.domain.operaciones.ReciboDomiciliado.FechaVencimientoInvalidaException;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@PageTitle("Recibos domiciliados")
@Route("recibos-domiciliados")
public class RecibosDomiciliadosView extends VerticalLayout {

    public RecibosDomiciliadosView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("CLIENTE")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                setSizeFull();
                add(HeaderUsuarioLogueadoView.Header());

                H1 titleLayout = new H1("Recibos Domiciliados");
                titleLayout.getStyle().set("margin-top", "10px");
                titleLayout.getStyle().set("margin-bottom", "10px");
                titleLayout.getStyle().set("text-align", "center");
                titleLayout.setWidth("100%");

                add(titleLayout);

                Grid<ReciboDomiciliado> grid = new Grid<>(ReciboDomiciliado.class, false);
                grid.addColumn(ReciboDomiciliado::getEmisor).setHeader("Emisor");
                grid.addColumn(dateRenderer()).setHeader("Fecha").setComparator(ReciboDomiciliado::getFecha);
                grid.addColumn(ReciboDomiciliado::getConcepto).setHeader("Concepto");
                grid.setItemDetailsRenderer(createMovimientoDetailsRenderer());

                GridListDataView<ReciboDomiciliado> dataView = grid.setItems(obtenerRecibos(5));
                grid.setWidth("100%");
                grid.getStyle().set("align-self", "center");
                grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
                grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

                VerticalLayout tableLayout = new VerticalLayout();
                tableLayout.setSizeFull();
                tableLayout.setWidth("80%");
                tableLayout.getStyle().set("align-self", "center");

                tableLayout.add(grid);

                add(tableLayout);

                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM");
    private static DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm");
    private static DecimalFormat decimalformat = new DecimalFormat("0.00");

    private static Renderer<ReciboDomiciliado> dateRenderer() {
        return LitRenderer.<ReciboDomiciliado> of(
            "<vaadin-vertical-layout style=\"line-height: 60px;\">"
                + "  <span>${item.fecha}</span>"
                + "</vaadin-vertical-layout>")
            .withProperty("fecha", RecibosDomiciliadosView::getFormattedMovimientoDate);
    }

    private static String getFormattedMovimientoDate(ReciboDomiciliado movimiento) {
        LocalDateTime fecha = movimiento.getFecha();

        return fecha.format(dateFormatter);
    }

    private static ComponentRenderer<ReciboDetailsFormLayout, ReciboDomiciliado> createMovimientoDetailsRenderer() {
        return new ComponentRenderer<>(ReciboDetailsFormLayout::new, ReciboDetailsFormLayout::setRecibo);
    }

    private static class ReciboDetailsFormLayout extends FormLayout {
        private final TextField fechaTextField = new TextField("Fecha");
        private final TextField importeTextField = new TextField("Importe");
        private final TextField conceptoTextField = new TextField("Concepto");
        private final TextField balanceTextField = new TextField("Balance restante");

        public ReciboDetailsFormLayout() {
            Stream.of(fechaTextField, importeTextField, conceptoTextField, balanceTextField)
                .forEach(textField -> {
                    textField.setReadOnly(true);
                    add(textField);
                });
            
            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(conceptoTextField, 3);
        }

        public void setRecibo(ReciboDomiciliado movimiento) {
            fechaTextField.setValue(movimiento.getFecha().format(hourFormatter));
            importeTextField.setValue(getFormattedMovimientoImporteDecimales(movimiento) + " €");
            conceptoTextField.setValue(movimiento.getConcepto());
            balanceTextField.setValue(getFormattedMovimientoBalanceRestanteDecimales(movimiento) + " €");

            setHeight("auto");
        }
    }

    private static String getFormattedMovimientoImporteDecimales(ReciboDomiciliado movimiento) {
        double importe = movimiento.getImporte();

        return decimalformat.format(importe);
    }

    private static String getFormattedMovimientoBalanceRestanteDecimales(ReciboDomiciliado movimiento) {
        double importe = movimiento.getBalanceRestante();

        return decimalformat.format(importe);
    }

    private List<ReciboDomiciliado> obtenerRecibos(int numRecibos) {
        //return ReciboDomiciliado.findAll().list();
        List<ReciboDomiciliado> recibos = new ArrayList<>();
        for(int i = 0; i < numRecibos; i++) {

            // Genrate random importe not equals to 0
            float importe = 0;
            while(importe == 0) {
                importe = (float) (Math.random() * 1000);
            }

            // Generate random concepto between Adeudo vodafone, adeudo gas, adeudo luz, adeudo agua, adeudo internet
            String concepto = "";
            int conceptoNum = (int) (Math.random() * 5);
            switch(conceptoNum) {
                case 0:
                    concepto = "Adeudo vodafone";
                    break;
                case 1:
                    concepto = "Adeudo gas";
                    break;
                case 2:
                    concepto = "Adeudo luz";
                    break;
                case 3:
                    concepto = "Adeudo agua";
                    break;
                case 4:
                    concepto = "Adeudo internet";
                    break;
            }

            // Generate random fecha between 1 month ago and now
            LocalDateTime fecha = LocalDateTime.now().minusMonths((int) (Math.random() * 2)).minusDays((int) (Math.random() * 30));

            // Generate random emisor between Vodafone, Gas Natural, Iberdrola, Aguas de Alicante, Movistar
            String emisor = "";
            int emisorNum = (int) (Math.random() * 5);
            switch(emisorNum) {
                case 0:
                    emisor = "Vodafone";
                    break;
                case 1:
                    emisor = "Gas Natural";
                    break;
                case 2:
                    emisor = "Iberdrola";
                    break;
                case 3:
                    emisor = "Aguas de Alicante";
                    break;
                case 4:
                    emisor = "Movistar";
                    break;
            }

            //Generate random balance greater to 0
            float balance = 0;
            while(balance == 0) {
                balance = (float) (Math.random() * 1000);
            }

            // Generate random fechaVencimiento between 1 month ago and now
            LocalDateTime fechaVencimiento = LocalDateTime.now().minusMonths((int) (Math.random() * 2)).minusDays((int) (Math.random() * 30));

            try {
                recibos.add(new ReciboDomiciliado(importe, fecha, balance, fechaVencimiento, emisor, concepto));
            } catch (ImporteInvalidoException | FechaInvalidaException | BalanceRestanteInvalidoException
                    | FechaVencimientoInvalidaException | EmisorInvalidoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Delete recibos with same emisor
        List<ReciboDomiciliado> recibosSinDuplicados = new ArrayList<>();
        for(ReciboDomiciliado recibo : recibos) {
            boolean existe = false;
            for(ReciboDomiciliado reciboSinDuplicados : recibosSinDuplicados) {
                if(reciboSinDuplicados.getEmisor().equals(recibo.getEmisor())) {
                    existe = true;
                    break;
                }
            }
            if(!existe) {
                recibosSinDuplicados.add(recibo);
            }
        }

        return recibosSinDuplicados;
    }


}

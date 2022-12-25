package es.uca.iw.biwan.views.transferenciasTraspasos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.domain.usuarios.Usuario;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.TransferenciaService;
import es.uca.iw.biwan.aplication.service.TraspasoService;

@PageTitle("Transferencias y Traspasos")
@Route("transferencias-traspasos")
@CssImport("./themes/biwan/transferenciasTraspasos.css")
public class TransferenciasTraspasosView extends VerticalLayout {

    public TransferenciasTraspasosView() {
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("CLIENTE")) {
                ConfirmDialog error = new ConfirmDialog("Error", "No eres un cliente", "Volver", event -> {
                    UI.getCurrent().navigate("");
                });
                error.open();
            } else {
                setHeight("100%");

                add(HeaderUsuarioLogueadoView.Header());

                VerticalLayout layout = new VerticalLayout();
                layout.setWidth("80%");
                layout.setHeight("100%");
                layout.getStyle().set("align-self", "center");

                HorizontalLayout tipoMovimiento = new HorizontalLayout();

                Button transferencia = new Button("A otra cuenta");
                Button traspaso = new Button("A una de mis cuentas");
                transferencia.addClassName("BotonesTransferenciaTraspaso");
                traspaso.addClassName("BotonesTransferenciaTraspaso");

                VerticalLayout formulario = new VerticalLayout();
                formulario.getStyle().set("align-self", "center");

                transferencia.addClickListener(e -> {
                    TransferenciaForm form = new TransferenciaForm();
                    formulario.removeAll();
                    formulario.add(form);
                });

                traspaso.addClickListener(e -> {
                    TraspasoForm form = new TraspasoForm();
                    formulario.removeAll();
                    formulario.add(form);
                });


                tipoMovimiento.add(transferencia, traspaso);

                layout.add(tipoMovimiento, formulario);

                add(layout);

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

    private static class TransferenciaForm extends FormLayout {

        private TransferenciaService transferenciaService;
        private CuentaService cuentaService;

        private final ComboBox<Cuenta> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final TextField cuentaDestino = new TextField("Cuenta Destino");
        private final TextField beneficiario = new TextField("Beneficiario");
        private final TextField concepto = new TextField("Concepto");
        private final NumberField importe = new NumberField("Importe");

        Button realizarTransferencia = new Button("Enviar");

        public TransferenciaForm() {
            getStyle().set("align-self", "center");

            //TODO: Obtener cuentas del usuario logueado
            cuentaOrigen.setItems(cuentaService.findAll());
            cuentaOrigen.setItemLabelGenerator(Cuenta::getIBAN);
            
            importe.setValue(0.0);

            realizarTransferencia.addClickListener(e -> {

                try {
                    float importeTransferencia = importe.getValue().floatValue();

                    float balanceActual = cuentaOrigen.getValue().getBalance();

                    if (importeTransferencia > balanceActual) {
                        throw new Movimiento.ImporteInvalidoException("El importe de la transferencia es superior al balance de la cuenta");
                    }

                    if(importeTransferencia <= 0) {
                        throw new Movimiento.ImporteInvalidoException("El importe de la transferencia debe ser mayor que 0");
                    }

                    float nuevoBalance = balanceActual - importeTransferencia;

                    Transferencia transferencia = new Transferencia(
                        importeTransferencia,
                        LocalDateTime.now(),
                        nuevoBalance,
                        cuentaOrigen.getValue().getIBAN(),
                        cuentaDestino.getValue(),
                        beneficiario.getValue(),
                        concepto.getValue()
                    );

                    //Save transferencia in database and update balance
                    this.transferenciaService.save(transferencia);
                    this.cuentaService.updateBalance(cuentaOrigen.getValue(), nuevoBalance);

                }
                catch (Exception ex) {

                    if (ex instanceof Movimiento.ImporteInvalidoException) {
                        importe.setErrorMessage(ex.getMessage());
                        importe.setInvalid(true);
                    }

                    if(ex instanceof NumberFormatException) {
                        importe.setErrorMessage("El importe debe ser un número");
                        importe.setInvalid(true);
                    }

                    if(ex instanceof Movimiento.FechaInvalidaException) {
                        Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE);
                    }

                    if(ex instanceof Transferencia.CuentaInvalidaException) {
                        cuentaOrigen.setErrorMessage(ex.getMessage());
                        cuentaOrigen.setInvalid(true);
                    }

                    if(ex instanceof Transferencia.BeneficiarioInvalidoException) {
                        beneficiario.setErrorMessage(ex.getMessage());
                        beneficiario.setInvalid(true);
                    }

                }
                
                
            });
            realizarTransferencia.addClassName("EnviarButtonOtraCuenta");
            add(cuentaOrigen, cuentaDestino, beneficiario, concepto, importe, realizarTransferencia);
            setColspan(realizarTransferencia, 3);
        }
        
    }

    private static class TraspasoForm extends FormLayout {

        private TraspasoService traspasoService;
        private CuentaService cuentaService;

        private final ComboBox<Cuenta> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final ComboBox<Cuenta> cuentaDestino = new ComboBox<>("Cuenta Destino");
        private final TextField concepto = new TextField("Concepto");
        private final NumberField importe = new NumberField("Importe");

        Button realizarTraspaso = new Button("Enviar");

        public TraspasoForm() {
            getStyle().set("align-self", "center");

            //TODO: Obtener cuentas del usuario logueado
            List<Cuenta> cuentasUsuario = cuentaService.findAll();

            cuentaOrigen.setItems(cuentasUsuario);
            cuentaOrigen.setItemLabelGenerator(Cuenta::getIBAN);

            cuentaDestino.setItems(cuentasUsuario);
            cuentaDestino.setItemLabelGenerator(Cuenta::getIBAN);

            importe.setValue(0.0);

            realizarTraspaso.addClickListener(e -> {

                try {
                    float importeTraspaso = importe.getValue().floatValue();

                    float balanceActual = cuentaOrigen.getValue().getBalance();

                    if (importeTraspaso > balanceActual) {
                        throw new Movimiento.ImporteInvalidoException("El importe de la transferencia es superior al balance de la cuenta");
                    }

                    if(importeTraspaso <= 0) {
                        throw new Movimiento.ImporteInvalidoException("El importe de la transferencia debe ser mayor que 0");
                    }

                    float nuevoBalance = balanceActual - importeTraspaso;

                    Traspaso traspaso = new Traspaso(
                        importeTraspaso,
                        LocalDateTime.now(),
                        nuevoBalance,
                        cuentaOrigen.getValue().getIBAN(),
                        cuentaDestino.getValue().getIBAN(),
                        concepto.getValue()
                    );

                    //Save traspaso in database and update balance
                    this.traspasoService.save(traspaso);
                    this.cuentaService.updateBalance(cuentaOrigen.getValue(), nuevoBalance);

                } catch (Exception ex) {

                    if (ex instanceof Movimiento.ImporteInvalidoException) {
                        importe.setErrorMessage(ex.getMessage());
                        importe.setInvalid(true);
                    }

                    if(ex instanceof NumberFormatException) {
                        importe.setErrorMessage("El importe debe ser un número");
                        importe.setInvalid(true);
                    }

                    if(ex instanceof Movimiento.FechaInvalidaException) {
                        Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE);
                    }

                    if(ex instanceof Traspaso.CuentaInvalidaException) {
                        cuentaOrigen.setErrorMessage(ex.getMessage());
                        cuentaOrigen.setInvalid(true);
                    }

                }
            });
            realizarTraspaso.addClassName("EnviarButtonMisCuentas");
            add(cuentaOrigen, cuentaDestino, concepto, importe, realizarTraspaso);
            setColspan(realizarTraspaso, 3);
        }
        
    }

}
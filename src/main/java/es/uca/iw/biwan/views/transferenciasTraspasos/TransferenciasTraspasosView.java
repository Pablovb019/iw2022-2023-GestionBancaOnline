package es.uca.iw.biwan.views.transferenciasTraspasos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.dependency.CssImport;
import org.json.JSONObject;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

@PageTitle("Transferencias y Traspasos")
@Route("transferencias-traspasos")
@CssImport("./themes/biwan/transferenciasTraspasos.css")
public class TransferenciasTraspasosView extends VerticalLayout {

    public TransferenciasTraspasosView() {
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

    private static class TransferenciaForm extends FormLayout {

        private final ComboBox<Cuenta> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final TextField cuentaDestino = new TextField("Cuenta Destino");
        private final TextField beneficiario = new TextField("Beneficiario");
        private final TextField concepto = new TextField("Concepto");
        private final NumberField importe = new NumberField("Importe");

        Button realizarTransferencia = new Button("Enviar");

        public TransferenciaForm() {
            getStyle().set("align-self", "center");

            //cuentaOrigen.setItems(Cuenta.findAll());
            cuentaOrigen.setItems(generateRandomCuentaDatosPrueba(3));
            cuentaOrigen.setItemLabelGenerator(Cuenta::getIBAN);
            
            importe.setValue(0.0);

            //TODO: Obtener balance de la cuenta seleccionada
            float balanceActual = 1000;

            realizarTransferencia.addClickListener(e -> {

                try {
                    float importeTransferencia = importe.getValue().floatValue();

                    if (importeTransferencia > balanceActual) {
                        JSONObject json = new JSONObject();
                        json.put("message", "El importe de la transferencia es superior al balance de la cuenta");
                        json.put("field", "importe");
                        throw new Exception(json.toString());
                    }

                    if(importeTransferencia <= 0) {
                        JSONObject json = new JSONObject();
                        json.put("message", "El importe de la transferencia debe ser mayor que 0");
                        json.put("field", "importe");
                        throw new Exception(json.toString());
                    }

                    Transferencia transferencia = new Transferencia(
                        importeTransferencia,
                        LocalDate.now(),
                        balanceActual - importeTransferencia,
                        cuentaOrigen.getValue().getIBAN(),
                        cuentaDestino.getValue(),
                        beneficiario.getValue(),
                        concepto.getValue()
                    );

                    //TODO: Save transferencia in database and update balance

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());

                    JSONObject json = new JSONObject(ex.getMessage());

                    if(json.has("field")){
                        switch (json.getString("field")) {
                            case "cuentaOrigen":
                                cuentaOrigen.setErrorMessage(json.getString("message"));
                                cuentaOrigen.setInvalid(true);
                                break;
                            case "cuentaDestino":
                                cuentaDestino.setErrorMessage(json.getString("message"));
                                cuentaDestino.setInvalid(true);
                                break;
                            case "beneficiario":
                                beneficiario.setErrorMessage(json.getString("message"));
                                beneficiario.setInvalid(true);
                                break;
                            case "concepto":
                                concepto.setErrorMessage(json.getString("message"));
                                concepto.setInvalid(true);
                                break;
                            case "importe":
                                importe.setErrorMessage(json.getString("message"));
                                importe.setInvalid(true);
                                break;
                        }
                    }
                }
                
            });
            realizarTransferencia.addClassName("EnviarButtonOtraCuenta");
            add(cuentaOrigen, cuentaDestino, beneficiario, concepto, importe, realizarTransferencia);
            setColspan(realizarTransferencia, 3);
        }
        
    }

    private static class TraspasoForm extends FormLayout {

        //private final TextField cuentaOrigen = new TextField("Cuenta Origen");
        private final ComboBox<Cuenta> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final ComboBox<Cuenta> cuentaDestino = new ComboBox<>("Cuenta Destino");
        private final TextField concepto = new TextField("Concepto");
        private final NumberField importe = new NumberField("Importe");

        Button realizarTraspaso = new Button("Enviar");

        public TraspasoForm() {
            getStyle().set("align-self", "center");

            List<Cuenta> cuentasUsuario = generateRandomCuentaDatosPrueba(3);

            //cuentaOrigen.setItems(Cuenta.findAll());
            cuentaOrigen.setItems(cuentasUsuario);
            cuentaOrigen.setItemLabelGenerator(Cuenta::getIBAN);

            //cuentaDestino.setItems(Cuenta.findAll());
            cuentaDestino.setItems(cuentasUsuario);
            cuentaDestino.setItemLabelGenerator(Cuenta::getIBAN);

            importe.setValue(0.0);

            //TODO: Obtener balance de la cuenta seleccionada
            float balanceActual = 1000;

            realizarTraspaso.addClickListener(e -> {

                try {
                    float importeTraspaso = importe.getValue().floatValue();

                    if (importeTraspaso > balanceActual) {
                        JSONObject json = new JSONObject();
                        json.put("message", "El importe del traspaso es superior al balance de la cuenta");
                        json.put("field", "importe");
                        throw new Exception(json.toString());
                    }

                    if(importeTraspaso <= 0) {
                        JSONObject json = new JSONObject();
                        json.put("message", "El importe del traspaso debe ser mayor que 0");
                        json.put("field", "importe");
                        throw new Exception(json.toString());
                    }

                    Traspaso traspaso = new Traspaso(
                        importeTraspaso,
                            LocalDate.now(),
                        balanceActual - importeTraspaso,
                        cuentaOrigen.getValue().getIBAN(),
                        cuentaDestino.getValue().getIBAN(),
                        concepto.getValue()
                    );

                    //TODO: Save traspaso in database and update balance

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());

                    JSONObject json = new JSONObject(ex.getMessage());

                    if(json.has("field")){
                        switch (json.getString("field")) {
                            case "cuentaOrigen":
                                cuentaOrigen.setErrorMessage(json.getString("message"));
                                cuentaOrigen.setInvalid(true);
                                break;
                            case "cuentaDestino":
                                cuentaDestino.setErrorMessage(json.getString("message"));
                                cuentaDestino.setInvalid(true);
                                break;
                            case "concepto":
                                concepto.setErrorMessage(json.getString("message"));
                                concepto.setInvalid(true);
                                break;
                            case "importe":
                                importe.setErrorMessage(json.getString("message"));
                                importe.setInvalid(true);
                                break;
                        }
                    }
                }
            });
            realizarTraspaso.addClassName("EnviarButtonMisCuentas");
            add(cuentaOrigen, cuentaDestino, concepto, importe, realizarTraspaso);
            setColspan(realizarTraspaso, 3);
        }
        
    }

    private static List<Cuenta> generateRandomCuentaDatosPrueba(int numeroCuentas) {

        List<Cuenta> cuentas = new ArrayList<Cuenta>();
        
        for (int i = 0; i < numeroCuentas; i++) {
            // Generate random IBAN bank account number 24 digits
            String IBAN = "ES";
            for (int j = 0; j < 22; j++) {
                IBAN += (int) (Math.random() * 10);
            }

            // cuentas.add(new Cuenta(1000, IBAN));
        }
        
        return cuentas;
    }

}
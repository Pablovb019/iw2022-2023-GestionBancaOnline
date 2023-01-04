package es.uca.iw.biwan.views.transferenciasTraspasos;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.MovimientoService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Estado;
import es.uca.iw.biwan.domain.operaciones.TransaccionBancaria;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import es.uca.iw.biwan.domain.usuarios.Administrador;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;
import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@PageTitle("Transferencias y Traspasos")
@Route("transferencias-traspasos")
@CssImport("./themes/biwan/transferenciasTraspasos.css")
public class TransferenciasTraspasosView extends VerticalLayout {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private UsuarioService usuarioService;

    public TransferenciasTraspasosView() {
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
            } else {
                add(FormsTransferenciaTraspaso());
                add(FooterView.Footer());
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", event -> {
                UI.getCurrent().navigate("/login");
            });
            error.open();
        }
    }

    public Component FormsTransferenciaTraspaso() {
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
            ArrayList<Cuenta> cuentas = cuentaService.findCuentaByCliente(VaadinSession.getCurrent().getAttribute(Cliente.class));
            if (cuentas.size() == 0) {
                ConfirmDialog errorUnaCuenta = new ConfirmDialog("Error", "No se puede realizar una transferencia. El usuario no tiene al menos 1 cuenta.", "Aceptar", event -> {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                });
                errorUnaCuenta.open();
            } else {
                TransferenciaForm form = new TransferenciaForm();
                formulario.removeAll();
                formulario.add(form);
            }
        });

        traspaso.addClickListener(e -> {
            ArrayList<Cuenta> cuentas = cuentaService.findCuentaByCliente(VaadinSession.getCurrent().getAttribute(Cliente.class));
            if (cuentas.size() < 2) {
                ConfirmDialog errorUnaCuenta = new ConfirmDialog("Error", "No se puede realizar un traspaso. El usuario no tiene al menos 2 cuentas.", "Aceptar", event -> {
                    UI.getCurrent().navigate("pagina-principal-cliente");
                });
                errorUnaCuenta.open();
            } else {
                TraspasoForm form = new TraspasoForm();
                formulario.removeAll();
                formulario.add(form);
            }
        });


        tipoMovimiento.add(transferencia, traspaso);
        layout.add(tipoMovimiento, formulario);

        return layout;
    }

    private class TransferenciaForm extends FormLayout {
        private final ComboBox<String> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final TextField cuentaDestino = new TextField("Cuenta Destino");
        private final TextField concepto = new TextField("Concepto");
        private final BigDecimalField importe = new BigDecimalField("Importe");
        IBANValidator ibanValidator = new IBANValidator();

        Button realizarTransferencia = new Button("Enviar");
        Binder<Transferencia> binder = new Binder<>(Transferencia.class);

        public TransferenciaForm() {
            getStyle().set("align-self", "center");
            importe.setValue(BigDecimal.ZERO);
            realizarTransferencia.addClassName("EnviarButtonOtraCuenta");
            cuentaOrigen.setRequired(true);
            cuentaDestino.setRequired(true);
            concepto.setRequired(true);
            importe.setRequiredIndicatorVisible(true);

            add(cuentaOrigen, cuentaDestino, concepto, importe, realizarTransferencia);
            setColspan(realizarTransferencia, 3);

            ArrayList<Cuenta> cuentasCliente = cuentaService.findCuentaByCliente(VaadinSession.getCurrent().getAttribute(Cliente.class));
            ArrayList<String> IBANCliente = cuentasCliente.stream().map(Cuenta::getIBAN).collect(Collectors.toCollection(ArrayList::new));
            cuentaOrigen.setItems(IBANCliente);

            binder.forField(cuentaOrigen).asRequired("La cuenta origen es obligatoria").bind(Transferencia::getIban, Transferencia::setIban);

            binder.forField(cuentaDestino).asRequired("La cuenta destino es obligatoria").withValidator(ibanValidator::isValid, "El IBAN no es válido").bind(Transferencia::getIbanDestino, Transferencia::setIbanDestino);

            binder.forField(concepto).asRequired("El concepto es obligatorio").bind(Transferencia::getConcept, Transferencia::setConcept);

            binder.forField(importe).asRequired("El importe es obligatorio").withValidator(importe -> importe.compareTo(BigDecimal.ZERO) > 0, "El importe debe ser mayor que 0").bind(Transferencia::getValue, Transferencia::setValue);

            realizarTransferencia.addClickShortcut(Key.ENTER);
            realizarTransferencia.addClickListener(e -> {
                if (binder.validate().isOk()) {
                    if (binder.validate().isOk()) {
                        Transferencia newTransferencia = new Transferencia();
                        newTransferencia.setId(UUID.randomUUID());
                        newTransferencia.setIban(cuentaOrigen.getValue());
                        newTransferencia.setIbanDestino(cuentaDestino.getValue());
                        newTransferencia.setConcept(concepto.getValue());
                        newTransferencia.setValue(importe.getValue().setScale(2, RoundingMode.HALF_UP));
                        newTransferencia.setIssuer("Transferencia BIWAN IW 2023");
                        newTransferencia.setTransactionType(TransaccionBancaria.TRANSFERENCIA.toString());
                        Cuenta cuentaOrigen = cuentaService.findCuentaByIban(newTransferencia.getIban());
                        Cuenta cuentaDestino = cuentaService.findCuentaByIban(newTransferencia.getIbanDestino());

                        if (cuentaOrigen.getBalance() < newTransferencia.getValue().doubleValue()) {
                            Notification errorSaldo = new Notification("No tienes suficiente saldo en la cuenta seleccionada", 3000);
                            errorSaldo.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            errorSaldo.open();

                        } else {
                            if (cuentaDestino != null && Objects.equals(usuarioService.findClienteByCuenta(cuentaOrigen).getUUID(), usuarioService.findClienteByCuenta(cuentaDestino).getUUID())) {
                                Notification errorPropietario = new Notification("En una transferencia, las cuentas origen y destino no pueden ser del mismo cliente", 3000);
                                errorPropietario.addThemeVariants(NotificationVariant.LUMO_ERROR);
                                errorPropietario.open();
                            } else {
                                newTransferencia.setTransactionStatus(Estado.ACCEPTED.toString());
                                movimientoService.saveTransferencia(newTransferencia, cuentaOrigen);

                                double nuevoSaldoOrigen = cuentaOrigen.getBalance() - newTransferencia.getValue().doubleValue();
                                cuentaOrigen.setBalance(nuevoSaldoOrigen);
                                cuentaService.updateBalance(cuentaOrigen);

                                if (cuentaDestino != null) {
                                    double nuevoSaldoDestino = cuentaDestino.getBalance() + newTransferencia.getValue().doubleValue();
                                    cuentaDestino.setBalance(nuevoSaldoDestino);
                                    cuentaService.updateBalance(cuentaDestino);
                                }

                                ConfirmDialog confirmacion = new ConfirmDialog("Transferencia realizada", "La transferencia se ha realizado correctamente", "Volver", event1 -> {
                                    UI.getCurrent().navigate("pagina-principal-cliente");
                                });
                                confirmacion.open();
                            }
                        }
                    }
                }
            });
        }
    }

    private class TraspasoForm extends FormLayout {

        private final ComboBox<String> cuentaOrigen = new ComboBox<>("Cuenta Origen");
        private final ComboBox<String> cuentaDestino = new ComboBox<>("Cuenta Destino");
        private final TextField concepto = new TextField("Concepto");
        private final BigDecimalField importe = new BigDecimalField("Importe");
        IBANValidator ibanValidator = new IBANValidator();

        Button realizarTransferencia = new Button("Enviar");
        Binder<Transferencia> binder = new Binder<>(Transferencia.class);

        public TraspasoForm() {
            getStyle().set("align-self", "center");
            importe.setValue(BigDecimal.ZERO);
            realizarTransferencia.addClassName("EnviarButtonOtraCuenta");
            cuentaOrigen.setRequired(true);
            cuentaDestino.setRequired(true);
            concepto.setRequired(true);
            importe.setRequiredIndicatorVisible(true);

            add(cuentaOrigen, cuentaDestino, concepto, importe, realizarTransferencia);
            setColspan(realizarTransferencia, 3);

            ArrayList<Cuenta> cuentasCliente = cuentaService.findCuentaByCliente(VaadinSession.getCurrent().getAttribute(Cliente.class));
            ArrayList<String> IBANCliente = cuentasCliente.stream().map(Cuenta::getIBAN).collect(Collectors.toCollection(ArrayList::new));
            cuentaOrigen.setItems(IBANCliente);
            cuentaDestino.setItems(IBANCliente);

            binder.forField(cuentaOrigen).asRequired("La cuenta origen es obligatoria").bind(Transferencia::getIban, Transferencia::setIban);

            binder.forField(cuentaDestino).asRequired("La cuenta destino es obligatoria").bind(Transferencia::getIbanDestino, Transferencia::setIbanDestino);

            binder.forField(concepto).asRequired("El concepto es obligatorio").bind(Transferencia::getConcept, Transferencia::setConcept);

            binder.forField(importe).asRequired("El importe es obligatorio").withValidator(importe -> importe.compareTo(BigDecimal.ZERO) > 0, "El importe debe ser mayor que 0").bind(Transferencia::getValue, Transferencia::setValue);

            realizarTransferencia.addClickShortcut(Key.ENTER);
            realizarTransferencia.addClickListener(e -> {
                if (binder.validate().isOk()) {
                    if (binder.validate().isOk()) {
                        Traspaso newTraspaso = new Traspaso();
                        newTraspaso.setId(UUID.randomUUID());
                        newTraspaso.setIban(cuentaOrigen.getValue());
                        newTraspaso.setIbanDestino(cuentaDestino.getValue());
                        newTraspaso.setConcept(concepto.getValue());
                        newTraspaso.setValue(importe.getValue().setScale(2, RoundingMode.HALF_UP));
                        newTraspaso.setIssuer("Traspaso BIWAN IW 2023");
                        newTraspaso.setTransactionType(TransaccionBancaria.TRASPASO.toString());
                        Cuenta cuentaOrigen = cuentaService.findCuentaByIban(newTraspaso.getIban());
                        Cuenta cuentaDestino = cuentaService.findCuentaByIban(newTraspaso.getIbanDestino());

                        if (cuentaOrigen.getBalance() < newTraspaso.getValue().doubleValue()) {
                            Notification errorSaldo = new Notification("No tienes suficiente saldo en la cuenta seleccionada", 3000);
                            errorSaldo.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            errorSaldo.open();

                        } else {
                            if (Objects.equals(cuentaOrigen.getUUID(), cuentaDestino.getUUID())) {
                                Notification errorMismaCuenta = new Notification("No puedes realizar un traspaso a la misma cuenta", 3000);
                                errorMismaCuenta.addThemeVariants(NotificationVariant.LUMO_ERROR);
                                errorMismaCuenta.open();

                            } else {
                                newTraspaso.setTransactionStatus(Estado.ACCEPTED.toString());
                                movimientoService.saveTraspaso(newTraspaso, cuentaOrigen);

                                double nuevoSaldoOrigen = cuentaOrigen.getBalance() - newTraspaso.getValue().doubleValue();
                                cuentaOrigen.setBalance(nuevoSaldoOrigen);
                                cuentaService.updateBalance(cuentaOrigen);

                                double nuevoSaldoDestino = cuentaDestino.getBalance() + newTraspaso.getValue().doubleValue();
                                cuentaDestino.setBalance(nuevoSaldoDestino);
                                cuentaService.updateBalance(cuentaDestino);

                                ConfirmDialog confirmacion = new ConfirmDialog("Traspaso realizado", "El traspaso se ha realizado correctamente", "Volver", event1 -> {
                                    UI.getCurrent().navigate("pagina-principal-cliente");
                                });
                                confirmacion.open();
                            }
                        }
                    }
                }
            });
        }
    }
}
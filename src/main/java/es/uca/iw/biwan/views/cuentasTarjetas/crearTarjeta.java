package es.uca.iw.biwan.views.cuentasTarjetas;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.TarjetaService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderUsuarioLogueadoView;

import java.time.LocalDate;
import java.util.ArrayList;

@Route("crear-tarjeta-gestor")
@PageTitle("Crear Tarjeta")
@CssImport("./themes/biwan/crearTarjeta.css")
public class crearTarjeta extends VerticalLayout {

    private TarjetaService tarjetaService;
    private CuentaService cuentaService;
    private static Usuario usuarioSeleccionado;
    private ComboBox<Cuenta> comboBoxCuentasCliente;

    // Setter para coger la información que depende del usuario que hayamos seleccionado para crear tarjeta
    public static void setUsuarioSeleccionado(Usuario usuario) {
        usuarioSeleccionado = usuario;
    }

    public crearTarjeta(TarjetaService tarjetaService, CuentaService cuentaService) {
        this.tarjetaService = tarjetaService;
        this.cuentaService = cuentaService;
        VaadinSession session = VaadinSession.getCurrent();
        if(session.getAttribute(Usuario.class) != null) {
            if (!session.getAttribute(Usuario.class).getRol().contentEquals("GESTOR")) {
                UI.getCurrent().navigate("");
            } else {
                //NEW
                VerticalLayout layoutCrearTarjeta = new VerticalLayout();

                //ADD
                layoutCrearTarjeta.add(HeaderUsuarioLogueadoView.Header(), LayoutPrincipalCrearTarjeta(), FooterView.Footer());

                //ALIGNMENT
                layoutCrearTarjeta.expand(LayoutPrincipalCrearTarjeta());
                layoutCrearTarjeta.setAlignItems(Alignment.CENTER);
                layoutCrearTarjeta.setSizeFull();

                add(layoutCrearTarjeta);
            }
        } else {
            ConfirmDialog error = new ConfirmDialog("Error", "No has iniciado sesión", "Aceptar", null);
            error.open();
            UI.getCurrent().navigate("");
        }
    }

    private VerticalLayout LayoutPrincipalCrearTarjeta() {

        Binder<Tarjeta> binderCrearTarjeta = new Binder<>(Tarjeta.class);

        // Coger usuario logueado
        VaadinSession session = VaadinSession.getCurrent();
        String nombre = session.getAttribute(Usuario.class).getNombre() + " " + session.getAttribute(Usuario.class).getApellidos();

        //NEW
        VerticalLayout layoutPrincipalCrearTarjeta = new VerticalLayout();
        H1 Titulo = new H1("Bienvenido Gestor: " + nombre);
        H3 NombreClienteSeleccionado = new H3("Creación de tarjeta para el cliente: " + usuarioSeleccionado.getNombre() + " " + usuarioSeleccionado.getApellidos());

        TextField NumeroTarjeta = new TextField("Número de Tarjeta", "XXXX XXXX XXXX XXXX");
        NumeroTarjeta.setMaxLength(19);
        NumeroTarjeta.setAllowedCharPattern("[\\d\\s]");
        NumeroTarjeta.setErrorMessage("Formato no válido");
        NumeroTarjeta.setClearButtonVisible(true);
        DatePicker FechaCaducidad = new DatePicker("Fecha de Caducidad");
        FechaCaducidad.setClearButtonVisible(true);
        FechaCaducidad.setErrorMessage("Fecha o formato no válidos");
        TextField CVV = new TextField("CVV", "XXX");
        CVV.setMaxLength(3);
        CVV.setAllowedCharPattern("\\d");
        CVV.setErrorMessage("Formato no válido");
        CVV.setClearButtonVisible(true);
        HorizontalLayout layoutFechaCaducidadCVV = new HorizontalLayout();
        Button CrearTarjetaSubmit = new Button("Crear Tarjeta");
        CrearTarjetaSubmit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        CrearTarjetaSubmit.addClickShortcut(Key.ENTER);
        ComboBox<Cuenta> comboBox = generateComboBoxCuentasCliente();

        //BINDER
        binderCrearTarjeta.forField(NumeroTarjeta)
                .asRequired("El número de tarjeta es obligatorio")
                .withValidator(numeroTarjeta -> numeroTarjeta.length() == 19, "El IBAN debe tener 16 caracteres con espacio cada 4")
                .withValidator(numeroTarjeta -> numeroTarjeta.matches("^(\\s*\\t*[0-9]){16}$"), "No es un formato correcto, revisa que está bien escrito")
                .bind(Tarjeta::getNumeroTarjeta, Tarjeta::setNumeroTarjeta);

        binderCrearTarjeta.forField(FechaCaducidad)
                .asRequired("El balance es obligatorio")
                .withValidator(fechaCaducidad -> !fechaCaducidad.isBefore(LocalDate.now()), "La fecha introducida debe ser posterior a la actual")
                .bind(Tarjeta::getFechaCaducidad, Tarjeta::setFechaCaducidad);

        binderCrearTarjeta.forField(CVV)
                .asRequired("El balance es obligatorio")
                .withValidator(cvv -> cvv.length() == 3, "El CVV introducido debe ser 3 números")
                .withValidator(cvv -> cvv.matches("\\d{3}"), "No es un formato correcto, revisa que está bien escrito")
                .bind(Tarjeta::getCVV, Tarjeta::setCVV);

        //ADD CLASS NAME
        Titulo.addClassName("TituloCrearTarjeta");
        layoutPrincipalCrearTarjeta.addClassName("layoutPrincipalCrearTarjeta");
        NumeroTarjeta.addClassName("Campo");
        FechaCaducidad.addClassName("FechaCaducidad");
        CVV.addClassName("CVV");
        CrearTarjetaSubmit.addClassName("CrearTarjetaSubmit");

        //ALIGNMENT
        layoutPrincipalCrearTarjeta.setHorizontalComponentAlignment(Alignment.START, Titulo, NombreClienteSeleccionado);
        layoutPrincipalCrearTarjeta.setHorizontalComponentAlignment(Alignment.CENTER, generateComboBoxCuentasCliente(), NumeroTarjeta, layoutFechaCaducidadCVV, CrearTarjetaSubmit);
        layoutPrincipalCrearTarjeta.setHeightFull();

        //ADD
        layoutFechaCaducidadCVV.add(FechaCaducidad, CVV);
        layoutPrincipalCrearTarjeta.add(Titulo, NombreClienteSeleccionado, generateComboBoxCuentasCliente(), NumeroTarjeta, layoutFechaCaducidadCVV, CrearTarjetaSubmit);

        CrearTarjetaSubmit.addClickListener(event -> {
            if (binderCrearTarjeta.validate().isOk()) {
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setNumeroTarjeta(NumeroTarjeta.getValue());
                tarjeta.setFechaCaducidad(FechaCaducidad.getValue());
                tarjeta.setActiva(false); //Tiene que ser activada por el cliente para empezar a usarla
                tarjeta.setCVV(CVV.getValue());
                tarjeta.setLimiteGasto(200.00); //200€ de forma predeterminada
                boolean correcto = ComprobarDatos(tarjeta);
                if(correcto) { CreateRequest(tarjeta, comboBoxCuentasCliente.getValue().getIBAN()); }
            }
        });

        return layoutPrincipalCrearTarjeta;
    }

    private boolean ComprobarDatos(Tarjeta tarjeta) {
        if (tarjetaService.findTarjetaByNumeroTarjeta(tarjeta.getNumeroTarjeta()) != null) {
            Notification errorTelefono = new Notification("El número de tarjeta ya está en uso", 3000);
            errorTelefono.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorTelefono.open();
            return false;
        }
        return true;
    }

    private void CreateRequest(Tarjeta tarjeta, String ibanClienteSeleccionado) {
        try {
            tarjetaService.save(tarjeta, ibanClienteSeleccionado);
            ConfirmDialog confirmRequest = new ConfirmDialog("Cuenta Creada", "Cuenta creada correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("/pagina-principal-gestor");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud. Comunique al administrador del sitio el error.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

    private ComboBox<Cuenta> generateComboBoxCuentasCliente() {

        ArrayList<Cuenta> cuentasClienteSeleccionado = cuentaService.findCuentaByUUID(usuarioSeleccionado.getUUID());
        // Create a combo box with cuentas
        comboBoxCuentasCliente = new ComboBox<>();
        ;
        comboBoxCuentasCliente.setItems(cuentasClienteSeleccionado);
        comboBoxCuentasCliente.setItemLabelGenerator(iban -> iban.getIBAN());
        comboBoxCuentasCliente.setLabel("Cuenta");
        comboBoxCuentasCliente.isRequired();
        comboBoxCuentasCliente.setClearButtonVisible(true);
        comboBoxCuentasCliente.setRequired(true);
        comboBoxCuentasCliente.setRequiredIndicatorVisible(true);
        comboBoxCuentasCliente.setWidth("300px");
        comboBoxCuentasCliente.setPlaceholder("Selecciona una cuenta");
        comboBoxCuentasCliente.setHelperText("Selecciona una cuenta para crear una tarjeta");
        comboBoxCuentasCliente.setErrorMessage("Tienes que elegir una cuenta a la que asignar la nueva tarjeta");
        comboBoxCuentasCliente.getStyle().set("align-self", "center");
        if (!cuentasClienteSeleccionado.isEmpty()) {
            Object firstItem = comboBoxCuentasCliente.getDataProvider().fetch(new Query<>()).findFirst().orElse(null);
            comboBoxCuentasCliente.setValue((Cuenta) firstItem);
        }
        comboBoxCuentasCliente.addValueChangeListener(event -> {
            comboBoxCuentasCliente.setValue(event.getValue());
        });
        return comboBoxCuentasCliente;
    }
}


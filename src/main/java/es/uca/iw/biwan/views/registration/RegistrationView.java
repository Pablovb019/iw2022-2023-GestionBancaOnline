package es.uca.iw.biwan.views.registration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.usuarios.Persona;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./themes/biwan/registration.css")
@PageTitle("Formulario de registro")
@Route("registration")
public class RegistrationView extends VerticalLayout {

    @Autowired
    private UsuarioService usuarioService;

    private final FormLayout registration = new FormLayout();

    public RegistrationView() {

        //NEW
        VerticalLayout layoutRegistration = new VerticalLayout();
        HorizontalLayout layoutHorRegistration = new HorizontalLayout();

        //ADD
        layoutHorRegistration.add(Registration());
        layoutRegistration.add(HeaderView.Header(), layoutHorRegistration, FooterView.Footer());

        //ALIGNMENT
        layoutHorRegistration.setWidth("30%");
        layoutRegistration.expand(layoutHorRegistration);
        layoutHorRegistration.setVerticalComponentAlignment(Alignment.CENTER, Registration());
        layoutHorRegistration.setAlignItems(Alignment.CENTER);
        layoutRegistration.setAlignItems(Alignment.CENTER);
        layoutRegistration.setSizeFull();

        add(layoutRegistration);
    }

    private FormLayout Registration() {

        //NEW
        TextField firstName = new TextField("Nombre");
        TextField lastName = new TextField("Apellidos");
        NumberField phoneNumber = new NumberField("Teléfono");
        TextField dni = new TextField("DNI");
        DatePicker birthDate = new DatePicker("Fecha de Nacimiento");
        PasswordField password = new PasswordField("Contraseña");
        PasswordField confirmPassword = new PasswordField("Confirmar contraseña");
        EmailField email = new EmailField();
        email.setLabel("Correo electrónico");
        email.getElement().setAttribute("name", "email");
        email.setErrorMessage("Introduce una dirección de correo electrónico válida");
        email.setClearButtonVisible(true);
        Button submit = new Button("Crear cuenta");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.setClassName("ButtonSubmitRegistration");
        H1 Titulo = new H1("Crear cuenta");
        FormLayout formLayout = new FormLayout();

        //ADD CLASS NAME
        Titulo.addClassName("CrearCuenta");

        //ADD
        formLayout.add(Titulo, firstName, lastName, phoneNumber, dni, birthDate, email, password, confirmPassword, submit);

        //ALIGNMENT
        formLayout.setResponsiveSteps( new FormLayout.ResponsiveStep("", 2));
        formLayout.setColspan(Titulo, 2);
        formLayout.setColspan(email, 2); // Stretch the email field over 2 columns
        setSizeFull();

        submit.addClickListener(event -> {
            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || dni.isEmpty() || birthDate.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                ConfirmDialog error = new ConfirmDialog("Error", "Rellena todos los campos", "Aceptar", null);
                error.open();
            } else {
                if (password.getValue().equals(confirmPassword.getValue())) {
                    Persona persona = new Persona(firstName.getValue(), lastName.getValue(), birthDate.getValue(), phoneNumber.getValue(), dni.getValue(), email.getValue(), password.getValue());

                    ConfirmDialog confirmRequest = new ConfirmDialog("Crear Solicitud", "¿Desea crear la solicitud? Los datos no podrán ser modificados", "Aceptar", event1 -> {
                        CreateRequest(persona);
                    });
                    confirmRequest.open();
                } else {
                    ConfirmDialog passwordError = new ConfirmDialog("Error", "Las contraseñas no coinciden", "Aceptar", event1 -> {
                    });
                    passwordError.open();
                }
            }
        });
        return formLayout;
    }

    private void CreateRequest(Persona persona) {
        try {
            usuarioService.save(persona);
            ConfirmDialog confirmRequest = new ConfirmDialog("Solicitud creada", "La solicitud ha sido creada correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("");
            });
            confirmRequest.open();
        } catch (Exception e) {
            ConfirmDialog error = new ConfirmDialog("Error", "Ha ocurrido un error al crear la solicitud.\n" +
                    "Error: " + e, "Aceptar", null);
            error.open();
        }
    }

}
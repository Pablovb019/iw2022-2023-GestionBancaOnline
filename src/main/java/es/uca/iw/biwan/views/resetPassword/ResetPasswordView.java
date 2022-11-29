package es.uca.iw.biwan.views.resetPassword;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("/themes/biwan/resetPassword.css")
@PageTitle("Restablecer contraseña")
@Route("reset-password")
public class ResetPasswordView extends VerticalLayout {

    private final FormLayout registration = new FormLayout();

    public ResetPasswordView() {

        //NEW
        VerticalLayout layoutResetPassword = new VerticalLayout();
        HorizontalLayout layoutHorResetPassword = new HorizontalLayout();

        //ADDs
        layoutHorResetPassword.add(ResetPassword());
        layoutResetPassword.add(HeaderView.Header(), layoutHorResetPassword, FooterView.Footer());

        //ALIGNMENT
        layoutHorResetPassword.setWidth("30%");
        layoutResetPassword.expand(layoutHorResetPassword);
        layoutHorResetPassword.setVerticalComponentAlignment(Alignment.CENTER, ResetPassword());
        layoutResetPassword.setAlignItems(Alignment.CENTER);
        layoutResetPassword.setSizeFull();

        add(layoutResetPassword);
    }

    private FormLayout ResetPassword() {

        //NEW
        FormLayout formLayout = new FormLayout();
        VerticalLayout InfoLayout = new VerticalLayout();
        HorizontalLayout TituloPasswordRulesLayout = new HorizontalLayout();
        HorizontalLayout PasswordRule1Layout = new HorizontalLayout();
        HorizontalLayout PasswordRule2Layout = new HorizontalLayout();
        HorizontalLayout PasswordRule3Layout = new HorizontalLayout();
        HorizontalLayout PasswordRule4Layout = new HorizontalLayout();
        PasswordField password = new PasswordField("Nueva contraseña");
        PasswordField confirmPassword = new PasswordField("Confirmar contraseña");
        Button submit = new Button("Restablecer contraseña");
        H1 Titulo = new H1("Restablecer contraseña");
        H2 Description = new H2("Por favor, introduzca su nueva contraseña.");
        Text PasswordRules = new Text("Las contraseñas deben");
        Text PasswordRule1 = new Text("Ser al menos de 8 caracteres");
        Text PasswordRule2 = new Text("Tener al menos 1 carácter en mayúsculas");
        Text PasswordRule3 = new Text("Tener al menos 1 carácter numérico");
        Text PasswordRule4 = new Text("Tener al menos 1 símbolo");
        Icon InfoIcon = new Icon(VaadinIcon.INFO_CIRCLE);
        Icon CheckMarkIcon1 = new Icon(VaadinIcon.CHECK);
        Icon CheckMarkIcon2 = new Icon(VaadinIcon.CHECK);
        Icon CheckMarkIcon3 = new Icon(VaadinIcon.CHECK);
        Icon CheckMarkIcon4 = new Icon(VaadinIcon.CHECK);

        //ADD CLASS NAME
        submit.setClassName("ButtonSubmitResetPassword");
        Titulo.addClassName("RestablecerContraseña");
        password.addClassName("PasswordField");
        confirmPassword.addClassName("PasswordField");
        Description.addClassName("Descripcion");
        InfoLayout.addClassName("InfoLayout");
        TituloPasswordRulesLayout.addClassNames("Bold", "TituloPasswordRulesLayout");
        InfoIcon.addClassName("InfoIcon");
        CheckMarkIcon1.addClassName("CheckIcon");
        CheckMarkIcon2.addClassName("CheckIcon");
        CheckMarkIcon3.addClassName("CheckIcon");
        CheckMarkIcon4.addClassName("CheckIcon");

        //CSS
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //ADD
        TituloPasswordRulesLayout.add(InfoIcon, PasswordRules);
        PasswordRule1Layout.add(CheckMarkIcon1, PasswordRule1);
        PasswordRule2Layout.add(CheckMarkIcon2, PasswordRule2);
        PasswordRule3Layout.add(CheckMarkIcon3, PasswordRule3);
        PasswordRule4Layout.add(CheckMarkIcon4, PasswordRule4);
        InfoLayout.add(TituloPasswordRulesLayout, PasswordRule1Layout, PasswordRule2Layout, PasswordRule3Layout, PasswordRule4Layout);
        formLayout.add(Titulo, Description, InfoLayout, password, confirmPassword, submit);
        setSizeFull();

        return formLayout;
    }

}
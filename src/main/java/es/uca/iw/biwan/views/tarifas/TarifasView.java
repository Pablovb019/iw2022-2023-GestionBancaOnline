package es.uca.iw.biwan.views.tarifas;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.biwan.views.footers.FooterView;
import es.uca.iw.biwan.views.headers.HeaderView;

@CssImport("./themes/biwan/tarifas.css")
@Route("tarifas")
@PageTitle("Cuentas y tarifas")
@AnonymousAllowed
public class TarifasView extends VerticalLayout{
    public TarifasView() {

        //NEW
        VerticalLayout layoutTarifas = new VerticalLayout();
        VerticalLayout layoutTextoTarifas = new VerticalLayout();
        H1 Titulo = new H1("Cuentas y tarifas");
        H3 CuentaBiwan = new H3("CUENTA BIWAN");
        H5 CuentaBiwan1 = new H5("Una cuenta corriente con un programa que te permite disfrutar de servicios" +
                " para hacer más fácil tu día a día.");
        H5 CuentaBiwan2 = new H5("La Cuenta BIWAN es una cuenta corriente, no remunerada para personas" +
                " físicas, residentes fiscalmente en España y mayores de 18 años, que puedes contratar online.");
        H5 CuentaBiwan3 = new H5("Si, por ejemplo, tienes tus ingresos domiciliados y realizas tus pagos con la" +
                " Cuenta BIWAN, no pagarás comisión mantenimiento de la cuenta (TIN 0%, TAE 0%).");
        H4 SubTituloCuentaBiwan = new H4("Características de la Cuenta BIWAN");
        H5 CaracteristicaCuentaBiwan1 = new H5("• 0€ de comisión de mantenimiento de la cuenta si domicilias tu" +
                " nómina y realizas pagos (al menos 3 recibos o 6 usos de tus tarjetas) con la cuenta.");
        H5 CaracteristicaCuentaBiwan2 = new H5("• 10€ de comisión de mantenimiento de la cuenta si domicilias tu " +
                "nómina o tienes un producto de ahorro, financiación, protección o mercados.");
        H3 CuentaJoven = new H3("CUENTA JOVEN");
        H5 CuentaJoven1 = new H5("Abre tu Cuenta Joven en pocos minutos sin moverte de donde estés y únete a todos" +
                " los que ya hoy disfrutan de sus ventajas.");
        H5 CuentaJoven2 = new H5("Es una cuenta para nuevos clientes, no remunerada para personas físicas, " +
                "residentes fiscalmente en España y que tengan entre 18 y 28 años.");
        H4 SubTituloCuentaJoven = new H4("Características de la Cuenta Joven");
        H5 CaracteristicaCuentaJoven1 = new H5("• Abre tu cuenta sin comisión de mantenimiento, es decir, no pagas " +
                "por tener tu cuenta.");
        H5 CaracteristicaCuentaJoven2 = new H5("• Podrás tener dos cuentas por titular.");
        H5 CaracteristicaCuentaJoven3 = new H5("• Paga donde quieras con tu Tarjeta BIWAN, la obtienes al abrir la " +
                "cuenta sin comisión de emisión y mantenimiento al abrir la cuenta. Además, podrás beneficiarte con un" +
                " seguro de accidentes hasta 120.000 €.");
        H5 CaracteristicaCuentaJoven4 = new H5("Realiza transferencias en euros sin comisión, desde nuestra Banca" +
                " Online.");
        H3 TarifasGenerales = new H3("TARIFAS GENERALES");
        H5 TarifasGenerales1 = new H5("• Tarifa de apertura de cuenta: 25€");
        H5 TarifasGenerales2 = new H5("• Tarifa de mantenimiento de cuenta: 15€ al mes");
        H5 TarifasGenerales3 = new H5("• Tarifa por uso de cajero automático: 1€ por transacción en cajeros " +
                "automáticos que no son de la propiedad de BIWAN");
        H5 TarifasGenerales4 = new H5("• Tarifa por envío de cheques: 3€ por cheque enviado por correo");
        H5 TarifasGenerales5 = new H5("• Tarifa por retirar dinero antes de tiempo: un 2% del monto total retirado" +
                " de una cuenta de CD (certificado de depósito) antes del plazo establecido");
        H5 TarifasGenerales6 = new H5("• Tarifa por no cumplir con el saldo mínimo: 25€ si el saldo de la cuenta" +
                " de cheques cae por debajo de 500€ durante un mes calendario");

        //ADD
        layoutTextoTarifas.add(Titulo, CuentaBiwan, CuentaBiwan1, CuentaBiwan2, CuentaBiwan3, SubTituloCuentaBiwan,
                CaracteristicaCuentaBiwan1, CaracteristicaCuentaBiwan2, CuentaJoven, CuentaJoven1, CuentaJoven2,
                SubTituloCuentaJoven, CaracteristicaCuentaJoven1, CaracteristicaCuentaJoven2, CaracteristicaCuentaJoven3,
                TarifasGenerales, TarifasGenerales1, TarifasGenerales2, TarifasGenerales3, TarifasGenerales4,
                TarifasGenerales5, TarifasGenerales6);
        layoutTarifas.add(HeaderView.Header(), layoutTextoTarifas, FooterView.Footer());

        //ADD CLASS NAME
        Titulo.addClassName("Titulo");
        layoutTextoTarifas.addClassName("layoutTextoTarifas");
        CuentaBiwan1.addClassName("texto");
        CuentaBiwan2.addClassName("texto");
        CuentaBiwan3.addClassName("texto");
        SubTituloCuentaBiwan.addClassName("Subtitulo");
        CaracteristicaCuentaBiwan1.addClassName("texto");
        CaracteristicaCuentaBiwan2.addClassName("texto");
        CuentaJoven1.addClassName("texto");
        CuentaJoven2.addClassName("texto");
        SubTituloCuentaJoven.addClassName("Subtitulo");
        CaracteristicaCuentaJoven1.addClassName("texto");
        CaracteristicaCuentaJoven2.addClassName("texto");
        CaracteristicaCuentaJoven3.addClassName("texto");
        CaracteristicaCuentaJoven4.addClassName("texto");
        TarifasGenerales1.addClassName("texto");
        TarifasGenerales2.addClassName("texto");
        TarifasGenerales3.addClassName("texto");
        TarifasGenerales4.addClassName("texto");
        TarifasGenerales5.addClassName("texto");
        TarifasGenerales6.addClassName("texto");

        //ALIGNMENT
        layoutTextoTarifas.setWidth("50%");
        layoutTarifas.expand(layoutTextoTarifas);
        layoutTextoTarifas.setAlignItems(FlexComponent.Alignment.START);
        layoutTarifas.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutTarifas.setSizeFull();

        add(layoutTarifas);
    }
}

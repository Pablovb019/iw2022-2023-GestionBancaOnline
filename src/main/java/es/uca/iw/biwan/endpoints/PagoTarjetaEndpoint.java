package es.uca.iw.biwan.endpoints;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.PagoTarjetaService;
import es.uca.iw.biwan.aplication.service.TarjetaService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.SMS.SMSHandler;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Estado;
import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import es.uca.iw.biwan.domain.operaciones.TipoPago;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@RestController
public class PagoTarjetaEndpoint {
    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarjetaService tarjetaService;

    @Autowired
    private PagoTarjetaService pagoTarjetaService;

    String CodigoSeguridad;

    @PostMapping(value = "/api/pagosTarjeta")
    PagoTarjeta addPagoTarjeta(@RequestBody PagoTarjeta nuevoPagoTarjeta) {
        Cuenta cuenta = cuentaService.findCuentaByNumeroTarjeta(nuevoPagoTarjeta.getCardNumber());
        Tarjeta tarjeta = tarjetaService.findTarjetaByNumeroTarjeta(nuevoPagoTarjeta.getCardNumber());

        if (cuenta == null) {
            nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
            System.out.println(">>>> Compra anulada ya que el número de tarjeta no coincide con ninguna cuenta. <<<<");
            System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
            return nuevoPagoTarjeta;
        }

        Cliente cliente = usuarioService.findClienteByCuenta(cuenta);
        nuevoPagoTarjeta.setId(UUID.randomUUID());

        if (tarjeta.getLimiteGasto() == 0 || tarjeta.getLimiteGasto() < nuevoPagoTarjeta.getValue().doubleValue()) {
            nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
            System.out.println(">>>> Compra anulada ya que el importe supera el límite de gasto de la tarjeta <<<<");
            System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
            pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
        } else {
            if (nuevoPagoTarjeta.getValue().compareTo(new BigDecimal("50.0")) > 0) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que excede el gasto máximo por compra de 50 euros. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            YearMonth fechaCaducidad = YearMonth.of(2000 + nuevoPagoTarjeta.getYear(), nuevoPagoTarjeta.getMonth());

            if (!Objects.equals(YearMonth.from(tarjeta.getFechaCaducidad()), fechaCaducidad)) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que la fecha de caducidad no coincide con la de la cuenta. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            if (!Objects.equals(tarjeta.getCSV(), nuevoPagoTarjeta.getCsc())) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que el CSV no coincide con el de la cuenta. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            if (!tarjeta.getActiva()) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que la tarjeta no está activa. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            String NombreTitularCliente = cliente.getNombre() + " " + cliente.getApellidos();

            if (!Objects.equals(NombreTitularCliente, nuevoPagoTarjeta.getCardholderName())) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que el nombre del titular no coincide con el de la cuenta. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            if (cuenta.getBalance() < nuevoPagoTarjeta.getValue().doubleValue()) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que el saldo de la cuenta es inferior al importe de la compra. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            if (nuevoPagoTarjeta.getValue().compareTo(new BigDecimal("10.0")) <= 0) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.ACCEPTED);
                System.out.println(">>>> Compra aceptada y no requiere confirmacion por ser <=10 euros. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);

                double nuevoBalance = cuenta.getBalance() - nuevoPagoTarjeta.getValue().doubleValue();
                cuenta.setBalance(nuevoBalance);
                cuentaService.updateBalance(cuenta);

                double nuevoLimiteTarjeta = tarjeta.getLimiteGasto() - nuevoPagoTarjeta.getValue().doubleValue();
                tarjeta.setLimiteGasto(nuevoLimiteTarjeta);
                tarjetaService.updateLimiteGasto(tarjeta);
                return nuevoPagoTarjeta;
            }

            nuevoPagoTarjeta.setPaymentStatus(Estado.SECURITY_TOKEN_REQUIRED);
            System.out.println(">>>> Recibida peticion de compra. Se requiere autorizacion mediante token de seguridad... <<<<");
            System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");

            if (nuevoPagoTarjeta.getType() == TipoPago.ONLINE) {
                System.out.println(">>>> Se ha detectado que el pago es online. Se procede a enviar un SMS al usuario para que confirme la operacion... <<<<");
                int telefono = cliente.getTelefono().intValue();
                System.out.println(">>>> Se ha detectado que el telefono del usuario es: " + telefono + " <<<<");
                int number = new Random().nextInt(999999);
                CodigoSeguridad = String.format("%06d", number);
                System.out.println(">>>> Se ha generado el codigo de seguridad: " + CodigoSeguridad + " <<<<");
                Twilio.init(SMSHandler.ACCOUNT_SID, SMSHandler.AUTH_TOKEN);
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+34" + telefono),
                        new com.twilio.type.PhoneNumber("+12074896965"),
                        "\nPara confirmar el pago introduzca el siguiente codigo de seguridad: " + CodigoSeguridad
                                + ".\nSi usted no ha realizado esta operación, contacte con su banco lo antes posible."
                ).create();
                System.out.println(">>>> Se ha enviado el SMS al usuario con el codigo de seguridad. <<<<");
                nuevoPagoTarjeta.setSecurityToken(Integer.parseInt(CodigoSeguridad));
            }

            // ONLINE

            if (!Objects.equals(String.valueOf(nuevoPagoTarjeta.getSecurityToken()), CodigoSeguridad) && nuevoPagoTarjeta.getType() == TipoPago.ONLINE) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.REJECTED);
                System.out.println(">>>> Compra anulada ya que el codigo de seguridad no coincide con el enviado al usuario. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            if (nuevoPagoTarjeta.getId() != null && nuevoPagoTarjeta.getPaymentStatus().equals(Estado.SECURITY_TOKEN_REQUIRED) && nuevoPagoTarjeta.getSecurityToken() != null && nuevoPagoTarjeta.getType() == TipoPago.ONLINE) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.ACCEPTED);
                System.out.println(">>>> Recibida autorizacion de compra. Compra ONLINE aceptada. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
                return nuevoPagoTarjeta;
            }

            // OFFLINE

            if (nuevoPagoTarjeta.getId() != null && nuevoPagoTarjeta.getPaymentStatus().equals(Estado.SECURITY_TOKEN_REQUIRED) && nuevoPagoTarjeta.getSecurityToken() != null && nuevoPagoTarjeta.getType() == TipoPago.OFFLINE) {
                nuevoPagoTarjeta.setPaymentStatus(Estado.ACCEPTED);
                System.out.println(">>>> Recibida autorizacion de compra. Compra OFFLINE aceptada. <<<<");
                System.out.println(">>>> Payload devuelto: " + nuevoPagoTarjeta + " <<<<");
                pagoTarjetaService.savePagoTarjeta(nuevoPagoTarjeta, tarjeta);
            }
        }
        return nuevoPagoTarjeta;
    }
}

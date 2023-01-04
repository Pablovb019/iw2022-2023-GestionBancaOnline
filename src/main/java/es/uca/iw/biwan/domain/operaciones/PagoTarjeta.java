package es.uca.iw.biwan.domain.operaciones;

import es.uca.iw.biwan.domain.tarjeta.Tarjeta;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class PagoTarjeta {

    @Id
    @Column(length = 16)
    private UUID id;
    private String paymentStatus;

    @NotEmpty(message = "El numero de tarjeta es obligatorio")
    @Transient
    private String cardNumber;

    @NotEmpty(message = "El nombre del titular de la tarjeta es obligatorio")
    @Transient
    private String cardholderName;

    @NotNull(message = "El mes de expiracion de la tarjeta es obligatorio")
    @Transient
    private Integer month;

    @NotNull(message = "El ade expiracion de la tarjeta es obligatorio")
    @Transient
    private Integer year;

    @NotEmpty(message = "El codigo de seguridad CSC es obligatorio")
    @Transient
    private String csc;

    @NotNull(message = "El importe es obligatorio")
    private BigDecimal value;

    @Column(columnDefinition="varchar(255)")
    private String type;

    @Transient
    private Integer securityToken;

    @NotEmpty(message = "El nombre del establecimiento debe estar relleno")
    private String shop;

    @ManyToOne
    @JoinColumn(name = "tarjeta_id")
    private Tarjeta tarjeta;

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderName() {
        return this.cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCsc() {
        return this.csc;
    }

    public void setCsc(String csc) {
        this.csc = csc;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getShop() {
        return this.shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSecurityToken() {
        return this.securityToken;
    }

    public void setSecurityToken(Integer securityToken) {
        this.securityToken = securityToken;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String toString() {
        return "CreditCardPayment [paymentStatus=" + this.paymentStatus + ", id=" + this.id + ", cardNumber=" + this.cardNumber + ", cardholderName=" + this.cardholderName + ", month=" + this.month + ", year=" + this.year + ", csc=" + this.csc + ", value=" + this.value + ", type=" + this.type + ", securityToken=" + this.securityToken + ", shop=" + this.shop + "]";
    }

    public Tarjeta getTarjeta() {
        return this.tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getCardNumbers() {
        return this.tarjeta.getNumeroTarjeta();
    }
}
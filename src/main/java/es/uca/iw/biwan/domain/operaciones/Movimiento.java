package es.uca.iw.biwan.domain.operaciones;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "tipo_movimiento")
public class Movimiento {

    @Id
    @Column(length = 16)
    private UUID id;

    @Column(columnDefinition="varchar(255)")
    private Estado transactionStatus;

    private String issuer;

    @Column(columnDefinition="varchar(255)")
    private TransaccionBancaria transactionType;

    @NotEmpty(message = "El concepto es obligatorio")
    private String concept;

    @NotEmpty(message = "El IBAN es obligatorio")
    private String iban;

    @NotNull(message = "El importe es obligatorio")
    private BigDecimal value;

    public String getConcept() {
        return this.concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Estado getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(Estado transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransaccionBancaria getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransaccionBancaria transactionType) {
        this.transactionType = transactionType;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String toString() {
        return "Movimiento [id=" + this.id + ", transactionStatus=" + String.valueOf(this.transactionStatus) + ", issuer=" + this.issuer + ", transactionType=" + String.valueOf(this.transactionType) + ", concept=" + this.concept + ", iban=" + this.iban + ", value=" + String.valueOf(this.value) + "]";
    }

}


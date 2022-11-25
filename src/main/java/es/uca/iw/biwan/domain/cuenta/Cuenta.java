package es.uca.iw.biwan.domain.cuenta;

public class Cuenta {
    private float balance;
    private String IBAN;

    public Cuenta(float balance, String IBAN) {
        this.balance = balance;
        this.IBAN = IBAN;
    }

    public float getBalance() { return balance; }
    public void setBalance(float balance) { this.balance = balance; }

    public String getIBAN() { return IBAN; }
    public void setIBAN(String IBAN) { this.IBAN = IBAN; }
}

package es.uca.iw.biwan.domain.operaciones;

public enum TransaccionBancaria {
    WITHDRAWAL,
    DEPOSIT,
    TRANSFERENCIA,
    TRASPASO;

    private TransaccionBancaria() {
    }
}
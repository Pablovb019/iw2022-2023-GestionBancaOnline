package es.uca.iw.biwan.domain.operaciones;

public enum Estado {
    REQUESTED,
    SECURITY_TOKEN_REQUIRED,
    REJECTED,
    ACCEPTED;

    private Estado() {
    }
}

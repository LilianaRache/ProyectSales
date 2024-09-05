package baseFiles.enums;

public enum DocumentType {

    CEDULA_CIUDADANIA("CC"),
    NUMERO_IDENTIFICACION_TRIBUTARIA("NIT"),
    CEDULA_EXTRANJERIA("CE"),
    PASAPORTE("PAS"),
    PERMISO_ESPECIAL_PERMANENCIA("PEP");

    private final String code;

    DocumentType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.Arrays;

public class Comando {
    private final TipoComando tipo;
    private final ArrayList<String> params;

    public Comando(TipoComando tipo, String ... params) {
        this.tipo = tipo;
        this.params = new ArrayList<>();
        this.params.addAll(Arrays.asList(params));
    }

    public TipoComando getTipo() {
        return tipo;
    }

    public ArrayList<String> getParams() {
        return params;
    }
}

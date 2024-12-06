package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class Configuracao extends EstadosAdapter{


    public Configuracao(Gestor gestor) {
        super(gestor);
    }

    @Override
    public IEstado gerirAlunos() {
        return new GerirAlunos(gestor);
    }

    @Override
    public IEstado gerirProp() {
        return new GerirProps(gestor);
    }

    @Override
    public IEstado gerirDoc() {
        return new GerirDoc(gestor);
    }

    @Override
    public IEstado proximaFase() {
        return new OpcoesCandidatura(gestor, true);
    }



    @Override
    public IEstado fechaFase() {
        return new OpcoesCandidatura(gestor,false);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.CONFIG;
    }
}

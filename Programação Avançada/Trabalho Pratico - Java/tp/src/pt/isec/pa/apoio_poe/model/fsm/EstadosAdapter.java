package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public abstract class EstadosAdapter implements IEstado{
   protected final Gestor gestor;

    public EstadosAdapter(Gestor gestor) {
        this.gestor = gestor;
    }

    @Override
    public IEstado gerirAlunos() {
        return this;
    }

    @Override
    public IEstado gerirProp() {
        return this;
    }

    @Override
    public IEstado voltar() {
        return this;
    }

    @Override
    public IEstado gerirDoc() {
        return this;
    }

    @Override
    public IEstado gerir(Comando comando) {
        return this;
    }

    @Override
    public IEstado proximaFase() {
        return this;
    }

    @Override
    public IEstado faseAnterior() {
        return this;
    }

    @Override
    public IEstado recomecar() {
        return this;
    }

    @Override
    public IEstado fechaFase() {
        return this;
    }
}

package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Properties;

public class MaquinaEstadosObservable extends PropertyChangeSupport {
    private MaquinaEstados maquinaEstados;


    public MaquinaEstadosObservable(MaquinaEstados maquinaEstados) {
        super(maquinaEstados);
        this.maquinaEstados = maquinaEstados;
    }

    public void gerir(Comando comando) {
        maquinaEstados.gerir(comando);
        firePropertyChange(null, null, null);
    }

    public void gerirAlunos() {
        maquinaEstados.gerirAlunos();
        firePropertyChange(null, null, null);
    }

    public void gerirDoc() {
        maquinaEstados.gerirDoc();
        firePropertyChange(null, null, null);
    }

    public void gerirProp() {
        maquinaEstados.gerirProp();
        firePropertyChange(null, null, null);
    }

    public ArrayList<String> getRegisto() {
        return maquinaEstados.getRegisto();
    }

    public EstadoGestor getEstadoAtual() {
        return maquinaEstados.getEstadoAtual();
    }

    public void voltar() {
        maquinaEstados.voltar();
        firePropertyChange(null, null, null);
    }

    public void proximaFase() {
        maquinaEstados.proximaFase();
        firePropertyChange(null, null, null);
    }

    public void faseAnterior() {
        maquinaEstados.faseAnterior();
        firePropertyChange(null, null, null);
    }

    public void recomecar() {
        maquinaEstados.recomecar();
        firePropertyChange(null, null, null);
    }

    public void fechaFase() {
        maquinaEstados.fechaFase();
        firePropertyChange(null, null, null);
    }
}

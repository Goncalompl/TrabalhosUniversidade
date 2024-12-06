package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;

import java.io.Serializable;
import java.util.ArrayList;

public interface IEstado extends Serializable {
    IEstado gerir(Comando comando);
    IEstado proximaFase();
    IEstado gerirAlunos();
    IEstado gerirProp();
    IEstado gerirDoc();
    IEstado voltar();
    IEstado faseAnterior();
    IEstado recomecar();
    IEstado fechaFase();

    EstadoGestor getEstado();
}

package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class AtribuicaoOrientadores extends EstadosAdapter{
    private final boolean fase3Aberta;

    public AtribuicaoOrientadores(Gestor gestor, boolean fase3Aberta) {
        super(gestor);
        this.fase3Aberta = fase3Aberta;
    }

    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case ATRIBUI_PROJETO :
                gestor.atribuiProjetos1();
                break;
            case ATRIBUI_DOCENTE_M :
                gestor.atribuirDocenteManual(comando.getParams().get(0),comando.getParams().get(1));
                break;
            case ATRIBUI_MANUAL :
                //gestor.atribuiManual(comando.getParams().get(0));
                break;
            case LISTAR_PROP_D :
                gestor.listarAlunosProDocAtr();
                gestor.listarAlunosProDocNAtr();
                gestor.listarPropostasAtribuidas();
                break;
        }
        return this;
    }


    @Override
    public IEstado proximaFase() {
        return new Consulta(gestor,true);
    }

    @Override
    public IEstado faseAnterior() {
        if(fase3Aberta)
            return new AtribuicaoPropostas(gestor,false);
        else return this;

    }

    @Override
    public IEstado fechaFase() {
        if(fase3Aberta)
            return this;
        return new Consulta(gestor, false);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.ATRIB_ORIENT;
    }

}

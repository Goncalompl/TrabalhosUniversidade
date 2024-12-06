package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class AtribuicaoPropostas extends EstadosAdapter{

    private boolean fase2Aberta;

    public AtribuicaoPropostas(Gestor gestor, boolean fase2Aberta) {
        super(gestor);
        this.fase2Aberta = fase2Aberta;
    }
    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case ATRIBUI_PROP_A :
                break;
            case ATRIBUI_PROP_NA :
                gestor.atribuiPropostas();
                break;
            case ATRIBUI_PROP_MANUAL :
                gestor.atribuiManual(Long.parseLong(comando.getParams().get(0)),comando.getParams().get(1));
                break;
            case LISTA_PROP:
                gestor.getAlunosProDocAtr();
                gestor.getAlunosProDocNAtr();
                gestor.getPropostasAtribuidas();

                break;
            case LISTAR_ALUNOS:
                gestor.listaAlunos();
                break;
        }
        return this;
    }



    @Override
    public IEstado proximaFase() {
        if(fase2Aberta)
            return this;
        return new AtribuicaoOrientadores(gestor, true);
    }

    @Override
    public IEstado faseAnterior() {
        if(fase2Aberta)
            return new OpcoesCandidatura(gestor,false);
        else return this;
    }

    @Override
    public IEstado fechaFase() {
        return new AtribuicaoOrientadores(gestor, false);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.ATRIB_PROP;
    }
}

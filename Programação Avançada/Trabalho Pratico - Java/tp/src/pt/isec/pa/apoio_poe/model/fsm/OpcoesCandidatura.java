package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class OpcoesCandidatura extends EstadosAdapter{
    private final boolean fase1Aberta;

    public OpcoesCandidatura(Gestor gestor, boolean fase1Aberta) {
        super(gestor);
        this.fase1Aberta = fase1Aberta;
    }
    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case INSERIR_CAND:
                gestor.inserirCandidatura(comando.getParams().get(0));
                break;
            case LISTAR_CAND:
                gestor.listaCandidaturas();
                break;
            case CONSULTAR_CAND:
                gestor.consultaCandidatura(comando.getParams().get(0));
            case ALUNOS_AUTOPROPOSTOS :
                gestor.listarAlunosAutoPropostos();
                break;
            case ALUNOS_CAND :
                gestor.listarAlunosCandidatura();
                break;
            case ALUNOS_NATRIBUIDOS :
                gestor.listarAlunosNCandidatura();
                break;
        }
        return this;
    }


    @Override
    public IEstado proximaFase() {
        if(fase1Aberta)
            return this;
        return new AtribuicaoPropostas(gestor, true);
    }

    @Override
    public IEstado faseAnterior() {
        if(fase1Aberta)
            return new Configuracao(gestor);
        else return this;
    }

    @Override
    public IEstado fechaFase() {
        if(fase1Aberta)
            return this;
        return new AtribuicaoPropostas(gestor, false);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.OP_CAND;
    }
}

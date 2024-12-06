package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;

public class GerirAlunos extends EstadosAdapter {

    public GerirAlunos(Gestor gestor)  {
        super(gestor);
    }

    @Override
    public IEstado gerir(Comando comando) {
        gestor.clearRegisto();
        switch (comando.getTipo()) {
            case INSERIR_ALUNO:
                //Aluno(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder)
                gestor.inserirAluno(Long.parseLong(comando.getParams().get(0)), comando.getParams().get(1), comando.getParams().get(2),comando.getParams().get(3), comando.getParams().get(4),Double.parseDouble(comando.getParams().get(5)), comando.getParams().get(6));
                break;
            case LISTAR_ALUNOS :
                gestor.listaAlunos();
                break;
            case EDITAR_ALUNO:
                gestor.editarAluno(Long.parseLong(comando.getParams().get(0)),Long.parseLong(comando.getParams().get(1)),comando.getParams().get(2),comando.getParams().get(3),comando.getParams().get(4),comando.getParams().get(5),Double.parseDouble(comando.getParams().get(6)),comando.getParams().get(7));
            case CONSULTAR_ALUNO:
                gestor.consultarAluno(Long.parseLong(comando.getParams().get(0)));
                break;
            case LER_CSV_ALUNOS:
                gestor.obterInfoFich(comando.getParams().get(0));
                break;
            case ELIMINA_ALUNO:
                gestor.eliminarAluno(Long.parseLong(comando.getParams().get(0)));



        }
        return this;
    }

    @Override
    public IEstado voltar() {
        return new Configuracao(gestor);
    }

    @Override
    public EstadoGestor getEstado() {
        return EstadoGestor.GERIR_ALUNOS;
    }

    @Override
    public IEstado fechaFase() {
        return new Configuracao(gestor);
    }
}

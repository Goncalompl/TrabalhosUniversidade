package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Comando;
import pt.isec.pa.apoio_poe.model.data.Gestor;
import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.docente.Docente;
import pt.isec.pa.apoio_poe.model.data.propostas.GestorPropostas;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MaquinaEstados implements Serializable {
    private final Gestor gestor;
    private IEstado estadoAtual;

    public MaquinaEstados() {
        this.gestor = new Gestor();
        setEstadoAtual(new Configuracao(gestor));
    }

    public EstadoGestor getEstadoAtual() {
        return estadoAtual.getEstado();
    }

    public void setEstadoAtual(IEstado estado) {
        this.estadoAtual = estado;
    }

    public void listaAlunos() {
        gestor.listaAlunos();
    }

    public ArrayList<Proposta> getPropostas() {
        return gestor.getPropostas();
    }

    public ArrayList<Aluno> getAlunosAutopropostos(GestorPropostas propostas) {
        return gestor.getAlunosAutopropostos(propostas);
    }

    public ArrayList<String> getRegisto() {
        return gestor.getRegisto();
    }

    public ArrayList<Aluno> getCandidaturasRegistadas1() {
        return gestor.getCandidaturasRegistadas1();
    }

    public ArrayList<Aluno> getAlunosAutopropostos1() {
        return gestor.getAlunosAutopropostos1();
    }

    public ArrayList<Aluno> getCandidaturasRegistada(ArrayList<Proposta> candidaturaR) {
        return gestor.getCandidaturasRegistada(candidaturaR);
    }

    public void gerir(Comando comando) {
        setEstadoAtual(estadoAtual.gerir(comando));
    }
    public void gerirAlunos() {
        setEstadoAtual(estadoAtual.gerirAlunos());
    }
    public void gerirDoc() {
        setEstadoAtual(estadoAtual.gerirDoc());
    }
    public void gerirProp() {
        setEstadoAtual(estadoAtual.gerirProp());
    }
    public void voltar(){setEstadoAtual(estadoAtual.voltar());}
    public void proximaFase() {
        setEstadoAtual(estadoAtual.proximaFase());
    }
    public void faseAnterior() {
        setEstadoAtual(estadoAtual.faseAnterior());
    }
    public void recomecar() {
        setEstadoAtual(estadoAtual.recomecar());
    }

    public GestorPropostas getCandidaturas() {
        return gestor.getCandidaturas();
    }

    public ArrayList<Aluno> getAlunosCandidatura() {
        return gestor.getAlunosCandidatura();
    }

    public void listaCandidaturas() {
        gestor.listaCandidaturas();
    }

    public void fechaFase() {setEstadoAtual(estadoAtual.fechaFase());}

    public ArrayList<Aluno> getAlunos() {
        return gestor.getAlunos();
    }

    public Docente consultarDocente(String email) {
        return gestor.consultarDocente(email);
    }

    public boolean inserirProposta(Proposta x) {
        return gestor.inserirProposta(x);
    }

    public Proposta consultaProposta(String codI) {
        return gestor.consultaProposta(codI);
    }

    public Proposta consultaCandidatura(String codI) {
        return gestor.consultaCandidatura(codI);
    }

    public void inserirAluno(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, String aceder) {
        gestor.inserirAluno(nrE, nome, email, siglaC, siglaR, classificacao, aceder);
    }

    public void consultarAluno(long nrE) {
        gestor.consultarAluno(nrE);
    }

    public void eliminarAluno(long nrE) {
        gestor.eliminarAluno(nrE);
    }

    public boolean obterInfoFich(String nomeFich) {
        return gestor.obterInfoFich(nomeFich);
    }

    public void Editar(long nrAluno) {
        gestor.Editar(nrAluno);
    }

    public boolean Inserir(Docente x) {
        return gestor.Inserir(x);
    }

    public void Eliminar(String email) {
        gestor.Eliminar(email);
    }

    public void Editar(String email) {
        gestor.Editar(email);
    }

    public ArrayList<Proposta> getPropostas(boolean autoproposta, boolean propostaD, boolean candidatura, boolean nCandidatura) {
        return gestor.getPropostas(autoproposta, propostaD, candidatura, nCandidatura);
    }

    public ArrayList<Aluno> atribuiPropostas() {
        return gestor.atribuiPropostas();
    }

    public ArrayList<Aluno> getAlunosAtribuidos() {
        return gestor.getAlunosAtribuidos();
    }

    public ArrayList<Aluno> getAlunosNAtribuidos() {
        return gestor.getAlunosNAtribuidos();
    }

    public String atribuiManual(long nrAluno, String codI) {
        return gestor.atribuiManual(nrAluno, codI);
    }

    public ArrayList<Proposta> getPropostasDisponiveis(boolean autoproposta, boolean propostaD, boolean atribuidas, boolean nAtribuidas) {
        return gestor.getPropostasDisponiveis(autoproposta, propostaD, atribuidas, nAtribuidas);
    }

    public ArrayList<Docente> getDocenteAtribuido() {
        return gestor.getDocenteAtribuido();
    }

    public boolean atribuirDocenteManual(String email, String codI) {
        return gestor.atribuirDocenteManual(email, codI);
    }

    public boolean inserirCandidatura(String codI) {
        return gestor.inserirCandidatura(codI);
    }

    public void atribuiProjetos1() {
        gestor.atribuiProjetos1();
    }

    public ArrayList<Aluno> getAlunosProDocAtr() {
        return gestor.getAlunosProDocAtr();
    }

    public ArrayList<Aluno> getAlunosProDocNAtr() {
        return gestor.getAlunosProDocNAtr();
    }

    public double mediaProDocentes() {
        return gestor.mediaProDocentes();
    }

    public double maxProDocentes() {
        return gestor.maxProDocentes();
    }

    public double minProDocentes() {
        return gestor.minProDocentes();
    }

    public ArrayList<Aluno> getAlunosNAtrCPro() {
        return gestor.getAlunosNAtrCPro();
    }

    public ArrayList<Proposta> getPropostasDis() {
        return gestor.getPropostasDis();
    }

    public ArrayList<Docente> getDocentes() {
        return gestor.getDocentes();
    }

    public ArrayList<Proposta> getPropostasAtribuidas() {
        return gestor.getPropostasAtribuidas();
    }
    public boolean guardaSnapShot(String nomeFich) {

        try {
            FileOutputStream fos = new FileOutputStream(nomeFich);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}

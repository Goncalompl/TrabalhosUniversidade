package pt.isec.pa.apoio_poe.model.data.alunos;

import pt.isec.pa.apoio_poe.model.data.propostas.GestorPropostas;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;

public class Aluno {

    private long nrEstudante;
    private String nome;
    private String email;
    private String siglaC;
    private String siglaR;
    private double classificacao;
    private boolean acederEstagio;
    private GestorPropostas propostas;


    public Aluno(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, boolean aceder){
        this.nrEstudante = nrE;
        this.nome = nome;
        this.email = email;
        this.siglaC = siglaC;
        this.siglaR = siglaR;
        this.classificacao = classificacao;
        this.acederEstagio = aceder;
    }

    @Override
    public String toString() {
        return "Numero de Estudante: " + nrEstudante + "\nNome: " + nome + "\nEmail: " + email + "\nSigla Curso" + siglaC + "\nSigla Ramo " + siglaR + "\nClassificação: " + classificacao + "\nPode acededer a estagio: " + acederEstagio;
    }

    public long getNrEstudante() {
        return this.nrEstudante;
    }
    public String getNome() {
        return this.nome;
    }
    public String getEmail() {
        return this.email;
    }
    public String getSiglaC() {
        return this.siglaC;
    }
    public String getSiglaR() {
        return this.siglaR;
    }
    public double getClassificacao() {
        return this.classificacao;
    }
    public boolean getAcederEstagio() {
        return this.acederEstagio;
    }
    public GestorPropostas getPropostas() {return this.propostas;}


    public void setNrEstudante(long nrEstudante){
        this.nrEstudante = nrEstudante;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSiglaC(String siglaC) {
        this.siglaC = siglaC;
    }
    public void setSiglaR(String siglaR) {
        this.siglaR = siglaR;
    }
    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }
    public void setAcederEstagio(boolean x) {
        this.acederEstagio = x;
    }


    public void adicionaPropostas(Proposta ... propostas) {
        for (int i = 0; i < propostas.length; i++) {
            this.propostas.inserir(propostas[i]);
        }

    }

    public boolean temPropostas() {
        return !propostas.getPropostas().isEmpty();
    }

}

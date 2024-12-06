package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.alunos.GestorAlunos;
import pt.isec.pa.apoio_poe.model.data.docente.Docente;
import pt.isec.pa.apoio_poe.model.data.docente.GestorDocentes;
import pt.isec.pa.apoio_poe.model.data.propostas.*;

import javax.print.Doc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Gestor implements Serializable {
    private final GestorPropostas propostas;
    private final GestorPropostas candidaturas;
    private final GestorAlunos alunos;
    private final GestorDocentes docentes;

    public ArrayList<Proposta> getPropostas() {
        return propostas.getPropostas();
    }

    public ArrayList<Aluno> getCandidaturasRegistada(ArrayList<Proposta> candidaturaR) {
        return alunos.getCandidaturasRegistada(candidaturaR);
    }

    public ArrayList<Aluno> getCandidaturasRegistadas1(){
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno = null;
        for (Proposta proposta: candidaturas.getPropostas()) {
            if(proposta.getNrAluno() != 0){
                aluno = alunos.consultar(proposta.getNrAluno());
                    if(aluno == null)
                        continue;
                 resultado.add(aluno);
            }
        }

        return resultado;
    }

    public ArrayList<Aluno> getAlunosAutopropostos(GestorPropostas propostas) {
        return alunos.getAlunosAutopropostos(propostas);
    }

    public ArrayList<Aluno> getAlunosAutopropostos1() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno = null;
        for (Proposta proposta: propostas.getPropostas()) {
            if(proposta instanceof Autoproposto){
                aluno = alunos.consultar(proposta.getNrAluno());
                resultado.add(aluno);}


        }

        return resultado;
    }

    private final ArrayList<String> registo;

    public Gestor() {
        this.alunos = new GestorAlunos();
        this.docentes = new GestorDocentes();
        candidaturas = new GestorPropostas();
        propostas = new GestorPropostas();
        registo = new ArrayList<>();
    }

    public boolean inserirProposta(Proposta x) {
        return candidaturas.inserir(x);
    }

    public void inserirPropE(String codI, String titulo, String area, String entidade, long aluno) {

        Estagio x = new Estagio(codI,titulo,area,entidade,aluno);
        propostas.inserir(x);
        registo.add("Estagio inserido com sucesso");
    }

    public void inserirPropP(String codI, String titulo, String ramo, String emailPro, long nrEstudante) {

        Projeto x = new Projeto(codI,titulo,ramo,emailPro,nrEstudante);
        propostas.inserir(x);
        registo.add("Projeto inserido com sucesso");
    }

    public Proposta consultaProposta(String codI) {
        return propostas.consulta(codI);
    }



    public Proposta consultarEstagio(String codI) {
        return propostas.consultaE(codI);
    }

    public Proposta consultaE(String codI) {
        return propostas.consultaE(codI);
    }
    public boolean inserirCandidatura(String codI){
        Proposta proposta = consultaProposta(codI);
        if(proposta == null) {
            return false;
        }
        candidaturas.inserir(proposta);
        return true;
    }

    public Proposta consultaCandidatura(String codI) {
        return candidaturas.consulta(codI);
    }



    public void inserirAluno(long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, String aceder)
    {
        Aluno x = new Aluno(nrE, nome, email, siglaC, siglaR, classificacao, aceder.equalsIgnoreCase("s"));
        alunos.inserir(x);
        registo.add("Aluno inserido com sucesso");
    }

    public void editarAluno(long nrEstudante, long nrE, String nome, String email, String siglaC, String siglaR, double classificacao, String aceder) {
        Aluno aluno = alunos.consultar(nrEstudante);
        aluno.setNrEstudante(nrE);
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setSiglaC(siglaC);
        aluno.setSiglaR(siglaR);
        aluno.setClassificacao(classificacao);

        aluno.setAcederEstagio(Objects.equals(aceder, "S"));

        //alunos.editar(nrEstudante);
    }

    public boolean guardarEstagios(String nomeFich) {
        return propostas.guardarEstagios(nomeFich);
    }

    public int getTamanhoAlunos() {
        return alunos.size();
    }

    public void editarDoc(String emailDco, String email, String nome) {
        Docente docente = docentes.consultar(emailDco);
        docente.setEmail(email);
        docente.setNome(nome);

        docentes.editar(emailDco);
    }




    public void inserirDoc(String nome, String email){

        Docente x = new Docente(nome,email);
        docentes.inserir(x);
        registo.add("Doc inserido com sucesso");
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos.getAlunos();
    }

    public void consultarAluno(long nrE) {
        Aluno aluno = alunos.consultar(nrE);
        if(aluno != null)
            registo.add(alunos.consultar(nrE).toString());
        else registo.add("Nenhum aluno encontrado");
    }

    public void eliminarAluno(long nrE) {
        alunos.eliminar(nrE);
    }

    public boolean obterInfoFichEstagio(String nomeFich) {
        return propostas.obterInfoFichEstagio(nomeFich);
    }

    public boolean obterInfoFichProjeto(String nomeFich) {
        return propostas.obterInfoFichProjeto(nomeFich);
    }

    public boolean obterInfoFichAutoproposto(String nomeFich) {
        return propostas.obterInfoFichAutoproposto(nomeFich);
    }

    public boolean obterInfoFichDoc(String nomeFich) {
        return docentes.obterInfoFichDoc(nomeFich);
    }

    public boolean obterInfoFich(String nomeFich) {
        return alunos.obterInfoFich(nomeFich);
    }

    public void Editar(long nrAluno) {
        alunos.editar(nrAluno);
    }

    public boolean Inserir(Docente x) {
        return docentes.inserir(x);
    }

    public ArrayList<Docente> getDocentes() {
        return docentes.getDocentes();
    }

    public Docente consultarDocente(String email) {
        Docente docente = docentes.consultar(email);
        if(email != null)
            registo.add(String.valueOf(docentes.consultar(email)));
        else registo.add("Nenhum Docente encontrado");


        return docentes.consultar(email);
    }

    public void Eliminar(String email) {
        docentes.eliminar(email);
    }

    public void Editar(String email) {
        docentes.editar(email);
    }

    public ArrayList<Proposta> getPropostasAtribuidas() {
        ArrayList<Proposta> resultado = new ArrayList<>();
        for (Proposta proposta: propostas.getPropostas()) {
            if(proposta.isAlunoAtribuido())
                resultado.add(proposta);
        }
        return resultado;
    }

    public void listarPropostasAtribuidas() {
        for (Proposta proposta: propostas.getPropostas()) {
            if(proposta.isAlunoAtribuido())
                registo.add(proposta.toString());
        }


    }

    public void editar(String email) {
        docentes.editar(email);
    }

    public ArrayList<Proposta> getPropostas(boolean autoproposta, boolean propostaD, boolean candidatura, boolean nCandidatura) {
        ArrayList<Proposta> resultado = new ArrayList<>();
        GestorPropostas temp = new GestorPropostas();
        boolean flag;
        if (candidatura) {
            resultado.addAll(candidaturas.getPropostas(autoproposta, propostaD));
        }
        if (nCandidatura) {
            for (int i = 0; i < propostas.size(); i++) {
                flag = false;
                for (int j = 0; j < candidaturas.size(); j++) {
                    if (candidaturas.get(j).getNrAluno() == propostas.get(i).getNrAluno()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    temp.inserir(propostas.get(i));
                }

            }
        }
        resultado.addAll(temp.getPropostas(autoproposta, propostaD));
        if (!candidatura && !nCandidatura)
            resultado.addAll(propostas.getPropostas(autoproposta, propostaD));
        return resultado;
    }

    public ArrayList<Aluno> atribuiPropostas() {
        Aluno outro;
        ArrayList<Aluno> conflito = new ArrayList<>();
        for (Aluno aluno : getAlunosNAtribuidos()) {
            if (!aluno.getAcederEstagio())
                break;
            for (Proposta proposta : aluno.getPropostas().getPropostas()) {
                if (proposta.isAlunoAtribuido()) {
                    outro = alunos.consultar(proposta.getNrAluno());
                    if (aluno.getClassificacao() == outro.getClassificacao()) {
                        conflito.add(outro);
                        conflito.add(aluno);
                        return conflito;
                    }

                } else {
                    proposta.setNrAluno(aluno.getNrEstudante());
                    break;
                }
            }
        }

        return null;
    }

    public ArrayList<Aluno> getAlunosAtribuidos() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        for (Aluno aluno : alunos.getAlunos()) {
            for (Proposta proposta : propostas.getPropostas()) {
                if (aluno.getNrEstudante() == proposta.getNrAluno()) {
                    resultado.add(aluno);
                    break;
                }
            }
        }
        return resultado;
    }

    public void listarAlunosAtribuidos() {
        for (Aluno aluno : alunos.getAlunos()) {
            for (Proposta proposta : propostas.getPropostas()) {
                if (aluno.getNrEstudante() == proposta.getNrAluno()) {
                    registo.add(aluno.toString());
                    break;
                }
            }
        }

    }

    public GestorPropostas getCandidaturas() {
        return candidaturas;
    }

    public ArrayList<Aluno> getAlunosNAtribuidos() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        boolean flag;
        for (Aluno aluno : alunos.getAlunos()) {
            flag = false;
            for (Proposta proposta : propostas.getPropostas()) {
                if (aluno.getNrEstudante() == proposta.getNrAluno()) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                resultado.add(aluno);
        }
        return resultado;
    }

    public void listarAlunosNAtribuidos() {
        boolean flag;
        for (Aluno aluno : alunos.getAlunos()) {
            flag = false;
            for (Proposta proposta : propostas.getPropostas()) {
                if (aluno.getNrEstudante() == proposta.getNrAluno()) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                registo.add(aluno.toString());
        }

    }

    public String atribuiManual(long nrAluno, String codI) {
        Proposta proposta = propostas.consulta(codI);
        if (proposta == null)
            return "Proposta nao encontrada";
        if (proposta.isAlunoAtribuido())
            return "Proposta ja foi atribuida";
        if (alunos.consultar(nrAluno) == null)
            return "Aluno nao encontrado";
        proposta.setNrAluno(nrAluno);
        return "Proposta atribuida com sucesso";
    }

    public ArrayList<Proposta> getPropostasDisponiveis(boolean autoproposta, boolean propostaD, boolean atribuidas, boolean nAtribuidas) {
        ArrayList<Proposta> resultado = new ArrayList<>();
        GestorPropostas temp = new GestorPropostas();
        boolean flag;
        if (atribuidas) {
            for (Proposta proposta : propostas.getPropostas(autoproposta, propostaD)) {
                if (proposta.isAlunoAtribuido())
                    resultado.add(proposta);
            }

        }
        if (nAtribuidas) {
            for (Proposta proposta : propostas.getPropostas(autoproposta, propostaD)) {
                if (!proposta.isAlunoAtribuido())
                    resultado.add(proposta);
            }
        }
        resultado.addAll(temp.getPropostas(autoproposta, propostaD));
        if (!atribuidas && !nAtribuidas)
            resultado.addAll(propostas.getPropostas(autoproposta, propostaD));
        return resultado;
    }

    public boolean eliminarProp(String codI) {
        return propostas.eliminarProp(codI);
    }

    public ArrayList<Docente> getDocenteAtribuido() {
        ArrayList<Docente> resultado = new ArrayList<>();
        Docente docente;
        for (Proposta proposta : candidaturas.getProDocenteAtribuido()) {
            docente = docentes.consultar(proposta.getEmailDoc());
            if (docente != null)
                resultado.add(docente);
        }
        return resultado;
    }

    public boolean atribuirDocenteManual(String email, String codI) {
        Docente docente;
        Proposta proposta;

        docente = docentes.consultar(email);
        proposta = candidaturas.consulta(codI);

        if (docente == null || proposta == null)
            return false;


        if (proposta.isDocenteAtribuido())
            return false;

        proposta.setEmailDoc(email);
        return true;
    }

    public ArrayList<Aluno> getAlunosProDocAtr() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno;
        for (Proposta candidatura : candidaturas.getProDocenteAtribuido()) {
            aluno = alunos.consultar(candidatura.getNrAluno());
            if(aluno != null)
                resultado.add(aluno);
        }
        return resultado;
    }

    public void listarAlunosProDocAtr(){
            Aluno aluno;
            for (Proposta candidatura : candidaturas.getProDocenteAtribuido()) {
                aluno = alunos.consultar(candidatura.getNrAluno());
                if(aluno != null)
                    registo.add(aluno.toString());

            }

        }


    public ArrayList<Aluno> getAlunosProDocNAtr() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno;
        for (Proposta candidatura : candidaturas.getProDocenteNAtribuido()) {
            aluno = alunos.consultar(candidatura.getNrAluno());
            if(aluno != null)
                resultado.add(aluno);
        }
        return resultado;
    }

    public void listarAlunosProDocNAtr() {
        Aluno aluno;
        for (Proposta candidatura : candidaturas.getProDocenteNAtribuido()) {
            aluno = alunos.consultar(candidatura.getNrAluno());
            if(aluno != null)
                registo.add(aluno.toString());

        }

    }

    public double mediaProDocentes () {
        double media = 0;
        for (Docente docente: docentes.getDocentes()) {
            media += candidaturas.contaProDocente(docente);
        }
        media /= docentes.size();

        return media;
    }

    public double maxProDocentes () {
        int maior = -1;
        int nCandidaturas;
        for (Docente docente: docentes.getDocentes()) {
            nCandidaturas = candidaturas.contaProDocente(docente);
            if(nCandidaturas > maior)
               maior = nCandidaturas;
        }

        return maior;
    }

    public double minProDocentes () {
        int menor = candidaturas.size()+1;
        int nCandidaturas;
        for (Docente docente: docentes.getDocentes()) {
            nCandidaturas = candidaturas.contaProDocente(docente);
            if(nCandidaturas < menor)
                menor = nCandidaturas;
        }
        if(menor == candidaturas.size()+1)
            return -1;
        return menor;
    }

    public ArrayList<Aluno> getAlunosNAtrCPro() {
        ArrayList<Aluno> resultado = new ArrayList<>();
        for (Aluno aluno : getAlunosNAtribuidos()) {
            if(aluno.temPropostas())
                resultado.add(aluno);
        }

        return resultado;
    }

    public ArrayList<Proposta> getPropostasDis() {
        return propostas.getPropostasDis();
    }

    public void listarPropostasDis(){
        for (Proposta proposta : propostas.getPropostasDis()) {
                registo.add(proposta.toString());
        }
    }

    public void listaAlunos() {

        for (Aluno aluno:getAlunos()) {
            registo.add(aluno.toString());
        }

    }

    public  void listaDocentes() {
        for (Docente docente:getDocentes()) {
            registo.add(docente.toString());
        }
    }

    public void listaPropostas(boolean autoproposta, boolean propostaD, boolean candidatura, boolean nCandidatura) {
        for (Proposta proposta : getPropostas(autoproposta, propostaD, candidatura, nCandidatura)) {
            registo.add(proposta.toString());
        }
    }

    public void listaCandidaturas() {
        for (Proposta candidatura: candidaturas.getPropostas()) {
            registo.add(candidatura.toString());
        }
    }

    public  void listaPropostas(String codI){
        for (Proposta candidatura: propostas.getPropostas()) {
            if(candidatura instanceof Estagio)
                if(Objects.equals(candidatura.getCodI(), codI))
                    registo.add(candidatura.toString());
        }
    }

    public  void listaPropostasProjeto(String codI){
        for (Proposta candidatura: propostas.getPropostas()) {
            if(candidatura instanceof Projeto)
                if(Objects.equals(candidatura.getCodI(), codI))
                    registo.add(candidatura.toString());
        }
    }

    public ArrayList<Aluno> getAlunosCandidatura(){
        ArrayList<Aluno> resultado = new ArrayList<>();
        Aluno aluno = null;
        for (Proposta candidatura: candidaturas.getPropostas()) {
            if(candidatura.getNrAluno() != 0 )
                aluno = alunos.consultar(candidatura.getNrAluno());
                resultado.add(aluno);
        }
        return resultado;
    }

    public void listarAlunosCandidatura(){
        for (Aluno aluno: alunos.getAlunosCandidatura(candidaturas)) {
            registo.add(aluno.toString());
        }
    }

    public void listarAlunosNCandidatura(){
        for (Aluno aluno: alunos.getAlunosNCandidatura(candidaturas)) {
            registo.add(aluno.toString());
        }
    }
    public void listarAlunosAutoPropostos(){
        for (Aluno aluno: alunos.getAlunosAutopropostos(candidaturas)) {
            registo.add(aluno.toString());
        }
    }


    public ArrayList<String> getRegisto() {
        return registo;
    }

    public void clearRegisto() {
        registo.clear();
    }

    public void atribuiProjetos1() {
        for (Proposta projeto : candidaturas.getPropostas()) {
            projeto.setEmailDoc(projeto.getEmailDoc());
        }
    }

}


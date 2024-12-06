package pt.isec.pa.apoio_poe.model.data.docente;

import pt.isec.pa.apoio_poe.model.data.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.GestorPropostas;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GestorDocentes {
    private ArrayList<Docente> docentes = new ArrayList<Docente>();

    public boolean inserir(Docente x) {
        for (int i = 0; i < docentes.size(); i++) {
            if(docentes.get(i).getEmail() == x.getEmail()) {
                return false;
            }
        }
        docentes.add(x);
        return true;
    }

    public ArrayList<Docente> getDocentes() {
        return docentes;
    }

    public int size() {
        return docentes.size();
    }

    public Docente consultar(String email) {
        for (Docente docente : docentes)
            if (Objects.equals(docente.getEmail(), email))
                return docente;
        return null;
    }

    public void eliminar(String email) {
        for (int i = 0; i < docentes.size(); i++) {
            if(Objects.equals(email, docentes.get(i).getEmail())) {
                docentes.remove(i);
                System.out.println("Docente removido com sucesso");
            }
        }
    }

    public boolean verifica(Docente x) {
        for (int i = 0; i < docentes.size(); i++) {
            if (docentes.get(i) == x) {
                return true;
            }
        }
        return false;
    }

    public void editar(String email) {

        Docente x = consultar(email);
        Scanner scanner = new Scanner(System.in);
        int num;
        if(verifica(x) == false) {
            num = 4;
        } else {
            System.out.println("Insira os dados que pertende alterar:\n 1-Nome do pt.isec.pa.apoio_poe.model.data.Docente\n 2-Email do pt.isec.pa.apoio_poe.model.data.Docente\n 3-Papel do pt.isec.pa.apoio_poe.model.data.Docente");
            num =scanner.nextInt();
        }
        Scanner scanner1 = new Scanner(System.in);
        switch (num) {
            case 1:
                System.out.println("Introduza um novo Nome do pt.isec.pa.apoio_poe.model.data.Docente: ");
                String nome = scanner1.nextLine();
                x.setNome(nome);
                break;
            case 2:
                System.out.println("Introduza um novo Email: ");
                String email1 = scanner1.nextLine();
                x.setEmail(email1);
                break;
            case 3:
                int papel = 0;
                while(papel != 1 && papel != 2) {
                    System.out.println("Introduza um novo  Papel do pt.isec.pa.apoio_poe.model.data.Docente: 1-Orientador  2-Proponente ");
                    papel = scanner1.nextInt();
                    /*if (papel == 1) {
                        x.setPapel(PapelDocente.ORIENTADOR);
                    } else if (papel == 2) {
                        x.setPapel(PapelDocente.PROPONENTE);
                    }*/
                }
                break;
            case 4:
                System.out.println("O pt.isec.pa.apoio_poe.model.data.Docente nao esta inserido ");
                break;

        }
    }

    public boolean obterInfoFichDoc(String nomeFich) {
        File ficheiro = new File(nomeFich);
        Scanner scanner;
        String line;
        String [] splitted;
        String nome , email;

        try {
            scanner = new Scanner(ficheiro);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            splitted = line.split(",");
            if (splitted.length != 2) {
                scanner.close();
                return false;
            }
            nome = splitted[0];
            email = splitted[1];
            inserir(new Docente(nome,email));
        }

        scanner.close();
        return true;
    }

    public void atribuiProjetos(ArrayList<Projeto> projetos) {
        for (Projeto projeto : projetos) {
            projeto.setEmailDoc(projeto.getEmailPro());
        }
    }
}

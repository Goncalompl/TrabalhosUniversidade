
#include <ncurses.h> 
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <fcntl.h>
#include <pthread.h>
#include <time.h>

#include "jogoUI.h"

#define LINHAS 15
#define COLUNAS 85
#define MOTOR_LOCK_FILE "motor.lock"

Jogador jogadortemp;
Mensagem msg;
 char mapa[LINHAS][COLUNAS];

Jogador jogadoresAtivos[MAX_PLAYERS];
int njogadores = 0;

struct argsTeclado {
    int tipo;
    WINDOW* janela;
    char caracter;
};
struct argsContador{
    WINDOW* janela;
};
struct argsRecebeJogadores{
    WINDOW* janela;
};
struct argsMapa{
    WINDOW* janela;
};
struct argsRecebemsg{
    WINDOW* janela;
};

#define PIPE_MOTOR "PIPEMOTOR"
#define PIPE_ATUALIZA "ATUALIZA"
#define FIFO_JOGADOR "jogador_%d"
#define FIFO_MAPA "PIPEMAPA"
#define PIPE_WIN "PIPEWIN"


  int posX;
  int posY;
char fifo_jogador[50];
char info[200];
//criar janelas

void enviamsg(char nome[100],char mensagem[100]){
    strcpy(msg.nome,nome);
   strcpy(msg.mensagem,mensagem);
    char fifo_jogador[20];
    for(int i = 0; i <njogadores;i++){
        if(strcmp(jogadoresAtivos[i].nome, nome) == 0){
        sprintf(fifo_jogador, FIFO_JOGADOR, jogadoresAtivos[i].pid);
        int playerFd = open(fifo_jogador, O_WRONLY);
        if (playerFd == -1) {
            continue;
        }

        // Escrever no fifo
       int size = write(playerFd,&msg,sizeof(msg));;

        close(playerFd);
        }
    }
    
}

void* recebemsg(void* arg) {
        struct argsRecebemsg* args = (struct argsRecebemsg*)arg;
    WINDOW* janela = args->janela;
    char fifo_jogador[20];
    sprintf(fifo_jogador, FIFO_JOGADOR, jogadortemp.pid);

    int playerFd = open(fifo_jogador, O_RDONLY);
    if (playerFd == -1) {
        perror("Error opening player FIFO for reading");
        exit(EXIT_FAILURE);
    }

do{
    // Read the message struct from the player's FIFO
    read(playerFd, &msg, sizeof(msg));
   

    mvwaddstr(janela, 3, 3, msg.mensagem);
        wrefresh(janela);
}while(1);

    close(playerFd);
}

void desenhaMapa(WINDOW *janela, int tipo)
{

    if (tipo == 1)
    {
        scrollok(janela, TRUE); 
        mvwprintw(janela,1,3, "#> Carregue no espaco para inserir comando");
        wborder(janela, '*', '*', '*', '*', '*', '*', '*', '*'); 
        
    }
    if(tipo == 2)
    {
        keypad(janela, TRUE); 
        wclear(janela);
        wborder(janela, '*', '*', '*', '*', '*', '*', '*', '*'); 
   for (int i = 0; i < LINHAS; i++) {
        for (int j = 0; j < COLUNAS; j++) {
            mvwprintw(janela, i, j, "%c", mapa[i][j]);
        }
    }
    //mvwaddch(janela, posX, posY, caracter);
            }
            else{
        scrollok(janela, TRUE); 
        mvwprintw(janela,1,2, "Info Jogadores");
        wborder(janela, '*', '*', '*', '*', '*', '*', '*', '*'); 
            }



        

    refresh(); 
    wrefresh(janela); 
}

void encerraJogoUI() {
    // Abra o FIFO do motor
    int fd = open(PIPE_MOTOR, O_WRONLY);

    if (fd == -1) {
        perror("Erro ao abrir PIPE_MOTOR");
        exit(EXIT_FAILURE);
    }

    // Envie um sinal para encerrar o jogoUI
    char mensagem[] = "exit";
    write(fd, mensagem, strlen(mensagem) + 1);

    // Feche o descritor de arquivo
    close(fd);
}

void winJogoUI() {
    // Abra o FIFO do motor
    int fd = open(PIPE_WIN, O_WRONLY);

    if (fd == -1) {
        perror("Erro ao abrir PIPE_MOTOR");
        exit(EXIT_FAILURE);
    }

    // Envie um sinal para encerrar o jogoUI
    char mensagem[] = "o Jogo terminou, vitoria";
    write(fd, mensagem, strlen(mensagem) + 1);

    // Feche o descritor de arquivo
    close(fd);
}

void enviaUser(char nome[100],int x, int y){
    strcpy(jogadortemp.nome,nome);
    jogadortemp.caracter = nome[0];
    jogadortemp.x = x;
    jogadortemp.y = y;
    jogadortemp.pid = getpid();
     sprintf(fifo_jogador, FIFO_JOGADOR, jogadortemp.pid);
    //printf("%s da struct e pid struct %d\n",jogadortemp.nome,jogadortemp.pid);
    int fd = open(PIPE_MOTOR,O_WRONLY);
    int size = write(fd,&jogadortemp,sizeof(jogadortemp));
    //printf("Escrevi... %s %c %d %d %d\n", jogadortemp.nome, jogadortemp.caracter,jogadortemp.pid,jogadortemp.x, jogadortemp.y);

    if(access(fifo_jogador, F_OK) != 0) {
        mkfifo(fifo_jogador, 0666);
    }

}
void* recebeMapa(void* arg) {
    struct argsMapa* args = (struct argsMapa*)arg;
    WINDOW* janela = args->janela;

    if (access(FIFO_MAPA, F_OK) != 0) {
        mkfifo(FIFO_MAPA, 0666);
    }

    while (1) {
        int fd = open(FIFO_MAPA, O_RDONLY);
        if (fd == -1) {
            perror("Erro a abrir o FIFO_MAPA");
            exit(EXIT_FAILURE);
        }

        ssize_t bytes_read = read(fd, mapa, sizeof(mapa));
        if (bytes_read == -1) {
            perror("Erro a ler do FIFO_MAPA");
            exit(EXIT_FAILURE);
        }

        close(fd);

        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                mvwprintw(janela, i, j, "%c", mapa[i][j]);
            }
        }
        desenhaMapa(janela, 2);
        wrefresh(janela);
    }

    pthread_exit(NULL);
}

void enviaPosXY(int x, int y){
    jogadortemp.x = x;
    jogadortemp.y = y;
 
    //printf("%s da struct e pid struct %d\n",jogadortemp.nome,jogadortemp.pid);
    //Abre pipe para escrita
    int fd = open(PIPE_ATUALIZA, O_WRONLY);
    //escreve no pipe
    int size = write(fd,&jogadortemp,sizeof(jogadortemp));
    //printf("Escrevi... %s %c %d %d %d\n",jogadortemp.nome, jogadortemp.caracter,jogadortemp.pid,jogadortemp.x,jogadortemp.y);

}

void* recebeLista(void* arg) {
    struct argsContador* args = (struct argsContador*)arg;
    WINDOW* janela = args->janela;

    // Abrir pipe para escrita
    int fj = open(fifo_jogador, O_RDWR);
    if (fj == -1) {
        unlink(fifo_jogador);
        exit(1);
    }

    do {
        // Ler do pipe
        int n = read(fj, info, sizeof(info) - 1);

    

        // Atualizar a janela com a lista de jogadores
        mvwaddstr(janela, 2, 1, info);
        wrefresh(janela);
        sleep(1);
    } while (1);

    // Fechar o fifo do client
    close(fj);
}



//teclado
void* trataTeclado(void* arg) 
{

       struct argsTeclado* args = (struct argsTeclado*)arg;
    WINDOW* janelaTopo = args->janela;
    WINDOW* janelaBaixo = args[1].janela; // Access the second window in the array
    char caracter = args->caracter;
    keypad(janelaTopo, TRUE);       
    wmove(janelaTopo, 1, 1);        
    //nota a posição é relativa à janelaTopo e não ao ecrã.
int tecla = wgetch(janelaTopo); // MUITO importante: o input é feito sobre a janela em questão, neste caso na janelaTopo
    char comando[100];             // string que vai armazenar o comando
//x=1 y=3
    while (tecla != 113) // trata as tecla até introduzirem a letra q. O código asci de q é 113
    {

   if (tecla == KEY_UP && posX > 0 && mapa[posX - 1][posY] != '#'&& mapa[posX - 1][posY] != 'O' && mapa[posX - 1][posY] != 'X') {
    posX--;
    enviaPosXY(posX, posY);
} else if (tecla == KEY_RIGHT && posY < COLUNAS - 1 && mapa[posX][posY + 1] != '#' && mapa[posX][posY + 1] != 'O' && mapa[posX][posY + 1] != 'X') {
    posY++;
    enviaPosXY(posX, posY);
} else if (tecla == KEY_LEFT && posY > 0 && mapa[posX][posY - 1] != '#' && mapa[posX][posY - 1] != 'O' && mapa[posX][posY - 1] != 'X') {
    posY--;
    enviaPosXY(posX, posY);
} else if (tecla == KEY_DOWN && posX < LINHAS - 1 && mapa[posX + 1][posY] != '#' && mapa[posX + 1][posY] != 'O' && mapa[posX + 1][posY] != 'X') {
    posX++;
    enviaPosXY(posX, posY);
}
        else if (tecla == ' ') // trata a tecla espaço
        {  // a tecla espaço ativa a janela inferior e tem o scroll ativo
          //  o wprintw e o wgetstr devem ser utilizados em janelas que tem o scroll ativo.
          wclear(janelaBaixo);
        wborder(janelaBaixo, '*', '*', '*', '*', '*', '*', '*', '*'); 
        wrefresh(janelaBaixo);
            echo();      
            mvwprintw(janelaBaixo,1,3, "#> "); // utilizada para imprimir. 
              wrefresh(janelaBaixo);  
                                            //nota como a janelaBaixo tem o scroll ativo, ele vai imprimindo linha a linha
            wgetstr(janelaBaixo, comando);  // para receber do teclado uma string na "janelaBaixo" para a variavel comando
            char *comando_token;

        
        comando_token = strtok(comando, " \n"); // Remove o caractere de nova linha

        if (strncmp(comando_token, "players", strlen("players")) == 0) {
            wclear(janelaBaixo);
            wborder(janelaBaixo, '*', '*', '*', '*', '*', '*', '*', '*');
            wrefresh(janelaBaixo);
   mvwaddstr(janelaBaixo, 2, 1, info);
        wrefresh(janelaBaixo);
}    else if (strncmp(comando_token, "exit", strlen("exit")) == 0) {
            enviaPosXY(-1, -1);
            encerraJogoUI();   
            endwin();           
            exit(EXIT_SUCCESS); 
        }else if (strncmp(comando_token, "msg", strlen("msg")) == 0) {
            char* nome_utilizador = strtok(NULL, " \n");
            char* mensagem = strtok(NULL, "\n");  // Considering that the mensagem can contain spaces

            if (nome_utilizador != NULL && mensagem != NULL) {
                mvwprintw(janelaBaixo,2,3, "comando valido: msg para %s - mensagem: %s", nome_utilizador, mensagem);
                enviamsg(nome_utilizador,mensagem);
                wrefresh(janelaBaixo);
            } else {
                mvwprintw(janelaBaixo,2,3, "comando Invalido ");
            }
        } else {
            mvwprintw(janelaBaixo,2,3, "comando Invalido ");
        }       

            
            
            noecho(); //voltar a desabilitar o que o utilizador escreve
            wrefresh(janelaBaixo); //sempre que se escreve numa janela, tem de se fazer refresh
        }
        wmove(janelaTopo, 1, 1); // posiciona o cursor (visualmente) na posicao 1,1 da janelaTopo
        tecla = wgetch(janelaTopo); //espera que o utilizador introduza um inteiro. Importante e como já referido anteriormente introduzir a janela onde queremos receber o input
        wclear(janelaBaixo);
        wborder(janelaBaixo, '*', '*', '*', '*', '*', '*', '*', '*'); 
         mvwprintw(janelaBaixo,1,3, "#> Carregue no espaco para inserir comando");
        wrefresh(janelaBaixo);
    }
}


int main(int argc, char *argv[])
{

    if (access(MOTOR_LOCK_FILE, F_OK) != 0) {
        printf("O motor não está em execução. O jogoUI não pode ser iniciado.\n");
        exit(EXIT_FAILURE);
    }

      if (argc != 2) {
        printf("Nome nao foi introduzido\n");
        return EXIT_FAILURE;
    }


    printf("Bem-vindo, %s!\n", argv[1]);
    printf("Digite 'q' para sair ou c para continuar .\n");
    char teste;
    scanf("%c",&teste);
char caracter = argv[1][0];
  srand(time(NULL));
    do {
        posX = rand() % 13 + 1;  
        posY = 1; 
    } while (mapa[posX][posY] == '#');
enviaUser(argv[1],posX,posY);

pthread_t threadTeclado, threadLista, threadMapa, threadMensagem;
  
    // Inicializa assuntos de ncurses
    // -> mostra que tipo de inicialização pode ser necessária
    initscr(); // Obrigatorio e sempre a primeira operação de ncurses
    raw();  // desativa o buffer de input, cada tecla é lida imediatamente
    noecho();  // desliga o echo no ecrã, para voltar ativar devem utilizar a função echo();
    keypad(stdscr, TRUE);  // habilita as teclas  especiais, exemplo -> <-
    attrset(A_DIM);  //altera o brilho no print das janelas
    //as duas linhas seguintes são utilizadas para imprimir no ecrã, não utilizando nenhuma janela
    //as coordenas são relativas ao ecrã                                                               
    mvprintw(1, 10, "[ Up,Down,Right e Left comandos janela de cima ]");  // mensagem fora da janela, na linha 1, coluna 10 do ecrã
    mvprintw(2, 10, "[ space - muda para o foco da janela de baixo / q - sair ]"); // mensagem fora da janela, na linha 2, coluna 10 do ecrã
    WINDOW *janelaTopo = newwin(15, 82, 3, 1);  // Criar janela para a matriz de jogo, tendo os parametro numero de linhas,numero de colunas, posição onde começa a janela  e posição onde termina
    WINDOW *janelaBaixo = newwin(10, 82, 28, 1);  
    WINDOW *janelaMeio = newwin(8, 82, 19, 1);  
    desenhaMapa(janelaTopo, 2);  // função exemplo que desenha o janela no ecrã
    desenhaMapa(janelaBaixo, 1); // função exemplo que desenha o janela no ecrã
    desenhaMapa(janelaMeio,3);
       struct argsTeclado argsTopo = {1, janelaTopo, caracter};
       struct argsMapa argsMapa = {janelaTopo};
    struct argsTeclado argsBaixo = {2, janelaBaixo, caracter};
     struct argsTeclado argsArray[3] = {argsTopo, argsBaixo,caracter};
     struct argsContador argsContador = {janelaMeio};
     struct argsRecebeJogadores argsRecebeJogadores = {janelaTopo};
      struct argsRecebemsg argsRecebemsg = {janelaBaixo};
    pthread_create(&threadTeclado, NULL, trataTeclado, argsArray);
    pthread_create(&threadLista, NULL, recebeLista, &janelaMeio);
     pthread_create(&threadMapa, NULL, recebeMapa, &argsMapa);
     pthread_create(&threadMensagem, NULL, recebemsg, &argsRecebemsg);
     pthread_join(threadTeclado, NULL);
      pthread_join(threadLista, NULL);
       pthread_join(threadMapa, NULL);
       pthread_join(threadMensagem, NULL);
    endwin();  // encerra a utilização do ncurses. Muito importante senão o terminal fica inconsistente (idem se sair por outras vias)

    return 0;
}
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/select.h>
#include <pthread.h>
#include <signal.h>
#include <sys/wait.h>
#include "jogoUI.h"

Jogador jogadores[10];
Jogador atualizaJogadores;



char nomesJogadores[100];
  int posX[10];
    int posY[10];

#define LINHAS 15   
#define COLUNAS 85
int njogadores = 0;
int nbloqueios = 0;
int nbloqueiosbot = 0;
char info[200];

char mapa[LINHAS][COLUNAS];

#define PIPE_MOTOR "PIPEMOTOR"
#define PIPE_ATUALIZA "ATUALIZA"
#define PIPE_MAPA "PIPEMAPA"
#define FIFO_JOGADOR "jogador_%d"
#define MOTOR_LOCK_FILE "motor.lock"

#define MAX_COMMAND_LENGTH 50
#define MAX_COMMAND_QUEUE 10

typedef struct {
    char comando[MAX_COMMAND_LENGTH];
} Comando;





void signalJogadorRepetido(int signum) {
    if (signum == SIGUSR1) {
        printf("Jogador ja existe a terminar... \n");
        exit(EXIT_SUCCESS);
    }
}

int verificaExistenciaJogador(char nome[100]){
    for(int i = 0; i < njogadores; i++){
        if(strcmp(nome, jogadores[i].nome) == 0){
            return 1; // Jogador encontrado
        }
    }
    return 0; // Jogador não encontrado
}

void removeJogador(int pid) {
    for (int i = 0; i < njogadores; i++) {
        if (jogadores[i].pid == pid) {
            for (int j = i; j < njogadores - 1; j++) {
                jogadores[j] = jogadores[j + 1];
            }
            njogadores--;
            break;
        }
    }
}
void get_mapa(){
    FILE *file = fopen("mapa.txt", "r");

if (file == NULL) {
    perror("Erro a abrir ficheiro\n");
} else {
    for (int i = 0; i < LINHAS; i++) {
        if (fgets(mapa[i], COLUNAS, file) == NULL) {
            perror("Erro a ler linha\n");
            break; // Exit the loop on error
        }

        int len = strlen(mapa[i]);
        if (len > 0 && mapa[i][len - 1] == '\n') {
            mapa[i][len - 1] = '\0'; 
        }
    }

    fclose(file);
}
}
void enviaMapa(){
   
    int fd = open(PIPE_MAPA, O_WRONLY);

    if (fd == -1) {
        perror("Erro a abrir PIPE_MAPA\n");
        exit(EXIT_FAILURE);
    }

    // Write data to the named pipe
    write(fd, mapa, sizeof(mapa));

    // Close the file descriptor
    close(fd);
printf("Enviei o mapa\n");
for (int i = 0; i < LINHAS; i++) {
       printf("%s\n",mapa[i]);
    }
}

void listaJogadores() {
  sprintf(info, "Jogadores: %s BloqueiosBot: %d BloqueiosMoveis: %d", nomesJogadores, nbloqueiosbot, nbloqueios);
    for (int i = 0; i < njogadores; i++) {
        // Abre fifos de cada jogador
        char fifo_jogador[20];
        sprintf(fifo_jogador, FIFO_JOGADOR, jogadores[i].pid);

        // Abrir o fifo para escrita
        int playerFd = open(fifo_jogador, O_WRONLY);
        if (playerFd == -1) {
            perror("Erro a abrir pipe do jogador no broadcast\n");
            continue;
        }

        // Escrever no fifo
        write(playerFd, info, strlen(info) + 1);

        close(playerFd);
    }
}                                 


void* recebeJogadores(){
    char nomes[100];
    signal(SIGUSR1, signalJogadorRepetido);

 //Criar fifo  
    if(access(PIPE_MOTOR, F_OK) != 0) {
        mkfifo(PIPE_MOTOR, 0666);
    }

    // Abrir o fifo para leitura
    int fd = open(PIPE_MOTOR, O_RDWR);
    if(fd == -1 ) {
        printf("Erro na abertura do FIFO\n");
        unlink(PIPE_MOTOR);
        exit(1);
    }
    printf("Abri o Fifo motor\n");
do {
        // Ler do Fifo
        int n = read(fd, &atualizaJogadores, sizeof(atualizaJogadores));

         if (strcmp(atualizaJogadores.nome, "exit") == 0) {
        printf("JogoUI encerrado pelo jogador %d\n", atualizaJogadores.pid);
        kill(getpid(), SIGINT); // Envie um sinal de interrupção para encerrar o motor
    }

        printf("Recebi %s %c %d %d %d\n", atualizaJogadores.nome, atualizaJogadores.caracter,atualizaJogadores.pid, atualizaJogadores.x, atualizaJogadores.y);
        if (!verificaExistenciaJogador(atualizaJogadores.nome)) {
            if (atualizaJogadores.x == -1 && atualizaJogadores.y == -1) {
        // Marcador de saída, remove o jogador
        removeJogador(atualizaJogadores.pid);
        printf("Jogador saiu: %s\n", atualizaJogadores.nome);
    }

         
            if (njogadores < 10) {
                jogadores[njogadores++] = atualizaJogadores;
    strcat(nomesJogadores, atualizaJogadores.nome);
    strcat(nomesJogadores, " ");
    listaJogadores();
    printf("Jogador adicionado: %s\n", atualizaJogadores.nome);
                   strcat(nomes, atualizaJogadores.nome);
        strcat(nomes, " "); 
        listaJogadores(nomes);
        //printf("sai do broadcast\n");
        for(int i = 0; i < njogadores; i++){
            mapa[jogadores[i].x][jogadores[i].y] = jogadores[i].caracter;
        }
        enviaMapa();
        //printf("sai do mapa\n");

        printf("Nomes atuais: %s\n", nomes);
            } else {
                printf("Limite máximo de jogadores atingido.\n");
                  kill(atualizaJogadores.pid, SIGUSR1);
            }
        } else {
            printf("Jogador já existe: %s\n", atualizaJogadores.nome);
             kill(atualizaJogadores.pid, SIGUSR1);
        }
    } while (1);

}

void motor_SIGINT_handler(int signum) {
    // Exibir mensagem específica
    printf("O motor foi encerrado pelo comando 'end'.\n");

    // 1. Encerrar o Motor
    remove(MOTOR_LOCK_FILE);

    // 2. Avisar e Encerrar os Jogadores
    for (int i = 0; i < njogadores; i++) {
        // Avisar jogador sobre o encerramento (opcional)
        kill(jogadores[i].pid, SIGUSR1);

        // Encerrar jogador
        kill(jogadores[i].pid, SIGTERM);  // Ou use o sinal apropriado para encerrar o jogador
    }

    // Encerrar a própria thread de comandos
    pthread_exit(NULL);
}

void motor_SIGTERM_handler(int signum) {
    // Exibir mensagem específica
    printf("O motor foi encerrado pelo comando 'end'.\n");

    // 1. Encerrar o Motor
    remove(MOTOR_LOCK_FILE);

    // 2. Avisar e Encerrar os Jogadores
    for (int i = 0; i < njogadores; i++) {
        // Avisar jogador sobre o encerramento (opcional)
        kill(jogadores[i].pid, SIGUSR1);

        // Encerrar jogador
        kill(jogadores[i].pid, SIGTERM);  // Ou use o sinal apropriado para encerrar o jogador
    }

    // Encerrar a própria thread de comandos
    pthread_exit(NULL);
}





void* atualizaPosXY(){
    //Criar fifo
        if(access(PIPE_ATUALIZA, F_OK) != 0) {
        mkfifo(PIPE_ATUALIZA, 0666);
    }

    // Abrir o fifo para leitura
    int fd = open(PIPE_ATUALIZA, O_RDWR);
    if(fd == -1 ) {
        printf("Erro na abertura do FIFO\n");
        unlink(PIPE_ATUALIZA);
        exit(1);
    }
    printf("Abri o Fifo\n");

    do {
            // Ler do Fifo
           int n = read(fd, &atualizaJogadores, sizeof(atualizaJogadores));

//Atualizar jogador
            //printf("Recebi %s %c %d %d %d\n",atualizaJogadores.nome, atualizaJogadores.caracter,atualizaJogadores.pid,atualizaJogadores.x,atualizaJogadores.y);
            for(int i = 0; i <njogadores;i++){
                if(atualizaJogadores.pid == jogadores[i].pid){
                    mapa[jogadores[i].x][jogadores[i].y] = '\0';
                    jogadores[i].x = atualizaJogadores.x;
                    jogadores[i].y = atualizaJogadores.y;
                    //printf("Recebi e atualizei de %s %c x %d y %d\n",jogadores[i].nome,jogadores[i].caracter,jogadores[i].x,jogadores[i].y);
                    mapa[jogadores[i].x][jogadores[i].y] = jogadores[i].caracter;
                    enviaMapa();
                }
            }
           
}while (1);

}

void motor_removeLockFile(int signum) {
    // Remova o arquivo de trava
    remove(MOTOR_LOCK_FILE);
    exit(signum);
}



void* bot() {
    int pipe_fd[2];

    if (pipe(pipe_fd) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    int pid = fork();

    if (pid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) {
        close(pipe_fd[0]);
        dup2(pipe_fd[1], STDOUT_FILENO);
        close(pipe_fd[1]);

        execl("./bot", "bot", "10", "5", NULL);

    } else {
        close(pipe_fd[1]);

        char mensagem_bot[100];
        int argX, argY;

        for (int i = 0; i < 10; i++) {
            int size = read(pipe_fd[0], mensagem_bot, sizeof(mensagem_bot));
            sleep(5);
            //printf("Recebi do programa bot: %s\n", mensagem_bot);

            // Initialize token inside the loop
            char *token = strtok(mensagem_bot, " ");

            if (token != NULL) {
                argX = atoi(token);
                //printf("X: %d\n", argX);
            }

            token = strtok(NULL, " ");
            if (token != NULL) {
                argY = atoi(token);
                //printf("Y: %d\n", argY);
            }

          if (mapa[argX][argY] != '#') {
                // No collision, update mapa
                mapa[argX][argY] = 'O';
                nbloqueiosbot++;
                listaJogadores();
        }

        // Move close(pipe_fd[0]) outside the loop
        close(pipe_fd[0]);

        wait(NULL);
    }
}
}

void listarBots() {
    // Use o comando 'ps' para listar os processos em execução
   FILE *fp = popen("ps aux | grep 'bot' | grep -v grep", "r");
    if (fp == NULL) {
        perror("Erro ao executar comando ps");
        exit(EXIT_FAILURE);
    }

    // Leia a saída do comando linha por linha
    char buffer[256];
    while (fgets(buffer, sizeof(buffer), fp) != NULL) {
        printf("Bot em execução: %s", buffer);
    }

    // Feche o fluxo de arquivo
    pclose(fp);
}
void kickJogador(char *username) {
   signal(SIGUSR1, signalJogadorRepetido);
    for (int i = 0; i < njogadores; i++) {
        if (strcmp(jogadores[i].nome, username) == 0) {
            // Encontrou o jogador, remova-o
            printf("Kickando jogador: %s\n", username);
            
            // Avisar jogador sobre o kick (opcional)
            //kill(jogadores[i].pid, SIGUSR1);
            
            // Encerrar jogador
            kill(jogadores[i].pid, SIGUSR1);  // Ou use o sinal apropriado para encerrar o jogador

            // Remover o jogador da lista
            for (int j = i; j < njogadores - 1; j++) {
                jogadores[j] = jogadores[j + 1];
            }
            njogadores--;

            // Atualizar a lista de jogadores para os outros jogadores
            listaJogadores();

            // Atualizar o mapa e enviar
            //atualizarMapa();
            
            return;  // O jogador foi encontrado e removido, saia da função
        }
    }

    // Se chegou aqui, o jogador não foi encontrado
    printf("Jogador não encontrado: %s\n", username);
}

void adicionaBloqueios(){
  

    srand(time(NULL));
    if(nbloqueios < 10){
        do {
              posX[nbloqueios] = rand() % 13 + 1;  
            posY[nbloqueios] = rand() % 80 + 1; 
        } while (mapa[posX[nbloqueios]][posY[nbloqueios]] == '#');
        mapa[posX[nbloqueios]][posY[nbloqueios]] = 'X';
        nbloqueios++;
        listaJogadores();
    }
}
void removeBloqueios() {
    if (nbloqueios > 0) {
        nbloqueios--;

        mapa[posX[nbloqueios]][posY[nbloqueios]] = '\0';
        listaJogadores();
    }
}
void* moveBloqueios() {
    if(nbloqueios > 0){
    for (int i = 0; i < nbloqueios; ++i) {
        int Y = rand() % 3 - 1;

        mapa[posX[i]][posY[i]] = '\0';

        posY[i] = (posY[i] + Y);

        mapa[posX[i]][posY[i]] = 'X';
    }
    }
    sleep(5);
}

void* get_comandos(){
char comando[50];
char *comando_token;
int fd[2];

do{
     fgets(comando,50,stdin);
        comando_token = strtok(comando," ");
        //users
         if(strcmp(comando_token,"users\n")==0){
            
            printf("Lista de Jogadores: %s\n", nomesJogadores);
        }
        //kick
      else if (strcmp(comando_token, "kick") == 0) {
    char *username_token = strtok(NULL, " ");
    printf("Comando kick com username %s\n", username_token);
    
    // Adicione a lógica para encontrar e remover o jogador
    kickJogador(username_token);
}
        //bots
         else if(strcmp(comando_token,"bots\n")==0){
            listarBots();
            printf("Comando bots\n");
        }
        //bmov
          else if(strcmp(comando_token,"bmov\n")==0){
            adicionaBloqueios();
            printf("Comando bmov\n");
        }
        //rbm
          else if(strcmp(comando_token,"rbm\n")==0){
            removeBloqueios();
            printf("Comando rbm\n");
        }
        //begin
          else if(strcmp(comando_token,"begin\n")==0){
            
            printf("Comando begin\n");
        }
        //end
          else if(strcmp(comando_token,"end\n")==0){
            
              printf("Comando end\n");

    // 1. Encerrar o Motor
    kill(getpid(), SIGINT);  // Use o sinal apropriado

    // 2. Avisar e Encerrar os Jogadores
    for (int i = 0; i < njogadores; i++) {
        // Avisar jogador sobre o encerramento (opcional)
        kill(jogadores[i].pid, SIGUSR1);

        // Encerrar jogador
        kill(jogadores[i].pid, SIGTERM);  // Ou use o sinal apropriado para encerrar o jogador
    }

    // Encerrar a própria thread de comandos
    pthread_exit(NULL);     
          }
        else{
            printf("Comando invalido\n");
        }
}while(1);
}


int main(){
   
    pthread_t thread_comandos, thread_bot, thread_bloqueios;

    // Criar thread para a função get_comandos
    if (pthread_create(&thread_comandos, NULL, &get_comandos, NULL) != 0) {
        perror("Erro ao criar thread para comandos");
        exit(EXIT_FAILURE);
    }
       signal(SIGINT, motor_removeLockFile);
    signal(SIGTERM, motor_removeLockFile);
     signal(SIGINT, motor_SIGINT_handler);
    signal(SIGTERM, motor_SIGTERM_handler);

   

   if (access(MOTOR_LOCK_FILE, F_OK) == 0) {
        printf("Já existe uma instância do motor em execução.\n");
        exit(EXIT_FAILURE);
    }

    // Criar o arquivo de trava
    FILE *lockFile = fopen(MOTOR_LOCK_FILE, "w");
    if (lockFile == NULL) {
        perror("Erro ao criar o arquivo de trava");
        exit(EXIT_FAILURE);
    }
    fclose(lockFile);

 get_mapa();    
   
   pthread_t t1, t2;
     if (pthread_create(&t1, NULL, &recebeJogadores, NULL)) {
        return 1;
    }
    if (pthread_create(&t2, NULL, &atualizaPosXY, NULL)) {
        return 2;
    }
        if (pthread_create(&thread_bot, NULL, &bot, NULL)) {
        return 2;
    }
      if (pthread_create(&thread_bloqueios, NULL, &moveBloqueios, NULL)) {
        return 1;
    }
    
     if (pthread_join(thread_comandos, NULL) != 0) {
        perror("Erro ao aguardar thread de comandos");
        exit(EXIT_FAILURE);
    }
    if (pthread_join(t1, NULL)) {
        return 3;
    }
    if (pthread_join(t2, NULL)) {
        return 3;
    }
      if (pthread_join(thread_bot, NULL)) {
        return 3;
    }
       if (pthread_join(thread_bloqueios, NULL)) {
        return 3;
    }

    remove("server.lock");
    return 0;
}
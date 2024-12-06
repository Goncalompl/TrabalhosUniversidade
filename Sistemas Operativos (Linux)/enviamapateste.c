#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>

#define LINHAS 12
#define COLUNAS 60

int main() {
    const char *pipe_path = "PIPEMAPA";

    char mapa[LINHAS][COLUNAS + 1];

    FILE *file = fopen("mapa.txt", "r");

    if (file == NULL) {
        perror("Error opening file");
        return 1;
    }

    for (int i = 0; i < LINHAS; i++) {
        if (fgets(mapa[i], COLUNAS + 1, file) == NULL) {
            perror("Error reading line from file");
            fclose(file);
            return 1;
        }

        // Remove the newline character at the end
        size_t len = strlen(mapa[i]);
        if (len > 0 && mapa[i][len - 1] == '\n') {
            mapa[i][len - 1] = '\0';  // Replace '\n' with '\0'
        }
    }

    fclose(file);

    // Create the named pipe (if it doesn't exist)
    mkfifo(pipe_path, 0666);

    // Open the named pipe for writing
    int fd = open(pipe_path, O_WRONLY);

    if (fd == -1) {
        perror("Error opening the named pipe");
        exit(EXIT_FAILURE);
    }

    // Write data to the named pipe
    write(fd, mapa, sizeof(mapa));

    // Close the file descriptor
    close(fd);

    printf("Data sent successfully.\n");

    return 0;
}
#ifndef TRABALHOPRATICO_POO_RELOADED_APP_H
#define TRABALHOPRATICO_POO_RELOADED_APP_H

#include <fstream>
#include <string>
#include "Island.h"
#include "Player.h"
#include "Codes.h"

using namespace std;

class App {
private:
    Island* island;
    Player* player;

    int x, y, *day, *worker_cnt;
    float v;
    string type;

    vector <string> input;

    int CheckSyntax();
    bool CheckCoords();
    void Execute(int cmd_code);
    bool CheckPurchase();
    bool CheckWorkerPurchase();
    bool CheckAltPurchase();
    void LevelUp();
    void GiveResources(int x, int y);
    void UpdateStorage();
    void NewDay();
    void ReadFromFile();
    void SaveGame() const;
    void LoadGame(string &username);
    void SellBuilding(string type);
    void SellResource(string type, int quant);

public:
    App();
    ~App();

    void PrintIsland();
    void PrintCell(int x, int y);
    void PrintHUD();
    void Init();
    void GetInput();
};


#endif //TRABALHOPRATICO_POO_RELOADED_APP_H

#ifndef TRABALHOPRATICO_POO_RELOADED_STORAGE_H
#define TRABALHOPRATICO_POO_RELOADED_STORAGE_H

#include <iostream>
#include <vector>
#include "Resource.h"

using namespace std;

class Storage {
private:
    vector <Resource*> storage;

public:
    Storage();
    ~Storage();

    static Storage* Create();

    float GetCap(string type);
    float GetMaxCap(string type);

    void SetCap(string type, float cap);
    void SetMaxCap(string type, int max_cap);


};


#endif //TRABALHOPRATICO_POO_RELOADED_STORAGE_H

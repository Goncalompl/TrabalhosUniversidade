#include "Cell.h"

Cell::Cell() {
    type = "undef";
    building = nullptr;
    trees = 0;
}

Cell::~Cell() {
    delete building;
}

Cell* Cell::Create(const string& type) {
    Cell* zone = new Cell;

    if (type == undef)
        switch ( rand() % 6 + 1) {

            case 1:
                zone->type = mnt;
                break;

            case 2:
                zone->type = dsr;
                break;

            case 3:
                zone->type = pas;
                break;

            case 4:
                zone->type = flr;
                zone->trees = 20;
                zone->trees += rand() % 20;
                break;

            case 5:
                zone->type = pnt;
                break;

            case 6:
                zone->type = rad;
                break;

        }
    else zone->type = type;

    zone->building = Building::Create("undef");
    return zone;
}

void Cell::DestroyProbHandler(int* day) {
    if (type == pnt && building->GetDay() >= 10) {
        delete building;
        building = Building::Create("undef");
    }
    if (rand() % 100 < building->GetDestroyProb() * 100) {
        delete building;
        building = Building::Create("undef");
    }
}

void Cell::QuitProbHandler(int &day) {
    for (int i = 0; i < worker_list.size(); i++) {
        worker_list.at(i)->UpdateQuitProb(day);
        if (rand() % 100 < worker_list.at(i)->GetQuitProb() * 100) {
            worker_list.erase(worker_list.begin() + i);
        }
    }
}

void Cell::SetType(string type) {
    this->type = type;
}

string Cell::GetType() {
    return type;
}

int Cell::GetTrees() {
    return trees;
}

void Cell::GrowTrees() {
    trees++;
    if (trees > 100)
        trees = 100;
}

void Cell::DestroyTrees() {
    trees--;
    if (trees < 0)
        trees = 0;
}

string Cell::GetWorkerCount() {
    ostringstream oss;
    oss << worker_list.size();

    oss.str().resize(11, ' ');

    return oss.str();
}

string Cell::GetWorkersString() {
    ostringstream oss;
    for (auto & i : worker_list)
        oss << i->GetType() << ' ';

    oss.str().resize(11, ' ');

    return oss.str();
}

vector <Worker*> Cell::GetWorkerList() {
    return worker_list;
}

Worker* Cell::GetWorker(string id) {
    if (!worker_list.empty())
        for (auto &worker : worker_list) {
            if (worker->GetID() == id)
                return worker;
        }
    return nullptr;
}

void Cell::SetWorker(string type, int &day, int &worker_nr) {
    worker_list.emplace_back(Worker::Create(type, &day, &worker_nr));
}

void Cell::PasteWorker(Worker *worker) {
    worker_list.emplace_back(worker);
}

void Cell::DeleteWorker(string id) {
    for (int i = 0; i < worker_list.size(); i++) {
        if (worker_list.at(i)->GetID() == id)
            worker_list.erase(worker_list.begin() + i);
    }
}

string Cell::GetBuildingType() {
    return building->GetType();
}

void Cell::SetBuilding(const string& building_type) {
    building = Building::Create(building_type);
}

Building* Cell::GetBuilding() {
    return building;
}

void Cell::KillWorkers() {
    worker_list.erase(worker_list.begin(), worker_list.end());
    //worker_list.clear();
}

string Cell::GetCellInfo() {
    ostringstream oss;

    oss << "Informacao da zona" << endl << endl;
    oss << " ---------------------------" << endl;
    oss << "| Bioma: " << type << endl;
    oss << "| Edificio: " << building->GetType() << endl;
    if (type == flr) {
        oss << "| Arvores: " << trees << endl;
    }
    oss << "| Trabalhadores: " << endl;

    if ( !worker_list.empty() )
        for (int i = 0 ; i < worker_list.size() ; i++ ) {
            oss << "| Trabalhador nr. " << i + 1 << endl;
            oss << "| ID: " << worker_list.at(i)->GetID() << ';' << endl;
            oss << "| Tipo: " << worker_list.at(i)->GetType() << ';' << endl;
            oss << "| Probabilidade de demissao: " << worker_list.at(i)->GetQuitProb() * 100 << '%' << '.' << endl;
        }
    else
        oss << "| Nao existem trabalhadores." << endl;

    oss << endl;

    return oss.str();
}

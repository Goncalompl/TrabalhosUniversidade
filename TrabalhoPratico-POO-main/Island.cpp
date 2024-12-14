#include "Island.h"

Island::Island(){

}

Island::~Island() {
    for (int i = 0; i < cols * rows; i++) {
        delete zone[i];
    }
    delete[] zone;
}
Island* Island::Create(int cols, int rows) {
    auto* map = new Island;

    map->cols = cols;
    map->rows = rows;

    map->zone = new Cell*[cols * rows];
    for (int i = 0; i < cols * rows; ++i)
        map->zone[i] = Cell::Create();

    return map;
}

string Island::PrintIsland() {
    ostringstream oss;

    for (int j = 0; j < rows; ++j) {
        for (int i = 0; i < cols; ++i)
            oss << " -----------------";
            oss << endl;

        for (int i = 0; i < cols; ++i)
            oss << "|Bioma:" << zone[j * rows + i]->GetType();
            oss << '|' << endl;

        for (int i = 0; i < cols; ++i)
            oss << "|Ed.:" << zone[j * rows + i]->GetBuildingType();
        oss << '|' << endl;

        for (int i = 0; i < cols; ++i)
            oss << "|Trab.:" << zone[j * rows + i]->GetWorkersString();
        oss << "          |" << endl;

        for (int i = 0; i < cols; ++i)
            oss << "|Total:" << zone[j * rows + i]->GetWorkerCount();
        oss << '|' << endl;
    }

    for (int i = 0; i < cols; ++i)
        oss << " -----------------";

    oss << endl;

    return oss.str();
}

int Island::GetCols() const {
    return cols;
}

int Island::GetRows() const {
    return rows;
}

string Island::GetZonesSaveData() const{
    ostringstream oss;

    oss << "---start_zone_info" << endl;
    for (int z = 0; z < cols * rows; ++z) {
        oss << "i" << z << "" << endl;
        oss << "b" << zone[z]->GetType() << "" << endl;
        oss << "e" << zone[z]->GetBuildingType() << "" << endl;
        oss << "n" << zone[z]->GetBuilding()->GetLevel() << "" << endl;
        oss << "t" << zone[z]->GetWorkerCount() << "" << endl;

        oss << "---start-trabalhadores" << endl;
        for (auto & worker : zone[z]->GetWorkerList()) {
            oss << "id" << worker->GetID() << "" << endl;
            oss << "ty" << worker->GetType() << "" << endl;
        }
        oss << "---end-trabalhadores" << endl;
    }
    oss << "---end_zone_info" << endl;
    return oss.str();
}

string Island::GetIslandSaveData() const {
    ostringstream oss;

    oss << "---start_info-island" << endl;
    oss << "c" << cols << "" << endl;
    oss << "r" << rows << "" << endl;
    oss << "---end_info-island" << endl;

    return oss.str();
}

void Island::LoadData(const string& filename) {
    fstream fs;
    fs.open(filename, ios::in);

    if (!fs.is_open()) {
        cout << "Erro ao abrir ficheiro: " << filename << "." << endl;
        return;
    }

    string data;
    int generate_map = 0;
    bool new_cell = false;
    int i_index = -1;
    while (getline(fs, data)) {

        if (data.at(0) == 'c') {
            string _cols = data;
            _cols.erase(_cols.begin());
            cols = stoi(_cols);
            generate_map++;
        }

        if (data.at(0) == 'r') {
            string _rows = data;
            _rows.erase(_rows.begin());
            rows = stoi(_rows);
            generate_map++;
        }

        if (data.at(0) == 'i' && data.at(1) != 'd' ) {
            new_cell = true;
            string s_index = data;
            s_index.erase(s_index.begin());
            i_index = stoi(s_index);
        }

        if (data.at(0) == 'b') {
            data.erase(data.begin());
            string biome = data;
            zone[i_index] = Cell::Create(biome);
        }

        if (data.at(0) == 'e') {
            data.erase(data.begin());
            string building = data;
            zone[i_index]->SetBuilding(building);
        }

        if (data.at(0) == 'n') {
            data.erase(data.begin());
            int building_lvl = stoi(data);
            zone[i_index]->GetBuilding()->SetLevel(building_lvl);
        }

    }

    fs.close();

    cout << "Jogo carregado com sucesso!" << endl;
}

void Island::RadiationUpdate(int& day) {

    for (int i = 0; i < cols * rows; i++) {

        int left = i - 1;
        int right = i + 1;
        int above = i - cols;
        int below = i + cols;

        int lottery = rand() % 4 + 1;

        if (zone[i]->GetType() == rad && zone[i]->GetBuildingType() != filt) {

            zone[i]->SetBuilding(undef);

            if (left >= 0 && lottery == 1) {
                zone[left]->SetType(rad);
                zone[left]->SetBuilding(undef);

            } else if (right <= cols * rows - 1 && lottery == 2) {
                zone[right]->SetType(rad);
                zone[right]->SetBuilding(undef);

            } else if (above >= 0 && lottery == 3) {
                zone[above]->SetType(rad);
                zone[above]->SetBuilding(undef);

            } else if (below <= cols * rows - 1 && lottery == 4) {
                zone[below]->SetType(rad);
                zone[below]->SetBuilding(undef);
            }
        } else if (zone[i]->GetType() == rad &&
                   zone[i]->GetBuildingType() == filt &&
                   zone[i]->GetBuilding()->GetLevel() == 2) {

            switch (rand() % 6 + 1) {

                case 1:
                    zone[i]->SetType(mnt);
                    break;

                case 2:
                    zone[i]->SetType(flr);
                    break;

                case 3:
                    zone[i]->SetType(pnt);
                    break;

                case 4:
                    zone[i]->SetType(dsr);
                    break;

                case 5:
                    zone[i]->SetType(pas);
                    break;
            }
        }
    }
}

void Island::GrowTrees() {
    for (int i = 0; i < cols * rows; i++) {
        if (zone[i]->GetType() == flr && zone[i]->GetBuildingType() == undef)
            zone[i]->GrowTrees();
    }
}

void Island::DestroyTrees() {
    for (int i = 0; i < cols * rows; i++) {
        if (zone[i]->GetType() == flr && zone[i]->GetBuildingType() != undef)
            zone[i]->DestroyTrees();
    }
}

Worker* Island::FindWorker(string id) {
    for (int i = 0; i < cols * rows; i++) {
        for (auto &w : zone[i]->GetWorkerList()) {
            if (w->GetID() == id)
                return w;
        }
    }
    return nullptr;
}

void Island::MoveWorker(string id, int* x, int* y) {
    for (int i = 0; i < cols * rows; i++) {
        if (zone[i]->GetWorker(id) != nullptr && !zone[i]->GetWorker(id)->HasMoved()) {
            SpawnWorker(zone[i]->GetWorker(id), *x * rows + *y);
            zone[i]->GetWorker(id)->SetHasMoved(true);
            zone[i]->DeleteWorker(id);
            return;
        }
    }
}

void Island::SpawnWorker(Worker* worker, int pos){
    zone[pos]->PasteWorker(worker);
}

Cell* Island::GetZone(const int x, const int y) {
    return zone[x * rows + y];
}

void Island::SetBuilding(const int x, const int y, const string& type) {
    zone[x * rows + y]->SetBuilding(type);
}

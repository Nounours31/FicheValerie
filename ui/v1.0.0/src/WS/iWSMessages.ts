export interface iPersonne  {
    id: number;
    genre: string;
    date: string;
    nom: string;
}


export interface iBulletinSalaire {
    id: number;
    idPersonne: number;
    mois: string;
    annee: number;
}


export interface iPersonne  {
    id: number;
    genre: string;
    nom: string;
}


export interface iBulletinSalaire {
    id: number;
    idPersonne: number;
    mois: number;
    annee: number;
    tarifHoraire: number;
}


export interface iActivite {
    id: number;
    idBulletinSalaire: number;
    tarifHoraire: number;
    activite: string;
    gmtepoch_debut: number;
    gmtepoch_fin: number;
    // debut?: Date;
    // fin?: Date;
}

export interface iActiviteEnum {
    id: number;
    nom: string;
}

export interface iPdf {
    id: number;
    idBulletinSalaire: number;
    file: string;
}

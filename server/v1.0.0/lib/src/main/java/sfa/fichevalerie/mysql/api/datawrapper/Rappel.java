package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.util.Date;
import java.util.Objects;

public class Rappel extends ObjectWrapper implements iObjectWrapper {
    int id;
    int idBulletinSalaire;
    int idBulletinSalaireOrigine;
    int status;
    float tarifHoraire;
    float dureeenheure;

    public float getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(float tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }

    Date date;

    public float getDureeenheure() {
        return dureeenheure;
    }

    public void setDureeenheure(float dureeenheure) {
        this.dureeenheure = dureeenheure;
    }

    public int getIdBulletinSalaire() {
        return idBulletinSalaire;
    }

    public void setIdBulletinSalaire(int idBulletinSalaire) {
        this.idBulletinSalaire = idBulletinSalaire;
    }

    public int getIdBulletinSalaireOrigine() {
        return idBulletinSalaireOrigine;
    }

    public void setIdBulletinSalaireOrigine(int idBulletinSalaireOrigine) {
        this.idBulletinSalaireOrigine = idBulletinSalaireOrigine;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String[] allColone() {
        return new String[] {
                "id","idBulletinSalaire", "idBulletinSalaireOrigine", "status", "date"
        };
    }

    @Override
    public void set(String key, Object val) throws E4AException {
        switch (key) {
            case "id": this.setId((Integer)val);break;
            case "idBulletinSalaire": this.setIdBulletinSalaire((Integer)val);break;
            case "idBulletinSalaireOrigine": this.setIdBulletinSalaireOrigine((Integer)val);break;
            case "status": this.setStatus((Integer)val);break;
            case "dureeenheure": this.setDureeenheure((Float)val);break;
            case "date": this.setDate((Date)val);break;
            default: throw new E4AException("Rappel :Key["+key+"] Inconnue");
        }
    }

    @Override
    public String toString() {
        return "Rappel{" +
                "id=" + id +
                ", idBulletinSalaire=" + idBulletinSalaire +
                ", idBulletinSalaireOrigine=" + idBulletinSalaireOrigine +
                ", status=" + status +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rappel rappel = (Rappel) o;
        return id == rappel.id &&
                idBulletinSalaire == rappel.idBulletinSalaire &&
                idBulletinSalaireOrigine == rappel.idBulletinSalaireOrigine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idBulletinSalaire, idBulletinSalaireOrigine);
    }
}

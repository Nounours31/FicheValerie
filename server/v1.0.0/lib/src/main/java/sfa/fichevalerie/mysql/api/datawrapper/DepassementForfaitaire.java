package sfa.fichevalerie.mysql.api.datawrapper;

import sfa.fichevalerie.tools.E4AException;

import java.util.Date;
import java.util.Objects;

public class DepassementForfaitaire extends ObjectWrapper implements iObjectWrapper {
    int id;
    int idBulletinSalaire;
    float dureeenheure;
    Date date;
    float tarifHoraire;

    public DepassementForfaitaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBulletinSalaire() {
        return idBulletinSalaire;
    }

    public void setIdBulletinSalaire(int idBulletinSalaire) {
        this.idBulletinSalaire = idBulletinSalaire;
    }

    public float getDureeenheure() {
        return dureeenheure;
    }

    public void setDureeenheure(float dureeenheure) {
        this.dureeenheure = dureeenheure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTarifHoraire() {
        return tarifHoraire;
    }

    public void setTarifHoraire(float tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }

    @Override
    public String toString() {
        return "DepassementForfaitaire{" +
                "id=" + id +
                ", idBulletinSalaire=" + idBulletinSalaire +
                ", dureeenheure=" + dureeenheure +
                ", date=" + date +
                ", tarifHoraire=" + tarifHoraire +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepassementForfaitaire that = (DepassementForfaitaire) o;
        return id == that.id && idBulletinSalaire == that.idBulletinSalaire && Float.compare(that.dureeenheure, dureeenheure) == 0 && Float.compare(that.tarifHoraire, tarifHoraire) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idBulletinSalaire, dureeenheure, tarifHoraire);
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
            case "date": this.setDate((Date)val);break;
            default: throw new E4AException("Rappel :Key["+key+"] Inconnue");
        }
    }
}

package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPersonaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class EdificiosDAODatabaseImpl extends BaseDAODatabaseImpl implements EdificiosDAO
{
    @Override
    public List<Edificio> getEdificiosByCentroId(Long centroId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        List<String> edificios = query.from(qAula).where(qAula.centro.id.eq(centroId))
                .orderBy(qAula.edificio.asc()).distinct().list(qAula.edificio);

        return creaListaEdificiosDesde(edificios);
    }

    private List<Edificio> creaListaEdificiosDesde(List<String> edificiosStr)
    {
        List<Edificio> edificios = new ArrayList<Edificio>();

        for (String edificioStr : edificiosStr)
        {
            Edificio edificio = new Edificio();
            edificio.setNombre(edificioStr);
            edificios.add(edificio);
        }

        return edificios;
    }

    @Override
    public List<PlantaEdificio> getPlantasEdificioByCentroAndEdificio(Long centroId, String edificio)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;

        List<String> plantasEdificio = query.from(qAula)
                .where(qAula.centro.id.eq(centroId).and(qAula.edificio.eq(edificio)))
                .orderBy(qAula.planta.asc()).distinct().list(qAula.planta);

        return creaListaPlantasEdificioDesde(plantasEdificio);
    }

    private List<PlantaEdificio> creaListaPlantasEdificioDesde(List<String> plantasEdificioStr)
    {
        List<PlantaEdificio> plantasEdificio = new ArrayList<PlantaEdificio>();

        for (String plantaEdificioStr : plantasEdificioStr)
        {
            PlantaEdificio plantaEdificio = new PlantaEdificio();
            plantaEdificio.setNombre(plantaEdificioStr);
            plantaEdificio.setValor(plantaEdificioStr);
            plantasEdificio.add(plantaEdificio);
        }

        return plantasEdificio;
    }

    @Override
    public List<Edificio> getEdificiosVisiblesPorUsuarioByCentroId(Long centroId, Long semestreId,
            Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPersonaDTO qAulaPersona = QAulaPersonaDTO.aulaPersonaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        List<String> edificios = query
                .from(qAula, qAulaPersona, qAulaPlanificacion)
                .where(qAulaPersona.personaId.eq(connectedUserId)
                        .and(qAulaPlanificacion.aula.id.eq(qAula.id))
                        .and(qAula.centro.id.eq(qAulaPersona.centroId))
                        .and(qAula.centro.id.eq(centroId)).and(qAula.id.eq(qAulaPersona.aulaId))
                        .and(qAulaPlanificacion.semestre.eq(semestreId)))
                .orderBy(qAula.edificio.asc()).distinct().list(qAula.edificio);

        return creaListaEdificiosDesde(edificios);
    }

    @Override
    public List<PlantaEdificio> getPlantasEdificioVisiblesPorUsuarioByCentroAndSemestreAndEdificio(
            Long centroId, Long semestreId, String edificio, Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPersonaDTO qAulaPersona = QAulaPersonaDTO.aulaPersonaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        List<String> plantasEdificio = query
                .from(qAula, qAulaPersona, qAulaPlanificacion)
                .where(qAulaPersona.personaId.eq(connectedUserId)
                        .and(qAula.id.eq(qAulaPersona.aulaId))
                        .and(qAulaPlanificacion.aula.id.eq(qAula.id))
                        .and(qAula.centro.id.eq(centroId)).and(qAula.centro.id.eq(centroId))
                        .and(qAula.edificio.eq(edificio))
                        .and(qAulaPlanificacion.semestre.eq(semestreId)))
                .orderBy(qAula.planta.asc()).distinct().list(qAula.planta);

        return creaListaPlantasEdificioDesde(plantasEdificio);
    }
}

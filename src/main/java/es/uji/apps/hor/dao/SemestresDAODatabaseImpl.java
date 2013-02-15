package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.QAulaDTO;
import es.uji.apps.hor.db.QAulaPersonaDTO;
import es.uji.apps.hor.db.QAulaPlanificacionDTO;
import es.uji.apps.hor.db.QItemDTO;
import es.uji.apps.hor.db.QItemsAsignaturaDTO;
import es.uji.apps.hor.db.QSemestreDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.model.Semestre;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class SemestresDAODatabaseImpl extends BaseDAODatabaseImpl implements SemestresDAO
{
    @Override
    public List<Semestre> getSemestres(Long curso, Long estudioId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QItemDTO itemDTO = QItemDTO.itemDTO;
        QItemsAsignaturaDTO asignaturaDTO = QItemsAsignaturaDTO.itemsAsignaturaDTO;
        QSemestreDTO semestreDTO = QSemestreDTO.semestreDTO;

        List<SemestreDTO> listaSemestresTuples = query.from(asignaturaDTO)
                .join(asignaturaDTO.item, itemDTO).join(itemDTO.semestre, semestreDTO)
                .where(asignaturaDTO.estudioId.eq(estudioId).and(itemDTO.cursoId.eq(curso)))
                .orderBy(semestreDTO.id.asc()).listDistinct(semestreDTO);

        List<Semestre> semestres = new ArrayList<Semestre>();

        for (SemestreDTO sem : listaSemestresTuples)
        {
            Semestre semestre = new Semestre(sem.getId());
            semestre.setNombre(sem.getNombre());
            semestres.add(semestre);
        }

        return semestres;
    }

    @Override
    public Semestre insert(Semestre semestre)
    {
        SemestreDTO semestreDTO = new SemestreDTO();
        semestreDTO.setNombre(semestre.getNombre());
        semestreDTO = insert(semestreDTO);

        return new Semestre(semestreDTO.getId(), semestreDTO.getNombre());
    }

    @Override
    public List<Semestre> getSemestresVisiblesByCentroAndAulas(Long centroId, Long connectedUserId)
    {
        JPAQuery query = new JPAQuery(entityManager);

        QAulaDTO qAula = QAulaDTO.aulaDTO;
        QAulaPersonaDTO qAulaPersona = QAulaPersonaDTO.aulaPersonaDTO;
        QAulaPlanificacionDTO qAulaPlanificacion = QAulaPlanificacionDTO.aulaPlanificacionDTO;

        List<Long> semestres = query
                .from(qAula, qAulaPersona, qAulaPlanificacion)
                .where(qAulaPersona.personaId.eq(connectedUserId)
                        .and(qAula.id.eq(qAulaPersona.aulaId))
                        .and(qAulaPlanificacion.aula.id.eq(qAula.id))
                        .and(qAulaPersona.centroId.eq(centroId))).distinct()
                .orderBy(qAulaPlanificacion.semestre.asc()).list(qAulaPlanificacion.semestre);

        List<Semestre> listaSemestres = new ArrayList<Semestre>();

        for (Long semestre : semestres)
        {
            listaSemestres.add(new Semestre(semestre, String.valueOf(semestre)));
        }

        return listaSemestres;
    }
}
package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

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
}
package es.uji.apps.hor.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.DetalleSemestreDTO;
import es.uji.apps.hor.db.QDetalleSemestreDTO;
import es.uji.apps.hor.db.QEstudioDTO;
import es.uji.apps.hor.db.QSemestreDTO;
import es.uji.apps.hor.db.QTipoEstudioDTO;
import es.uji.apps.hor.db.SemestreDTO;
import es.uji.apps.hor.db.TipoEstudioDTO;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.SemestreDetalle;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.db.BaseDAODatabaseImpl;

@Repository
public class SemestresDetalleDAODatabaseImpl extends BaseDAODatabaseImpl implements
        SemestresDetalleDAO
{

    @Override
    public List<SemestreDetalle> getSemestresDetalleTodos()
    {
        JPAQuery query = new JPAQuery(entityManager);
        QDetalleSemestreDTO detalleSemestre = QDetalleSemestreDTO.detalleSemestreDTO;
        QSemestreDTO semestre = QSemestreDTO.semestreDTO;
        QTipoEstudioDTO tipoEstudio = QTipoEstudioDTO.tipoEstudioDTO;

        List<DetalleSemestreDTO> queryResult = query.from(detalleSemestre)
                .join(detalleSemestre.semestre, semestre).fetch()
                .join(detalleSemestre.tiposEstudio, tipoEstudio).fetch().list(detalleSemestre);

        List<SemestreDetalle> semestresDetalle = new ArrayList<SemestreDetalle>();
        for (DetalleSemestreDTO detalle : queryResult)
        {
            semestresDetalle.add(convierteDetalleDTOEnDetalleModelo(detalle));
        }
        return semestresDetalle;
    }

    private SemestreDetalle convierteDetalleDTOEnDetalleModelo(DetalleSemestreDTO detalleDTO)
    {
        SemestreDetalle detalleModelo = new SemestreDetalle(detalleDTO.getId(),
                convierteSemestreDTOenSemesetreModelo(detalleDTO.getSemestre()),
                convierteTiposEstudioDTOenTiposEstudioModelo(detalleDTO.getTiposEstudio()),
                detalleDTO.getFechaInicio(), detalleDTO.getFechaFin(),
                detalleDTO.getNumeroSemanas());
        detalleModelo.setFechaExamenesInicio(detalleDTO.getFechaExamenesInicio());
        detalleModelo.setFechaExamenesFin(detalleDTO.getFechaExamenesFin());
        return detalleModelo;
    }

    private Semestre convierteSemestreDTOenSemesetreModelo(SemestreDTO semestreDTO)
    {
        return new Semestre(semestreDTO.getId(), semestreDTO.getNombre());
    }

    private TipoEstudio convierteTiposEstudioDTOenTiposEstudioModelo(TipoEstudioDTO tipoEstudioDTO)
    {
        return new TipoEstudio(tipoEstudioDTO.getId(), tipoEstudioDTO.getNombre());
    }

    @Override
    public List<SemestreDetalle> getSemestresDetallesPorEstudioIdYSemestreId(Long estudioId,
            Long semestreId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        QDetalleSemestreDTO detalleSemestre = QDetalleSemestreDTO.detalleSemestreDTO;
        QTipoEstudioDTO tipoEstudio = QTipoEstudioDTO.tipoEstudioDTO;
        QEstudioDTO estudio = QEstudioDTO.estudioDTO;

        List<DetalleSemestreDTO> queryResult = query
                .from(detalleSemestre, estudio)
                .join(estudio.tipoEstudio, tipoEstudio)
                .join(detalleSemestre.tiposEstudio, tipoEstudio)
                .fetch()
                .where(estudio.id.eq(estudioId).and(
                        estudio.tipoEstudio.eq(tipoEstudio).and(
                                detalleSemestre.semestre.id.eq(semestreId)))).list(detalleSemestre);

        List<SemestreDetalle> semestresDetalle = new ArrayList<SemestreDetalle>();
        for (DetalleSemestreDTO detalle : queryResult)
        {
            semestresDetalle.add(convierteDetalleDTOEnDetalleModelo(detalle));
        }
        return semestresDetalle;
    }

}

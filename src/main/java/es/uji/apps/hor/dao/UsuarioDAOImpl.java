package es.uji.apps.hor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import es.uji.apps.hor.db.CargoPersonaDTO;
import es.uji.apps.hor.db.QCargoPersonaDTO;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.Usuario;
import es.uji.commons.db.BaseDAODatabaseImpl;
import es.uji.commons.sso.dao.ApaDAO;

@Repository
public class UsuarioDAOImpl extends BaseDAODatabaseImpl implements UsuarioDAO
{

    @Override
    public Usuario getUsuarioById(Long usuarioId)
    {
        JPAQuery query = new JPAQuery(entityManager);
        Usuario usuario = new Usuario();

        QCargoPersonaDTO qCargo = QCargoPersonaDTO.cargoPersonaDTO;

        query.from(qCargo).where(qCargo.persona.id.eq(usuarioId));

        for (CargoPersonaDTO cargoDTO : query.list(qCargo))
        {
            if (usuario.getCentro() == null)
            {
                usuario.setCentro(creaCentroDeCargoDTO(cargoDTO));
            }

            usuario.getEstudios().add(creaEstudioDeCargoDTO(cargoDTO));
        }

        return usuario;
    }

    private Estudio creaEstudioDeCargoDTO(CargoPersonaDTO cargoDTO)
    {
        Estudio estudioNuevo = new Estudio();
        estudioNuevo.setId(cargoDTO.getEstudio().getId());
        estudioNuevo.setNombre(cargoDTO.getEstudio().getNombre());
        return estudioNuevo;
    }

    private Centro creaCentroDeCargoDTO(CargoPersonaDTO cargoDTO)
    {
        Centro centroNuevo = new Centro();
        centroNuevo.setId(cargoDTO.getCentro().getId());
        centroNuevo.setNombre(cargoDTO.getCentro().getNombre());
        return centroNuevo;
    }

    @Autowired
    private ApaDAO apaDAO;

    @Override
    public boolean elUsuarioEsAdmin(long usuarioId)
    {
        return apaDAO.hasPerfil("HOR", "ADMIN", usuarioId);
    }

}

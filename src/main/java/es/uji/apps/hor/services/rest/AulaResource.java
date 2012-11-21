package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.TreeRowsetAula;
import es.uji.apps.hor.services.AulaService;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.rest.model.tree.TreeRow;

@Path("aula")
public class AulaResource
{
    @InjectParam
    private AulaService consultaAulas;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getAulas()
    {
        List<Aula> aulas = consultaAulas.getAulas();

        return UIEntity.toUI(aulas);
    }

    @GET
    @Path("centro/{id}/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public TreeRowsetAula getAulasTree(@PathParam("id") String centroId)

    {
        TreeRowsetAula treeRowset = new TreeRowsetAula();

        List<Aula> listaAulas = consultaAulas.getAulasByCentroId(Long.parseLong(centroId));

        treeRowset = ordenaEstructuraToRowSet(listaAulas);

        return treeRowset;

    }

    @GET
    @Path("tree")
    @Produces(MediaType.APPLICATION_JSON)
    public TreeRowsetAula getAulasTree(@QueryParam("centroId") String centroId,
            @QueryParam("estudioId") String estudioId)
    {
        TreeRowsetAula treeRowset = new TreeRowsetAula();

        if (!(centroId == null || centroId.isEmpty() || estudioId == null || estudioId.isEmpty()))
        {
            List<Aula> listaAulas = consultaAulas.getAulasByCentroIdAndestudioId(
                    Long.parseLong(centroId), Long.parseLong(estudioId));

            treeRowset = ordenaEstructuraToRowSet(listaAulas);
        }

        return treeRowset;

    }

    private TreeRowsetAula ordenaEstructuraToRowSet(List<Aula> listaAulas)
    {

        TreeRowsetAula treerow = new TreeRowsetAula();
        HashMap<String, List<String>> hashEdificiosYTiposAula = extraeEdificiosYTiposAula(listaAulas);
        HashMap<String, List<Aula>> hashAulas = extraeAulasPorEdificioYTipoAula(
                hashEdificiosYTiposAula, listaAulas);

        for (Map.Entry<String, List<String>> nodo : hashEdificiosYTiposAula.entrySet())
        {
            String edificio = nodo.getKey();
            List<String> listaTipos = nodo.getValue();

            treerow.getRow().add(edificioToTreeRow(edificio, listaTipos, hashAulas));
        }

        return treerow;

    }

    private HashMap<String, List<Aula>> extraeAulasPorEdificioYTipoAula(
            HashMap<String, List<String>> listaEdificiosYTiposAula, List<Aula> listaAulas)
    {
        HashMap<String, List<Aula>> hashAulas = new HashMap<String, List<Aula>>();

        for (Aula aula : listaAulas)
        {
            if (!hashAulas.keySet().contains(aula.getEdificio() + aula.getTipo()))
            {
                List<Aula> aulas = new ArrayList<Aula>();
                aulas.add(aula);
                hashAulas.put(aula.getEdificio() + aula.getTipo(), aulas);
            }
            else
            {
                List<Aula> aulas = hashAulas.get(aula.getEdificio() + aula.getTipo());
                aulas.add(aula);
            }
        }

        return hashAulas;

    }

    private TreeRow edificioToTreeRow(String edificio, List<String> listaTipos,
            HashMap<String, List<Aula>> hashAulas)
    {
        TreeRow treeRowEdificio = new TreeRow();

        Aula aulaEdificio = hashAulas.get(edificio + listaTipos.get(0)).get(0);

        treeRowEdificio.setId(UUID.randomUUID().toString());
        treeRowEdificio.setTitle(aulaEdificio.getEdificio());
        treeRowEdificio.setText(aulaEdificio.getEdificio());
        treeRowEdificio.setLeaf("false");

        List<TreeRow> listaTreeRowTipos = new ArrayList<TreeRow>();

        for (String tipoAula : listaTipos)
        {
            listaTreeRowTipos.add(tipoAulaToTreeRow(edificio, tipoAula, hashAulas));
        }

        treeRowEdificio.setHijos(listaTreeRowTipos);
        return treeRowEdificio;

    }

    private TreeRow tipoAulaToTreeRow(String edificio, String tipo,
            HashMap<String, List<Aula>> hashAulas)
    {
        TreeRow treeRowTipoAula = new TreeRow();

        Aula tipoAula = hashAulas.get(edificio + tipo).get(0);

        treeRowTipoAula.setId(UUID.randomUUID().toString());
        treeRowTipoAula.setTitle(tipoAula.getTipo());
        treeRowTipoAula.setText(tipoAula.getTipo());
        treeRowTipoAula.setLeaf("false");

        List<TreeRow> listaTreeRowAulas = new ArrayList<TreeRow>();

        for (Aula aula : hashAulas.get(edificio + tipo))
        {
            listaTreeRowAulas.add(aulaToTreeRow(aula));
        }

        treeRowTipoAula.setHijos(listaTreeRowAulas);

        return treeRowTipoAula;

    }

    private TreeRow aulaToTreeRow(Aula aula)
    {
        TreeRow nodo = new TreeRow();

        nodo.setId(aula.getId().toString());
        nodo.setTitle(aula.getNombre());
        nodo.setText(aula.getNombre());
        nodo.setLeaf("true");
        return nodo;
    }

    private HashMap<String, List<String>> extraeEdificiosYTiposAula(List<Aula> listaAulas)
    {
        HashMap<String, List<String>> hashEdificios = new HashMap<String, List<String>>();

        for (Aula aula : listaAulas)
        {
            if (!hashEdificios.keySet().contains(aula.getEdificio()))
            {
                List<String> tipos = new ArrayList<String>();
                tipos.add(aula.getTipo());
                hashEdificios.put(aula.getEdificio(), tipos);
            }

            if (!hashEdificios.get(aula.getEdificio()).contains(aula.getTipo()))
            {
                hashEdificios.get(aula.getEdificio()).add(aula.getTipo());
            }
        }

        return hashEdificios;
    }

    @GET
    @Path("estudio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getAulasAsignadasToEstudio(@PathParam("id") String estudioId,
            @QueryParam("semestreId") String semestreId, @QueryParam("cursoId") String cursoId)
    {
        Long semestre = null;
        try
        {
            semestre = Long.parseLong(semestreId);
        }
        catch (Exception e)
        {
        }

        Long curso = null;
        try
        {
            curso = Long.parseLong(cursoId);
        }
        catch (Exception e)
        {
        }

        List<AulaPlanificacion> aulasAsignadas = consultaAulas.getAulasAsignadasToEstudio(
                Long.parseLong(estudioId), semestre, curso);

        return UIEntity.toUI(aulasAsignadas);
    }

    @PUT
    @Path("estudio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> asignaAulaToEstudio(@PathParam("id") String estudioId,
            @QueryParam("aulaId") String aulaId, @QueryParam("semestreId") String semestreId,
            @QueryParam("cursoId") String cursoId) throws RegistroNoEncontradoException,
            NumberFormatException
    {
        Long semestre = null;
        try
        {
            semestre = Long.parseLong(semestreId);
        }
        catch (Exception e)
        {
        }

        Long curso = null;
        try
        {
            curso = Long.parseLong(cursoId);
        }
        catch (Exception e)
        {
        }

        AulaPlanificacion aulaPlanificacion = consultaAulas.asignaAulaToEstudio(
                Long.parseLong(estudioId), Long.parseLong(aulaId), semestre, curso);

        return Collections.singletonList(UIEntity.toUI(aulaPlanificacion));
    }
    
    @DELETE
    @Path("planificacion/{id}")
    public void deleteAulaAsignadaToEstudio(@PathParam("id") String aulaPlanificacionId)
    {
        consultaAulas.deleteAulaAsignadaToEstudio(Long.parseLong(aulaPlanificacionId));
    }
}
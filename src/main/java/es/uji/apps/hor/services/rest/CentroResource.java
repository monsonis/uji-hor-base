package es.uji.apps.hor.services.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.core.InjectParam;

import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.services.CentroService;
import es.uji.commons.rest.UIEntity;
import es.uji.commons.rest.exceptions.RegistroNoEncontradoException;
import es.uji.commons.rest.model.tree.TreeRow;
import es.uji.commons.rest.model.tree.TreeRowset;

@Path("centro")
public class CentroResource
{
    @InjectParam
    private CentroService consultaCentros;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UIEntity> getCentros()
    {
        List<Centro> centros = consultaCentros.getCentros();

        return UIEntity.toUI(centros);
    }

    @GET
    @Path("{id}/tree")
    @Produces(MediaType.APPLICATION_JSON)
    public TreeRowset getAulasCentroRowSet(@PathParam("id") String centroId)
            throws RegistroNoEncontradoException, NumberFormatException
    {
        TreeRowset treeRowSetCentro = new TreeRowset();
        Centro centro = consultaCentros.getCentroById(Long.parseLong(centroId));

        for (Edificio edificio : centro.getEdificios())
        {
            treeRowSetCentro.getRow().add(creaRowSetDesdeEdificio(edificio));
        }
        return treeRowSetCentro;
    }

    private TreeRow creaRowSetDesdeEdificio(Edificio edificio)
    {
        TreeRow treeRowEdificio = new TreeRow();

        treeRowEdificio.setId(UUID.randomUUID().toString());
        treeRowEdificio.setTitle("Edifici " + edificio.getNombre());
        treeRowEdificio.setText("Edifici " + edificio.getNombre());
        treeRowEdificio.setLeaf("false");

        List<TreeRow> listaTreeRowTipos = new ArrayList<TreeRow>();

        for (TipoAula tipoAula : edificio.getTiposAulas())
        {
            listaTreeRowTipos.add(tipoAulaToTreeRow(tipoAula));
        }

        treeRowEdificio.setHijos(listaTreeRowTipos);

        return treeRowEdificio;
    }

    private TreeRow tipoAulaToTreeRow(TipoAula tipoAula)
    {
        TreeRow treeRowTipoAula = new TreeRow();

        treeRowTipoAula.setId(UUID.randomUUID().toString());
        treeRowTipoAula.setTitle("Tipus " + tipoAula.getNombre());
        treeRowTipoAula.setText("Tipus " + tipoAula.getNombre());
        treeRowTipoAula.setLeaf("false");

        List<TreeRow> listaTreeRowPlantas = new ArrayList<TreeRow>();
        List<Aula> listaAulas = tipoAula.getAulas();
        List<PlantaEdificio> listaPlantasEdificio = getPlantasEdificioDeListaAulas(listaAulas);

        for (PlantaEdificio planta : listaPlantasEdificio)
        {
            listaTreeRowPlantas.add(plantaPorTipoToTreeRow(planta, tipoAula));
        }
        treeRowTipoAula.setHijos(listaTreeRowPlantas);

        return treeRowTipoAula;

    }

    private List<PlantaEdificio> getPlantasEdificioDeListaAulas(List<Aula> listaAulas)
    {
        List<PlantaEdificio> listaPlantasEdificio = new ArrayList<PlantaEdificio>();

        for (Aula aula : listaAulas)
        {
            if (!listaPlantasEdificio.contains(aula.getPlanta()))
            {
                listaPlantasEdificio.add(aula.getPlanta());
            }
        }

        return listaPlantasEdificio;

    }

    private TreeRow plantaPorTipoToTreeRow(PlantaEdificio planta, TipoAula tipoAula)
    {
        TreeRow treeRowPlanta = new TreeRow();

        treeRowPlanta.setId(UUID.randomUUID().toString());
        treeRowPlanta.setTitle("Planta " + planta.getNombre());
        treeRowPlanta.setText("Planta " + planta.getNombre());
        treeRowPlanta.setLeaf("false");

        List<TreeRow> listaTreeRowAulas = new ArrayList<TreeRow>();

        for (Aula aula : planta.getAulas())
        {
            if (aula.getTipo().equals(tipoAula))
            {
                listaTreeRowAulas.add(aulaToTreeRow(aula));
            }
        }
        treeRowPlanta.setHijos(listaTreeRowAulas);

        return treeRowPlanta;

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

}
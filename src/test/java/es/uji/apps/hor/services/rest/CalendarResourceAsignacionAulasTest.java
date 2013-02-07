package es.uji.apps.hor.services.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.StringKeyStringValueIgnoreCaseMultivaluedMap;

import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.builders.AreaEdificioBuilder;
import es.uji.apps.hor.builders.AulaBuilder;
import es.uji.apps.hor.builders.AulaPlanificacionBuilder;
import es.uji.apps.hor.builders.CentroBuilder;
import es.uji.apps.hor.builders.EdificioBuilder;
import es.uji.apps.hor.builders.EstudioBuilder;
import es.uji.apps.hor.builders.PlantaEdificioBuilder;
import es.uji.apps.hor.builders.TipoAulaBuilder;
import es.uji.apps.hor.builders.TipoEstudioBuilder;
import es.uji.apps.hor.dao.AulaDAO;
import es.uji.apps.hor.dao.CentroDAO;
import es.uji.apps.hor.dao.EventosDAO;
import es.uji.apps.hor.model.AreaEdificio;
import es.uji.apps.hor.model.Aula;
import es.uji.apps.hor.model.AulaPlanificacion;
import es.uji.apps.hor.model.Centro;
import es.uji.apps.hor.model.Edificio;
import es.uji.apps.hor.model.Estudio;
import es.uji.apps.hor.model.PlantaEdificio;
import es.uji.apps.hor.model.Semestre;
import es.uji.apps.hor.model.TipoAula;
import es.uji.apps.hor.model.TipoEstudio;
import es.uji.commons.rest.UIEntity;

@Ignore
public class CalendarResourceAsignacionAulasTest extends AbstractCalendarResourceTest
{
    private Long aulaPlanificacionId;
    private Long aulaPlanificacionSinEstudioId;

    @Autowired
    private CentroDAO centroDao;

    @Autowired
    private AulaDAO aulaDao;

    @Autowired
    protected EventosDAO eventosDao;

    public void creaDatosIniciales() throws Exception
    {
        creaEventosIniciales();
        creaAulasParaAsignarAEventos();
    }

    @Transactional
    private void creaAulasParaAsignarAEventos() throws DuracionEventoIncorrectaException,
            ParseException
    {
        TipoEstudio tipoEstudio = new TipoEstudioBuilder().withId("G").withNombre("Grau").build();

        Estudio otroEstudio = new EstudioBuilder(estudiosDAO).withNombre("Estudio de Prueba")
                .withTipoEstudio(tipoEstudio).build();
        otroEstudioId = otroEstudio.getId();

        Centro centro = new CentroBuilder(centroDao).withNombre("Centro de Prueba").build();
        AreaEdificio areaEdificio = new AreaEdificioBuilder().withNombre("Área 1").build();
        PlantaEdificio plantaEdificio = new PlantaEdificioBuilder().withNombre("1").build();
        TipoAula tipoAula = new TipoAulaBuilder().withNombre("1").build();

        AreaEdificio areaEdificio2 = new AreaEdificioBuilder().withNombre("Área 2").build();
        PlantaEdificio plantaEdificio2 = new PlantaEdificioBuilder().withNombre("2").build();
        TipoAula tipoAula2 = new TipoAulaBuilder().withNombre("2").build();

        Edificio edificio = new EdificioBuilder().withNombre("Edificio 1").withCentro(centro)
                .build();

        Semestre semestre = new Semestre(semestreId);
        
        Aula aula1 = new AulaBuilder(aulaDao).withArea(areaEdificio).withCodigo("AUL1")
                .withEdificio(edificio).withNombre("Aula 1").withPlanta(plantaEdificio)
                .withPlazas(new Long(100)).withTipo(tipoAula).build();

        Aula aula2 = new AulaBuilder(aulaDao).withArea(areaEdificio2).withCodigo("AUL2")
                .withEdificio(edificio).withNombre("Aula 2").withPlanta(plantaEdificio2)
                .withPlazas(new Long(100)).withTipo(tipoAula2).build();

        AulaPlanificacion aulaPlanificacion = new AulaPlanificacionBuilder(aulaDao)
                .withAula(aula1).withEstudio(estudio).withSemestre(semestre)
                .build();

        aulaPlanificacionId = aulaPlanificacion.getId();

        AulaPlanificacion aulaPlanificacionSinEstudio = new AulaPlanificacionBuilder(aulaDao)
                .withAula(aula2).withEstudio(otroEstudio).withSemestre(semestre)
                .build();

        aulaPlanificacionSinEstudioId = aulaPlanificacionSinEstudio.getId();

    }

    @Test
    @Transactional
    public void asignaUnAulaAUnEvento()
    {
        this.getListaEventosGenericos();
        String eventoId = this.getListaEventosGenericos().get(0).get("id");

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionId));
        params.putSingle("tipoAccion", "F");

        resource.path("calendario/eventos/aula/evento/" + eventoId)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        assertThat(tieneAulaAsignada(eventoId), is(true));
    }

    @Test
    @Transactional
    public void asignaUnAulaAUnEventoConDistintoEstudio()
    {
        this.getListaEventosGenericos();
        String eventoId = this.getListaEventosGenericos().get(1).get("id");

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionSinEstudioId));
        params.putSingle("tipoAccion", "F");

        ClientResponse response = resource.path("calendario/eventos/aula/evento/" + eventoId)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        String message = response.getEntity(String.class);

        assertThat(message,
                containsString("L'aula seleccinada no està assignada a la titulació del event"));
    }

    @Test
    @Transactional
    public void asignaAulaAUnEventoYPropagaAGruposDivididos() throws Exception
    {

        String evento_id = "2";

        resource.path("calendario/eventos/generica/divide/" + evento_id)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionId));
        params.putSingle("tipoAccion", "T");

        resource.path("calendario/eventos/aula/evento/" + evento_id)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        UIEntity duplicado = getEventoDuplicado();

        assertThat(coincidenAulasEventoDividido(evento_id, duplicado.get("id")), is(true));
    }

    @Test
    @Transactional
    public void asignaAulaAEventoYCompruebaQueAlDesplanificarEventoSeDesasignaElAula()
    {
        this.getListaEventosGenericos();
        String eventoId = this.getListaEventosGenericos().get(0).get("id");

        MultivaluedMap<String, String> params = new StringKeyStringValueIgnoreCaseMultivaluedMap();
        params.putSingle("aulaId", String.valueOf(aulaPlanificacionId));
        params.putSingle("tipoAccion", "F");

        resource.path("calendario/eventos/aula/evento/" + eventoId)
                .accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, params);

        resource.path("calendario/eventos/generica/" + eventoId)
                .accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);

        resource.path("grupoAsignatura/sinAsignar/" + eventoId)
                .accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class);

        assertThat(tieneAulaAsignada(eventoId), is(false));
    }

    private boolean tieneAulaAsignada(String eventoId)
    {
        UIEntity entity = getDatosEventoGenerico(eventoId);

        return entity.get("aula_planificacion_id") != null;
    }

    private boolean coincidenAulasEventoDividido(String originalId, String duplicadoId)
    {
        UIEntity entityO = getDatosEventoGenerico(originalId);
        UIEntity entityD = getDatosEventoGenerico(duplicadoId);

        return entityD.get("aula_planificacion_id").equals(entityO.get("aula_planificacion_id"));
    }

    private UIEntity getEventoDuplicado() throws ParseException
    {
        String id_original = "2";
        int hora_inicio_esperada = 11;
        int minuto_inicio_esperado = 0;
        int segundo_inicio_esperado = 0;
        int dia_esperado = Calendar.WEDNESDAY;

        Calendar cal = Calendar.getInstance();

        for (UIEntity entity : getListaEventosGenericos())
        {
            String entity_id = entity.get("id").trim();
            String entity_start_str = entity.get("start").trim();

            Date entity_start_date = UIEntityDateFormat.parse(entity_start_str);
            cal.setTime(entity_start_date);

            if (cal.get(Calendar.HOUR_OF_DAY) == hora_inicio_esperada
                    && cal.get(Calendar.MINUTE) == minuto_inicio_esperado
                    && cal.get(Calendar.SECOND) == segundo_inicio_esperado
                    && cal.get(Calendar.DAY_OF_WEEK) == dia_esperado
                    && !entity_id.equals(id_original))
            {
                return entity;
            }
        }

        return null;
    }
}

package es.uji.apps.hor.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import es.uji.apps.hor.AsignaturasComparator;
import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.EventoFueraDeFechasSemestreException;
import es.uji.apps.hor.EventoFueraDeRangoException;
import es.uji.apps.hor.EventoNoDivisibleException;
import es.uji.apps.hor.RangoHorarioFueradeLimites;

@Component
public class Evento
{
    private static final int LUNES = 1;
    private static final int MARTES = 2;
    private static final int MIERCOLES = 3;
    private static final int JUEVES = 4;
    private static final int VIERNES = 5;
    private static final int SABADO = 6;
    private static final int DOMINGO = 7;

    private static final Long UNA_HORA_EN_MILISEGUNDOS = (long) 3600000;

    private Long id;
    private Calendario calendario;
    private String titulo;
    private String observaciones;
    private Date inicio;
    private Date fin;
    private Boolean detalleManual;
    private Integer numeroIteraciones;
    private Integer repetirCadaSemanas;
    private Date desdeElDia;
    private Date hastaElDia;
    private List<Asignatura> asignaturas = new ArrayList<Asignatura>();
    private Semestre semestre;
    private String grupoId;
    private Long subgrupoId;
    private Long plazas;
    private List<EventoDetalle> eventosDetalle = new ArrayList<EventoDetalle>();

    private Aula aula;

    public Evento(Long id, Calendario calendario, String titulo, Date inicio, Date fin)
    {
        this.id = id;
        this.calendario = calendario;
        this.titulo = titulo;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Evento()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Calendario getCalendario()
    {
        return calendario;
    }

    public void setCalendario(Calendario calendario)
    {
        this.calendario = calendario;
    }

    public String getDescripcionParaUnEstudio(Long estudioId)
    {
        Asignatura asignatura = getAsignaturaDelEstudio(estudioId);
        if (asignatura == null)
        {
            return "";
        }

        String texto = MessageFormat.format("{0} {1} {2}{3}", asignatura.getId(), grupoId,
                getCalendario().getLetraId(), subgrupoId);

        if (tieneComunes())
        {
            texto = MessageFormat.format("{0} - C", texto);
        }

        if (getAula() != null)
        {
            texto = MessageFormat.format("{0} {1}", texto, getAula().getCodigo());
        }

        return texto;
    }

    public String getDescripcionCompletaParaUnEstudio(Long estudioId)
    {
        Asignatura asignatura = getAsignaturaDelEstudio(estudioId);
        if (asignatura == null)
        {
            return "";
        }

        String texto = MessageFormat.format("{0} {1} Grup {2} {3}{4}", asignatura.getId(),
                asignatura.getNombre(), grupoId, getCalendario().getLetraId(), subgrupoId);

        if (tieneComunes())
        {
            texto = MessageFormat.format("{0} - Comú", texto);
        }

        if (getAula() != null)
        {
            texto = MessageFormat.format("{0} {1}", texto, getAula().getNombre());
        }

        return texto;
    }

    public boolean tieneComunes()
    {
        return asignaturas.size() > 1;
    }

    public String getTitulo()
    {
        if (titulo != null && !titulo.isEmpty())
        {
            return titulo;
        }
        else
        {
            return this.toString();
        }
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getObservaciones()
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones)
    {
        this.observaciones = observaciones;
    }

    public Date getInicio()
    {
        return inicio;
    }

    private void setInicio(Date inicio)
    {
        this.inicio = inicio;
    }

    public Date getFin()
    {
        return fin;
    }

    private void setFin(Date fin)
    {
        this.fin = fin;
    }

    public void setFechaInicioYFin(Date inicio, Date fin) throws DuracionEventoIncorrectaException
    {
        if (fechasEnElMismoDiaYEnSemanaLaboral(inicio, fin))
        {
            this.setInicio(inicio);
            this.setFin(fin);
            propagaRangoHorarioAEventosDetalle();
        }
        else
        {
            throw new DuracionEventoIncorrectaException();
        }
    }

    private boolean fechasEnElMismoDiaYEnSemanaLaboral(Date inicio, Date fin)
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();

        calInicio.setTime(inicio);
        calFin.setTime(fin);

        return mismoDia(calInicio, calFin) && noEsFinDeSemana(calInicio);
    }

    private boolean mismoDia(Calendar calInicio, Calendar calFin)
    {
        return calInicio.get(Calendar.YEAR) == calFin.get(Calendar.YEAR)
                && calInicio.get(Calendar.MONTH) == calFin.get(Calendar.MONTH)
                && calInicio.get(Calendar.DAY_OF_MONTH) == calFin.get(Calendar.DAY_OF_MONTH);
    }

    private boolean noEsFinDeSemana(Calendar calInicio)
    {
        return calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }

    public Boolean hasDetalleManual()
    {
        return detalleManual;
    }

    public void setDetalleManual(Boolean detalleManual)
    {
        this.detalleManual = detalleManual;
    }

    public Integer getNumeroIteraciones()
    {
        return numeroIteraciones;
    }

    public void setNumeroIteraciones(Integer numeroIteraciones)
    {
        this.numeroIteraciones = numeroIteraciones;
    }

    public Integer getRepetirCadaSemanas()
    {
        return repetirCadaSemanas;
    }

    public void setRepetirCadaSemanas(Integer repetirCadaSemanas)
    {
        this.repetirCadaSemanas = repetirCadaSemanas;
    }

    public Date getDesdeElDia()
    {
        return desdeElDia;
    }

    public void setDesdeElDia(Date desdeElDia)
    {
        this.desdeElDia = desdeElDia;
    }

    public Date getHastaElDia()
    {
        return hastaElDia;
    }

    public void setHastaElDia(Date hastaElDia)
    {
        this.hastaElDia = hastaElDia;
    }

    public Evento divide() throws EventoNoDivisibleException
    {
        if (duraMenosDeUnaHora())
        {
            throw new EventoNoDivisibleException();
        }

        Evento nuevoEvento = this.clonar();
        nuevoEvento.retrasaHoraInicioAMitadDuracion();
        this.reduceDuracionALaMitad();

        nuevoEvento.propagaRangoHorarioAEventosDetalle();
        this.propagaRangoHorarioAEventosDetalle();
        return nuevoEvento;
    }

    private boolean duraMenosDeUnaHora()
    {
        return getDuracionEnMilisegundos() < UNA_HORA_EN_MILISEGUNDOS;
    }

    private void propagaRangoHorarioAEventosDetalle()
    {
        for (EventoDetalle eventoDetalle : this.getEventosDetalle())
        {
            eventoDetalle.estableceHoraYMinutosInicio(this.getInicio());
            eventoDetalle.estableceHoraYMinutosFin(this.getFin());
        }
    }

    private void reduceDuracionALaMitad()
    {
        Date nuevaHoraFin = new Date(this.getFin().getTime() - this.getDuracionEnMilisegundos() / 2);
        this.setFin(nuevaHoraFin);
    }

    private Long getDuracionEnMilisegundos()
    {
        return this.getFin().getTime() - this.getInicio().getTime();
    }

    private void retrasaHoraInicioAMitadDuracion()
    {
        Date nuevaHoraInicio = new Date(this.getInicio().getTime()
                + this.getDuracionEnMilisegundos() / 2);
        this.setInicio(nuevaHoraInicio);
    }

    private Evento clonar()
    {

        Evento nuevo = new Evento();
        nuevo.setCalendario(this.getCalendario());
        nuevo.setTitulo(this.getTitulo());
        nuevo.setObservaciones(this.getObservaciones());
        nuevo.setInicio(this.getInicio());
        nuevo.setFin(this.getFin());
        nuevo.setDetalleManual(this.hasDetalleManual());
        nuevo.setNumeroIteraciones(this.getNumeroIteraciones());
        nuevo.setRepetirCadaSemanas(this.getRepetirCadaSemanas());
        nuevo.setDesdeElDia(this.getDesdeElDia());
        nuevo.setHastaElDia(this.getHastaElDia());
        nuevo.setAula(this.getAula());
        nuevo.setAsignaturas(this.getAsignaturas());
        nuevo.setSemestre(this.getSemestre());
        nuevo.setGrupoId(this.getGrupoId());
        nuevo.setSubgrupoId(this.getSubgrupoId());
        nuevo.setPlazas(this.getPlazas());

        if (this.hasDetalleManual())
        {
            for (EventoDetalle eventoDetalle : this.getEventosDetalle())
            {
                EventoDetalle nuevoEventoDetalle = eventoDetalle.clonar();
                nuevoEventoDetalle.setEvento(nuevo);
                nuevo.addEventoDetalle(nuevoEventoDetalle);
            }
        }

        return nuevo;
    }

    public void addEventoDetalle(EventoDetalle nuevoEventoDetalle)
    {
        this.eventosDetalle.add(nuevoEventoDetalle);
    }

    public EventoDetalle creaDetalleEnFecha(Date fecha)
    {
        EventoDetalle detalle = new EventoDetalle(this, fecha);
        addEventoDetalle(detalle);
        return detalle;
    }

    public List<Asignatura> getAsignaturas()
    {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas)
    {
        Collections.sort(asignaturas, new AsignaturasComparator());
        this.asignaturas = asignaturas;
    }

    public Asignatura getAsignaturaDelEstudio(Long estudioId)
    {
        for (Asignatura asig : asignaturas)
        {
            if (asig.getEstudio().getId().equals(estudioId))
            {
                return asig;
            }
        }

        return null;
    }

    public Semestre getSemestre()
    {
        return semestre;
    }

    public void setSemestre(Semestre semestre)
    {
        this.semestre = semestre;
    }

    public String getGrupoId()
    {
        return grupoId;
    }

    public void setGrupoId(String grupoId)
    {
        this.grupoId = grupoId;
    }

    public Long getSubgrupoId()
    {
        return subgrupoId;
    }

    public void setSubgrupoId(Long subgrupoId)
    {
        this.subgrupoId = subgrupoId;
    }

    private Integer convierteDiaSemaanDeCalendar(Integer diaCalendario)
    {
        switch (diaCalendario)
        {
        case Calendar.MONDAY:
            return LUNES;
        case Calendar.TUESDAY:
            return MARTES;
        case Calendar.WEDNESDAY:
            return MIERCOLES;
        case Calendar.THURSDAY:
            return JUEVES;
        case Calendar.FRIDAY:
            return VIERNES;
        case Calendar.SATURDAY:
            return SABADO;
        case Calendar.SUNDAY:
            return DOMINGO;
        }

        return 0;
    }

    public Integer getDia()
    {
        if (this.getInicio() == null)
        {
            return null;
        }

        Calendar actual = Calendar.getInstance(new Locale("es", "ES"));
        actual.setTime(this.getInicio());

        return convierteDiaSemaanDeCalendar(actual.get(Calendar.DAY_OF_WEEK));

    }

    public Long getPlazas()
    {
        return plazas;
    }

    public void setPlazas(Long plazas)
    {
        this.plazas = plazas;
    }

    public List<EventoDetalle> getEventosDetalle()
    {
        return eventosDetalle;
    }

    public void setEventosDetalle(List<EventoDetalle> eventosDetalle)
    {
        this.eventosDetalle = eventosDetalle;
    }

    public void desplanificar()
    {
        this.setInicio(null);
        this.setFin(null);
        this.setAula(null);
    }

    public void actualizaAula(Aula aula) throws AulaNoAsignadaAEstudioDelEventoException
    {
        if (!aulaAsignadaAAlgunEstudioDelEvento(aula))
        {
            throw new AulaNoAsignadaAEstudioDelEventoException();
        }

        this.aula = aula;
    }

    public void desasignaAula()
    {
        aula = null;
    }

    private boolean aulaAsignadaAAlgunEstudioDelEvento(Aula aula)
    {

        List<Long> estudiosIds = new ArrayList<Long>();

        for (Asignatura asignatura : asignaturas)
        {
            estudiosIds.add(asignatura.getEstudio().getId());
        }

        for (AulaPlanificacion planificacion : aula.getPlanificacion())
        {
            Estudio estudio = planificacion.getEstudio();

            if (estudiosIds.contains(estudio.getId())
                    && semestre.getSemestre().equals(planificacion.getSemestre().getSemestre()))
            {
                return true;
            }
        }
        return false;
    }

    public void vaciaEventosDetalle()
    {
        getEventosDetalle().clear();
    }

    public String getAsignaturasComunes(Long estudioId)
    {
        if (!tieneComunes())
        {
            return "";
        }

        String comunes = "";

        for (Asignatura asignatura : asignaturas)
        {
            if (!asignatura.getEstudio().getId().equals(estudioId))
            {
                comunes = comunes + ", " + asignatura.getId();
            }
        }

        if (comunes.length() > 2)
        {
            comunes = comunes.substring(2);
        }

        return comunes;
    }

    public void compruebaDentroDeLosRangosHorarios(List<RangoHorario> rangosHorarios)
            throws EventoFueraDeRangoException
    {
        for (RangoHorario rangoHorario : rangosHorarios)
        {
            try
            {
                rangoHorario.compruebaEventoDentroDeRango(this);
            }
            catch (RangoHorarioFueradeLimites e)
            {
                throw new EventoFueraDeRangoException(grupoId,
                        rangoHorario.getRangoHorarioAsString());
            }
        }

    }

    public void compruebaDentroFechasSemestre(Date fechaInicioSemestre, Date fechaFinSemestre)
            throws EventoFueraDeFechasSemestreException
    {
        if (!dentroDelRangoDeFechas(fechaInicioSemestre, fechaFinSemestre))
        {
            throw new EventoFueraDeFechasSemestreException();
        }

    }

    private boolean dentroDelRangoDeFechas(Date fechaInicio, Date fechaFin)
    {
        return getInicio().after(fechaInicio) && getFin().before(fechaFin);

    }

    public String getDescripcionConGrupoYComunes()
    {
        String tituloEvento = "";

        for (Asignatura asignatura : this.asignaturas)
        {
            tituloEvento = tituloEvento + " " + asignatura.getId();
        }

        tituloEvento += " " + this.grupoId + " " + this.calendario.getLetraId() + this.subgrupoId;

        return tituloEvento;
    }

    public Aula getAula()
    {
        return aula;
    }

    public void setAula(Aula aula)
    {
        this.aula = aula;
    }

}

package es.uji.apps.hor.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.uji.apps.hor.AulaNoAsignadaAEstudioDelEventoException;
import es.uji.apps.hor.DuracionEventoIncorrectaException;
import es.uji.apps.hor.dao.EventosDAO;

@Component
public class Evento
{
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
    private Asignatura asignatura;
    private Semestre semestre;
    private String grupoId;
    private Long subgrupoId;
    private Long plazas;
    private List<EventoDetalle> eventosDetalle = new ArrayList<EventoDetalle>();
    private List<Evento> eventosComunes;

    private AulaPlanificacion aulaPlanificacion;

    private EventosDAO eventosDAO;

    @Autowired
    public void setEventosDAO(EventosDAO eventosDAO)
    {
        this.eventosDAO = eventosDAO;
    }

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

    public String getTitulo()
    {
        return titulo;
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

    public void setInicio(Date inicio)
    {
        this.inicio = inicio;
    }

    public Date getFin()
    {
        return fin;
    }

    public void setFin(Date fin)
    {
        this.fin = fin;
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

    public Evento divide()
    {
        Evento nuevoEvento = this.clonar();
        nuevoEvento.retrasaHoraInicioAMitadDuracion();
        this.reduceDuracionALaMitad();

        nuevoEvento.propagaRangoHorarioAEventosDetalle();
        this.propagaRangoHorarioAEventosDetalle();
        return nuevoEvento;
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
        nuevo.setAulaPlanificacion(this.getAulaPlanificacion());
        nuevo.setAsignatura(this.getAsignatura());
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

    private void addEventoDetalle(EventoDetalle nuevoEventoDetalle)
    {
        this.getEventosDetalle().add(nuevoEventoDetalle);
    }

    public AulaPlanificacion getAulaPlanificacion()
    {
        return aulaPlanificacion;
    }

    public void setAulaPlanificacion(AulaPlanificacion aulaPlanificacion)
    {
        this.aulaPlanificacion = aulaPlanificacion;
    }

    public Asignatura getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura)
    {
        this.asignatura = asignatura;
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
            return 1;
        case Calendar.TUESDAY:
            return 2;
        case Calendar.WEDNESDAY:
            return 3;
        case Calendar.THURSDAY:
            return 4;
        case Calendar.FRIDAY:
            return 5;
        case Calendar.SATURDAY:
            return 6;
        case Calendar.SUNDAY:
            return 7;
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

    public List<Evento> getEventosComunes()
    {
        return eventosComunes;
    }

    public void setEventosComunes(List<Evento> eventosComunes)
    {
        this.eventosComunes = eventosComunes;
    }

    public void updateDiaYHora(Date inicio, Date fin) throws DuracionEventoIncorrectaException
    {
        Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();

        calInicio.setTime(inicio);
        calFin.setTime(fin);

        if (calInicio.get(Calendar.YEAR) == calFin.get(Calendar.YEAR)
                && calInicio.get(Calendar.MONTH) == calFin.get(Calendar.MONTH)
                && calInicio.get(Calendar.DAY_OF_MONTH) == calFin.get(Calendar.DAY_OF_MONTH)
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {

            this.setInicio(inicio);
            this.setFin(fin);
        }
        else
        {
            throw new DuracionEventoIncorrectaException();
        }
    }

    public void actualizaAulaPlanificacion(AulaPlanificacion aulaPlanificacion)
            throws AulaNoAsignadaAEstudioDelEventoException
    {
        if (!aulaPlanificacion.getEstudioId().equals(asignatura.getEstudio().getId()))
        {
            throw new AulaNoAsignadaAEstudioDelEventoException();
        }
        
        this.aulaPlanificacion = aulaPlanificacion;
    }
}

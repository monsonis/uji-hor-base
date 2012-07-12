Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos' ],
    model : [ 'Calendario', 'Evento' ],
    refs : [
    {
        selector : 'filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    },
    {
        selector : 'selectorCalendarios',
        ref : 'selectorCalendarios'
    } ],

    init : function()
    {
        this.control(
        {
            'filtroGrupos > #grupos' :
            {
                select : this.refreshCalendar
            },
            'selectorCalendarios checkbox' :
            {
                change : this.refreshCalendar
            },
            'selectorGrupos button' :
            {
                click : this.addEvento
            }
        });
    },

    refreshCalendar : function()
    {
        var titulaciones = this.getFiltroGrupos().down('#titulaciones');
        var cursos = this.getFiltroGrupos().down('#cursos');
        var semestres = this.getFiltroGrupos().down('#semestres');
        var grupos = this.getFiltroGrupos().down('#grupos');

        if (grupos.getValue() != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

            var storeEventos = this.getStoreEventosStore();
            storeEventos.getProxy().extraParams["estudioId"] = titulaciones.getValue();
            storeEventos.getProxy().extraParams["cursoId"] = cursos.getValue();
            storeEventos.getProxy().extraParams["semestreId"] = semestres.getValue();
            storeEventos.getProxy().extraParams["grupoId"] = grupos.getValue();
            storeEventos.getProxy().extraParams["calendariosIds"] = calendarios;

            this.getPanelCalendario().getActiveView().refresh(true);
        }
    },

    addEvento : function()
    {
        var evento = Ext.create('Extensible.calendar.data.EventModel',
        {
            StartDate : '2012-07-10 17:00:00',
            EndDate : '2012-07-10 18:30:00',
            Title : 'My cool event',
            Notes : 'Some notes',
            CalendarId : 5
        });
        this.getStoreCalendariosStore().add(evento);
        this.getStoreCalendariosStore().sync(
        {
            callback : function()
            {
                this.getPanelCalendario().getActiveView().refresh(true);
            }
        });
    }

});
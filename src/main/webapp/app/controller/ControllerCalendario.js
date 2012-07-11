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
    } ],

    init : function()
    {
        this.control(
        {
            'filtroGrupos > #grupos' :
            {
                select : this.onFilterSelected
            },
            'selectorGrupos button' :
            {
                click : this.addEvento
            }
        });
    },

    onFilterSelected : function(combo, records)
    {
        var grupo = records[0].get('grupo');
        var titulaciones = this.getFiltroGrupos().down('#titulaciones');
        var cursos = this.getFiltroGrupos().down('#cursos');
        var semestres = this.getFiltroGrupos().down('#semestres');
        var storeEventos = this.getStoreEventosStore();
        storeEventos.getProxy().extraParams["estudioId"] = titulaciones.getValue();
        storeEventos.getProxy().extraParams["cursoId"] = cursos.getValue();
        storeEventos.getProxy().extraParams["semestreId"] = semestres.getValue();
        storeEventos.getProxy().extraParams["grupoId"] = grupo;
        this.getPanelCalendario().getActiveView().refresh(true);
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
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
    }

});
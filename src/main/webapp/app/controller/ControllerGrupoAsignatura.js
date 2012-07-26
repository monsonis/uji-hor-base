Ext.define('HOR.controller.ControllerGrupoAsignatura',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreGruposAsignaturasSinAsignar', 'StoreCalendarios' ],
    model : [ 'GrupoAsignatura' ],
    refs : [
    {
        selector : 'selectorGrupos',
        ref : 'selectorGrupos'
    },
    {
        selector : 'panelHorarios filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'selectorCalendarios',
        ref : 'selectorCalendarios'
    } ],

    init : function()
    {
        this.control(
        {
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                select : this.updateAsignaturasSinAsignar
            },
            'selectorCalendarios checkbox' :
            {
                change : this.updateAsignaturasSinAsignar
            },
        });
    },

    updateAsignaturasSinAsignar : function()
    {
        var store = this.getStoreGruposAsignaturasSinAsignarStore();

        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();

        if (grupo != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

            store.load(
            {
                callback : this.onGruposAsignaturasSinAsignarLoaded,
                params :
                {
                    estudioId : estudio,
                    cursoId : curso,
                    semestreId : semestre,
                    grupoId : grupo,
                    calendariosIds : calendarios
                },
                scope : this
            });

        }
    },

    onGruposAsignaturasSinAsignarLoaded : function(gruposAsignaturas, request)
    {
        var view = this.getSelectorGrupos();

        view.removeAll();

        for ( var i = 0, len = gruposAsignaturas.length; i < len; i++)
        {
            var margin = '10 10 0 10';

            var button =
            {
                xtype : 'button',
                text : gruposAsignaturas[i].data.titulo,
                padding : '2 5 2 5',
                margin : margin,
                grupoAsignaturaId : gruposAsignaturas[i].data.id,
            };

            view.add(button);
        }
    }

});
Ext.define('HOR.controller.ControllerGrupoAsignatura',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreGruposAsignaturasSinAsignar' ],
    model : [ 'GrupoAsignatura' ],
    refs : [
    {
        selector : 'selectorGrupos',
        ref : 'selectorGrupos'
    },
    {
        selector : 'filtroGrupos',
        ref : 'filtroGrupos'
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
        var store = this.getStoreGruposAsignaturasSinAsignarStore();

        var estudio = this.getFiltroGrupos().down('#titulaciones').getValue();
        var curso = this.getFiltroGrupos().down('#cursos').getValue();
        var semestre = this.getFiltroGrupos().down('#semestres').getValue();

        store.load(
        {
            callback : this.onGruposAsignaturasSinAsignarLoaded,
            params :
            {
                estudioId : estudio,
                cursoId : curso,
                semestreId : semestre,
                grupoId : records[0].data.grupo
            },
            scope : this
        });
    },

    onGruposAsignaturasSinAsignarLoaded : function(gruposAsignaturas, request)
    {
        var view = this.getSelectorGrupos();

        view.removeAll();

        for ( var i = 0, len = gruposAsignaturas.length; i < len; i++)
        {
            var margin = '5 30 0 30';

            if (i == 0)
            {
                margin = '25 30 0 30';
            }
            else if (i == len - 1)
            {
                margin = '5 30 25 30';
            }

            var button =
            {
                xtype : 'button',
                text : gruposAsignaturas[i].data.titulo,
                padding : 5,
                margin : margin,
                grupoAsignaturaId : gruposAsignaturas[i].data.id
            };

            view.add(button);
        }
    },
    addEvento : function()
    {
        var rec = new Extensible.calendar.data.EventModel(
        {
            StartDate : '2101-01-12 12:00:00',
            EndDate : '2101-01-12 13:30:00',
            Title : 'My cool event',
            Notes : 'Some notes'
        });
    }
});
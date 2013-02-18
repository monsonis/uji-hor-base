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
        selector : 'panelHorarios selectorCalendarios',
        ref : 'selectorCalendarios'
    },
    {
        selector : 'panelHorarios formAsignacionAulas',
        ref : 'formAsignacionAulas'
    },
    {
        selector : 'panelHorarios filtroGrupos combobox[name=grupo]',
        ref : 'comboGrupos'
    } ],

    initComponent : function()
    {
        this.addEvents('updateGrupos');
        this.callParent(arguments);
    },
    init : function()
    {
        var me = this;
        this.control(
        {
            'selectorGrupos' :
            {
                updateGrupos : this.updateAsignaturasSinAsignar
            },
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                blur : this.updateAsignaturasSinAsignar
            },
            'panelHorarios selectorCalendarios checkbox' :
            {
                change : this.updateAsignaturasSinAsignar
            },
            'panelCalendario' :
            {
                editdetails : function()
                {
                    me.getFiltroGrupos().setDisabled(true);
                    me.getSelectorGrupos().setDisabled(true);
                    me.getSelectorCalendarios().setDisabled(true);

                },
                eventcancel : function()
                {
                    me.getFiltroGrupos().setDisabled(false);
                    me.getSelectorGrupos().setDisabled(false);
                    me.getSelectorCalendarios().setDisabled(false);

                },
                eventasignaaula : function()
                {
                    me.getFiltroGrupos().setDisabled(true);
                    me.getSelectorGrupos().setDisabled(true);
                    me.getSelectorCalendarios().setDisabled(true);
                }
            },
            'formAsignacionAulas' :
            {
                eventasigaaulacancel : function()
                {
                    me.getFiltroGrupos().setDisabled(false);
                    me.getSelectorGrupos().setDisabled(false);
                    me.getSelectorCalendarios().setDisabled(false);
                }
            }
        });
    },

    updateAsignaturasSinAsignar : function()
    {
        if (this.getComboGrupos().getValue() == '')
        {
            return;
        }

        var store = this.getStoreGruposAsignaturasSinAsignarStore();

        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().getGruposSelected();

        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

        store.load(
        {
            callback : this.onGruposAsignaturasSinAsignarLoaded,
            params :
            {
                estudioId : estudio,
                cursoId : curso,
                semestreId : semestre,
                gruposId : grupo,
                calendariosIds : calendarios
            },
            scope : this
        });

    },

    onGruposAsignaturasSinAsignarLoaded : function(gruposAsignaturas, request)
    {
        var view = this.getSelectorGrupos();

        view.removeAll();

        var botones = new Array();

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

            botones.push(button);

        }

        view.add(botones);
    }

});
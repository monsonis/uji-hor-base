Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos', 'StoreGruposAsignaturasSinAsignar' ],
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
    },
    {
        selector : 'panelHorarios',
        ref : 'panelHorarios'
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
            },
            'filtroGrupos > #horarios' :
            {
                select : this.changeCalendarType
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

    addEvento : function(button)
    {
        var grupoId = button.grupoAsignaturaId;
        var storeGrupo = this.getStoreGruposAsignaturasSinAsignarStore();
        var record = storeGrupo.getById(grupoId);
        record.set('asignado', true);
        var ref = this;
        storeGrupo.sync(
        {
            callback : function()
            {
                button.destroy();
                ref.getPanelCalendario().getActiveView().refresh(true);
            }
        });
    },

    changeCalendarType : function(combo, record)
    {
        var tipoHorario = combo.getValue();

        switch (tipoHorario)
        {
        case 'Ma':
            this.getPanelHorarios().down('#calendarioMi').hide();
            this.getPanelHorarios().down('#calendarioTa').hide();
            this.getPanelHorarios().down('#calendarioMa').show();
            break;
        case 'Ta':
            this.getPanelHorarios().down('#calendarioMi').hide();
            this.getPanelHorarios().down('#calendarioMa').hide();
            this.getPanelHorarios().down('#calendarioTa').show();
            break;
        default:
            this.getPanelHorarios().down('#calendarioMa').hide();
            this.getPanelHorarios().down('#calendarioTa').hide();
            this.getPanelHorarios().down('#calendarioMi').show();
        }

        if (this.getFiltroGrupos().down('#grupos').getValue() == null)
        {
            this.getPanelCalendario().limpiaCalendario();
        }
        else
        {
            this.getPanelCalendario().getActiveView().refresh(true);
        }

        /*
         * var startHour = 8; var endHour = 22;
         * 
         * switch (tipoHorario) { case 'Ma': startHour = 8; endHour = 15; break; case 'Ta':
         * startHour = 15; endHour = 22; break; }
         * 
         * this.getPanelHorarios().down('panelCalendario').destroy();
         * 
         * var panelCalendario = Ext.create('HOR.view.horarios.PanelCalendario', { multiDayViewCfg : {
         * dayCount : 5, startDay : 1, startDayIsStatic : true, viewStartHour : startHour, showTime :
         * false, showMonth : false, viewEndHour : endHour, getStoreParams : function() { var params =
         * this.getStoreDateParams(); params.estudioId =
         * this.store.getProxy().extraParams['estudioId']; params.cursoId =
         * this.store.getProxy().extraParams['cursoId']; params.grupoId =
         * this.store.getProxy().extraParams['grupoId']; params.semestreId =
         * this.store.getProxy().extraParams['semestreId']; params.calendariosIds =
         * this.store.getProxy().extraParams['calendariosIds']; return params; } } });
         * 
         * this.getPanelHorarios().items.items[1].add(panelCalendario);
         */
    }
});
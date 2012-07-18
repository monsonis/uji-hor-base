Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos', 'StoreGruposAsignaturasSinAsignar', 'StoreConfiguracion' ],
    model : [ 'Calendario', 'Evento', 'Configuracion' ],
    refs : [
    {
        selector : 'panelHorarios filtroGrupos',
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
            'panelHorarios filtroGrupos combobox[name=grupo]' :
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
            'panelCalendario' :
            {
                eventresize : this.updateEvento,
                dayclick : function()
                {
                    return false;
                },
                rangeselect : function()
                {
                    return false;
                }
            }
        });
    },

    refreshCalendar : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        if (grupos.getValue() != null)
        {
            var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

            var storeEventos = this.getStoreEventosStore();
            storeEventos.getProxy().extraParams["estudioId"] = titulaciones.getValue();
            storeEventos.getProxy().extraParams["cursoId"] = cursos.getValue();
            storeEventos.getProxy().extraParams["semestreId"] = semestres.getValue();
            storeEventos.getProxy().extraParams["grupoId"] = grupos.getValue();
            storeEventos.getProxy().extraParams["calendariosIds"] = calendarios;

            var storeConfiguracion = this.getStoreConfiguracionStore();

            var ref = this;
            storeConfiguracion.load(
            {
                params :
                {
                    estudioId : titulaciones.getValue(),
                    cursoId : cursos.getValue(),
                    semestreId : semestres.getValue(),
                    grupoId : grupos.getValue()
                },
                scope : this,
                callback : function(records, operation, success)
                {
                    if (success)
                    {
                        var record = records[0];
                        var fechaInicio = record.get('horaInicio');
                        var fechaFin = record.get('horaFin');
                        
                        console.log(fechaInicio);
                        var inicio = Ext.Date.parse(fechaInicio, 'd/m/Y H:i:s', true);
                        var fin = Ext.Date.parse(fechaFin, 'd/m/Y H:i:s', true);
                        var horaInicio = Ext.Date.format(inicio, 'H:i');
                        var horaFin = Ext.Date.format(fin, 'H:i');
                        
                        var panelCalendario = ref.getPanelCalendario();
                        panelCalendario.destroy();
                    }
                }
            });
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

    updateEvento : function(calendario, registro)
    {
        var inicio = registro.get('StartDate');
        var fin = registro.get('EndDate');
        var storeEventos = this.getStoreEventosStore();
        storeEventos.sync();
    }

});
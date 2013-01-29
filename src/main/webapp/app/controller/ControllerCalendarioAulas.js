Ext.define('HOR.controller.ControllerCalendarioAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreAulasDetalle', 'StoreSemestreDetalles' ],
    refs : [
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    },
    {
        selector : 'panelCalendarioPorAula',
        ref : 'panelCalendarioPorAula'
    },
    {
        selector : 'panelCalendarioAulas selectorCalendarios',
        ref : 'selectorCalendarios'
    },
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    } ],

    init : function()
    {
        this.control(
        {
            'selectorAulas button' :
            {
                click : this.refreshEventsCalendar
            },
        });
    },

    refreshEventsCalendar : function(button)
    {
        var aulaId = button.aulaId;
        var calendarios = this.getSelectorCalendarios().getCalendarsSelected();

        var panelCalendario = this.getPanelCalendarioPorAula();
        if (!panelCalendario)
        {
            panelCalendario = this.getPanelCalendarioDetalle();
        }
        var panelPadre = panelCalendario.up('panel');

        Ext.Array.each(Ext.ComponentQuery.query('panelCalendarioPorAula'), function(panel)
        {
            panel.destroy();
        });

        var eventos = Ext.create('HOR.store.StoreAulasDetalle');
        Extensible.calendar.data.EventModel.reconfigure();

        var inicio = this.getInicioSemestre();
        var fin = new Date();
        fin.setDate(inicio.getDate() + 7);

        var params =
        {
            aulaId : aulaId,
            calendariosIds : calendarios,
            startDate : inicio,
            endDate : fin
        };
        eventos.getProxy().extraParams = params;

        var ref = this;
        panelPadre.add(
        {
            xtype : 'panelCalendarioPorAula',
            eventStore : eventos,
            showMultiDayView : true,
            viewConfig :
            {
                viewStartHour : 8,
                viewEndHour : 22
            },
            listeners :
            {
                afterrender : function()
                {
                    ref.getPanelCalendarioPorAula().setStartDate(inicio);
                    eventos.load();
                }
            }
        });
    },

    getInicioSemestre : function()
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var store = this.getStoreSemestreDetallesStore();

        for ( var i = 0; i < store.getCount(); i++)
        {
            var record = store.getAt(i);
            if (record.get('id') == semestre)
            {
                return record.get('fechaInicio');
            }
        }
    }
});
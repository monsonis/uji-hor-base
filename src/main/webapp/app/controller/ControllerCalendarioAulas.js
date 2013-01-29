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
        
        var store = this.getStoreAulasDetalleStore();
        
        var inicio = new Date();
        var fin = new Date();
        fin.setDate(inicio.getDate() + 7);
        
        var params = 
        {
                aulaId : aulaId,
                calendariosIds : calendarios,
                startDate : inicio,
                endDate : fin
        };
        store.getProxy().extraParams = params;
        
        var fechaInicio = this.getInicioSemestre();
        if (fechaInicio)
        {
            this.getPanelCalendarioPorAula().setStartDate(fechaInicio);
        }
        
        var ref = this;
        
        store.load(
        {
            scope : this,
            callback : function()
            {
                ref.getPanelCalendarioPorAula().getActiveView().refresh();
            }
        });
    },
    
    getInicioSemestre : function()
    {
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var store = this.getStoreSemestreDetallesStore();
        
        for (var i=0; i < store.getCount(); i++)
        {
            var record = store.getAt(i);
            if (record.get('id') == semestre)
            {
                return record.get('fechaInicio');
            }
            console.log(record);
        }
    }
});
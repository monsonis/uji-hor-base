Ext.define('HOR.controller.ControllerCalendarioAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreAulasDetalle' ],
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
        
        var ref = this;
        
        store.load(
        {
            scope : this,
            callback : function()
            {
                //ref.getPanelCalendarioPorAula().getActiveView().refresh(true);
            }
        });
    }
});
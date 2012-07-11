Ext.define('HOR.store.StoreCalendarios',
{
    extend: 'Extensible.calendar.data.MemoryCalendarStore',
    autoLoad : true,
    proxy :
    {
        type : 'ajax',
        url : '/hor/rest/calendario',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'data'
        }
    },
    addEvento: function(evento) {
        this.add(evento);
        this.getPanelCalendario().getActiveView().refresh(true);
    }
});
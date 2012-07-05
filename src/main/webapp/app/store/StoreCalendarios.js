Ext.define('HOR.store.StoreCalendarios',
{
    extend: 'Extensible.calendar.data.MemoryCalendarStore',
    model : 'HOR.model.Calendario',
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
    }
});
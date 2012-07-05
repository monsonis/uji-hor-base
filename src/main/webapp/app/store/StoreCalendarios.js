Ext.define('HOR.store.StoreCalendarios',
{
    extend: 'Extensible.calendar.data.MemoryCalendarStore',
    autoLoad : false,
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
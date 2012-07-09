Ext.define('HOR.store.StoreCalendarios',
{
    extend: 'Extensible.calendar.data.MemoryCalendarStore',
    model: 'Extensible.calendar.data.CalendarModel',
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
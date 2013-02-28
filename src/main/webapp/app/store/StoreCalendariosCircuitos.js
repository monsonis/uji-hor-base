Ext.define('HOR.store.StoreCalendariosCircuitos',
{
    extend : 'Extensible.calendar.data.MemoryCalendarStore',
    autoLoad : true,
    proxy :
    {
        type : 'ajax',
        url : '/hor/rest/calendario/circuito',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'data'
        }
    }
});
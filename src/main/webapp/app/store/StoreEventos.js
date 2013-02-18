Ext.define('HOR.store.StoreEventos',
{
    extend : 'Extensible.calendar.data.EventStore',
    autoLoad : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/calendario/eventos/generica',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'data'
        },

        extraParams :
        {
            estudioId : null,
            cursoId : null,
            semestreId : null,
            gruposId : null,
            calendariosIds : null
        },

        writer :
        {
            type : 'json',
            nameProperty : 'mapping'
        },

        listeners :
        {
            exception : function(proxy, response, operation, options)
            {
                var msg = response.message ? response.message : Ext.decode(response.responseText).message;
                Ext.Msg.alert('Server Error', msg);
            }
        }
    },

    initComponent : function(cfg)
    {
        this.initConfig(cfg);
        this.callParent(arguments);
    }
});

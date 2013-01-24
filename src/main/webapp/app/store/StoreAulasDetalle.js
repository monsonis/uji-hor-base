Ext.define('HOR.store.StoreAulasDetalle',
{
    extend : 'Extensible.calendar.data.EventStore',
    autoLoad : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/aula/calendario/detalle',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'data'
        },

        extraParams :
        {
            centroId : null,
            semstreId : null,
            edificioId : null,
            tipoAulaId : null,
            plantaId : null,
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

    listeners :
    {
        'write' : function(store, operation)
        {
        }
    },
    initComponent : function(cfg)
    {
        this.initConfig(cfg);
        this.callParent(arguments);
    }
});
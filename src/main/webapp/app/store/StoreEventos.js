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
        
        extraParams  :
        {
            estudioId : 1,
            cursoId : null,
            semestreId : null,
            grupoId : null
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
            var title = Ext.value(operation.records[0].data[Extensible.calendar.data.EventMappings.Title.name], '(No title)');
            switch (operation.action)
            {
            case 'create':
                Extensible.example.msg('Add', 'Added "' + title + '"');
                break;
            case 'update':
                Extensible.example.msg('Update', 'Updated "' + title + '"');
                break;
            case 'destroy':
                Extensible.example.msg('Delete', 'Deleted "' + title + '"');
                break;
            }
        }
    },
    initComponent: function(cfg) {
        this.initConfig(cfg);
        this.callParent(arguments); 
    }
});

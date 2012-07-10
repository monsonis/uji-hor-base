Ext.define('HOR.store.StoreEventos',
{
	extend: 'Extensible.calendar.data.EventStore',
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

        writer :
        {
            type : 'json',
            nameProperty : 'mapping'
        },
        
//        extraParams: {
//            estudioId: 210,
//            cursoId: 1,
//            semestreId: 1,
//            grupoId: 'A'
//        },
//
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
    }
});

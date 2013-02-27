Ext.define('HOR.store.StoreCircuitos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Circuito',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/circuito',
        
        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },
        
        writer :
        {
            type : 'json'
        }
    }
});
Ext.define('HOR.store.StoreAulasAsignadas',
{
    extend : 'Ext.data.Store',
    autoLoad : false,
    autoSync : false,
    model : 'HOR.model.AulaPlanificacion',
    sorters : [
    {
        property : 'nombre',
        direction : 'ASC'
    } ],
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/aula/estudio/',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        writer :
        {
            type : 'json'
        },
        listeners :
        {
            exception : function(proxy, response, operation)
            {
                var myResponseJSON = JSON.parse(response.responseText);
                Ext.Msg.alert('Error', myResponseJSON['msg']);
            }
        }
    }
});
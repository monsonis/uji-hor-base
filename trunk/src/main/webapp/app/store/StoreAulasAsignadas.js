Ext.define('HOR.store.StoreAulasAsignadas',
{
    extend : 'Ext.data.Store',
    autoLoad : false,
    autoSync : false,
    model : 'HOR.model.AulaPlanificacion',
    sorters: [{
        property: 'nombre',
        direction: 'ASC'
    }],
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
                var msgList = response.responseXML.getElementsByTagName("msg");

                if (msgList && msgList[0] && msgList[0].firstChild)
                {
                    Ext.Msg.alert("Error", msgList[0].firstChild.nodeValue);
                }
            }
        }
    }
});
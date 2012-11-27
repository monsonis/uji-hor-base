Ext.define('HOR.store.StoreAulasAsignadas',
{
    extend : 'Ext.data.Store',
    autoLoad : false,
    autoSync : true,
    model : 'HOR.model.AulaPlanificacion',
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
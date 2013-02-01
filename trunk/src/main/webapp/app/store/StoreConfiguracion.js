Ext.define('HOR.store.StoreConfiguracion',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Configuracion',

    autoLoad : false,
    autoSync : false,

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/calendario/config',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        writer :
        {
            type : 'json',
            successProperty : 'success'
        },
        listeners :
        {
            exception : function(proxy, response, operation)
            {

                if (response.responseXML)
                {
                    var msgList = response.responseXML.getElementsByTagName("msg");

                    if (msgList && msgList[0] && msgList[0].firstChild)
                    {
                        Ext.MessageBox.show({
                            title: 'Server error',
                            msg: msgList[0].firstChild.nodeValue,
                            icon: Ext.MessageBox.ERROR,
                            buttons: Ext.Msg.OK
                        });
                    }
                }
            }
        }
    }
});
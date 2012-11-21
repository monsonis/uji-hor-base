Ext.define('HOR.store.StoreAulasAsignadas',
{
    extend: 'Ext.data.Store',
    model: 'HOR.model.Aula',
    autoLoad : false,
    expanded: true,
    proxy :
    {
        type : 'ajax',
        url : '/hor/rest/aula/tree',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'row'
        }
    }
});
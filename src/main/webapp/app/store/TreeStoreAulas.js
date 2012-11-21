Ext.define('HOR.store.TreeStoreAulas',
{
    extend: 'Ext.data.TreeStore',
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
Ext.define('HOR.store.StoreTreePanelAulas',
{
    extend: 'Ext.data.TreeStore',
    model: 'TreeItem',
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
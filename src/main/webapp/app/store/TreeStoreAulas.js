Ext.define('HOR.store.TreeStoreAulas',
{
    extend: 'Ext.data.TreeStore',
    autoLoad : false,
    expanded: true,
    sorters: [{property:'text', direction: 'ASC'}],
    folderSort : true,
    
    proxy :
    {
        type : 'ajax',
        url : '/hor/rest/centro/tree',
        noCache : false,

        reader :
        {
            type : 'json',
            root : 'row'
        }
    }
});
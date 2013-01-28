Ext.define('HOR.view.permisos.GridPermisos',
{
    extend : 'Ext.grid.Panel',
    requires : [],
    store : 'StorePermisos',
    alias : 'widget.gridPermisos',

    disableSelection : true,
    sortableColumns : false,

    columns : [
    {
        text : 'id',
        hidden: true,
        dataIndex : 'id',
        menuDisabled : true,
        flex : 1
    },
    {
        text : 'Persona',
        dataIndex : 'persona',
        menuDisabled : true,
        flex : 1
    },
    {
        text : 'Titulació',
        dataIndex : 'estudio',
        menuDisabled : true,
        flex : 1
    },
    {
        text : 'Tipos càrrec',
        dataIndex : 'tipoCargo',
        menuDisabled : true,
        flex: 1
    } ]

});

Ext.define('HOR.view.aulas.GridAulas',
{
    extend : 'Ext.grid.Panel',
    title: 'Aules asignades',
    alias : 'widget.gridAulas',
    store : 'StoreAulasAsignadas',
    disableSelection : false,
    sortableColumns : false,
    tbar: [ {
        xtype: 'button',
        name: 'borrar',
        text: 'Esborrar aula',
        iconCls : 'application-add'
    }],
    columns : [
    {
        text : 'Aula',
        dataIndex : 'nombre',
        width: 250
    }]
});
Ext.define('HOR.view.aulas.GridAulas',
{
    extend : 'Ext.grid.Panel',
    title: 'Aules asignades',
    alias : 'widget.gridAulas',
    tbar: [ {
        xtype: 'button',
        text: 'Esborrar aula',
        iconCls : 'application-add'
    }],

    columns : [
    {
        text : 'Aula',
        dataIndex : 'name'
    },
    {
        text : 'Centro',
        dataIndex : 'centro',
        flex : 1
    } ]
});
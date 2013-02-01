Ext.define('HOR.view.aulas.GridAulas',
{
    extend : 'Ext.grid.Panel',
    title : 'Aules asignades',
    alias : 'widget.gridAulas',
    store : 'StoreAulasAsignadas',
    disableSelection : false,
    selModel: {
        mode: 'MULTI'
    },
    sortableColumns : true,
    tbar: [ {
        xtype: 'button',
        name: 'borrar',
        text: 'Esborrar aula',
        iconCls : 'application-delete'
    } ],
    columns : [
    {
        text : 'Aula',
        dataIndex : 'nombre',
        width : '40%',
    },
    {
        text : 'Edificio',
        dataIndex : 'edificio',
        width : '15%'
    },
    {
        text : 'Tipo',
        dataIndex : 'tipo',
        width : '15%'
    },
    {
        text : 'Planta',
        dataIndex : 'planta',
        width : '15%'
    },
    {
        text : 'Semestre',
        dataIndex : 'semestreId',
        width : '15%'
    } ]
});
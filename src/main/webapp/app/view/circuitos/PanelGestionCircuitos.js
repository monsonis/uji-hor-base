Ext.define('HOR.view.circuitos.PanelGestionCircuitos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.panelGestionCircuitos',
    border: 0,
    autoScroll : true,
    padding : 5,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        name : 'nuevo-circuito',
        xtype : 'button',
        margin : '0 0 5 0',
        width : '40',
        flex : 0,
        text : 'Crear circuit',
        iconCls : 'application-add'
    },
    {
        name : 'editar-circuito',
        xtype : 'button',
        margin : '0 0 5 0',
        width : '40',
        flex : 0,
        text : 'Editar circuit',
        iconCls : 'application-edit'
    },
    {
        name : 'eliminar-circuito',
        xtype : 'button',
        margin : '0 0 5 0',
        width : '40',
        flex : 0,
        text : 'Esborrar circuit',
        iconCls : 'application-delete'
    } ]
});
Ext.define('HOR.view.permisos.PanelPermisos',
{
    extend : 'Ext.panel.Panel',
    title : 'Gestió de Permisos',
    requires : [ 'HOR.view.permisos.GridPermisos', 'HOR.view.permisos.VentanaNewPermiso' ],
    alias : 'widget.panelPermisos',
    closable : true,
    autoScroll : true,

    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 10
    },

    tbar : [
    {
        xtype : 'button',
        text : 'Afegir',
        action : 'add-permiso'
    },
    {
        xtype : 'button',
        text : 'Esborrar',
        action : 'borrar-permiso'
    } ],

    items : [
    {
        xtype : 'gridPermisos'
    } ]

});
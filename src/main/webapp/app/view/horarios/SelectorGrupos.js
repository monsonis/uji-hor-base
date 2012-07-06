Ext.define('HOR.view.horarios.SelectorGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorGrupos',
    title : 'Grups sense asignar',
    padding : 5,
    flex : 1,
    autoScroll : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        xtype : 'button',
        text : 'Cargar grupos',
        action : 'cargar'
    } ]
});
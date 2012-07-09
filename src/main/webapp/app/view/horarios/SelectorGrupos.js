Ext.define('HOR.view.horarios.SelectorGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorGrupos',
    title : 'Grups sense asignar',
    autoScroll : true,
    padding : 5,
    height : 30,
    flex : 1,
    autoScroll : true,
    layout : 'fit',
    items : [
    {
        xtype : 'panel',
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
    } ]
});
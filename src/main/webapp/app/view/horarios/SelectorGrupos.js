Ext.define('HOR.view.horarios.SelectorGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorGrupos',
    title : 'Grups sense asignar',
    padding : 5,
    flex : 1,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        xtype : 'button',
        text : 'AE1012 - TE1',
        padding : 5,
        margin : '25 30 0 30'
    },
    {
        xtype : 'button',
        text : 'AE1012 - TE2',
        padding : 5,
        margin : '5 30 0 30'
    },
    {
        xtype : 'button',
        text : 'AE1012 - PR1',
        padding : 5,
        margin : '5 30 0 30'
    },
    {
        xtype : 'button',
        text : 'AE1012 - PR2',
        padding : 5,
        margin : '5 30 0 30'
    },
    {
        xtype : 'button',
        text : 'AE1012 - SE2',
        padding : 5,
        margin : '5 30 0 30'
    }, ]
});
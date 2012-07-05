Ext.define('HOR.view.horarios.SelectorCalendarios',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCalendarios',
    title : 'Calendaris',
    padding : 5,
    height: 230,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        xtype : 'checkboxgroup',
        columns : 1,
        vertical : true,
        items : [
        {
            xtype : 'checkbox',
            boxLabel : 'Teoria',
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Problemes',
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Laboratoris',
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Seminaris',
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Tutories',
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Avaluació',
            padding: '10 10 0 10'
        }, ]
    } ]

});
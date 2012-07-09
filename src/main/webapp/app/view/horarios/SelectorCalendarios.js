Ext.define('HOR.view.horarios.SelectorCalendarios',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCalendarios',
    title : 'Calendaris',
    padding : 5,
    height: 250,
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
            checked: true,
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Problemes',
            checked: true,
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Laboratoris',
            checked: true,
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Seminaris',
            checked: true,
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Tutories',
            checked: true,
            padding: '10 10 0 10'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Avaluació',
            checked: true,
            padding: '10 10 0 10'
        }, ]
    } ]

});
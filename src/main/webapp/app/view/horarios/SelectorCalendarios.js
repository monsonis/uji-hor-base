Ext.define('HOR.view.horarios.SelectorCalendarios',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCalendarios',
    title : 'Calendaris',
    padding : 5,
    flex : 1,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        xtype : 'checkboxgroup',
        columns : 1,
        padding : 10,
        vertical : true,
        items : [ {
            xtype: 'checkbox',
            boxLabel: 'Calendario 1'
        } ]
    } ]

});
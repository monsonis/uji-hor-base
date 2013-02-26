Ext.define('HOR.view.circuitos.PanelCircuitos',
{
    extend : 'Ext.panel.Panel',
    title : 'Ocupació d\'aules',
    requires : [ 'HOR.view.circuitos.FiltroCircuitos', 'HOR.view.circuitos.PanelGestionCircuitos', 'HOR.view.circuitos.SelectorCircuitos', 'HOR.view.circuitos.SelectorCalendariosCircuitos' ],
    alias : 'widget.panelCircuitos',
    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    items : [
    {
        xtype : 'filtroCircuitos',
        height : 60
     },
    {
        xtype : 'panel',
        flex : 1,
        border : 0,
        layout :
        {
            type : 'hbox',
            align : 'stretch'
        },
        items : [
        {
            width : 150,
            border : 0,
            layout :
            {
                type : 'vbox',
                align : 'stretch'
            },
            items : [
            {
                xtype : 'panelGestionCircuitos'
            },
            {
                xtype : 'selectorCircuitos'
            },
            {
                xtype: 'selectorCalendariosCircuitos'
            }]
//        },
//        {
//            xtype : 'panelCalendario',
//            flex : 1
        } ]
    } ]

});
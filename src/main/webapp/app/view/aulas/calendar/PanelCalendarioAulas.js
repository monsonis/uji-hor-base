Ext.define('HOR.view.aulas.calendar.PanelCalendarioAulas',
{
    extend : 'Ext.panel.Panel',
    title : 'Ocupació d\'aules',
    requires : [ 'HOR.view.aulas.calendar.FiltroAulas', 'HOR.view.aulas.calendar.SelectorAulas', 'HOR.view.horarios.SelectorCalendarios', 'HOR.view.aulas.calendar.PanelCalendarioPorAula'],
    alias : 'widget.panelCalendarioAulas',
    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    items : [
    {
        xtype : 'filtroAulas',
        height : 60,
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
                xtype : 'selectorAulas'
            },
            {
                xtype : 'selectorCalendarios'
            } ]
        },
        {
            xtype : 'panelCalendarioPorAula',
            flex : 1
        } ]
    }]

});
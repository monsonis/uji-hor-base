Ext.define('HOR.view.horarios.PanelHorarios',
{
    extend : 'Ext.panel.Panel',
    title : 'Gestió d\'horaris',
    requires : [ 'HOR.view.horarios.FiltroGrupos', 'HOR.view.horarios.PanelCalendario', 'HOR.view.horarios.PanelCalendarioDetalle', 'HOR.view.horarios.SelectorGrupos', 'HOR.view.horarios.SelectorCalendarios', 'HOR.view.commons.MenuSuperior' ],
    alias : 'widget.panelHorarios',
    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    items : [
    {
        xtype : 'filtroGrupos',
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
                xtype : 'selectorGrupos'
            },
            {
                xtype : 'selectorCalendarios'
            } ]
        },
        {
            xtype : 'panelCalendario',
            flex : 1
        } ]
    } ]
});
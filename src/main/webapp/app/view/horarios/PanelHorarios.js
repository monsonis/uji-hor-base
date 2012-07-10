Ext.define('HOR.view.horarios.PanelHorarios',
{
    extend : 'Ext.panel.Panel',
    title : 'Gestión Horarios',
    requires : [ 'HOR.view.horarios.FiltroGrupos', 'HOR.view.horarios.PanelCalendario', 'HOR.view.horarios.SelectorGrupos', 'HOR.view.horarios.SelectorCalendarios' ],

    closable : true,
    layout :
    {
        type : 'vbox',
        align : 'stretch',
        padding : 5
    },

    initComponent : function()
    {
        this.callParent();
    },

    items : [
    {
        xtype : 'filtroGrupos',
        height : 120
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
            width : 200,
            border : 0,
            layout :
            {
                type : 'vbox',
                align : 'stretch'
            },
            items : [
            {
                xtype : 'selectorGrupos',
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
Ext.define('HOR.view.horarios.PanelHorarios',
{
    extend : 'Ext.panel.Panel',
    title : 'Gestión Horarios',
    requires : [ 'HOR.view.horarios.FiltroGrupos', 'HOR.view.horarios.PanelCalendario' ],

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
        flex : 1
    },
    {
        xtype: 'panelCalendario',
        flex: 1
    }]

});
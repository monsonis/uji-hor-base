Ext.define('HOR.view.aulas.calendar.PanelCalendarioAulas',
{
    extend : 'Ext.panel.Panel',
    title : 'Ocupació d\'aules',
    requires : [ 'HOR.view.aulas.calendar.FiltroAulas'],
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
    }]

});
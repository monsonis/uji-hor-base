Ext.define('HOR.view.aulas.PanelAulas',
{
    extend : 'Ext.panel.Panel',
    title : 'Assignació d\'aules a estudis',
    requires : [ 'HOR.view.aulas.FiltroAsignacionAulas', 'HOR.view.aulas.TreePanelAulas', 'HOR.view.aulas.GridAulas' ],
    alias : 'widget.panelAulas',
    closable : true,
    layout :
    {
        type : 'vbox',
        padding : 10
    },

    items : [
    {
        xtype : 'filtroAsignacionAulas',
        width : 800
    },
    {
        xtype : 'panel',
        layout : 'hbox',
        items : [
        {
            xtype : 'treePanelAulas',
            disabled: true,
            padding: '10',
            width: 400,
            height: 400
        },
        {
            xtype : 'gridAulas',
            padding: '10',
            disabled: true,
            width: 400,
            height: 400
        } ]
    } ]

});
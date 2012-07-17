Ext.define('HOR.view.configuracion.PanelConfiguracion',
{
    extend : 'Ext.panel.Panel',
    title : 'Configuración',
    alias : 'widget.panelConfiguracion',
    requires : [ 'HOR.view.commons.FiltroGrupos', 'HOR.view.configuracion.ConfiguracionHorario' ],

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
        height : 120
    },
    {
        xtype : 'configuracionHorario'
    } ]

});
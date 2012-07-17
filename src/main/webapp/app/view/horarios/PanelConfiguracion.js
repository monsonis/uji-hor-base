Ext.define('HOR.view.horarios.PanelConfiguracion',
{
    extend : 'Ext.panel.Panel',
    title : 'Configuración',
    alias : 'widget.panelConfiguracion',
    requires : [ 'HOR.view.horarios.FiltroGrupos', 'HOR.view.horarios.ConfiguracionHorario' ],

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
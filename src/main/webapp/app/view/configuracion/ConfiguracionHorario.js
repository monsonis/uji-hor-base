Ext.define('HOR.view.configuracion.ConfiguracionHorario',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.configuracionHorario',
    requires : [ 'HOR.view.configuracion.SelectorHoras' ],

    border : false,
    layout : 'anchor',

    items : [
    {
        xtype : 'selectorHoras',
        anchor: '50%',
            
    }, {
        xtype: 'button',
        text: 'Guardar'
    } ]
});

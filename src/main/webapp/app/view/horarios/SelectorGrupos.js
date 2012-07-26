Ext.define('HOR.view.horarios.SelectorGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorGrupos',
    title : 'Grups sense asignar',
    autoScroll : true,
    padding : 5,
    flex : 1,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    
    limpiaGrupos: function() {
        this.removeAll();
    }
});

Ext.define('HOR.view.aulas.TreePanelAulas',
{
    extend : 'Ext.tree.Panel',
    title: 'Aules disponibles',
    alias : 'widget.treePanelAulas',
    rootVisible : false,
    store : 'StoreTreePanelAulas',
    tbar: [ {
        xtype: 'button',
        text: 'Afegir aula',
        iconCls : 'application-add'
    }]

});

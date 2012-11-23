Ext.define('HOR.view.aulas.TreePanelAulas',
{
    extend : 'Ext.tree.Panel',
    title: 'Aules disponibles',
    alias : 'widget.treePanelAulas',
    rootVisible : false,
    store : 'TreeStoreAulas',
    tbar: [ {
        xtype: 'button',
        text: 'Afegir aula',
        name: 'anyadir',
        iconCls : 'application-add'
    }]

});
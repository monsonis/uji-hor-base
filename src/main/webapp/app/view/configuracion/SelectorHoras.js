Ext.define('HOR.view.configuracion.SelectorHoras',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorHoras',
    title : 'Hores del calendari',
    padding : 5,

    layout :
    {
        type : 'hbox',
        align : 'stretch'
    },

    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Hora inici',
        store : 'StoreHoras',
        queryModel : 'local',
        editable : false,
        displayField : 'name',
        valueField : 'id',
        padding : '10 5 10 5',
        flex : 1,
        name : 'horaInicio'
    },
    {
        xtype : 'combobox',
        fieldLabel : 'Hora fi',
        store : 'StoreHoras',
        queryModel : 'local',
        editable : false,
        displayField : 'name',
        valueField : 'id',
        padding : '10 5 10 30',
        flex : 1,
        name : 'horaFin'
    } ],

});
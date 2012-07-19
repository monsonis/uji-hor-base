Ext.define('HOR.view.configuracion.SelectorHoras',
{
    extend : 'Ext.form.FieldSet',
    alias : 'widget.selectorHoras',
    title : 'Hores del calendari',
    padding : 5,

    width: 500,
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
        name : 'horaInicio',
        flex : 2
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
        name : 'horaFin',
        flex : 2
    } ],

});
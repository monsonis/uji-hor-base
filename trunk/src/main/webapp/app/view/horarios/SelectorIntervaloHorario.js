Ext.define('HOR.view.horarios.SelectorIntervaloHorario',
{
    extend : 'Ext.window.Window',
    title : 'Interval horari',
    alias : 'widget.selectorIntervaloHorario',
    padding : 5,
    closable : true,
    layout : 'fit',
    width : 500,
    modal : true,
    height : 180,
    closeAction: 'destroy',
    
    items : [
    {
        xtype : 'fieldset',
        padding : 10,
        margin : 10,
        title : 'Sel·lecciona l\'interval horari',
        layout : 'hbox',
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
            width : 200,
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
            width : 200,
        } ]
    } ],

    buttons : [
    {
        text : 'Guardar',
        action : 'save',
    },
    {
        text: 'Cancelar',
        action: 'cancel'
    }]
});